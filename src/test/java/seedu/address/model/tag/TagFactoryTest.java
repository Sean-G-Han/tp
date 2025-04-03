package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TagFactoryTest {

    @Test
    public void create_priorityTag() {
        Tag tag = TagFactory.createTag("Priority");
        assertTrue(tag instanceof PriorityTag);
        assertEquals("Priority", tag.tagName);
    }

    @Test
    public void create_tag() {
        Tag tag = TagFactory.createTag("abcdefghijklmnop");
        assertFalse(tag instanceof PriorityTag);
        assertTrue(tag instanceof Tag);
        assertEquals("Abcdefghijklmnop", tag.tagName);
    }

    @Test
    public void create_nonPriorityTag() {
        Tag tag = TagFactory.createTag("NonPriority");
        assertFalse(tag instanceof PriorityTag);
        assertEquals("Nonpriority", tag.tagName);
    }
}
