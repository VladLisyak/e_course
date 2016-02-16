package ua.nure.lisyak.SummaryTask4.util.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.lisyak.SummaryTask4.exception.FileProcessingException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public final class SettingsAndFolderPaths {
    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsAndFolderPaths.class);

    private static final String EHCACHE_CONFIG = "B:\\Programming\\GitRep\\e_course\\src\\ua\\nure\\lisyak\\SummaryTask4\\settings\\ehcache\\ehcache.xml";
    private static final String SQL_FILE = "/ua/nure/lisyak/SummaryTask4/settings/query.properties";
    private static final String DATABASE_CONFIG_FILE = "/ua/nure/lisyak/SummaryTask4/settings/db.properties";
    private static final String MAIL_PROPERTIES = "/ua/nure/lisyak/SummaryTask4/settings/mail.properties";
    private static final String REPORTS = "/templates/reports/";

    private static final String BUNDLE_PATH = "messages";

    private static final String CONFIG_FILE = "/ua/nure/lisyak/SummaryTask4/settings/config.properties";
    private static final String RESOURCE_PATH = "/uploads/";

    private static final int MAX_CHECKING_OUT;


    private static final int COURSES_PER_PAGE;
    private static final String UPLOAD_DIRECTORY;
    private static final String UPLOAD_COURSES_DIRECTORY;
    private static final String UPLOAD_USERS_DIRECTORY;
    private static final String UPLOAD_PATH_MAPPING;
    private static final List<String> ALLOWED_IMAGE_TYPES;
    private static final String UPDATE_ORDERS_TASK_EXECUTION_TIME;

   public static final class Images{
       public static final String TEMP_DIRECTORY = "B://e_course/";
       public static final int SIZE_THRESHOLD = 1024 * 1024;
       public static final int MAX_SIZE = 1024 * 1024 * 5;
   }

    static {
        try (InputStream resource = SettingsAndFolderPaths.class.getResourceAsStream(CONFIG_FILE)) {
            Properties prop = new Properties();
            prop.load(resource);
            MAX_CHECKING_OUT = Integer.parseInt(prop.getProperty("order.maxCheckingOut"));
            COURSES_PER_PAGE = Integer.parseInt(prop.getProperty("search.coursesPerPage"));
            UPLOAD_DIRECTORY = prop.getProperty("upload.dir");
            UPLOAD_COURSES_DIRECTORY = prop.getProperty("upload.dir.courses");
            UPLOAD_USERS_DIRECTORY = prop.getProperty("upload.dir.users");
            UPLOAD_PATH_MAPPING = prop.getProperty("upload.pathMapping");
            ALLOWED_IMAGE_TYPES = Arrays.asList(prop.getProperty("upload.image.allowedContentType").split(";"));
            UPDATE_ORDERS_TASK_EXECUTION_TIME = prop.getProperty("order.updateExecutionTime");
        } catch (IOException e) {
            LOGGER.error("Cannot load file: '{}'", CONFIG_FILE);
            throw new FileProcessingException("Cannot load file: '" + CONFIG_FILE + "'", e);
        }
    }
    public static String getEhcacheConfig() {
        return EHCACHE_CONFIG;
    }
    public static String getSqlFile() {
        return SQL_FILE;
    }

    public static String getDatabaseConfigFile() {
        return DATABASE_CONFIG_FILE;
    }

    public static String getMailProperties() {
        return MAIL_PROPERTIES;
    }

    public static String getReports() {
        return REPORTS;
    }

    public static int getMaxCheckingOut() {
        return MAX_CHECKING_OUT;
    }

    public static int getCoursesPerPage() {
        return COURSES_PER_PAGE;
    }

    public static String getUploadDirectory() {
        return UPLOAD_DIRECTORY;
    }

    public static String getUploadCoursesDirectory() {
        return UPLOAD_COURSES_DIRECTORY;
    }

    public static String getUploadUsersDirectory() {
        return UPLOAD_USERS_DIRECTORY;
    }

    public static String getUploadPathMapping() {
        return UPLOAD_PATH_MAPPING;
    }

    public static String getUpdateOrdersTaskExecutionTime() {
        return UPDATE_ORDERS_TASK_EXECUTION_TIME;
    }

    public static boolean isImage(String imageString) {
        String type = imageString.split("/|;")[1];
        return ALLOWED_IMAGE_TYPES.contains(type);
    }

    public static String getBundlePath() {
        return BUNDLE_PATH;
    }

    public static String getResourcePath() {
        return RESOURCE_PATH;
    }

    public static String getConfigFile() {
        return CONFIG_FILE;
    }

    private SettingsAndFolderPaths() {
    }

}
