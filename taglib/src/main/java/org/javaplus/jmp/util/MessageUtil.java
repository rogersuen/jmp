package org.javaplus.jmp.util;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * The utility used to return localized messages.
 * @author Stephen Suen
 */
public class MessageUtil implements Constants {

    /**
     * Returns the localized message for the specified message ID.
     * @param id the message id
     * @return the localized message
     */
    public static String getMessage(String id) {
        return getMessage(id, (Object[]) null);
    }

    /**
     * Returns the localized message for the specified message ID, formatted
     * with the specified parameters.
     * @param id the message id
     * @param params format parameters
     * @return the localized message
     */
    public static String getMessage(String id, Object... params) {
        String message = id;
        try {
            ResourceBundle rb = ResourceBundle.getBundle(RESOURCE_NAME);
            message = rb.getString(message);
        } catch (MissingResourceException ex) {
            // ignore
        }

        if (params != null) {
            MessageFormat mf = new MessageFormat(message);
            message = mf.format(params);
        }

        return message;
    }
}
