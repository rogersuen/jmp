package org.javaplus.jmp.taglib;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.activation.DataHandler;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.ContentType;
import javax.mail.internet.ParseException;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.javaplus.jmp.util.MessageUtil;
import org.javaplus.jmp.util.MessagingResponse;

/**
 * <p>
 * <code>PartTagSupport</code> is the base class for all messaging tags
 * that create an associated instance of {@link javax.mail.Part}.</p>
 * <p>
 * <code>PartTagSupport</code> provides the support to most important
 * header fields by tag attributes, especially for Content-Type and
 * Content-Disposition. <code>PartTagSupport</code> also provides
 * subclasses the facilities to prepare the content from the specified
 * URL or the body content.</p>
 *
 * @author Stephen Suen
 */
public abstract class PartTagSupport extends MessagingTagSupport
        implements PartTag {
    //
    // Tag attributes --------------------------------------------------------//
    //

    private String contentType;
    private String description;
    private String disposition;
    private String filename;
    private String url;
    private String context;
    //
    // Private used members --------------------------------------------------//
    //
    private boolean isAbsoluteUrl;
    private String charset;
    private Part part;

    /**
     * Creates new instance of tag handler
     */
    public PartTagSupport() {
        super();
    }

    /**
     * Create and return an instance of <code>Part</code> associated with
     * this messaging tag. 
     * @return the created <code>Part</code> instance.
     * @see #getPart() 
     */
    protected abstract Part createPart();

    public Part getPart() {
        return part;
    }

    //
    // Getters/Setters for attributes ----------------------------------------//
    //
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = normalizeAttribute(contentType);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        // no need to "normailize"
        this.description = description;
    }

    public String getDisposition() {
        return disposition;
    }

    public void setDisposition(String disposition) {
        this.disposition = normalizeAttribute(disposition);
    }

    public String getFileName() {
        return filename;
    }

    public void setFileName(String filename) {
        this.filename = normalizeAttribute(filename);
    }

    /**
     * Sets the URL of the resource to import.
     * @param url the URL
     */
    public void setUrl(String url) {
        this.url = normalizeAttribute(url);
    }

    /**
     * Returns the URL of the resource to import.
     * @return the URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * Returns the name of the context when accessing a relative
     * URL resource that belongs to a foreign context.
     * @return the name of the context.
     */
    public String getContext() {
        return context;
    }

    /**
     * Sets the name of the context when accessing a relative
     * URL resource that belongs to a foreign context.
     * @param context the name of the context
     */
    public void setContext(String context) {
        this.context = normalizeAttribute(context);
    }

    //
    // Protected methods -----------------------------------------------------//
    //
    /**
     * <p>
     * Set the appropriate content to the associated <code>Part</code>
     * instance if the content has not been set by any nested tag.
     * Subclasses must call this method to prepare the content before 
     * adding their associated <code>Part</code> instances to
     * the enclosing entity associated with their enclosing tags.</p>
     * <p>
     * The overall strategy implemented by this method is:</p>
     * <ul>
     * <li>
     * If the content of associated <code>Part</code> instance is not
     * <code>null</code>, no content will be set by this method</li>
     * <li>
     * If <code>url</code> is specified, the resource located at the
     * URL specified by <code>url</code> and optional <code>context</code>
     * attribute will be loaded and set as the content. The Content-Type
     * determined from the resource will override the one specified by
     * the "contentType" attribute.</li>
     * <li>
     * Otherwise, set the text returned from the method
     * {@link #getBodyContentString()} as the content with the Content-Type
     * specified by the <code>contentType</code> attribute.</li>
     * </ul>
     * 
     * @throws JspException if an error occurs
     * @see #doEndTag()
     * @see #getBodyContentString() 
     */
    protected void setContent() throws JspException {
        // set Content-Description if necessary
        if (description != null) {
            try {
                part.setDescription(description);
            } catch (MessagingException ex) {
                throw new JspException(MessageUtil.getMessage(
                        "parttagsupport_setdescription_failed", description),
                        ex);
            }
        }

        // set Content-Disposition if necessary
        if (disposition != null) {
            try {
                part.setDisposition(disposition);
            } catch (MessagingException ex) {
                throw new JspException(MessageUtil.getMessage(
                        "parttagsupport_setdisposition_failed", disposition),
                        ex);
            }
        }

        // check whether content has been set by any nested tag
        // NOTE:
        // an empty content could probably throw an exception,
        // ignore it
        boolean isContentSet = false;
        try {
            isContentSet = (part.getContent() != null);
        } catch (IOException ex) {
        } catch (MessagingException ex) {
        }

        if (isContentSet) {
            // content has been by nested tags (for example, MultipartTag)
        } else if (url != null) {
            setUrlContent();
        } else {
            setBodyContent();
        }

        // set the filename parameter if needed
        if (filename != null) {
            try {
                part.setFileName(filename);
            } catch (MessagingException ex) {
                throw new JspTagException(MessageUtil.getMessage(
                        "parttagsupport_setfilename_failed", filename),
                        ex);
            }
        }
    }

    /**
     * Returns the character encoding. This overrided method returns
     * the value of <code>charset</code> parameter specified in
     * the <code>contentType</code> attribute if available,
     * otherwise, delegates to the implementaion of superclass.
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

    //
    // Overrided BodyTag method ----------------------------------------------//
    //
    /**
     * Performs any necessary validation on the attributes specified by
     * this implementation, and creates an empty <code>Part</code>
     * instance associated with this tag.
     * 
     * @return the value returned from
     *          {@link MessagingTagSupport#doStartTag()}
     * @throws JspException if an error occurs
     * @see #createPart() 
     */
    @Override
    public int doStartTag() throws JspException {
        int result = super.doStartTag();

        // validate the following attributes:
        // - url
        // - context
        // - contentType
        // - disposition
        if (context != null) {
            if (url == null) {
                // standalone "context" attribute is not allowed
                throw new JspTagException(MessageUtil.getMessage(
                        "parttagsupport_context_without_url"));
            } else if (!context.startsWith("/") || !url.startsWith("/")) {
                // in the case of relative URL in a foreign context,
                // values of both "context" and "url" attribute must
                // start with "/"
                throw new JspTagException(MessageUtil.getMessage(
                        "parttagsupport_bad_relative"));
            }
        }

        isAbsoluteUrl = isAbsoluteUrl(url);

        //
        // TODO: should me do like this?
        //
        // Although Content-Type can technically default to some value,
        // and even nested tags could potentially override the value
        // specified here, we enforce a Content-Type attribute
        // must be specified to avaid common mistakes that a missing
        // Content-Type leads to an ugly message to be preduced.
//        if (contentType == null) {
//            throw new JspTagException(MessageUtil.getMessage(
//                    "parttagsupport_contenttype_required"));
//        }

        // verify and "normalize" Content-Type, determine charset if
        // specified
        if (contentType != null) {
            ContentType contentTypeObj = null;
            try {
                contentTypeObj = new ContentType(contentType);
            } catch (ParseException ex) {
                throw new JspTagException(MessageUtil.getMessage(
                        "parttagsupport_invalid_contenttype", contentType),
                        ex);
            }
            charset = contentTypeObj.getParameter("charset");
            contentType = contentTypeObj.toString();
        }

        // "normalize" standard Content-Disposition value
        if (Part.ATTACHMENT.equalsIgnoreCase(disposition)) {
            disposition = Part.ATTACHMENT;
        } else if (Part.INLINE.equalsIgnoreCase(disposition)) {
            disposition = Part.INLINE;
        }

        // create an empty Part instance
        part = createPart();

        return result;
    }

    /**
     * <p>
     * Subclasses should override this method to prepare the content of
     * the <code>Part</code> instance associated with the tag by calling
     * {@link #setContent()} method, and add the fully processed instance
     * to the enclosing entity aasociated with the enclosing messaing
     * tag if any.</p>
     * <p>
     * The default implementation of this class simply calls the superclass's
     * implementaion</p>
     * 
     * @return the value returned from superclass
     * @throws JspException if an error occurs
     * @see #setContent() 
     */
    @Override
    public int doEndTag() throws JspException {
        return super.doEndTag();
    }

    @Override
    public void release() {
        this.contentType = null;
        this.description = null;
        this.disposition = null;
        this.filename = null;
        this.url = null;
        this.context = null;

        this.isAbsoluteUrl = false;
        this.charset = null;
        this.part = null;

        super.release();
    }

    //
    // Private methods -------------------------------------------------------//
    //
    private void setBodyContent() throws JspException {
        String text = getBodyContentString();
        if (text != null) {
            try {
                // use tag attribute for Content-Type header field
                // any Content-Type header added by nested tags will
                // be ignored
                if (contentType == null) {
                    logger.warning(MessageUtil.getMessage(
                            "parttagsupport_missing_contenttype"));
                    part.setText(text);
                } else {
                    part.setContent(text, contentType);
                }
            } catch (MessagingException ex) {
                throw new JspTagException(MessageUtil.getMessage(
                        "parttagsupport_settext_failed", getContentType()),
                        ex);
            }
        }
    }

    private void setUrlContent() throws JspException {
        if (url == null) {
            return;
        } else if (isAbsoluteUrl) {
            setAbsUrlContent();
        } else {
            setRelUrlContent();
        }
    }

    private void setRelUrlContent() throws JspException {
        RequestDispatcher rd = null;
        if (context == null) {
            rd = pageContext.getRequest().getRequestDispatcher(url);
        } else {
            ServletContext sc =
                    pageContext.getServletContext().getContext(context);
            if (sc == null) {
                throw new JspTagException(MessageUtil.getMessage(
                        "parttagsupport_context_not_found", context));
            }
            rd = sc.getRequestDispatcher(url);
        }

        if (rd == null) {
            throw new JspTagException(MessageUtil.getMessage(
                    "parttagsupport_url_not_found", context, url));
        }

        MessagingResponse response =
                new MessagingResponse(pageContext.getResponse());
        try {
            rd.forward(pageContext.getRequest(), response);
        } catch (ServletException ex) {
            Throwable rootCause = ex.getRootCause();
            if (rootCause == null) {
                rootCause = ex;
            }
            throw new JspTagException(MessageUtil.getMessage(
                    "parttagsupport_relurl_exception",
                    context, url, rootCause.getMessage()),
                    rootCause);
        } catch (Throwable ex) {
            throw new JspTagException(MessageUtil.getMessage(
                    "parttagsupport_relurl_exception",
                    context, url, ex.getMessage()),
                    ex);
        }

        // make sure an 2XX status (indicating success)
        int sc = response.getStatus();
        if (sc < 200 || sc > 299) {
            throw new JspTagException(MessageUtil.getMessage(
                    "parttagsupport_relurl_non_success_status",
                    context, url, sc));
        }

        // determine the Content-Type
        String type = response.getContentType();
        if (type == null) {
            // should never happen
            type = contentType;
        } else if ((contentType != null) &&
                !type.equalsIgnoreCase(contentType)) {
            //
            // TODO: should we throw an exception?
            //
            logger.warning(MessageUtil.getMessage(
                    "parttagsupport_wrong_contenttype",
                    contentType, type, url, context));
        }

        byte[] buf = response.toByteArray();
        ByteArrayDataSource ds = new ByteArrayDataSource(buf, type);
        try {
            part.setDataHandler(new DataHandler(ds));
        } catch (MessagingException ex) {
            throw new JspTagException(MessageUtil.getMessage(
                    "parttagsupport_relurl_content_failed",
                    contentType, url, context),
                    ex);
        }
    }

    private void setAbsUrlContent() throws JspException {
        URL urlObj = null;
        try {
            urlObj = new URL(url);
        } catch (MalformedURLException ex) {
            throw new JspTagException(MessageUtil.getMessage(
                    "parttagsupport_absurl_malformed", url),
                    ex);
        }

        DataHandler dh = null;
        dh = new DataHandler(urlObj);
        try {
            part.setDataHandler(dh);
        } catch (MessagingException ex) {
            throw new JspTagException(MessageUtil.getMessage(
                    "parttagsupport_absurl_content_failed", url),
                    ex);
        }

        // verify the Content-Type
        String type = dh.getContentType();
        if ((contentType != null) && !contentType.equalsIgnoreCase(type)) {
            //
            // TODO: should we throw an exception?
            //
            logger.warning(MessageUtil.getMessage(
                    "parttagsupport_wrong_contenttype",
                    contentType, type, url, context));
        }
    }

    //
    // Private helpers -------------------------------------------------------//
    //
    /**
     * The valid characters in a URL scheme part.
     */
    private static final String VALID_SCHEME_CHARS =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+.-";

    /**
     * <p>
     * Check whether the specified URL represents an absolute URL.</p>
     * <p>
     *  An absolute URI is defined in RFC 2396 as following:</p>
     * <pre>
     * An absolute URI contains the name of the scheme being used (<scheme>)
     * followed by a colon (":") and then a string (the
     * <scheme-specific-part>) whose interpretation depends on the scheme.
     * </pre>
     * 
     * <pre>
     * Scheme names consist of a sequence of characters beginning with a
     * lower case letter and followed by any combination of lower case
     * letters, digits, plus ("+"), period ("."), or hyphen ("-").  For
     * resiliency, programs interpreting URI should treat upper case letters
     * as equivalent to lower case in scheme names (e.g., allow "HTTP" as
     * well as "http").
     *
     *     scheme        = alpha *( alpha | digit | "+" | "-" | "." )
     * 
     * </pre>
     * 
     * @param url the URL to check
     * @return <code>true</code> if the specified URL is an absolute URL,
     *          <code>false</code> otherwise.
     */
    private static boolean isAbsoluteUrl(String url) {
        if (url == null)
            return false;

        int iColon = url.indexOf(':');
        if (iColon == -1)
            return false;

        for (int i = 0; i < iColon; i++) {
            if (VALID_SCHEME_CHARS.indexOf(url.charAt(i)) == -1)
                return false;
        }

        return true;
    }
}
