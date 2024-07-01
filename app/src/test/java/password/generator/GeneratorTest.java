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
        // Cleanup code if needed
    }

    @Test
    @Disabled
    @DisplayName("GeneratePassword should generate a password of the correct length")
    public void testGeneratePassword_length() {
        generator = new Generator(true, true, true, true);
        Password password = generator.GeneratePassword(10);
        assertEquals(10, password.toString().length());
    }

    @Test
    @Disabled
    @DisplayName("GeneratePassword should throw an exception for non-positive length")
    public void testGeneratePassword_invalidLength() {
        generator = new Generator(true, true, true, true);
        assertThrows(IllegalArgumentException.class, () -> generator.GeneratePassword(0));
    }

    @Test
    @Disabled
    @DisplayName("Main loop should print menu and handle correct input")
    public void testMainLoop() {
        Mockito.when(scannerMock.nextLine()).thenReturn("4");
        generator = new Generator(scannerMock);

        generator.mainLoop();

        Mockito.verify(scannerMock, Mockito.times(1)).nextLine();
        // Additional assertions can be added to check printed output or other side effects
    }
}