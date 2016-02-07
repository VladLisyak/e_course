package ua.nure.lisyak.SummaryTask4.util.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Util for writing files to {@link HttpServletResponse} output stream
 */
public final class FileServletUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileServletUtil.class);

    /**
     * Writes file to {@link HttpServletResponse} output stream
     * @param file file to write
     * @param resp response with the output stream
     */
    public static void write(File file, HttpServletResponse resp) {
        try (FileInputStream stream = new FileInputStream(file)) {
            writeStream(stream, resp.getOutputStream());
        } catch (IOException e) {
            LOGGER.error("Unexpected exception while sending file", e);
        }
    }

    private static void writeStream(InputStream inputStream, OutputStream outputStream) throws IOException {
        final byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) > -1) {
            outputStream.write(buffer, 0, len);
        }
        outputStream.flush();
    }

}
