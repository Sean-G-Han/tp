package seedu.address.model.tag;

/**
 * Represents the Priority Tag in the address book, a special Yellow Tag
 */
public class PriorityTag extends Tag {
    public PriorityTag(String tagName) {
        super(tagName);
    }

    public PriorityTag() {
        super("Priority");
    }
}
