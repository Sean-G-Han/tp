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
 * Contains integration tests (interaction with the Model) and unit tests for {@code AddPolicyCommand}.
 */
public class AddPolicyCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_addPolicyToExistingClient_success() {
        Client clientToEdit = model.getFilteredClientList().get(INDEX_FIRST_CLIENT.getZeroBased());

        // Creating new policies to add
        Set<Tag> policiesToAdd = new HashSet<>();
        policiesToAdd.add(new Tag("Life Insurance"));
        policiesToAdd.add(new Tag("Health Insurance"));

        AddPolicyCommand addPolicyCommand = new AddPolicyCommand(INDEX_FIRST_CLIENT, policiesToAdd);

        Set<Tag> updatedPolicies = new HashSet<>(clientToEdit.getTags());
        updatedPolicies.addAll(policiesToAdd);

        Client expectedClient = new Client(
                clientToEdit.getName(),
                clientToEdit.getPhone(),
                clientToEdit.getEmail(),
                clientToEdit.getAddress(),
                updatedPolicies
        );

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setClient(clientToEdit, expectedClient);

        String expectedMessage = String.format(AddPolicyCommand.MESSAGE_SUCCESS, Messages.format(expectedClient));

        assertCommandSuccess(addPolicyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidClientIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClientList().size() + 1);
        Set<Tag> policiesToAdd = new HashSet<>();
        policiesToAdd.add(new Tag("Life Insurance"));

        AddPolicyCommand addPolicyCommand = new AddPolicyCommand(outOfBoundIndex, policiesToAdd);

        assertCommandFailure(addPolicyCommand, model, AddPolicyCommand.MESSAGE_CLIENT_NOT_FOUND);
    }

    @Test
    public void execute_addExistingPolicyToClient_success() {
        Client clientToEdit = model.getFilteredClientList().get(INDEX_SECOND_CLIENT.getZeroBased());

        // Get an existing policy from the client
        Set<Tag> existingPolicies = new HashSet<>(clientToEdit.getTags());
        Tag existingPolicy = existingPolicies.iterator().next(); // Assume the client has at least one policy

        Set<Tag> policiesToAdd = new HashSet<>();
        policiesToAdd.add(existingPolicy); // Trying to re-add the same policy

        AddPolicyCommand addPolicyCommand = new AddPolicyCommand(INDEX_SECOND_CLIENT, policiesToAdd);

        Client expectedClient = new Client(
                clientToEdit.getName(),
                clientToEdit.getPhone(),
                clientToEdit.getEmail(),
                clientToEdit.getAddress(),
                existingPolicies // No change since we're adding the same policy
        );

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setClient(clientToEdit, expectedClient);

        String expectedMessage = String.format(AddPolicyCommand.MESSAGE_SUCCESS, Messages.format(expectedClient));

        assertCommandSuccess(addPolicyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Set<Tag> policiesA = new HashSet<>();
        policiesA.add(new Tag("Policy A"));
        Set<Tag> policiesB = new HashSet<>();
        policiesB.add(new Tag("Policy B"));

        AddPolicyCommand addPolicyFirstCommand = new AddPolicyCommand(INDEX_FIRST_CLIENT, policiesA);
        AddPolicyCommand addPolicySecondCommand = new AddPolicyCommand(INDEX_SECOND_CLIENT, policiesB);

        // Same object -> returns true
        assertTrue(addPolicyFirstCommand.equals(addPolicyFirstCommand));

        // Same values -> returns true
        AddPolicyCommand addPolicyFirstCommandCopy = new AddPolicyCommand(INDEX_FIRST_CLIENT, policiesA);
        assertTrue(addPolicyFirstCommand.equals(addPolicyFirstCommandCopy));

        // Different types -> returns false
        assertFalse(addPolicyFirstCommand.equals(1));

        // Different index -> returns false
        assertFalse(addPolicyFirstCommand.equals(addPolicySecondCommand));

        // Different policies -> returns false
        AddPolicyCommand addPolicyDifferentPolicyCommand = new AddPolicyCommand(INDEX_FIRST_CLIENT, policiesB);
        assertFalse(addPolicyFirstCommand.equals(addPolicyDifferentPolicyCommand));

        // Null -> returns false
        assertFalse(addPolicyFirstCommand.equals(null));
    }

    @Test
    public void toStringMethod() {
        Set<Tag> policies = new HashSet<>();
        policies.add(new Tag("Life Insurance"));
        policies.add(new Tag("Health Insurance"));

        AddPolicyCommand addPolicyCommand = new AddPolicyCommand(INDEX_FIRST_CLIENT, policies);

        String expectedString = new ToStringBuilder(addPolicyCommand)
                .add("clientIndex", INDEX_FIRST_CLIENT)
                .add("policiesToAdd", policies)
                .toString();

        assertEquals(expectedString, addPolicyCommand.toString());
    }
}
