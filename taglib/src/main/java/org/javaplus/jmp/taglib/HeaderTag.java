package org.javaplus.jmp.taglib;

import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.MimeUtility;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.javaplus.jmp.util.MessageUtil;

/**
 * <code>HeaderTag</code> implements tags that create and add a header
 * field to the {@link javax.mail.Part} instance associated  with our most
 * immediate enclosing instance of a tag whose implementaion class is
 * a subclass of {@link PartTag}.
 *
 * @see org.javaplus.jmp.taglib.PartTag
 * @see javax.mail.Part
 * 
 * @author Stephen Suen
 */
public class HeaderTag extends MessagingTagSupport {

    private String name = null;
    private String value = null;

    /** 
     * Creates new instance of tag handler 
     */
    public HeaderTag() {
        super();
    }

    /**
     * Returns the header field name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the header field name to the speicified value.
     * @param name the new header name
     */
    public void setName(String name) {
        this.name = normalizeAttribute(name);
    }

    /**
     * Returns the header field value.
     * @return the value
     * @throws JspException if error occurred.
     */
    public String getValue() throws JspException {
        return value;
    }

    /**
     * Sets the header field value to the specified value.
     * @param value the new value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * <p>
     * Encodes the specified value if necessary. Subclasses should override
     * this method to provide necessary encoding required for the particular
     * header fields, and then call this method before returning. </p>
     * <p>
     * The algorithm used here is:</p>
     * <ul>
     * <li>Remove any leading and trailing whitespaces.</li>
     * <li>Replace every line break character , including <code>'\r'</code>
     * and <code>'\n'</code>, with a space character.
     * </li>
     * <li>Fold the result string by calling
     * {@link javax.mail.internet.MimeUtility#fold(int,String)}</li>
     * </ul>
     * <p>
     * This method will be called by {@link #doEndTag()} method to encode
     * the specified <code>value</code> attribute before adding the header
     * field to the enclosing <code>Part</code> instance.</p>
     *
     * @param value
     * @return the encoded value
     * @throws JspException if the value cannot be encoded
     * @see #doEndTag() 
     */
    protected String encodeValue(String value) throws JspException {
        // remove leading and trailing whitespaces
        value = normalizeAttribute(value);
        if (value == null) {
            return null;
        }

        // replace any line break with space
        StringBuffer sb = new StringBuffer(value.length());
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == '\r' || c == '\n') {
                sb.append(' ');
            } else {
                sb.append(c);
            }
        }

        value = sb.toString();
        int used = this.name.length();
        return MimeUtility.fold(used, value);
    }

    //
    // Overrided BodyTag method ----------------------------------------------//
    //
    /**
     * Performs any necessary validation on the <code>name</code> and
     * <code>value</code> attribute. An empty value or a value containing
     * only whitespaces is not allowed for both <code>name</code> and
     * <code>value</code> attribute. An <code>JspException</code>
     * will be thrown to indicate such an error.
     *
     * @return the value returned from the superclass
     * @throws JspException if an error occurs
     */
    @Override
    public int doStartTag() throws JspException {
        int result = super.doStartTag();

        if (name == null) {
            throw new JspTagException(MessageUtil.getMessage(
                    "headertag_null_name", name, value));
        }

        return result;
    }

    @Override
    public int doEndTag() throws JspException {
        // if value attribute not set, use body content as value
        if (value == null) {
            value = getBodyContentString();
        }

        value = encodeValue(value);
        if (value == null) {
            throw new JspTagException(MessageUtil.getMessage(
                    "headertag_null_value", name, value));
        }

        // add this header field to the Part instance associated with our 
        // most immediate enclosing PartTag instance if any,
        // otherwise, throw exception indicating the nesting error.
        MessagingTagSupport parentTag = getParentMessagingTag();
        if (parentTag == null || !(parentTag instanceof PartTag)) {
            throw new JspException(MessageUtil.getMessage(
                    "headertag_nesting_error", name, value));
        } else {
            Part part = ((PartTag) parentTag).getPart();
            try {
                part.addHeader(name, value);
            } catch (MessagingException ex) {
                throw new JspException(MessageUtil.getMessage(
                        "headertag_addheader_failed", name, value),
                        ex);
            }
        }

        return super.doEndTag();
    }

    @Override
    public void release() {
        this.name = null;
        this.value = null;
        super.release();
    }
}
