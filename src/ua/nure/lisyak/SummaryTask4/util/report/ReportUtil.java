package ua.nure.lisyak.SummaryTask4.util.report;

import fr.opensagres.xdocreport.converter.*;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.core.document.DocumentKind;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.lisyak.SummaryTask4.util.constant.SettingsAndFolderPaths;
import ua.nure.lisyak.SummaryTask4.exception.FileProcessingException;
import ua.nure.lisyak.SummaryTask4.util.file.FileService;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Base util for generating reports of various type.
 */
public abstract class ReportUtil {

    private static final String FOLDER = SettingsAndFolderPaths.getReports();
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportUtil.class);
    private FileService fileService;

    /**
     * Creates a new {@code ReportUtil} object.
     *
     * @param fileService file service object
     */
    public ReportUtil(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * Generates new report with the specified name.
     *
     * @param reportName report name (with extension)
     * @param locale     current locale
     * @return generated report
     * @throws FileProcessingException if cannot generate report
     */
    public Report generate(String reportName, String locale) {
        try {
            String template = FOLDER + getTemplateFileName() + ".odt";
            InputStream in = ReportUtil.class.getResourceAsStream(template);
            IXDocReport docReport = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
            IContext context = fill(docReport, locale);
            ReportType type = getType(reportName);
            if (type == null) {
                throw new FileProcessingException("Unknown report extension");
            }
            File reportFile = fileService.getFile(reportName);
            OutputStream out = new FileOutputStream(reportFile);
            docReport.process(context, out);
            return generate(reportFile, reportName, type);
        } catch (IOException | XDocReportException e) {
            LOGGER.warn("Cannot generate report", e);
            throw new FileProcessingException(e);
        }
    }

    /**
     * Gets name of the file that is used as a template for the report.
     *
     * @return name of the file that is used as a template for the report
     */
    protected abstract String getTemplateFileName();

    /**
     * Gets report title.
     *
     * @param locale current locale
     * @return report title
     */
    protected abstract String getReportTitle(String locale);

    /**
     * Fills a metadata of the report.
     *
     * @param metadata metadata to fill
     */
    protected abstract void fillMetaData(FieldsMetadata metadata);

    /**
     * Fills head of the report table.
     *
     * @param head   map of values to fill
     * @param locale current locale
     */
    protected abstract void fillHead(Map<String, String> head, String locale);

    /**
     * Fills body of the report table.
     *
     * @param context current document context
     */
    protected abstract void fillBody(IContext context);

    private IContext fill(IXDocReport docReport, String locale) throws XDocReportException {
        IContext context = docReport.createContext();
        FieldsMetadata metadata = new FieldsMetadata();
        fillMetaData(metadata);
        docReport.setFieldsMetadata(metadata);
        Map<String, String> head = new HashMap<>();
        fillHead(head, locale);
        context.put("head", head);
        fillBody(context);
        context.put("date", new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
        context.put("title", getReportTitle(locale));
        return context;
    }

    private ReportType getType(String fileName) {
        return ReportType.define(fileService.getExtension(fileName));
    }

    private Report generate(File reportFile, String reportName, ReportType type)
            throws FileNotFoundException, XDocConverterException {
        Report report = new Report();
        report.setType(type);
        if (type == ReportType.PDF) {
            Options options = Options.getFrom(DocumentKind.ODT).to(ConverterTypeTo.PDF);
            IConverter converter = ConverterRegistry.getRegistry().getConverter(options);
            InputStream in = new FileInputStream(reportFile);
            File converted = fileService.getFile(reportName + "." + type.getExtension());
            OutputStream out = new FileOutputStream(converted);
            converter.convert(in, out, options);
            report.setFile(converted);
            return report;
        }
        report.setFile(reportFile);
        LOGGER.debug("Report '{}' generated", report.getFile().getName());
        return report;
    }

}
