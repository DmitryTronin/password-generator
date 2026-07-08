package password.generator;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class GeneratorTest {
    private Generator generator;

    @AfterEach
    public void tearDown() {
        generator = null;
    }

    @Test
    @DisplayName("GeneratePassword should generate a password of the correct length")
    public void testGeneratePassword_length() {
        generator = new Generator(true, true, true, true);
        Password password = generator.GeneratePassword(10);
        assertEquals(10, password.toString().length());
    }

    @Test
    @DisplayName("GeneratePassword should throw an exception for non-positive length")
    public void testGeneratePassword_invalidLength() {
        generator = new Generator(true, true, true, true);
        assertThrows(IllegalArgumentException.class, () -> generator.GeneratePassword(0));
    }

    @Test
    @DisplayName("Main loop should retry when password length is not positive")
    public void testMainLoop_retriesNonPositivePasswordLength() {
        Scanner scanner = new Scanner("""
            1
            yes
            yes
            yes
            yes
            0
            8
            4
            """);
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(output));
            generator = new Generator(scanner);

            assertDoesNotThrow(() -> generator.mainLoop());
        } finally {
            System.setOut(originalOut);
            scanner.close();
        }

        String consoleOutput = output.toString(StandardCharsets.UTF_8);
        Assertions.assertTrue(consoleOutput.contains("Invalid input. Password length must be greater than 0."));
        Assertions.assertTrue(consoleOutput.contains("Your generated password: "));
    }
}
