package main;

import  frontend.Frontend;

        import org.eclipse.jetty.rewrite.handler.RedirectRegexRule;
        import org.eclipse.jetty.rewrite.handler.RewriteHandler;
        import org.eclipse.jetty.server.Handler;
        import org.eclipse.jetty.server.Server;
        import org.eclipse.jetty.server.handler.HandlerList;
        import org.eclipse.jetty.server.handler.ResourceHandler;
        import org.eclipse.jetty.servlet.ServletContextHandler;
        import org.eclipse.jetty.servlet.ServletHolder;

        import javax.servlet.Servlet;

/**
 * Created by Denis on 19.02.14.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Servlet frontend = new Frontend();

        Server server = new Server(7777);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(frontend), "/*"); /* servlet for all URL */

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(false);
        resource_handler.setResourceBase("static");

        RewriteHandler rewriteHandler = new RewriteHandler();
        rewriteHandler.setRewriteRequestURI(true);
        rewriteHandler.setRewritePathInfo(true);
        rewriteHandler.setOriginalPathAttribute("requestedPath");
        RedirectRegexRule rule = new RedirectRegexRule();
        rule.setRegex("/");
        rule.setReplacement("/index");
        rewriteHandler.addRule(rule);

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{rewriteHandler, resource_handler, context});
        server.setHandler(handlers);

        server.start();
        server.join();
    }
}
