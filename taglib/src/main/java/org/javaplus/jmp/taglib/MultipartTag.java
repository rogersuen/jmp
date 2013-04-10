package org.javaplus.jmp.taglib;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.ParseException;
import javax.servlet.jsp.JspException;
import org.javaplus.jmp.util.ContentTypeUtil;
import org.javaplus.jmp.util.MessageUtil;

/**
 * <code>MultipartTag</code> is the base class of tag implementations that
 * create and add a {@link javax.mail.Multipart} instance to the
 * {@link javax.mail.Part} instance associated  with our most
 * immediate enclosing instance of a tag whose implementaion class is
 * a subclass of {@link PartTag}.
 *
 * @author Stephen Suen
 */
public abstract class MultipartTag extends MessagingTagSupport {

    private Multipart multipart;

    /**
     * Create and return a new empty <code>Multipart</code> instance. 
     * Subclasses must implement this method to create associated
     * <code>Multipart</code> instance of approriate type.
     * 
     * @return an <code>Multipart</code> instance
     */
    protected abstract Multipart createMultipart();

    /** 
     * Creates new instance of tag handler 
     */
    public MultipartTag() {
        super();
    }

    /**
     * Returns the <code>Multipart</code> instance associated with
     * this tag.
     * @return the <code>Multipart</code> instance
     */
    public Multipart getMultipart() {
        return multipart;
    }

    //
    // Overrided BodyTag method ----------------------------------------------//
    //
    /**
     * <p>
     * The default implementation of this class creates an empty
     * <code>Multipart</code> instance asoociated with this tag.</p>
     *
     * @return the value returned from superclass
     * @throws JspException if an error occurs
     * @see #createMultipart() 
     */
    @Override
    public int doStartTag() throws JspException {
        int result = super.doStartTag();

        // create an empty Multipart instance
        multipart = createMultipart();

        return result;
    }

    /**
     * The default implementation of this class set the prepared
     * <code>Multipart</code> instance as content of the <code>Part</code>
     * instance associated with the most immediate enclosing
     * <code>PartTag</code>.
     *
     * @return the value returned from superclass
     * @throws JspException if an error occurs
     */
    @Override
    public int doEndTag() throws JspException {
        MessagingTagSupport parentTag = getParentMessagingTag();
        if (parentTag == null || !(parentTag instanceof PartTag)) {
            throw new JspException(MessageUtil.getMessage(
                    "multiparttag_nesting_error"));
        }

        PartTag partTag = (PartTag) parentTag;
        String contentType = partTag.getContentType();
        if (contentType != null) {
            String primaryType = null;
            try {
                primaryType = ContentTypeUtil.getPrimaryType(contentType);
            } catch (ParseException ex) {
                // PartTagSupport implementation ensures the contentType
                // has been validated, so, this should never happen
            }
            if (!"multipart".equalsIgnoreCase(primaryType)) {
                logger.warning(MessageUtil.getMessage(
                        "multiparttag_wrong_enclosure_contenttype",
                        contentType));
            }
        }

        // set this Multipart as the content of parent Part
        try {
            ((PartTag) parentTag).getPart().setContent(multipart);
        } catch (MessagingException ex) {
            throw new JspException(MessageUtil.getMessage(
                    "multiparttag_setcontent_failed"),
                    ex);
        }

        return super.doEndTag();
    }

    @Override
    public void release() {
        this.multipart = null;
        super.release();
    }
}
