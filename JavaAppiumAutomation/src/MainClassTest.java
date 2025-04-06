import org.junit.Test;
import static org.junit.Assert.*;

public class MainClassTest {

    @Test
    public void testGetLocalNumber() {
        MainClass obj = new MainClass();
        int local = obj.getLocalNumber();
        assertEquals("getLocalNumber should return 14", 14, local);
    }

    @Test
    public void testGetClassNumber() {
        MainClass instance = new MainClass();
        int value = instance.getClassNumber();

        assertTrue("Expected value above 45, but got: " + value, value > 45);
    }

    @Test
    public void testGetClassString() {
        MainClass main = new MainClass();
        String text = main.getClassString();

        if (!text.contains("hello") && !text.contains("Hello")) {
            fail("Returned string doesn't contain required substring. Got: " + text);
        }
    }
}
