package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */

public class SortCommandTest {

    private Model model;
    private Model expectedModel;
    private SortCommand sortCommand;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        sortCommand = new SortCommand();
    }

    /**
     * Tests the execution of the SortCommand with all clients.
     */
    @Test
    public void execute_sort_allClients() {
        expectedModel.sortClients();
        assertCommandSuccess(sortCommand, model, SortCommand.MESSAGE_SORTED_SUCCESS, expectedModel);
    }

    /**
     * Tests the execution of the SortCommand with an empty list.
     */
    @Test
    public void execute_sort_emptyList() {
        Model emptyModel = new ModelManager();
        assertCommandSuccess(sortCommand, emptyModel, SortCommand.MESSAGE_EMPTY, emptyModel);
    }

}
