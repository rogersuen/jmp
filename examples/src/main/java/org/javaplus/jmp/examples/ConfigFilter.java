package org.javaplus.jmp.examples;

import java.io.IOException;
import javax.mail.Session;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConfigFilter implements Filter {

    private FilterConfig filterConfig;
    private String configPage;

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        ServletContext context = filterConfig.getServletContext();
        Session mailSession = (Session) context.getAttribute("mail.session");
        if (mailSession == null) {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            String path = req.getServletPath();
            if (!path.contains("config.jsp")) {
                resp.sendRedirect(configPage);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
        this.filterConfig = config;
        this.configPage = config.getServletContext().getContextPath() +
                "/config.jsp";
    }

    public void destroy() {
        this.filterConfig = null;
        this.configPage = null;
    }
}
