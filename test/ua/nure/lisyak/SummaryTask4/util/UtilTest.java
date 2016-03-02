package ua.nure.lisyak.SummaryTask4.util;

import com.sun.java.accessibility.util.Translator;
import org.junit.Test;
import ua.nure.lisyak.SummaryTask4.model.enums.Role;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class UtilTest {
    private static final String LOCALES_FILE = "/locales.properties";
    private Translator translator;
    private Map<String, String> locales;
    private String defaultLocale;

    @Test
    public void LocaleUtilTest() {
        String[] s = {"ru","en"};
        LocaleUtil tuple = new LocaleUtil(Constants.ROUTES.BUNDLE_PATH, s);
        assertEquals("You do not have access rights", tuple.translate("validator.noRights","en"));
    }


    @Test
    public void testTuple() {
        Tuple<Integer, String> tuple = new Tuple<>(1, "value");
        assertEquals(1, (int) tuple.getFirstEntity());
        assertEquals("value", tuple.getSecondEntity());
    }

    @Test
    public void testFunctionContains() {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.STUDENT);
        roles.add(Role.TUTOR);

        assertTrue(Functions.contains(roles, "STUDENT"));
        assertFalse(Functions.contains(roles, "ADMIN"));
    }
}
