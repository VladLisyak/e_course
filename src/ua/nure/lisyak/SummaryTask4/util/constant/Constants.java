package ua.nure.lisyak.SummaryTask4.util.constant;

/**
 * String constants.
 *
 */
public final class Constants {
    /**
     * Application configuration paths.
     */
    public static final class ROUTES{

        public static final String DATABASE_PROPS_PATH = "database.properties";
        public static final String UPLOAD_AUTHORS_DIRECTORY = "authors/";
        public static final String UPLOAD_BOOKS_DIRECTORY = "books/";
        public static final String UPLOAD_DIR = "B://library/";
        public static final String BUNDLE_PATH = "messages";
    }

    /**
     * Constants for working with requests.
     */

        public final class Attributes {

        public static final String ADMINISTRATOR = "admin";
        public static final String AUTHOR = "author";
        public static final String AUTHORS = "authors";
        public static final String BOOK = "book";
        public static final String BOOKS = "books";
        public static final String CONNECTION_MANAGER = "connectionManager";
        public static final String CURRENT_ADMIN = "currentAdmin";
        public static final String CURRENT_LIBRARIAN = "currentLibrarian";
        public static final String CURRENT_LOCALE = "currentLocale";
        public static final String CURRENT_USER = "currentUser";
        public static final String DEFAULT_LOCALE = "defaultLocale";
        public static final String DESCRIPTION = "description";
        public static final String ID = "id";
        public static final String IMAGE = "image";
        public static final String FILE_SERVICE = "fileService";
        public static final String LIBRARIAN = "librarian";
        public static final String LOCALES = "locales";
        public static final String NAME = "name";
        public static final String ORDERED_BOOKS = "orderedBooks";
        public static final String QUERY = "query";
        public static final String PROCESS_RESULT = "result";
        public static final String PROCESS_RESULT_MAIN = "resultMain";
        public static final String PROCESS_RESULT_LIBRARY = "resultLibrary";
        public static final String PROCESS_RESULT_DASHBOARD = "resultDashboard";
        public static final String PUBLISHER = "publisher";
        public static final String PUBLISHERS = "publishers";
        public static final String REGISTRATION_SUCCEED = "registrationSucceed";
        public static final String SERIALIZER = "serializer";
        public static final String SERIALIZERS = "serializers";
        public static final String TITLE = "title";
        public static final String TRANSLATOR = "translator";
        public static final String URL = "url";
        public static final String USER = "user";
        public static final String USERS = "users";
        public static final String CACHE = "ehcache";
        public static final String LANG = "lang";
        public static final String CURRENT_USER_ROLE = "currUser";;
        public static final String NEW_LOCALE = "newLocale";
        public static final String LOGIN = "login";
        public static final String PASSWORD = "password";


        private Attributes() {
        }

    }

    /**
     * Services names
     */
    public static final class Service{
        public static final String FILE_PROC_SERVICE = "fileProcessingService";
    }

    /**
     * validation constants.
     *
     */
    public static final class Validation{
        public static final String LEN_3_TO_100 = "validator.lengthFrom3to100";
        public static final String LEN_5_TO_100 = "validator.lengthFrom5to100";
        public static final String LEN_5_TO_1000 = "validator.lengthFrom5to1000";
        public static final String LETTERS_ONLY = "validator.lettersOnly";
        public static final String INV_EMAIL = "validator.invalidEmail";
        public static final String PATT_MATCH = "validator.loginPatternMatch";
        public static final String NO_WHITESPACES = "validator.noWhitespaces";
        public static final String CANT_BE_EMPTY = "validator.cannotBeEmpty";
        public static final String PAGES_COUNT = "validator.pagesCount";
        public static final String YEAR = "validator.year";
        public static final String TOO_BIG = "validator.tooBig";
        public static final String CANT_BE_NEGATIVE = "validator.cannotBeNegative";

    }

    public static final class ServletPaths {
        public static final class User{
            public static final String USER = "/user/";

            public static final String HOME = USER+ "home";
            public static final String LOGOUT = USER + "logout";

            public static final String COURSE_LIST = USER + "courses";
            public static final String LOGIN = USER + "login";
        }


    }

    public static final class Pages {
        private static final String PREFIX = "/WEB-INF/pages/";

        public static final String ENABLED_ISSUE = PREFIX + "issue.jsp";

        public static final class User {
            private static final String USER_PREFIX = "user/";

            public static final String LOGIN = PREFIX+ USER_PREFIX + "login.jsp";
        }
    }

    private Constants(){
    }
}
