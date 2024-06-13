package password.generator;

import java.util.Scanner;

public class Main {

    /**
     *
     */
    public static final Scanner keyboard = new Scanner(System.in);

    /**
     * The main method is the entry point of the application. It instantiates a new object of the Generator class,
     * passes a Scanner instance, keyboard, to it for user input. Afterwards, it enters the main loop of the
     * Generator instance, where the application's main operations are performed.
     *
     * @param args Command-line arguments that are passed to the app when it's launched. Currently not used.
     */

    public static void main(String[] args) {
        Generator generator = new Generator(keyboard);
        generator.mainLoop();
        keyboard.close();
    }
}
