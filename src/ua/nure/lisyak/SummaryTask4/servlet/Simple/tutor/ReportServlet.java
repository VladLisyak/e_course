package ua.nure.lisyak.SummaryTask4.servlet.Simple.tutor;

import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import ua.nure.lisyak.SummaryTask4.exception.FileProcessingException;
import ua.nure.lisyak.SummaryTask4.model.JournalEntry;
import ua.nure.lisyak.SummaryTask4.servlet.BaseServlet;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;
import ua.nure.lisyak.SummaryTask4.util.file.FileService;
import ua.nure.lisyak.SummaryTask4.util.file.FileServletUtil;
import ua.nure.lisyak.SummaryTask4.util.report.Report;
import ua.nure.lisyak.SummaryTask4.util.report.ReportUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = Constants.ServletPaths.Tutor.REPORT)
public class ReportServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ReportUtil reportUtil = getUtil(getIntParam(req, Constants.Attributes.COURSE_ID));
            Report report = reportUtil.generate(req.getPathInfo(), getLocale(req));
            resp.setContentType(report.getContentType());
            FileServletUtil.write(report.getFile(), resp);
        } catch (FileProcessingException e) {
            resp.sendError(404);
        }
    }

    protected ReportUtil getUtil(int userId) {
        return new JournalReportUtil(getFileService(), userId);
    }

    private final class JournalReportUtil extends ReportUtil {
        private int courseId;

        public JournalReportUtil(FileService fileService, int courseId) {
            super(fileService);
            this.courseId = courseId;
        }

        @Override
        protected String getTemplateFileName() {
            return "journal";
        }

        @Override
        protected String getReportTitle(String locale) {
            return getCourseService().get(courseId).getTitle();
        }

        @Override
        protected void fillMetaData(FieldsMetadata metadata) {
            metadata.addFieldAsList("students.studentName");
            metadata.addFieldAsList("students.studentEmail");
            metadata.addFieldAsList("students.mark");
        }

        @Override
        protected void fillHead(Map<String, String> head, String locale) {
            head.put("studentName", getTranslator().translate("txt.name", locale));
            head.put("studentEmail", "Email");
            head.put("mark", getTranslator().translate("txt.mark", locale));
        }

        @Override
        protected void fillBody(IContext context) {
            List<JournalEntry> entries = getJournalEntryService().getAllByCourseId(courseId);
            context.put("students", entries);
        }
    }

}
