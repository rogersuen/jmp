package org.javaplus.jmp.taglib.internet;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import javax.mail.internet.MimeUtility;
import javax.servlet.jsp.JspException;
import org.javaplus.jmp.taglib.PartTag;
import org.javaplus.jmp.util.MessageUtil;

/**
 * <code>InternetTextHeaderTag</code> implements tags that create and add
 * a "unstructured" text header field to the {@link javax.mail.Part}
 * instance associated with our most immediate enclosing instance of a tag
 * whose implementaion class is a subclass of {@link PartTag}.
 *
 * @author Stephen Suen
 */
public class InternetTextHeaderTag extends InternetHeaderTag {

    public InternetTextHeaderTag() {
        super();
    }

    /**
     * Encodes the specified value. This overrided method uses
     * {@link MimeUtility#encodeText(String, String, String) } 
     * to encode the specified value. The MIME charset to be used to
     * encode the value is returned from {@link #getCharacterEncoding()};
     * the <code>encoding</code> parameter passed to
     * {@link MimeUtility#encodeText(String, String, String) } is
     * <code>null</code>.
     *
     * @return the encoded value
     * @throws JspException if the value cannot be encoded
     * @see #getCharacterEncoding()
     */
    @Override
    public String encodeValue(String value) throws JspException {
        if (value == null) {
            return value;
        }

        String encodedValue = null;
        String encoding = getCharacterEncoding();
        try {
            encodedValue = MimeUtility.encodeText(
                    value,
                    encoding,
                    null);
        } catch (UnsupportedEncodingException ex) {
            String msgId = "internettextheadertag_unsupportencoding";
            LogRecord log = new LogRecord(Level.SEVERE, msgId);
            Object[] params = new Object[]{
                getName(), value, encoding
            };
            log.setParameters(params);
            log.setThrown(ex);
            logger.log(log);

            throw new JspException(MessageUtil.getMessage(
                    "internettextheadertag_unsupportencoding",
                    getName(), value, encoding),
                    ex);
        }

        return super.encodeValue(encodedValue);
    }
}
