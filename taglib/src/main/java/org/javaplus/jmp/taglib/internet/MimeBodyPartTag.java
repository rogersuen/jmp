package org.javaplus.jmp.taglib.internet;

import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimePart;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.javaplus.jmp.taglib.BodyPartTag;
import org.javaplus.jmp.util.MessageUtil;

/**
 * <p>
 * <code>MimeBodyPartTag</code> implements tags that create and
 * add a {@link javax.mail.internet.MimeBodyPart} instance to the
 * {@link javax.mail.Multipart} instance associated  with our most
 * immediate enclosing instance of a mimePartTag whose implementaion class is
 * a subclass of {@link org.javaplus.jmp.taglib.MultipartTag}.</p>
 * <p>
 * <b>NOTE:</b> As far as I understand, "Content-ID" header field is useful
 * for <code>MimeBodyPart</code> instances only, but not for all
 * <code>MimePart</code> instances, particularly not for 
 * <code>MimeMessage</code> instances. In fact, althought
 * <code>MimeMessage</code> has a <code>setContentID()</code> method
 * defined, but <code>MimePart</code> has not. By now, "Content-ID"
 * is support by <code>MimeBodyPartTag</code>, but not by
 * <code>MimePartTag</code>.</p>
 *
 * @author Stephen Suen
 */
public class MimeBodyPartTag extends BodyPartTag implements MimePartTag {

    private String contentID;

    /** 
     * Creates new instance of mimePartTag handler
     */
    public MimeBodyPartTag() {
        super();
    }

    /**
     * Returns the value of the "Content-ID" header field. Returns
     * <code>null</code> if the field is unavailable or its value
     * is absent.
     *
     * @return the value of the <code>contentID</code> attribute.
     */
    public String getContentID() {
        return contentID;
    }

    /**
     * Set the "Content-ID" header field of this MIME part.
     * @param cid the value of the "Content-ID" header field
     */
    public void setContentID(String cid) {
        this.contentID = normalizeAttribute(cid);
    }

    /**
     * Create and return an ampty <code>MimeBodyPart</code> instance.
     * @return an <code>MimeBodyPart</code> instance.
     */
    @Override
    protected Part createPart() {
        return new MimeBodyPart();
    }

    public MimePart getMimePart() {
        return (MimePart) getPart();
    }

    //
    // Overrided BodyTag method ----------------------------------------------//
    //
    @Override
    public int doEndTag() throws JspException {
        if (contentID != null) {
            // angel bracket if necessary as per RFC
            if (contentID.charAt(0) != '<') {
                contentID = "<" + contentID + ">";
            }
            
            try {
                MimeBodyPart bodyPart = (MimeBodyPart) getPart();
                bodyPart.setContentID(contentID);
            } catch (MessagingException ex) {
                throw new JspTagException(MessageUtil.getMessage(
                        "mimebodyparttag_setcid_failed", contentID),
                        ex);
            }
        }

        return super.doEndTag();
    }

    @Override
    public void release() {
        this.contentID = null;
        super.release();
    }
}
