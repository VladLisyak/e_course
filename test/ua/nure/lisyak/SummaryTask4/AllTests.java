package ua.nure.lisyak.SummaryTask4;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ua.nure.lisyak.SummaryTask4.repository.JdbcCourseRepositoryTest;
import ua.nure.lisyak.SummaryTask4.repository.JdbcMessageRepositoryTest;
import ua.nure.lisyak.SummaryTask4.repository.JdbcUserRepositoryTest;
import ua.nure.lisyak.SummaryTask4.util.SerializationTest;
import ua.nure.lisyak.SummaryTask4.util.UtilTest;
import ua.nure.lisyak.SummaryTask4.util.validation.AllValidatorsTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SerializationTest.class,
        UtilTest.class,
        AllValidatorsTest.class,
        JdbcCourseRepositoryTest.class,
        JdbcUserRepositoryTest.class,
        JdbcMessageRepositoryTest.class,
})
public class AllTests {

}
