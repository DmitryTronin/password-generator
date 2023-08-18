package password.generator;

import java.util.InputMismatchException;
import java.util.Scanner;


public class Generator {
    Alphabet alphabet;
    public static Scanner keyboard;

    public Generator(Scanner scanner) {
        keyboard = scanner;
    }

    public Generator(boolean IncludeUpper, boolean IncludeLower, boolean IncludeNum, boolean IncludeSym) {
        alphabet = new Alphabet(IncludeUpper, IncludeLower, IncludeNum, IncludeSym);
    }

    public void mainLoop() {
        System.out.println("Welcome to Password Services");
        printMenu();

        String userOption = "-1";
        keyboard = new Scanner(System.in);
        while (!userOption.equals("4")) {
            try {
                userOption = keyboard.nextLine().trim();
            } catch (Exception e) {
                System.err.println("Sorry, can't parse input. Select one of the available commands.");
                printMenu();
                if (keyboard.hasNext()) {
                    keyboard.nextLine(); //consume the wrong input
                }
                continue;
            }

            switch (userOption) {
                case "1" -> {
                    requestPassword();
                }
                case "2" -> {
                    checkPassword();
                }
                case "3" -> {
                    printUsefulInfo();
                }
                case "4" -> printQuitMessage();

                default -> {
                    System.out.println();
                    System.out.println("Please select one of the available commands");
                    printMenu();
                }
            }
        }
    }

    protected Password GeneratePassword(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Password length must be greater than 0");
        }
        final StringBuilder pass = new StringBuilder();
        final int alphabetLength = alphabet.getAlphabet().length();

        int max = alphabetLength - 1;
        int min = 0;
        int range = max - min + 1;

        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * range) + min;
            pass.append(alphabet.getAlphabet().charAt(index));
        }

        if (pass.length() != length) {
            throw new RuntimeException("Generated password length does not match the expected length.");
        }

        return new Password(pass.toString());
    }


    private void printUsefulInfo() {
        String usefulInfo = """

                Use a minimum password length of 8 or more characters if permitted.
                Include lowercase and uppercase alphabetic characters, numbers and symbols if permitted.
                Generate passwords randomly where feasible.
                Avoid using the same password twice (e.g., across multiple user accounts and/or software systems).
                Avoid character repetition, keyboard patterns, dictionary words, letter or number sequences,
                usernames, relative or pet names, romantic links (current or past) and biographical information (e.g., ID numbers, ancestors' names or dates).
                Avoid using information that the user's colleagues and/or acquaintances might know to be associated with the user.
                Do not use passwords which consist wholly of any simple combination of the aforementioned weak components.
                """;

        System.out.println(usefulInfo);
        printMenu();
    }


    private void requestPassword() {
        boolean IncludeUpper = false;
        boolean IncludeLower = false;
        boolean IncludeNum = false;
        boolean IncludeSym = false;

        boolean correctParams;

        System.out.println();
        System.out.println("Password Generator. Please answer the following questions by Y(es) or N(o) \n");

        do {
            String input;
            correctParams = false;

            do {
                System.out.println("\nDo you want Lowercase letters \"abcd...\" to be used? ");
                input = keyboard.next();
                System.out.println("Your said: " + input); // Printing user's input
                PasswordRequestError(input);
            } while (!input.equalsIgnoreCase("yes") && !input.equalsIgnoreCase("no") && !input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n"));

            if (isInclude(input)) IncludeLower = true;

            do {
                System.out.println("\nDo you want Uppercase letters \"ABCD...\" to be used? ");
                input = keyboard.next();
                System.out.println("Your said: " + input); // Printing user's input
                PasswordRequestError(input);
            } while (!input.equalsIgnoreCase("yes") && !input.equalsIgnoreCase("no") && !input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n"));

            if (isInclude(input)) IncludeUpper = true;

            do {
                System.out.println("\nDo you want Numbers \"1234...\" to be used? ");
                input = keyboard.next();
                System.out.println("Your said: " + input); // Printing user's input
                PasswordRequestError(input);
            } while (!input.equalsIgnoreCase("yes") && !input.equalsIgnoreCase("no") && !input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n"));

            if (isInclude(input)) IncludeNum = true;

            do {
                System.out.println("\nDo you want Symbols \"!@#$...\" to be used? ");
                input = keyboard.next();
                System.out.println("Your said: " + input); // Printing user's input
                PasswordRequestError(input);
            } while (!input.equalsIgnoreCase("yes") && !input.equalsIgnoreCase("no") && !input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n"));

            if (isInclude(input)) IncludeSym = true;

            //No Pool Selected
            if (!IncludeUpper && !IncludeLower && !IncludeNum && !IncludeSym) {
                System.out.println("You have selected no characters to generate your " +
                        "password, at least one of your answers should be Yes\n");
                correctParams = true;
            }

        } while (correctParams);

        System.out.println("Great! Now enter the length of the password");
        int length = 0;
        while (true) {
            try {
                length = keyboard.nextInt();
                break;
            } catch (InputMismatchException e) {
                keyboard.nextLine();  // to consume the incorrect token
                System.out.println("Invalid input. Please enter an integer for the password length.");
            }
        }
        keyboard.nextLine();

        final Generator generator = new Generator(IncludeUpper, IncludeLower, IncludeNum, IncludeSym);
        final Password password = generator.GeneratePassword(length);

        System.out.println("Your generated password: " + password);
    }

    private boolean isInclude(String Input) {
        return Input.equalsIgnoreCase("yes") || Input.equalsIgnoreCase("y");
    }

    private void PasswordRequestError(String i) {
        if (!i.equalsIgnoreCase("yes") && !i.equalsIgnoreCase("no") && !i.equalsIgnoreCase("y") && !i.equalsIgnoreCase("n")) {
            System.out.println("You have entered something incorrect let's go over it again \n");
        }
    }

    private void checkPassword() {
        String input;

        System.out.print("\nEnter your password:");
        input = keyboard.next();

        final Password p = new Password(input);

        System.out.println(p.calculateScore());
    }

    public void printMenu() {
        System.out.println("\nEnter 1 - Password Generator");
        System.out.println("Enter 2 - Password Strength Check");
        System.out.println("Enter 3 - Show Information");
        System.out.println("Enter 4 - Quit");
    }

    private void printQuitMessage() {
        System.out.println("Bye!");
    }
}
