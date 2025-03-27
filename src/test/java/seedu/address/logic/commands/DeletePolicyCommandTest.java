package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLIENT;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeletePolicyCommand}.
 */
public class DeletePolicyCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_deleteExistingPolicyFromClient_success() {
        Client clientToEdit = model.getFilteredClientList().get(INDEX_FIRST_CLIENT.getZeroBased());

        // Get an existing policy from the client
        Set<Tag> existingPolicies = new HashSet<>(clientToEdit.getTags());
        Tag policyToDelete = existingPolicies.iterator().next(); // Assume the client has at least one policy

        Set<Tag> policiesToDelete = new HashSet<>();
        policiesToDelete.add(policyToDelete);

        DeletePolicyCommand deletePolicyCommand = new DeletePolicyCommand(INDEX_FIRST_CLIENT, policiesToDelete);

        Set<Tag> updatedPolicies = new HashSet<>(existingPolicies);
        updatedPolicies.remove(policyToDelete);

        Client expectedClient = new Client(
                clientToEdit.getName(),
                clientToEdit.getPhone(),
                clientToEdit.getEmail(),
                clientToEdit.getAddress(),
                updatedPolicies
        );

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setClient(clientToEdit, expectedClient);

        String expectedMessage = String.format(DeletePolicyCommand.MESSAGE_SUCCESS, Messages.format(expectedClient));

        assertCommandSuccess(deletePolicyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidClientIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClientList().size() + 1);
        Set<Tag> policiesToDelete = new HashSet<>();
        policiesToDelete.add(new Tag("Life Insurance"));

        DeletePolicyCommand deletePolicyCommand = new DeletePolicyCommand(outOfBoundIndex, policiesToDelete);

        assertCommandFailure(deletePolicyCommand, model, DeletePolicyCommand.MESSAGE_CLIENT_NOT_FOUND);
    }

    @Test
    public void execute_deleteNonExistingPolicyFromClient_throwsCommandException() {
        Client clientToEdit = model.getFilteredClientList().get(INDEX_SECOND_CLIENT.getZeroBased());

        Set<Tag> policiesToDelete = new HashSet<>();
        policiesToDelete.add(new Tag("NonExistentPolicy"));

        DeletePolicyCommand deletePolicyCommand = new DeletePolicyCommand(INDEX_SECOND_CLIENT, policiesToDelete);

        assertCommandFailure(deletePolicyCommand, model, DeletePolicyCommand.MESSAGE_POLICY_NOT_FOUND);
    }

    @Test
    public void equals() {
        Set<Tag> policiesA = new HashSet<>();
        policiesA.add(new Tag("Policy A"));
        Set<Tag> policiesB = new HashSet<>();
        policiesB.add(new Tag("Policy B"));

        DeletePolicyCommand deletePolicyFirstCommand = new DeletePolicyCommand(INDEX_FIRST_CLIENT, policiesA);
        DeletePolicyCommand deletePolicySecondCommand = new DeletePolicyCommand(INDEX_SECOND_CLIENT, policiesB);

        // Same object -> returns true
        assertTrue(deletePolicyFirstCommand.equals(deletePolicyFirstCommand));

        // Same values -> returns true
        DeletePolicyCommand deletePolicyFirstCommandCopy = new DeletePolicyCommand(INDEX_FIRST_CLIENT, policiesA);
        assertTrue(deletePolicyFirstCommand.equals(deletePolicyFirstCommandCopy));

        // Different types -> returns false
        assertFalse(deletePolicyFirstCommand.equals(1));

        // Different index -> returns false
        assertFalse(deletePolicyFirstCommand.equals(deletePolicySecondCommand));

        // Different policies -> returns false
        DeletePolicyCommand deletePolicyDifferentPolicyCommand = new DeletePolicyCommand(INDEX_FIRST_CLIENT, policiesB);
        assertFalse(deletePolicyFirstCommand.equals(deletePolicyDifferentPolicyCommand));

        // Null -> returns false
        assertFalse(deletePolicyFirstCommand.equals(null));
    }

    @Test
    public void toStringMethod() {
        Set<Tag> policies = new HashSet<>();
        policies.add(new Tag("Life Insurance"));
        policies.add(new Tag("Health Insurance"));

        DeletePolicyCommand deletePolicyCommand = new DeletePolicyCommand(INDEX_FIRST_CLIENT, policies);

        String expectedString = new ToStringBuilder(deletePolicyCommand)
                .add("clientIndex", INDEX_FIRST_CLIENT)
                .add("policiesToDelete", policies)
                .toString();

        assertEquals(expectedString, deletePolicyCommand.toString());
    }
}

