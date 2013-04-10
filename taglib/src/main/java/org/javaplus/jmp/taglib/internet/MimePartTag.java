package org.javaplus.jmp.taglib.internet;

import javax.mail.internet.MimePart;
import org.javaplus.jmp.taglib.PartTag;

/**
 * <code>MimePartTag</code> extends the <code>PartTag</code> interface
 * to add additional RFC822 and MIME specific semantics and attributes.
 * It provides the base interface for the <code>MimeMessageTag</code>
 * and <code>MimeBodyPart</code> classes.
 * 
 * @author Stephen Suen
 */
public interface MimePartTag extends PartTag {

    /**
     * Returns the <code>MimePart</code> instance associated with this
     * tag.
     * @return the <code>MimePart</code> instance.
     */
    public MimePart getMimePart();
}
