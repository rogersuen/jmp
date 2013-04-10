<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JavaMail Pages Tag Library - Advanced Examples</title>
  </head>
  <body>
    <%@include file="/WEB-INF/jspf/header.jspf" %>
    <h2>Advanced Examples</h2>
    <p>
      This section contains a collection of JSP pages that demonstrate
      advanced topics of JavaMail Pages (JMP) tag library. A strong
      understanding of JavaMail API and MIME message helps a lot to
      follow these examples:
    </p>

    <h3>
      <span>Multipart Message</span>
      <a href="../showSource.jsp?filenames=/advanced/multipart.jsp,/advanced/resource/html.jsp,/advanced/resource/plain.jsp,/send.jsp"><img
        src="../images/code.gif" title="Source Code" width="20" height="20" border="0"></a>
      <a href="../send.jsp?template=/advanced/multipart.jsp"><img
        src="../images/execute.gif" title="Execute" width="20" height="20" border="0"></a>
    </h3>
    <p>Define a simple multipart message:</p>
    <ul>
      <li>
        Defining a multipart using the &lt;m:multipart&gt; tag with a
        approriate <code>subtype</code> attribute value.
      </li>
      <li>
        Defining body parts using &lt;m:bodyPart&gt; tags with
        <code>url</code> attributes that specify the resources to be used as
        multipart content.
      </li>
      <li>
        <b>Tip:</b>
        The <code>alternative</code> subtype indicates that each part is an
        "alternative" version of the same (or similar) content.
      </li>
      <li>
        <b>Tip:</b>
        The <code>contentType</code> attribute for multipart message itself
        and every its body part using <code>url</code> attribute can be
        determined automatically.
      </li>
      <li>
        <b>Tip:</b>
        This example demonstrates a recommended way to define a typical
        real world message, that is, using <code>url</code> attributes
        instead of "inline" content.
      </li>
    </ul>

    <h3>
      <span>Attachment</span>
      <a href="../showSource.jsp?filenames=/advanced/attachment.jsp,/advanced/resource/attachment.jsp,/send.jsp"><img
        src="../images/code.gif" title="Source Code" width="20" height="20" border="0"></a>
      <a href="../send.jsp?template=/advanced/attachment.jsp"><img
        src="../images/execute.gif" title="Execute" width="20" height="20" border="0"></a>
    </h3>
    <p>Define a multipart message with an attachment:</p>
    <ul>
      <li>
        Defining a multipart using the &lt;m:multipart&gt; tag with the
        <code>subtype</code> attribute omitted.
      </li>
      <li>
        Defining a body part using the &lt;m:bodyPart&gt; tag with the
        <code>url</code> attribute that specify the resource to be used as
        HTML content.
      </li>
      <li>
        Defining a body part using the &lt;m:bodyPart&gt; tag with the
        <code>url</code> attribute that specify the resource to be used as
        the attachment. The <code>disposition</code> attribute with a value
        of <code>attachment</code> indicates this is an attachment.
      </li>
      <li>
        <b>Tip:</b>
        A &lt;m:multipart&gt; tag without explicit <code>subtype</code>
        attribute will use the default value of <code>mixed</code> as its
        subtype.
      </li>
    </ul>

    <h3>
      <span>Embedded Image</span>
      <a href="../showSource.jsp?filenames=/advanced/embedded.jsp,/advanced/resource/embedded.jsp,/send.jsp"><img
        src="../images/code.gif" title="Source Code" width="20" height="20" border="0"></a>
      <a href="../send.jsp?template=/advanced/embedded.jsp"><img
        src="../images/execute.gif" title="Execute" width="20" height="20" border="0"></a>
    </h3>
    <p>
      Define a complete HTML message with an embedded image included as
      part of the message:
    </p>
    <ul>
      <li>
        Defining a multipart using the &lt;m:multipart&gt; tag with
        <code>related</code> as the value of the <code>subtype</code>
        attribute.
      </li>
      <li>
        Defining a body part using the &lt;m:bodyPart&gt; tag with the
        <code>url</code> attribute that specify the resource to be used as
        HTML content.
      </li>
      <li>
        Defining an attachment using the &lt;m:bodyPart&gt; tag with the
        <code>url</code> attribute that specify the resource to be used as
        the attachment; the <code>disposition</code> attribute with a value
        of <code>attachment</code>; the <code>contentID</code> attribute
        specified the value of <code>cid</code> used for the
        <code>src</code> attribute of the &lt;image&gt; tag in the HTML
        content.
      </li>
    </ul>

  </body>
</html>