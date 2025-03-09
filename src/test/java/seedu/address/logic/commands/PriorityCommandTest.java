package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PRIORITY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class PriorityCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_toggleOnPriorityUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person priorityPerson = new PersonBuilder(firstPerson).withTags(VALID_TAG_FRIEND,
                VALID_TAG_PRIORITY).build();

        PriorityCommand priorityCommand = new PriorityCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(PriorityCommand.MESSAGE_PRIORITY_PERSON_SUCCESS,
                Messages.format(priorityPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, priorityPerson);

        assertCommandSuccess(priorityCommand, model, expectedMessage, expectedModel);

    }
}
