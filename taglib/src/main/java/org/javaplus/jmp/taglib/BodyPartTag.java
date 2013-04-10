package org.javaplus.jmp.taglib;

import java.util.logging.Level;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.servlet.jsp.JspException;
import org.javaplus.jmp.util.MessageUtil;

/**
 * <code>BodyPartTag</code> is the base class of tag implementations that
 * create and add a {@link javax.mail.BodyPart} instance to the
 * {@link javax.mail.Multipart} instance associated  with our most
 * immediate enclosing instance of a tag whose implementaion class is
 * a subclass of {@link MultipartTag}.
 *
 * @author Stephen Suen
 */
public abstract class BodyPartTag extends PartTagSupport {

    /** 
     * Creates new instance of tag handler 
     */
    public BodyPartTag() {
        super();
    }

    //
    // Overrided BodyTag method ----------------------------------------------//
    //
    /**
     * Delegate the content preparation of the associated
     * <code>BodyPart</code> instance to our superclass, and add the
     * prepared <code>BodyPart</code> instance to the
     * <code>Multipart</code> instance associated with the most immediate
     * enclosing <code>MultipartTag</code>.
     *
     * @return the value returned from superclass
     * @throws JspException if an error occurs
     */
    @Override
    public int doEndTag() throws JspException {
        // let the superclass prepare content for us
        setContent();

        // add the associated BodyPart instance to the Multipart
        // instance associated with the enclosing MultipartTag
        BodyPart bodyPart = (BodyPart) getPart();
        MessagingTagSupport parentTag = getParentMessagingTag();
        if (parentTag == null || !(parentTag instanceof MultipartTag)) {
            throw new JspException(MessageUtil.getMessage(
                    "bodyparttag_nesting_error"));
        }

        try {
            ((MultipartTag) parentTag).getMultipart().addBodyPart(bodyPart);
        } catch (MessagingException ex) {
            throw new JspException(MessageUtil.getMessage(
                    "bodyparttag_addbodypart_failed"),
                    ex);
        }

        return super.doEndTag();
    }
}
