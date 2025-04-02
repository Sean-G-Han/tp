package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showClientAtIndex;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_CLIENT;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteClientCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndicesUnfilteredList_success() {
        List<Index> indices = Arrays.asList(INDEX_FIRST_CLIENT, INDEX_SECOND_CLIENT);
        List<Client> clientsToDelete = Arrays.asList(
            model.getFilteredClientList().get(INDEX_FIRST_CLIENT.getZeroBased()),
            model.getFilteredClientList().get(INDEX_SECOND_CLIENT.getZeroBased())
        );
        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(indices);

        String expectedMessage = String.format(DeleteClientCommand.MESSAGE_DELETE_CLIENT_SUCCESS,
                Messages.format(clientsToDelete.get(0)) + ", " + Messages.format(clientsToDelete.get(1)));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteClient(clientsToDelete.get(1));
        expectedModel.deleteClient(clientsToDelete.get(0));

        assertCommandSuccess(deleteClientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClientList().size() + 1);
        List<Index> indices = Arrays.asList(INDEX_FIRST_CLIENT, outOfBoundIndex);
        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(indices);

        assertCommandFailure(deleteClientCommand, model, Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndicesFilteredList_success() {
        // Get the clients to delete first
        List<Client> clientsToDelete = Arrays.asList(
            model.getFilteredClientList().get(INDEX_FIRST_CLIENT.getZeroBased()),
            model.getFilteredClientList().get(INDEX_SECOND_CLIENT.getZeroBased())
        );

        // Show only the first client
        showClientAtIndex(model, INDEX_FIRST_CLIENT);

        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(Arrays.asList(INDEX_FIRST_CLIENT));

        String expectedMessage = String.format(DeleteClientCommand.MESSAGE_DELETE_CLIENT_SUCCESS,
                Messages.format(clientsToDelete.get(0)));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteClient(clientsToDelete.get(0));
        showNoClient(expectedModel);

        assertCommandSuccess(deleteClientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showClientAtIndex(model, INDEX_FIRST_CLIENT);

        Index outOfBoundIndex = INDEX_SECOND_CLIENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getClientList().size());

        List<Index> indices = Arrays.asList(INDEX_FIRST_CLIENT, outOfBoundIndex);
        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(indices);

        assertCommandFailure(deleteClientCommand, model, Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_singleIndexUnfilteredList_success() {
        List<Index> indices = Arrays.asList(INDEX_FIRST_CLIENT);
        Client clientToDelete = model.getFilteredClientList().get(INDEX_FIRST_CLIENT.getZeroBased());
        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(indices);

        String expectedMessage = String.format(DeleteClientCommand.MESSAGE_DELETE_CLIENT_SUCCESS,
                Messages.format(clientToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteClient(clientToDelete);

        assertCommandSuccess(deleteClientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        List<Index> firstIndices = Arrays.asList(INDEX_FIRST_CLIENT, INDEX_SECOND_CLIENT);
        List<Index> secondIndices = Arrays.asList(INDEX_SECOND_CLIENT, INDEX_THIRD_CLIENT);
        DeleteClientCommand deleteFirstCommand = new DeleteClientCommand(firstIndices);
        DeleteClientCommand deleteSecondCommand = new DeleteClientCommand(secondIndices);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteClientCommand deleteFirstCommandCopy = new DeleteClientCommand(firstIndices);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // different client -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));
    }

    @Test
    public void toStringMethod() {
        List<Index> targetIndices = Arrays.asList(Index.fromOneBased(1), Index.fromOneBased(2));
        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(targetIndices);
        String expected = DeleteClientCommand.class.getCanonicalName() + "{targetIndices=" + targetIndices + "}";
        assertEquals(expected, deleteClientCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoClient(Model model) {
        model.updateFilteredClientList(p -> false);
        assertTrue(model.getFilteredClientList().isEmpty());
    }
}
