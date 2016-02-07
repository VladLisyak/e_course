package ua.nure.lisyak.SummaryTask4.util.report;

import java.io.File;

/**
 * A report.
 */
public class Report {

    /**
     * Report type.
     */
    private ReportType type;

    /**
     * Report file.
     */
    private File file;

    /**
     * Gets content type of the report.
     *
     * @return content type of the report
     */
    public String getContentType() {
        return type.getContentType();
    }

    /**
     * Gets a report file.
     *
     * @return report file
     */
    public File getFile() {
        return file;
    }

    /**
     * Sets content type of the report.
     *
     * @param type content type
     * @see <a href="http://en.wikipedia.org/wiki/Internet_media_type#List_of_common_media_types">Content types</a>
     */
    public void setType(ReportType type) {
        this.type = type;
    }

    /**
     * Sets report file.
     *
     * @param file report file
     */
    public void setFile(File file) {
        this.file = file;
    }

}
