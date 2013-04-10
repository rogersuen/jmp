package org.javaplus.jmp.util;

import javax.mail.internet.ContentType;
import javax.mail.internet.ParseException;

/**
 * A utility class ued to parse a <code>String</code> represents the value
 * of a Content-Type header field.
 * 
 * @author Stephen Suen
 */
public class ContentTypeUtil {

    /**
     * Returns the primary type of the Content-Type represented by the
     * specified <code>String</code>.
     * @param type the content type
     * @return the primary type
     */
    public static String getPrimaryType(String type) throws ParseException {
        if (type == null)
            throw new NullPointerException("null Content-Type");

        ContentType obj = new ContentType(type);
        return obj.getPrimaryType();
    }

    /**
     * Returns the subtype of the Content-Type represented by the
     * specified <code>String</code>.
     * @param  type the content type
     * @return the subtype, or <code>null</code> if no subtype
     */
    public static String getSubType(String type) throws ParseException {
        if (type == null)
            throw new NullPointerException("null Content-Type");

        ContentType obj = new ContentType(type);
        return obj.getSubType();
    }

    /**
     * Returns the value of the parameter with the specified name in the
     * specified content type
     * @param  type the content type
     * @param  name the name of the parameter
     * @return the value of the parameter, or <code>null</code> if
     *          not specified
     * @throws ParseException if parsing failed
     */
    public static String getParameter(String type, String name)
            throws ParseException {
        if (type == null)
            throw new NullPointerException("null Content-Type");
        else if (name == null)
            throw new NullPointerException("null parameter name");

        ContentType obj = new ContentType(type);
        return obj.getParameter(name);
    }

    /**
     * Returns the value of the parameter with the name of "charset" in the
     * specified content type.
     * @param  type the content type
     * @return the value of the "charset" parameter, or <code>null</code>
     *          if not specified
     */
    public static String getCharset(String type) throws ParseException {
        return getParameter(type, "charset");
    }
}
