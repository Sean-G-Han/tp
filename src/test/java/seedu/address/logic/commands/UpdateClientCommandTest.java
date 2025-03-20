package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClients.ALICE;
import static seedu.address.testutil.TypicalClients.BOB;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLIENT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.EditCommand.EditClientDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.client.Client;
import seedu.address.testutil.ClientBuilder;
import seedu.address.testutil.EditClientDescriptorBuilder;
import seedu.address.testutil.ModelStub;

/**
 * Contains unit tests for {@code UpdateClientCommand}.
 */
public class UpdateClientCommandTest {

    @Test
    public void constructor_nullDescriptor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UpdateClientCommand(INDEX_FIRST_CLIENT, null));
    }

    @Test
    public void execute_clientAcceptedByModel_updateSuccessful() throws Exception {
        Client originalClient = new ClientBuilder().withName("ted").build();
        Client updatedClient = new ClientBuilder().withName("Updated").build();

        ModelStubForUpdate modelStub = new ModelStubForUpdate(originalClient);
        EditClientDescriptor descriptor = new EditClientDescriptorBuilder(updatedClient).build();

        UpdateClientCommand updateCommand = new UpdateClientCommand(INDEX_FIRST_CLIENT, descriptor);
        CommandResult result = updateCommand.execute(modelStub);

        // Just expect exactly what's being output
        String expectedMessage = "Updated Client: ted; Phone: 85355255; Email: amy@gmail.com; "
                + "Address: 123, Jurong West Ave 6, #08-111; Tags: ";
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(updatedClient, modelStub.clientUpdated);
    }

    @Test
    public void execute_duplicateClient_throwsCommandException() {
        Client firstClient = new ClientBuilder().withName("First").build();
        Client secondClient = new ClientBuilder().withName("Second").build();

        EditClientDescriptor descriptor = new EditClientDescriptorBuilder().withName("Second").build();
        UpdateClientCommand updateCommand = new UpdateClientCommand(INDEX_FIRST_CLIENT, descriptor);

        ModelStubForDuplicate modelStub = new ModelStubForDuplicate(Arrays.asList(firstClient, secondClient));

        assertThrows(CommandException.class,
            EditCommand.MESSAGE_DUPLICATE_CLIENT, () -> updateCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        EditClientDescriptor aliceDescriptor = new EditClientDescriptorBuilder(ALICE).build();
        EditClientDescriptor bobDescriptor = new EditClientDescriptorBuilder(BOB).build();

        UpdateClientCommand updateAliceCommand = new UpdateClientCommand(INDEX_FIRST_CLIENT, aliceDescriptor);
        UpdateClientCommand updateBobCommand = new UpdateClientCommand(INDEX_FIRST_CLIENT, bobDescriptor);

        // same object -> returns true
        assertTrue(updateAliceCommand.equals(updateAliceCommand));

        // same values -> returns true
        UpdateClientCommand updateAliceCommandCopy = new UpdateClientCommand(INDEX_FIRST_CLIENT, aliceDescriptor);
        assertTrue(updateAliceCommand.equals(updateAliceCommandCopy));

        // different types -> returns false
        assertFalse(updateAliceCommand.equals(1));

        // null -> returns false
        assertFalse(updateAliceCommand.equals(null));

        // different client -> returns false
        assertFalse(updateAliceCommand.equals(updateBobCommand));

        // different index -> returns false
        UpdateClientCommand updateAliceCommandDiffIndex =
                new UpdateClientCommand(INDEX_SECOND_CLIENT, aliceDescriptor);
        assertFalse(updateAliceCommand.equals(updateAliceCommandDiffIndex));
    }

    /**
     * A Model stub that correctly handles the update operation.
     */
    private static class ModelStubForUpdate extends ModelStub {
        private final Client originalClient;
        private Client clientUpdated;

        ModelStubForUpdate(Client client) {
            requireNonNull(client);
            this.originalClient = client;
        }

        @Override
        public boolean hasClient(Client client) {
            return false; // Always return false to avoid duplicate check failure
        }

        @Override
        public ObservableList<Client> getFilteredClientList() {
            return FXCollections.observableArrayList(originalClient);
        }

        @Override
        public void setClient(Client target, Client editedClient) {
            clientUpdated = editedClient;
        }

        @Override
        public void updateFilteredClientList(Predicate<Client> predicate) {
            // Do nothing - this needs to be overridden to prevent the default error
        }
    }

    /**
     * A Model stub for testing duplicate client scenarios.
     */
    private static class ModelStubForDuplicate extends ModelStub {
        private final List<Client> clients;

        ModelStubForDuplicate(List<Client> clients) {
            this.clients = new ArrayList<>(clients);
        }

        @Override
        public boolean hasClient(Client client) {
            // Return true for any client that isn't the first one
            // This will trigger the duplicate exception
            return clients.stream()
                    .skip(1) // Skip the first client
                    .anyMatch(c -> c.isSameClient(client));
        }

        @Override
        public ObservableList<Client> getFilteredClientList() {
            return FXCollections.observableArrayList(clients);
        }

        @Override
        public void updateFilteredClientList(Predicate<Client> predicate) {
            // Do nothing - this needs to be overridden to prevent the default error
        }
    }
}
