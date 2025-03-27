package seedu.address.model.client;

import java.util.List;

import seedu.address.commons.util.StringUtil;
/**
 * Tests that a {@code Client}'s {@code Name} or {@code Tag} matches any of the keywords given.
 * This predicate is equilivant to the OR operations when using find, i.e. loose matching
 */
public class ContainsKeywordsPredicate extends AbstractContainsKeywordsPredicate {

    public ContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    @Override
    public boolean test(Client client) {
        return keywords.stream()
                .anyMatch(keyword -> isKeywordMatch(client, keyword));
    }

    private boolean isKeywordMatch(Client client, String keyword) {
        boolean nameMatch = StringUtil.containsWordIgnoreCase(client.getName().fullName, keyword);
        boolean tagMatch = client.getTags().stream()
                .anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword));
        return nameMatch || tagMatch;
    }
}
