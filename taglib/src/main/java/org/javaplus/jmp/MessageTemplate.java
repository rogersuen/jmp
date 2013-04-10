package org.javaplus.jmp;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.javaplus.jmp.taglib.MessagingTagSupport;
import org.javaplus.jmp.util.LoggerEnum;
import org.javaplus.jmp.util.MessagingResponse;

/**
 * <p>
 * <code>MessageTemplate</code> implements a simple encapsulation of
 * a JSP page containing messaging tags from our tag library, which is
 * authored as a message template.</p>
 * <p>
 * A typical usage whould simply look something like this: </p>
 * <pre>
 *     MessageTemplate mt = MessageTemplate.getInstance(servletContext, path);
 *     Message message = mt.render(servletRequest, servletResponse);
 * </pre>
 * 
 * @author Stephen Suen
 */
public class MessageTemplate {

    private ServletContext servletContext = null;
    private RequestDispatcher dispatcher = null;
    private String path = null;
    private Logger logger = LoggerEnum.APPLICATION.getLogger();

    /**
     * Default constructor
     */
    protected MessageTemplate() {
    }

    /**
     * Create and return a <code>MessageTemplate</code> instance for the
     * specified context and path.
     * @param context the context that the requested template belongs to
     * @param path    the path to the requested template
     * @return        a <code>MessageTemplate</code> instance or
     *                 <code>null</code> if the specified template not found.
     */
    public static MessageTemplate getInstance(
            ServletContext context,
            String path) {
        RequestDispatcher dispatcher = context.getRequestDispatcher(path);
        if (dispatcher == null) {
            return null;
        }

        MessageTemplate templ = new MessageTemplate();
        templ.servletContext = context;
        templ.path = path;
        templ.dispatcher = dispatcher;
        return templ;
    }

    /**
     * Render and return <code>Message</code> instance from the template
     * for the specified request. The method could possibly return
     * <code>null</code>, even when there's no exception thrown out.
     * For example, when the wrapped target page is not a mail template page.
     * 
     * @param request the request
     * @param response the response
     * @return the rendered <code>Message</code> instance or
     *          <code>null</code> if unable to render a message
     * 
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    public Message render(ServletRequest request, ServletResponse response)
            throws ServletException, IOException {
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("DEBUG: MessageTemplate: rendering message: " + path);
        }

        // invoke the template
        dispatcher.forward(request, new MessagingResponse(response));
        Message message = (Message) request.getAttribute(
                MessagingTagSupport.ATTR_MESSAGE_ROOT);

        if (logger.isLoggable(Level.FINE)) {
            logger.fine("DEBUG: MessageTemplate: message rendered: " + path);
        }

        return message;
    }
}
