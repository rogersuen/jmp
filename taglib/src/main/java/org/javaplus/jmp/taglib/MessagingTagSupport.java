package org.javaplus.jmp.taglib;

import org.javaplus.jmp.util.Constants;
import java.util.LinkedList;
import java.util.logging.Logger;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * <p>
 * <code>MessagingTagSupport</code> is the base class for all messaging tags.
 * All messaging tags are <code>BodyTag</code> instances to allow evaluation
 * of the body contents.</p>
 * <p>
 * <code>MessagingTagSupport</code> extends <code>BodyTagSupport</code>
 * to add support for maintaining the internal stack of messaging tags.
 * <code>MessagingTagSupport</code> also maintains a copy of the body
 * content created by any template text and/or non-messaging custom tag
 * output occurring as the child of a messaging tag. </p>
 * 
 * @author Stephen Suen
 */
public abstract class MessagingTagSupport extends BodyTagSupport
        implements Constants {

    private String bodyContentString;
    /**
     * Logger instance.
     */
    protected static Logger logger = Logger.getLogger(
            MessagingTagSupport.class.getPackage().getName());

    /**
     * Default constructor.
     */
    public MessagingTagSupport() {
        super();
    }

    /**
     * Returns the <code>String</code> representation of evaluated
     * body content created by any template text and/or non-messaging
     * custom tag output occurring as the child of a messaging tag.
     * The value is avalialable only from the end of {@link #doAfterBody()}
     * of this class.
     * 
     * @return the body content as a <code>String</code>
     */
    protected String getBodyContentString() {
        return bodyContentString;
    }

    /**
     * Returns the character encoding used by this tag. Default implementation
     * returns the character encoding used by the parent messaging tag, or
     * if this is the topmost messaging tag, returns the character encoding
     * of the <code>ServletResponse</code> object.
     * 
     * @return the character encoding
     */
    protected String getCharacterEncoding() {
        String charset = null;
        MessagingTagSupport parent = getParentMessagingTag();
        if (parent != null) {
            charset = parent.getCharacterEncoding();
        } else {
            // this is the topmost entity, use ServletResponse
            // character encoding if avalialable
            ServletResponse response = pageContext.getResponse();
            charset = response.getCharacterEncoding();
        }
        return charset;
    }

    /**
     * "Normalize" the specified attribute value by removing leading
     * and trailing whitespaces. This method returns <code>null</code>
     * for a <code>String</code> that is empty or contains only whitespaces.
     * @param value the value to be normalized
     * @return the normalized value
     */
    protected String normalizeAttribute(String value) {
        if (value != null && !(value = value.trim()).isEmpty()) {
            return value;
        } else {
            return null;
        }
    }

    /**
     * Returns the most immediate enclosing {@link MessagingTagSupport}
     * instance of this instance if any; otherwise, return <code>null</code>;
     * @return the parent {@link MessagingTagSupport} instance, or
     *          <code>null</code> if not existed
     */
    protected MessagingTagSupport getParentMessagingTag() {
        ServletRequest request = pageContext.getRequest();
        @SuppressWarnings("unchecked")
        LinkedList<MessagingTagSupport> list =
                (LinkedList<MessagingTagSupport>) request.getAttribute(
                ATTR_MESSAGE_TAG_STACK);

        MessagingTagSupport parent = null;
        if (list != null) {
            int index = list.lastIndexOf(this);
            if (index == -1) {
                // this instance not found, return the top one
                parent = list.getLast();
            } else if (index > 0) {
                parent = list.get(index - 1);
            }
        }
        return parent;
    }

    //
    // Overrided BodyTag methods ---------------------------------------------//
    //
    /**
     * Push the messaing tag to the internal stack. Subclasses that override
     * this method should call this method before further action.
     * @return EVAL_BODY_BUFFERED
     * @throws JspException if an error occurs
     */
    @Override
    public int doStartTag() throws JspException {
        super.doStartTag();
        pushMessagingTag();
        return EVAL_BODY_BUFFERED;
    }

    /**
     * No action.
     * @throws JspException if an error occurs
     */
    @Override
    public void doInitBody() throws JspException {
        super.doInitBody();
    }

    /**
     * Save the evaluated <code>BodyContent</code> as a <code>String</code>.
     * Subclasses that override this method should call this method after
     * its processing before return.
     * 
     * @return SKIP_BODY
     * @throws JspException if an error occurs
     * @see #getBodyContentString()
     */
    @Override
    public int doAfterBody() throws JspException {
        // save the BodyContent as String
        BodyContent bc = getBodyContent();
        if (bc != null) {
            bodyContentString = bc.getString();
            bc.clearBody();
        }

        return SKIP_BODY;
    }

    /**
     * Pop the messaging tag out of the internal messaging tag stack.
     * Subclasses that override this method should call this method after
     * its processing before return.
     * @return EVAL_PAGE
     * @throws JspException if an error occurs
     */
    @Override
    public int doEndTag() throws JspException {
        popMessagingTag();
        return EVAL_PAGE;
    }

    /**
     * Release any resources allocated during the execution of this tag handler.
     * Subclasses that override this method should call this method after
     * its processing before return.
     */
    @Override
    public void release() {
        this.bodyContentString = null;
        super.release();
    }

    //
    // Private methods -------------------------------------------------------//
    //
    /**
     * Push current {@link MessagingTagSupport} instance to our tag stack,
     * create the stack if necessary
     */
    private void pushMessagingTag() {
        ServletRequest request = pageContext.getRequest();
        @SuppressWarnings("unchecked")
        LinkedList<MessagingTagSupport> list =
                (LinkedList<MessagingTagSupport>) request.getAttribute(
                ATTR_MESSAGE_TAG_STACK);
        if (list == null) {
            list = new LinkedList<MessagingTagSupport>();
            request.setAttribute(ATTR_MESSAGE_TAG_STACK, list);
        }
        list.add(this);
    }

    /**
     * Pop exact this {@link MessagingTagSupport} instance off our tag stack,
     * remove the stack if it is empty.
     */
    private void popMessagingTag() {
        ServletRequest request = pageContext.getRequest();
        @SuppressWarnings("unchecked")
        LinkedList<MessagingTagSupport> list =
                (LinkedList<MessagingTagSupport>) request.getAttribute(
                ATTR_MESSAGE_TAG_STACK);

        // if something wrong with nested tag, there could be other
        // tags left in the stack, pop them off together with this
        // instance
        MessagingTagSupport instance = null;
        while (list != null && instance != this) {
            instance = list.removeLast();
            if (list.isEmpty()) {
                request.removeAttribute(ATTR_MESSAGE_TAG_STACK);
                list = null;
            }
        }
    }
}
