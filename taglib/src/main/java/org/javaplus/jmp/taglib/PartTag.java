package org.javaplus.jmp.taglib;

import javax.mail.Part;

/**
 * <p>
 * <code>PartTag</code> defines an interface whose implementations create
 * a {@link javax.mail.Part} instance.</p>
 * <p>
 * <code>PartTag</code> is for the purpose of aligning with the class
 * hierachy of JavaMail API. All <code>PartTag</code> implementations
 * should extend from {@link PartTagSupport}.</p>
 *
 * @author Stephen Suen
 */
public interface PartTag {

    /**
     * Returns the value of the attribute for Content-Type.
     * @return the Content-Type
     */
    public String getContentType();

    /**
     * Set the value of the attribute for Content-Type.
     * @param contentType the Content-Type
     */
    public void setContentType(String contentType);

    /**
     * Returns the value of the attribute for Content-Description.
     * @return the Content-Description
     */
    public String getDescription();

    /**
     * Set the value of the attribute for Content-Description.
     * @param description the Content-Description
     */
    public void setDescription(String description);

    /**
     * Returns the value of the attribute for Content-Disposition.
     * @return the Content-Disposition
     */
    public String getDisposition();

    /**
     * Set the value of the attribute for Content-Disposition.
     * @param disposition the Content-Disposition
     */
    public void setDisposition(String disposition);

    /**
     * <p>
     * Returns the <code>fileName</code> attribute. This attribute is
     * used for the "filename" parameter associated with this part.
     * Useful if this part represents an "attachment" that was loaded from 
     * a resource file. The filename will usually be a simple name, not
     * including directory components.</p>
     * <p>
     * This attribute will be used as the value of the "filename" parameter
     * of the "Content-Disposition" header field of this part.
     * For compatibility with older mailers, the "name" parameter of the
     * "Content-Type" header is also set.</p>
     *
     * @return the filename parameter
     */
    public String getFileName();

    /**
     * <p>
     * Set the <code>fileName</code> attribute. This attribute is
     * used for the "filename" parameter associated with this part.
     * Useful if this part represents an "attachment" that was loaded from
     * a resource file. The filename will usually be a simple name, not
     * including directory components.</p>
     * <p>
     * Sets the "filename" parameter of the "Content-Disposition" header
     * field of this part. For compatibility with older mailers,
     * the "name" parameter of the "Content-Type" header is also set.</p>
     *
     * @param filename the filename
     */
    public void setFileName(String filename);

    /**
     * Returns the {@link javax.mail.Part} instance associated with
     * this tag implementation.
     * @return the part instance
     */
    public Part getPart();
}
