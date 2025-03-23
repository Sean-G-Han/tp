package seedu.address.model.client;

import java.util.List;

import seedu.address.commons.util.StringUtil;
/**
 * Tests that a {@code Client}'s {@code Name} or {@code Tag} contains all the keywords given.
 * This predicate is equilivant to the AND operations when using find, i.e. strict matching
 */
public class ContainsAllKeywordsPredicate extends AbstractContainsKeywordsPredicate {

    public ContainsAllKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    @Override
    public boolean test(Client client) {
        return keywords.stream().allMatch(keyword ->
                StringUtil.containsWordIgnoreCase(client.getName().fullName, keyword) ||
                        client.getTags().stream().anyMatch(tag ->
                                StringUtil.containsWordIgnoreCase(tag.tagName, keyword)
                        )
        );
    }
}
