package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = ":add";

    public static final String COMMAND_WORD_SHORT = ":a";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " or " + COMMAND_WORD_SHORT +  ": Adds a person to " +
        "the address book. \n"
            + "Parameters for :add : \n"
            + formatParameters(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_LOCATION, PREFIX_REMARK)
            + "\nExample: \n"
            + COMMAND_WORD + " "
            + formatExample(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_LOCATION, PREFIX_REMARK, "John Doe",
            "98765432", "johnd@example.com", "311, Clementi Ave 2, #02-25", "Likes to swim")

            + "\nParameters for :a : \n"
            + formatParameters(PREFIX_NAME_SHORT, PREFIX_PHONE_SHORT, PREFIX_EMAIL_SHORT, PREFIX_LOCATION_SHORT,
            PREFIX_REMARK_SHORT)
            + "\nExample: \n"
            + COMMAND_WORD_SHORT + " "
            + formatExample(PREFIX_NAME_SHORT, PREFIX_PHONE_SHORT, PREFIX_EMAIL_SHORT, PREFIX_LOCATION_SHORT,
            PREFIX_REMARK_SHORT, "John Doe", "98765432", "johnd@example.com", "311, " +
                    "Clementi Ave 2, #02-25", "Likes to swim");

    private static String formatParameters(Prefix prefixName, Prefix prefixPhone, Prefix prefixEmail,
                                           Prefix prefixLocation, Prefix prefixRemark) {
        return prefixName + "NAME "
                + prefixPhone + "PHONE "
                + prefixEmail + "EMAIL "
                + prefixLocation + "LOCATION "
                + prefixRemark + "REMARK";
    }

    private static String formatExample(Prefix prefixName, Prefix prefixPhone, Prefix prefixEmail,
                                        Prefix prefixLocation, Prefix prefixRemark, String name, String phone,
                                        String email, String location, String remark) {
        return prefixName + name + " "
                + prefixPhone + phone + " "
                + prefixEmail + email + " "
                + prefixLocation + location + " "
                + prefixRemark + remark;
    }


    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
