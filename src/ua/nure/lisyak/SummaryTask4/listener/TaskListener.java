package ua.nure.lisyak.SummaryTask4.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.lisyak.SummaryTask4.tasks.UpdateFinishedCourses;
import ua.nure.lisyak.SummaryTask4.util.constant.SettingsAndFolderPaths;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Executes tasks (on start and according to the specified schedule).
 */

public final class TaskListener implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskListener.class);
    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        long delay = getDelay(SettingsAndFolderPaths.getUpdateOrdersTaskExecutionTime());
        scheduler = Executors.newSingleThreadScheduledExecutor();
        Runnable updateOrders = new UpdateFinishedCourses(context);
        scheduler.schedule(updateOrders, 0, TimeUnit.MILLISECONDS);
        scheduler.schedule(new DailyTask(updateOrders), delay, TimeUnit.MILLISECONDS);
        LOGGER.info("Daily tasks set up.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }

    private long getDelay(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        Date date = new Date();
        Date now = date;
        String today = dateFormat.format(date);
        String dateToParse = today + " " + time;
        try {
            date = dateTimeFormat.parse(dateToParse);
        } catch (ParseException e) {
            LOGGER.warn("Cannot parse date.", e);
            return 0;
        }
        if (now.getTime() > date.getTime()) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DAY_OF_WEEK, 1);
            date = c.getTime();
        }
        return date.getTime() - now.getTime();
    }

    private final class DailyTask implements Runnable {

        private Runnable[] tasks;

        private DailyTask(Runnable... tasks) {
            this.tasks = tasks;
        }

        @Override
        public void run() {
            for (Runnable task : tasks) {
                scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.DAYS);
            }
        }
    }

}
