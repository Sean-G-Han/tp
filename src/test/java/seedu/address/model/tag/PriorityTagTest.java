package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PriorityTagTest {

    @Test
    void testDefaultConstructor() {
        // Test the default constructor
        PriorityTag priorityTag = new PriorityTag();
        assertEquals("Priority", priorityTag.tagName);
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> PriorityTag.isValidTagName(null));
    }

}
