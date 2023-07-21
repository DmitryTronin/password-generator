package password.generator;


public class Alphabet {

    protected static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    protected static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    protected static final String NUMBERS = "1234567890";
    protected static final String SYMBOLS = "!@#$%^&*()-_=+\\/~?";

	private final StringBuilder pool;


	public Alphabet(boolean uppercaseIncluded, boolean lowercaseIncluded, boolean numbersIncluded, boolean specialCharactersIncluded) {

		pool = new StringBuilder();
		if (uppercaseIncluded) pool.append(UPPERCASE_LETTERS);
		if (lowercaseIncluded) pool.append(LOWERCASE_LETTERS);
		if (numbersIncluded) pool.append(NUMBERS);
		if (specialCharactersIncluded) pool.append(SYMBOLS);
	}

	public String getAlphabet() {
		return pool.toString();
	}
}
