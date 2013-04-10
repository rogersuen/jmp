package org.javaplus.jmp.taglib.internet;

import javax.mail.Session;
import javax.mail.Part;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimePart;
import org.javaplus.jmp.taglib.MessageTag;

/**
 * <code>MimeMessageTag</code> implements tags that create a
 * {@link javax.mail.internet.MimeMessage} instance.
 * The implmentation will add the created
 * {@link javax.mail.internet.MimeMessage} instance to the
 * {@link javax.mail.Part} instance associated  with our most
 * immediate enclosing instance of a tag whose implementaion class is
 * a subclass of {@link org.javaplus.jmp.taglib.PartTag}. 
 * If the enclosing tag does not exist, the
 * {@link javax.mail.internet.MimeMessage} instance will be saved
 * to the request attribute.
 *
 * @author Stephen Suen
 */
public class MimeMessageTag extends MessageTag implements MimePartTag {

    /** 
     * Creates new instance of tag handler 
     */
    public MimeMessageTag() {
        super();
    }

    /**
     * Create and return an ampty <code>MimeMessage</code> instance.
     * @return an <code>MimeMessage</code> instance.
     */
    @Override
    protected Part createPart() {
        return new MimeMessage((Session) null);
    }

    public MimePart getMimePart() {
        return (MimePart) getPart();
    }
}
