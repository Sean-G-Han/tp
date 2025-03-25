package seedu.address.model.tag;

/**
 * Represents the Priority Tag in the address book, a special Yellow Tag
 */
public class PriorityTag extends Tag {
    static final String VALID_PRIORITY_TAG = "Priority";

    public PriorityTag() {
        super(VALID_PRIORITY_TAG);
    }
}
