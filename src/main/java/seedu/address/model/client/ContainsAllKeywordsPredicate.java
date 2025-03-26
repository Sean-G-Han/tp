package seedu.address.model.client;

import java.util.List;

import seedu.address.commons.util.StringUtil;
/**
 * Tests that a {@code Client}'s {@code Name} or {@code Tag} contains all the keywords given.
 * This predicate is equivalent to the AND operations when using find, i.e. strict matching
 */
public class ContainsAllKeywordsPredicate extends AbstractContainsKeywordsPredicate {

    public ContainsAllKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    @Override
    public boolean test(Client client) {
        if (keywords.isEmpty()) {
            return false; // Ensures empty keyword list returns false like to ContainsKeywordsPredicate
        }

        return keywords.stream()
                .allMatch(keyword -> isKeywordMatch(client, keyword));
    }

    private boolean isKeywordMatch(Client client, String keyword) {
        boolean nameMatch = StringUtil.containsWordIgnoreCase(client.getName().fullName, keyword);
        boolean tagMatch = client.getTags().stream()
                .anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword));
        return nameMatch || tagMatch;
    }
}
