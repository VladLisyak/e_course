package ua.nure.lisyak.SummaryTask4.util.validation;


import ua.nure.lisyak.SummaryTask4.model.User;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants.Validation;

import java.util.regex.Pattern;
/**
 * Performs validation of {@link User} objects.
 */
public class UserValidator extends AbstractValidator {
	private static final Pattern EMAIL_TEMPLATE = Pattern.compile(
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    private static final Pattern LOGIN_TEMPLATE = Pattern.compile(
            "^[A-ZА-Яа-яa-z0-9_-]+$", Pattern.CASE_INSENSITIVE);
    private static final Pattern NAME_TEMPLATE = Pattern.compile("^\\p{L}+$", Pattern.CASE_INSENSITIVE);


    /**
     * Instantiates a new UserValidator for validating {@link User}s.
     *
     * @param user       user that needs validation
     * @param locale     current locale value
     */
    public UserValidator(User user, String locale) {
        super(locale);
        if (user == null) {
            return;
        }
        putIssue("name", validateName(user.getName()));
        putIssue("email", validateEmail(user.getEmail()));
        putIssue("login", validateLogin(user.getLogin()));
        putIssue("password", validatePassword(user.getPassword()));
    }

    /**
     * Instantiates a new UserValidator that validates only user's login and password immediately.
     *
     * @param login       login to validate
     * @param password    password to validate
     * @param locale      current locale value
     */
    public UserValidator(String login, String password, String locale) {
        super(locale);
        putIssue("login", validateLogin(login));
        putIssue("password", validatePassword(password));
    }

    private String validateName(String name) {
        if (name == null || name.isEmpty()) {
            return Validation.CANT_BE_EMPTY;
        }
        if (!NAME_TEMPLATE.matcher(name).matches()) {
            return Validation.LETTERS_ONLY;
        }
        if (name.length() < 3 || name.length() > 100) {
            return Validation.LEN_3_TO_100;
        }
        return null;
    }


    private String validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            return Validation.CANT_BE_EMPTY;
        }
        if (!EMAIL_TEMPLATE.matcher(email).matches()) {
            return Validation.INV_EMAIL;
        }
        if (email.length() < 5 || email.length() > 100) {
            return Validation.LEN_5_TO_100;
        }
        return null;
    }

    private String validateLogin(String login) {
        if (login == null || login.isEmpty()) {
            return Validation.CANT_BE_EMPTY;
        }
        if (!LOGIN_TEMPLATE.matcher(login).matches()) {
            return Validation.PATT_MATCH;
        }
        if (login.length() < 5 || login.length() > 100) {
            return Validation.LEN_5_TO_100;
        }
        return null;
    }

    private String validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            return Validation.CANT_BE_EMPTY;
        }
        if (password.contains(" ")) {
            return Validation.NO_WHITESPACES;
        }
        if (password.length() < 5 || password.length() > 100) {
            return Validation.LEN_5_TO_100;
        }
        return null;
    }

}
