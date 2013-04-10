package org.javaplus.jmp.taglib;

import java.util.logging.Level;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.ParseException;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.javaplus.jmp.util.Constants;
import org.javaplus.jmp.util.ContentTypeUtil;
import org.javaplus.jmp.util.MessageUtil;

/**
 * <code>MessageTag</code> is the base class of tag implementations that
 * create a {@link javax.mail.Message} instance. The implmentation will
 * add the created {@link javax.mail.Message} instance to the
 * {@link javax.mail.Part} instance associated  with our most
 * immediate enclosing instance of a tag whose implementaion class is
 * a subclass of {@link PartTag}. If the enclosing tag does not exist,
 * the {@link javax.mail.Message} instance will be saved to the request
 * attribute
 *
 * @author Stephen Suen
 */
public abstract class MessageTag extends PartTagSupport {

    /**
     * If an instance of <code>MessageTag</code> is nested in an outer
     * <code>PartTag</code> instance, and the outer <code>PartTag</code>
     * has not <code>contentType</code> attribute set or the attribute
     * value is unexpected, override it with this constant.
     */
    public static final String DEFAULT_ENCLOSURE_CONTENT_TYPE =
            "message/rfc822";

    /**
     * Creates new instance of tag handler
     */
    public MessageTag() {
        super();
    }

    //
    // Overrided BodyTag method ----------------------------------------------//
    //
    /**
     * Delegate the content preparation of the associated
     * <code>Message</code> instance to our superclass, and add the
     * prepared <code>Message</code> instance to the <code>Part</code>
     * instance associated with the most immediate enclosing
     * <code>PartTag</code>, otherwise, if this is the topmost messaging
     * tag, save the final <code>Message</code> instance to the
     * request attribute.
     * 
     * @return the value returned from superclass
     * @throws JspException if an error occurs
     * @see Constants#ATTR_MESSAGE_ROOT
     */
    @Override
    public int doEndTag() throws JspException {
        // let the superclass prepare content for us
        setContent();

        Message message = (Message) getPart();
        MessagingTagSupport parentTag = getParentMessagingTag();
        if (parentTag == null) {
            // message ready
            try {
                message.saveChanges();
            } catch (MessagingException ex) {
                throw new JspTagException(MessageUtil.getMessage(
                        "messagetag_save_failed"),
                        ex);
            }

            ServletRequest request = pageContext.getRequest();
            request.setAttribute(ATTR_MESSAGE_ROOT, message);

            if (logger.isLoggable(Level.FINE)) {
                logger.fine(MessageUtil.getMessage(
                        "messagetag_message_ready"));
            }
        } else if (parentTag instanceof PartTag) {
            PartTag partTag = (PartTag) parentTag;
            //
            // TODO: should we decide the Content-Type here?
            //
            String type = partTag.getContentType();
            if (type != null) {
                // check whether our containing PartTag instance has an
                // appropriate Content-Type
                String primaryType = null;
                try {
                    primaryType = ContentTypeUtil.getPrimaryType(type);
                } catch (ParseException ex) {
                    // superclass should ensure this never happens
                }
                if (!"message".equalsIgnoreCase(primaryType)) {
                    //
                    // TODO: shoule we throw an exception?
                    //
                    logger.warning(MessageUtil.getMessage(
                            "messagetag_wrong_enclosure_contenttype",
                            type, DEFAULT_ENCLOSURE_CONTENT_TYPE));
                    type = null;
                }
            }

            // if content type is not specified or unexpected
            // primary type is specified, use the default value
            if (type == null)
                type = DEFAULT_ENCLOSURE_CONTENT_TYPE;

            try {
                partTag.getPart().setContent(message, type);
            } catch (MessagingException ex) {
                throw new JspTagException(MessageUtil.getMessage(
                        "messagetag_setcontent_failed", type),
                        ex);
            }
        } else {
            throw new JspException(MessageUtil.getMessage(
                    "messagetag_nesting_error"));
        }

        return super.doEndTag();
    }
}
