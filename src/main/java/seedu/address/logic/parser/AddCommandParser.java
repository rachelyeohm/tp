package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION_SHORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_SHORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME_SHORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_SHORT;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Location;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_LOCATION);

        ArgumentMultimap argMultimapShort =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME_SHORT, PREFIX_PHONE_SHORT, PREFIX_EMAIL_SHORT,
                        PREFIX_LOCATION_SHORT);

        if (arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_LOCATION, PREFIX_PHONE, PREFIX_EMAIL)
                && argMultimap.getPreamble().isEmpty()) {
            return new AddCommand(createPerson(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_LOCATION));
        } else if (arePrefixesPresent(argMultimapShort, PREFIX_NAME_SHORT, PREFIX_LOCATION_SHORT, PREFIX_PHONE_SHORT,
                PREFIX_EMAIL_SHORT) && argMultimapShort.getPreamble().isEmpty()) {
            return new AddCommand(createPerson(argMultimapShort, PREFIX_NAME_SHORT, PREFIX_PHONE_SHORT, PREFIX_EMAIL_SHORT,
                    PREFIX_LOCATION_SHORT));
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

    }
    /**
     * Creates a {@code Person} object by parsing the provided {@code ArgumentMultimap}.
     * This method validates that no duplicate prefixes are present for the given prefix fields,
     * and then parses the name, phone, email, and location from the corresponding prefixes.
     *
     * @param argMultimap The {@code ArgumentMultimap} containing the argument values to be parsed.
     * @param prefixName The prefix used to identify the name argument.
     * @param prefixPhone The prefix used to identify the phone argument.
     * @param prefixEmail The prefix used to identify the email argument.
     * @param prefixLocation The prefix used to identify the location argument.
     * @return A {@code Person} object containing the parsed name, phone, email, location, and a default remark.
     * @throws ParseException if any of the required fields are missing or cannot be parsed correctly.
     */
    private Person createPerson(ArgumentMultimap argMultimap, Prefix prefixName, Prefix prefixPhone,
                                Prefix prefixEmail, Prefix prefixLocation) throws ParseException {
        argMultimap.verifyNoDuplicatePrefixesFor(prefixName, prefixPhone, prefixEmail, prefixLocation);
        Name name = ParserUtil.parseName(argMultimap.getValue(prefixName).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(prefixPhone).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(prefixEmail).get());
        Location location = ParserUtil.parseAddress(argMultimap.getValue(prefixLocation).get());
        Remark remark = new Remark("");

        return new Person(name, phone, email, location, remark);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
