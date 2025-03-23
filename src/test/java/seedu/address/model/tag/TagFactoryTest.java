package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PRIORITY;

import org.junit.jupiter.api.Test;

public class TagFactoryTest {

    @Test
    public void create_priority_tag() {
        Tag tag = TagFactory.createTag(VALID_TAG_PRIORITY);
        assertTrue(tag instanceof PriorityTag);
    }

    @Test
    public void create_tag() {
        Tag tag = TagFactory.createTag("abcdefghijklmnop");
        assertFalse(tag instanceof PriorityTag);
    }
}
