package org.javaplus.jmp.taglib.internet;

import java.io.UnsupportedEncodingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.jsp.JspException;
import org.javaplus.jmp.taglib.PartTag;
import org.javaplus.jmp.util.MessageUtil;

/**
 * <p>
 * <code>InternetAddressTag</code> implements tags that create and add a
 * header field with a value represents a 
 * {@link javax.mail.internet.InternetAddress} list  to the
 * {@link javax.mail.Part} instance associated with our most immediate
 * enclosing instance of a tag whose implementaion class is a subclass
 * of {@link PartTag}.</p>
 * <p>
 * The <code>cs</code> attribute specifies the MIME cs
 * to be used to encoded any personal names found in addresses.</p>
 *
 * @author Stephen Suen
 */
public class InternetAddressTag extends InternetHeaderTag {

    private String address;
    private String personal;

    /**
     * Default constructor.
     */
    public InternetAddressTag() {
    }

    /**
     * Returns the email address.
     * @return email address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the email address.
     * @param address email address
     */
    public void setAddress(String address) {
        this.address = normalizeAttribute(address);
    }

    /**
     * Returns the personal name.
     * @return personal name
     */
    public String getPersonal() {
        return personal;
    }

    /**
     * Sets the personal name.
     * @param personal personal name
     */
    public void setPersonal(String personal) {
        this.personal = normalizeAttribute(personal);
    }

    /**
     * Encodes the specified value. This overrided method returns the value
     * as a <code>String</code> representation of an 
     * {@link javax.mail.internet.InternetAddress} list, after encodes
     * any personal names found in addresses as per RFC 2047. The MIME
     * cs to be used to encode personal names is returned from
     * {@link #getCharacterEncoding()}.
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

        // parse the value into InternetAddress list
        InternetAddress[] ias = null;
        try {
            ias = InternetAddress.parse(value);
        } catch (AddressException ex) {
            throw new JspException(MessageUtil.getMessage(
                    "internetaddresstag_parse_error", getName(), value),
                    ex);
        }

        // encoded personal names if nessessary
        for (int i = 0; i < ias.length; i++) {
            InternetAddress ia = ias[i];
            String cs = null;
            String p = ia.getPersonal();
            if (p != null) {
                // deferred checking context character cs
                cs = getCharacterEncoding();

                // encode personal name
                try {
                    ia.setPersonal(p, cs);
                } catch (UnsupportedEncodingException ex) {
                    throw new JspException(MessageUtil.getMessage(
                            "internetaddresstag_personal_error",
                            getName(), value, p, cs),
                            ex);
                }
            }
        }

        return super.encodeValue(InternetAddress.toString(ias));
    }

    @Override
    public int doEndTag() throws JspException {
        // "address" takes precedence over "value"
        if (address != null) {
            String cs = getCharacterEncoding();
            InternetAddress ia = null;
            try {
                ia = new InternetAddress(address, personal, cs);
            } catch (UnsupportedEncodingException ex) {
                throw new JspException(MessageUtil.getMessage(
                        "internetaddresstag_inetaddr_error",
                        getName(), getValue(), address, personal, cs),
                        ex);
            }
            setValue(ia.toString());
        }

        return super.doEndTag();
    }

    @Override
    public void release() {
        this.address = null;
        this.personal = null;
        super.release();
    }
}
