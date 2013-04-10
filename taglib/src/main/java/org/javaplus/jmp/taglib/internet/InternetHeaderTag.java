package org.javaplus.jmp.taglib.internet;

import org.javaplus.jmp.taglib.HeaderTag;

/**
 * <code>InternetHeaderTag</code> is the base class for all
 * tag implementations that create and add a MIME header field whose value
 * need to be encoded based on the user specified <code>charset</code>
 * attribute.
 *
 * @author Stephen Suen
 */
public abstract class InternetHeaderTag extends HeaderTag {

    private String charset;

    /** 
     * Creates new instance of tag handler 
     */
    public InternetHeaderTag() {
        super();
    }

    /**
     * Returns the MIME charset to be used to encode the header field value
     * as per RFC 2047.
     * @return the charset attribute
     */
    public String getCharset() {
        return charset;
    }

    /**
     * Sets the MIME charset to be used to encode the header field value
     * as per RFC 2047.
     * @param charset the charset attribute
     */
    public void setCharset(String charset) {
        this.charset = normalizeAttribute(charset);
    }

    /**
     * Returns the character encoding to be used to encoded the header
     * field value. This overrided method returns the value of
     * <code>charset</code> attribute if available, otherwise, delegates
     * to the implementaion of superclass.
     *
     * @return the chracter encoding
     */
    @Override
    protected String getCharacterEncoding() {
        if (charset != null) {
            return charset;
        } else {
            return super.getCharacterEncoding();
        }
    }

    @Override
    public void release() {
        this.charset = null;
        super.release();
    }
}
