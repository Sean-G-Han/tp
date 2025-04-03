package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand.EditClientDescriptor;
import seedu.address.logic.commands.UpdateClientCommand;
import seedu.address.testutil.EditClientDescriptorBuilder;

public class UpdateClientCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateClientCommand.MESSAGE_USAGE);

    private UpdateClientCommandParser parser = new UpdateClientCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_PHONE_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", UpdateClientCommand.MESSAGE_NOT_UPDATED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + PHONE_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + PHONE_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_disallowedFields_ignored() {
        // Name field is not allowed in update command
        String userInput = INDEX_FIRST_CLIENT.getOneBased() + NAME_DESC_AMY + PHONE_DESC_AMY;
        
        // Only the phone should be recognized, name should be ignored since it shouldn't be tokenized
        EditClientDescriptor descriptor = new EditClientDescriptorBuilder()
                .withPhone(VALID_PHONE_AMY)
                .build();
        UpdateClientCommand expectedCommand = new UpdateClientCommand(INDEX_FIRST_CLIENT, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
        
        // Tag field is not allowed in update command
        userInput = INDEX_FIRST_CLIENT.getOneBased() + TAG_DESC_FRIEND + EMAIL_DESC_AMY;
        
        // Only the email should be recognized, tag should be ignored since it shouldn't be tokenized
        descriptor = new EditClientDescriptorBuilder()
                .withEmail(VALID_EMAIL_AMY)
                .build();
        expectedCommand = new UpdateClientCommand(INDEX_FIRST_CLIENT, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validFields_success() {
        Index targetIndex = INDEX_FIRST_CLIENT;
        
        // Test phone only
        String userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        EditClientDescriptor descriptor = new EditClientDescriptorBuilder()
                .withPhone(VALID_PHONE_AMY)
                .build();
        UpdateClientCommand expectedCommand = new UpdateClientCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
        
        // Test email only
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditClientDescriptorBuilder()
                .withEmail(VALID_EMAIL_AMY)
                .build();
        expectedCommand = new UpdateClientCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
        
        // Test address only
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY;
        descriptor = new EditClientDescriptorBuilder()
                .withAddress(VALID_ADDRESS_AMY)
                .build();
        expectedCommand = new UpdateClientCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
        
        // Test all valid fields together
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
        descriptor = new EditClientDescriptorBuilder()
                .withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY)
                .build();
        expectedCommand = new UpdateClientCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
