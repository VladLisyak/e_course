package ua.nure.lisyak.SummaryTask4.listener;

import net.sf.ehcache.CacheManager;
import ua.nure.lisyak.SummaryTask4.db.holder.ThreadLocalConnectionHolder;
import ua.nure.lisyak.SummaryTask4.db.manager.HikariCPManager;
import ua.nure.lisyak.SummaryTask4.util.constant.SettingsAndFolderPaths;
import ua.nure.lisyak.SummaryTask4.util.context.ContextLoader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * ContextLoadListener class.
 *
 */

public final class ContextLoadListener implements ServletContextListener {

    private ContextLoader loader;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        loader = new ContextLoader(
                new ThreadLocalConnectionHolder(), new HikariCPManager(), sce.getServletContext(), CacheManager.create(SettingsAndFolderPaths.getEhcacheConfig())
        );
        loader.load(
                "ua.nure.lisyak.SummaryTask4.repository",
                "ua.nure.lisyak.SummaryTask4.service"
        );
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        loader.destroy(sce.getServletContext());
    }

}
