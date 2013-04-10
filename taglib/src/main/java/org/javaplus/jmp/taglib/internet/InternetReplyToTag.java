package org.javaplus.jmp.taglib.internet;

import javax.servlet.jsp.JspException;
import org.javaplus.jmp.taglib.MessageTag;
import org.javaplus.jmp.taglib.MessagingTagSupport;
import org.javaplus.jmp.util.MessageUtil;

/**
 * <code>InternetReplyToTag</code> implements tags that create and add a
 * "Reply-To" header field to the {@link javax.mail.Message} instance
 * associated with our most immediate enclosing instance of a tag whose
 * implementaion class is a subclass of {@link MessageTag}.
 * 
 * @author Stephen Suen
 */
public class InternetReplyToTag extends InternetAddressTag {

    /**
     * The constant name of this header field.
     */
    public static final String HEADER_NAME = "Reply-To";

    /**
     * Default constructor.
     */
    public InternetReplyToTag() {
        super();
        super.setName(HEADER_NAME);
    }

    /**
     * Sets the new name of this header field. This method does nothing
     * because the name of this header field must always be "Reply-To".
     * @param name the new name
     */
    @Override
    public void setName(String name) {
        // no-op, always be "Reply-To"
    }

    @Override
    public int doEndTag() throws JspException {
        MessagingTagSupport parentTag = getParentMessagingTag();
        if (parentTag == null || !(parentTag instanceof MessageTag)) {
            throw new JspException(MessageUtil.getMessage(
                    "internetreplytotag_nesting_error", getName(), getValue()));
        }

        return super.doEndTag();
    }
}
