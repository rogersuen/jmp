package org.javaplus.jmp.taglib.internet;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeMultipart;
import javax.servlet.jsp.JspException;
import org.javaplus.jmp.taglib.MultipartTag;
import org.javaplus.jmp.taglib.PartTag;
import org.javaplus.jmp.util.MessageUtil;

/**
 * <code>MimeMultipartTag</code> implements tags that create and add
 * a {@link javax.mail.internet.MimeMultipart} instance to the
 * {@link javax.mail.Part} instance associated  with our most
 * immediate enclosing instance of a tag whose implementaion class is
 * a subclass of {@link PartTag}.
 *
 * @author Stephen Suen
 */
public class MimeMultipartTag extends MultipartTag {

    private String subtype;

    /**
     * Creates new instance of tag handler 
     */
    public MimeMultipartTag() {
        super();
    }

    /**
     * Get the subtype.
     * @return the subtype
     */
    public String getSubtype() {
        return subtype;
    }

    /**
     * Set subtype.
     * @param subtype the subtype
     */
    public void setSubtype(String subtype) {
        this.subtype = normalizeAttribute(subtype);
    }

    /**
     * Create and return an ampty <code>MimeMultipart</code> instance.
     * @return an <code>MimeMultipart</code> instance.
     */
    @Override
    protected Multipart createMultipart() {
        return new MimeMultipart();
    }

    //
    // Overrided BodyTag method ----------------------------------------------//
    //
    @Override
    public int doStartTag() throws JspException {
        int result = super.doStartTag();

        // Set the subtype on newly created instance as per
        // MimeMultipart.setSubType() API:
        //
        //    Set the subtype. This method should be invoked only on a new
        //    MimeMultipart object created by the client. The default subtype
        //    of such a multipart object is "mixed".
        // 
        if (subtype != null) {
            MimeMultipart mmp = (MimeMultipart) getMultipart();
            try {
                mmp.setSubType(subtype);
            } catch (MessagingException ex) {
                throw new JspException(MessageUtil.getMessage(
                        "mimemultiparttag_subtype_failed", subtype),
                        ex);
            }
        }

        return result;
    }

    @Override
    public void release() {
        this.subtype = null;
        super.release();
    }
}
