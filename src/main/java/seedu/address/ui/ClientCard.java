package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.client.Client;
import seedu.address.model.tag.Tag;

/**
 * An UI component that displays information of a {@code Client}.
 */
public class ClientCard extends UiPart<Region> {

    private static final String FXML = "ClientListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Client client;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code ClientCode} with the given {@code Client} and index to display.
     */
    public ClientCard(Client client, int displayedIndex) {
        super(FXML);
        this.client = client;

        // Initialize UI elements
        initializeClientDetails(client, displayedIndex);
        populateTags(client);
    }

    /**
     * Initializes the client details in the UI.
     *
     * @param client The client whose details are being displayed.
     * @param displayedIndex The index number to be displayed.
     */
    private void initializeClientDetails(Client client, int displayedIndex) {
        id.setText(displayedIndex + ". ");
        name.setText(client.getName().fullName);
        phone.setText(client.getPhone().value);
        address.setText(client.getAddress().value);
        email.setText(client.getEmail().value);
    }

    /**
     * Populates the tag container with sorted tags.
     *
     * @param client The client whose tags need to be displayed.
     */
    private void populateTags(Client client) {
        client.getTags().stream()
                .sorted(Comparator.<Tag, Boolean>comparing(tag -> tag.tagName.equals("Priority"))
                        .thenComparing(tag -> tag.tagName))
                .map(this::createTagLabel)
                .forEach(tags.getChildren()::add);
    }

    /**
     * Creates a styled Label for a given tag.
     *
     * @param tag The tag for which a Label is created.
     * @return A styled Label representing the tag.
     */
    private Label createTagLabel(Tag tag) {
        Label label = new Label(tag.tagName);
        if (tag.tagName.equals("Priority")) {
            label.setStyle("-fx-background-color: orange; -fx-font-weight: bold;");
        }
        return label;
    }
}
