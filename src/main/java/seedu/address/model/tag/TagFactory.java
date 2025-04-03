package seedu.address.model.tag;

/**
 * A factory class for creating Tag objects based on a given tag name.
 * Special tag names are mapped to their respective subclasses.
 */
public class TagFactory {
    /**
     * Creates a Tag instance based on the given tag names
     * @param tagName The name of the tag.
     * @return A {@code Tag} instance, which may be a specialized subclass.
     */
    public static Tag createTag(String tagName) {
        // Add more for other types
        if (tagName.toLowerCase().replaceAll("\\s+", "").equals("priority")) {
            return new PriorityTag();
        }
        return new Tag(tagName);
    }
}
