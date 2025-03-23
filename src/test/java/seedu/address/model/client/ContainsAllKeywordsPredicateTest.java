package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ClientBuilder;

public class ContainsAllKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ContainsAllKeywordsPredicate firstPredicate = new ContainsAllKeywordsPredicate(firstPredicateKeywordList);
        ContainsAllKeywordsPredicate secondPredicate = new ContainsAllKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ContainsAllKeywordsPredicate firstPredicateCopy = new ContainsAllKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different client -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_containsKeywords_returnsTrue() {
        // One keyword
        ContainsAllKeywordsPredicate predicate = new ContainsAllKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new ClientBuilder().withName("Alice Bob").build()));
        predicate = new ContainsAllKeywordsPredicate(Collections.singletonList("Tag"));
        assertTrue(predicate.test(new ClientBuilder().withTags("Alice", "Tag").build()));

        // Multiple keywords
        predicate = new ContainsAllKeywordsPredicate(Arrays.asList("Alice", "Bob", "Tag"));
        assertTrue(predicate.test(new ClientBuilder().withName("Alice Bob").withTags("Tag").build()));

        // Mixed-case keywords
        predicate = new ContainsAllKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new ClientBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void doesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ContainsAllKeywordsPredicate predicate = new ContainsAllKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new ContainsAllKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new ClientBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new ContainsAllKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertFalse(predicate.test(new ClientBuilder().withName("Alice Carol").build()));
        predicate = new ContainsAllKeywordsPredicate(Arrays.asList("Bob", "Tag"));
        assertFalse(predicate.test(new ClientBuilder().withName("Alice Carol").withTags("Tag").build()));

        // Keywords match phone, email and address, but does not match name or tag
        predicate = new ContainsAllKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        ContainsAllKeywordsPredicate predicate = new ContainsAllKeywordsPredicate(keywords);

        String expected = ContainsAllKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
