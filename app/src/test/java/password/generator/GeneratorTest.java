package password.generator;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class GeneratorTest {
    private Generator generator;
    private Scanner scannerMock;

    @BeforeEach
    public void setUp() {
        scannerMock = Mockito.mock(Scanner.class);
    }

    @AfterEach
    public void tearDown() {
        generator = null;
        scannerMock = null;
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
    @DisplayName("Main loop should print menu and handle correct input")
    public void testMainLoop() {
        Mockito.when(scannerMock.nextLine())
            .thenReturn("1", "4"); // Simulate valid menu inputs: "1" (Password Generator), then "4" (Exit)
        Mockito.when(scannerMock.next())
            .thenReturn("yes", "yes", "yes", "yes"); // Simulate "yes" for all character pool prompts
        Mockito.when(scannerMock.nextInt())
            .thenReturn(8); // Simulate valid password length input

        generator = new Generator(scannerMock);
        generator.mainLoop();

        Mockito.verify(scannerMock, Mockito.times(3)).nextLine(); // Verify "nextLine" is called for menu inputs
        Mockito.verify(scannerMock, Mockito.atLeast(4)).next(); // Verify "next" is called for character pool prompts
        Mockito.verify(scannerMock, Mockito.times(1)).nextInt(); // Verify "nextInt" is called for password length
    }
}