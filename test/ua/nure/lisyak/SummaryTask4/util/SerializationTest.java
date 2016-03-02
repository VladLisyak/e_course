package ua.nure.lisyak.SummaryTask4.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ua.nure.lisyak.SummaryTask4.exception.SerializerException;
import ua.nure.lisyak.SummaryTask4.model.User;
import ua.nure.lisyak.SummaryTask4.model.enums.Enabled;
import ua.nure.lisyak.SummaryTask4.util.serialization.JSONSerializer;
import ua.nure.lisyak.SummaryTask4.util.serialization.StreamSerializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class SerializationTest {

        @Parameterized.Parameters
        public static Collection<StreamSerializer> data() {
            return Arrays.asList(
                    new StreamSerializer[]{new JSONSerializer()}
            );
        }

        private final StreamSerializer serializer;

        public SerializationTest(StreamSerializer serializer) {
            this.serializer = serializer;
        }

        @Test
        public void test() {
            User user = new User();
            user.setName("TestName");
            user.setLogin("TestLogin");
            user.setPassword("PaSsWoRd");
            user.setId(1);
            user.setEmail("emal@mail.ru");
            user.setEnabled(Enabled.ACTIVE);
            String s = serialize(user);
            User user2 = deserialize(s, User.class);
            List<User> users = Arrays.asList(user, user2);
            String bs = serialize(users, User.class);
            deserializeList(bs, User.class);
        }

        @Test(expected = SerializerException.class)
        public void testSerializationException() {
            serialize(new Object());
        }

        @Test(expected = SerializerException.class)
        public void testListSerializationException() {
            serialize(Arrays.asList(new Object()), Object.class);
        }

        @Test(expected = SerializerException.class)
        public void testDeserializationException() {
            deserialize("", Object.class);
        }

        @Test(expected = SerializerException.class)
        public void testListDeserializationException() {
            deserializeList("", Object.class);
        }

        @Test
        public void testContentType() {
            assertNotNull(serializer.getContentType());
        }

        private String serialize(Object object) {
            OutputStream stream = new ByteArrayOutputStream();
            serializer.serialize(stream, object);
            return stream.toString();
        }

        private <T> String serialize(List<T> object, Class<T> clazz) {
            OutputStream stream = new ByteArrayOutputStream();
            serializer.serializeList(stream, object, clazz);
            return stream.toString();
        }

        private <T> T deserialize(String string, Class<T> clazz) {
            InputStream stream = new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
            return serializer.deserialize(stream, clazz);
        }

        private <T> List<T> deserializeList(String string, Class<T> clazz) {
            InputStream stream = new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
            return serializer.deserializeList(stream, clazz);
        }

}
