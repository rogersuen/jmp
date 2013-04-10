package org.javaplus.jmp.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import javax.mail.internet.ContentType;
import javax.mail.internet.ParseException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * TODO: This class depends on HttpServletResponse. Fix this.
 * @author Stephen Suen
 */
public class MessagingResponse extends HttpServletResponseWrapper
        implements HttpServletResponse {

    private MessagingBuffer buffer;
    private String contentType;
    private String charset;
    private Locale locale;
    private int contentLength;
    private int status = SC_OK;

    public MessagingResponse(ServletResponse response) {
        super((HttpServletResponse)response);
        this.buffer = new MessagingBuffer();
        reset();
    }

    public byte[] toByteArray() {
        return buffer.toByteArray();
    }

    public void addCookie(Cookie cookie) {
    }

    public boolean containsHeader(String name) {
        return false;
    }

    public String encodeURL(String url) {
        return url;
    }

    public String encodeRedirectURL(String url) {
        return url;
    }

    @Deprecated
    public String encodeUrl(String url) {
        return encodeURL(url);
    }

    @Deprecated
    public String encodeRedirectUrl(String url) {
        return encodeRedirectURL(url);
    }

    public void sendError(int sc, String msg) throws IOException {
        setStatus(sc);
        resetBuffer();
    }

    public void sendError(int sc) throws IOException {
        sendError(sc, null);
    }

    public void sendRedirect(String location) throws IOException {
        setStatus(SC_TEMPORARY_REDIRECT);
    }

    public void setDateHeader(String name, long date) {
    }

    public void addDateHeader(String arg0, long arg1) {
    }

    public void setHeader(String arg0, String arg1) {
    }

    public void addHeader(String arg0, String arg1) {
    }

    public void setIntHeader(String arg0, int arg1) {
    }

    public void addIntHeader(String arg0, int arg1) {
    }

    public void setStatus(int sc) {
        this.status = sc;
    }

    /**
     * Returns the status code.
     * @return the status code.
     */
    public int getStatus() {
        return this.status;
    }

    @Deprecated
    public void setStatus(int sc, String msg) {
        setStatus(sc);
    }

    @Override
    public String getCharacterEncoding() {
        return charset;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public void setCharacterEncoding(String charset) {
        this.charset = charset;
    }

    @Override
    public void setContentLength(int length) {
        this.contentLength = length;
    }

    @Override
    public void setContentType(String type) {
        this.contentType = type;

        // check charset from buffer type
        ContentType contentTypeObj = null;
        try {
            contentTypeObj = new ContentType(contentType);
        } catch (ParseException ex) {
        }
        if (contentTypeObj != null) {
            charset = contentTypeObj.getParameter("charset");
        }
    }

    @Override
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return buffer.getOutputStream();
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return buffer.getWriter();
    }

    @Override
    public void flushBuffer() throws IOException {
        buffer.flushBuffer();
    }

    @Override
    public void resetBuffer() {
        buffer.resetBuffer();
    }

    @Override
    public void setBufferSize(int size) {
        buffer.setBufferSize(size);
    }

    @Override
    public int getBufferSize() {
        return buffer.getBufferSize();
    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {
        resetBuffer();
        this.charset = null;
        this.contentLength = 0;
        this.contentType = null;
        this.status = SC_OK;
        this.locale = null;
    }

    public static class MessagingBuffer {

        private ByteArrayOutputStream buffer;
        private PrintWriter writer;
        private ServletOutputStream outputStream;

        public MessagingBuffer() {
            buffer = new ByteArrayOutputStream();
        }

        public byte[] toByteArray() {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                } catch (IOException ex) {
                }
            } else if (writer != null) {
                writer.flush();
            }
            return buffer.toByteArray();
        }

        public ServletOutputStream getOutputStream()
                throws IllegalStateException {
            if (writer != null) {
                throw new IllegalStateException();
            }
            if (outputStream == null) {
                outputStream = new ServletOutputStream() {

                    @Override
                    public void write(int b) throws IOException {
                        buffer.write(b);
                    }
                };
            }
            return outputStream;
        }

        public PrintWriter getWriter() throws IllegalStateException {
            if (outputStream != null) {
                throw new IllegalStateException();
            }
            if (writer == null) {
                writer = new PrintWriter(buffer);
            }
            return writer;
        }

        public void resetBuffer() {
            buffer.reset();
            writer = null;
            outputStream = null;
        }

        public void flushBuffer() throws IOException {
            if (outputStream != null) {
                outputStream.flush();
            } else if (writer != null) {
                writer.flush();
            }
        }

        public int getBufferSize() {
            return 0;
        }

        public void setBufferSize(int size) {
        }
    }
}
