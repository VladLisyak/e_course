package ua.nure.lisyak.SummaryTask4.util.report;

/**
 * Enumeration of report types.
 */
public enum ReportType {

    ODT("odt", "application/vnd.oasis.opendocument.text"),
    PDF("pdf", "application/pdf");

    private String extension;
    private String contentType;

    private ReportType(String extension, String contentType) {
        this.extension = extension;
        this.contentType = contentType;
    }

    /**
     * Defines report type from file extension.
     *
     * @param extension file extension
     * @return report type if found, {@code null} otherwise.
     */
    public static ReportType define(String extension) {
        ReportType[] reportTypes = values();
        for (ReportType reportType : reportTypes) {
            if (reportType.extension.equals(extension)) {
                return reportType;
            }
        }
        return null;
    }

    public String getExtension() {
        return extension;
    }

    public String getContentType() {
        return contentType;
    }
}
