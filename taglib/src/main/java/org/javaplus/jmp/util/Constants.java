package org.javaplus.jmp.util;

/**
 * This interface defines constants used throughout ny this library.
 * 
 * @author Stephen Suen
 */
public interface Constants {

    /**
     * The base name of localized message resource bundle used by this
     * library.
     */
    public static final String RESOURCE_NAME = "org.javaplus.jmp.Messages";
    /**
     * The name of request attribute for the root {@link javax.mail.Message}
     * instance generated by the message template.
     */
    String ATTR_MESSAGE_ROOT = "org.javaplus.jmp.MESSAGE_ROOT";
    /**
     * The name of request attribute for the stack of 
     * {@link org.javaplus.jmp.taglib.MessagingTagSupport}.
     */
    String ATTR_MESSAGE_TAG_STACK = "org.javaplus.jmp.MESSAGE_TAG_STACK";
}
