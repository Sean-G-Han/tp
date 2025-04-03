package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
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
        assertParseFailure(parser, PHONE_DESC_AMY, MESSAGE_INVALID_FORMAT);

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
    public void parse_irrelevantPrefixes_failure() {
        // When name prefix is provided, it triggers MESSAGE_INVALID_FORMAT because
        // the parser doesn't recognize n/ as a valid prefix
        assertParseFailure(parser, "1 n/Bob", MESSAGE_INVALID_FORMAT);

        // When tag prefix is provided, it triggers MESSAGE_INVALID_FORMAT because
        // the parser doesn't recognize t/ as a valid prefix
        assertParseFailure(parser, "1 t/friend", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allContactFields_success() {
        Index targetIndex = INDEX_FIRST_CLIENT;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;

        EditClientDescriptor descriptor = new EditClientDescriptorBuilder()
                .withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY)
                .build();
        UpdateClientCommand expectedCommand = new UpdateClientCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someContactFields_success() {
        Index targetIndex = INDEX_FIRST_CLIENT;

        // Only phone
        String userInput1 = targetIndex.getOneBased() + PHONE_DESC_AMY;
        EditClientDescriptor descriptor1 = new EditClientDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        UpdateClientCommand expectedCommand1 = new UpdateClientCommand(targetIndex, descriptor1);
        assertParseSuccess(parser, userInput1, expectedCommand1);

        // Only email
        String userInput2 = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        EditClientDescriptor descriptor2 = new EditClientDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        UpdateClientCommand expectedCommand2 = new UpdateClientCommand(targetIndex, descriptor2);
        assertParseSuccess(parser, userInput2, expectedCommand2);

        // Only address
        String userInput3 = targetIndex.getOneBased() + ADDRESS_DESC_AMY;
        EditClientDescriptor descriptor3 = new EditClientDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        UpdateClientCommand expectedCommand3 = new UpdateClientCommand(targetIndex, descriptor3);
        assertParseSuccess(parser, userInput3, expectedCommand3);
    }
}
