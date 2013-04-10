<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JavaMail Pages Tag Library - Quick Start Examples</title>
  </head>
  <body>
    <%@include file="/WEB-INF/jspf/header.jspf" %>
    <h2>Quick Start Examples</h2>
    <p>
      This section contains a collection of simple JSP pages that demonstrate
      basic usage of JavaMail Pages (JMP) tag library. Follow these examples
      to familiarize yourself with the basic features and usage of JMP tags:
    </p>

    <h3>
      <span>Hello world!</span>
      <a href="../showSource.jsp?filenames=/quickstart/helloworld.jsp,/send.jsp"><img
        src="../images/code.gif" title="Source Code" width="20" height="20" border="0"></a>
      <a href="../send.jsp?template=/quickstart/helloworld.jsp"><img
        src="../images/execute.gif" title="Execute" width="20" height="20" border="0"></a>
    </h3>
    <p>Define a simple plain text message:</p>
    <ul>
      <li>
        Defining a message using &lt;m:message&gt; tag. Using the body
        content created by any template text and/or non-messaging custom tag
        output as the text content of the message.
      </li>
      <li>
        Defining the sender and recipient using &lt;m:from&gt; and
        &lt;m:to&gt; tags. Using the body content created by any template text
        and/or non-messaging custom tag output as the header field values.
      </li>
      <li>
        Defining the subject using &lt;m:subject&gt; tag. Using the body
        content created by any template text  and/or non-messaging custom tag
        output as the header field values.
      </li>
      <li>
        <b>Tip:</b>
        Using EL to generate dynamic content
      </li>
      <li>
        <b>Tip:</b>
        Using template text as the text content of a message will leave
        unexpected whitespaces in the generated message.
      </li>
    </ul>

    <h3>
      <span>Tag attributes</span>
      <a href="../showSource.jsp?filenames=/quickstart/attribute.jsp,/send.jsp"><img
        src="../images/code.gif" title="Source Code" width="20" height="20" border="0"></a>
      <a href="../send.jsp?template=/quickstart/attribute.jsp"><img
        src="../images/execute.gif" title="Execute" width="20" height="20" border="0"></a>
    </h3>
    <p>Using tag attributes to refine messages:</p>
    <ul>
      <li>
        Defining MIME type of a message using <code>contentType</code>
        attribute.
      </li>
      <li>
        Defining address header fields using <code>address</code> and
        <code>personal</code> attributes.
      </li>
      <li>
        Defining the value of an "unstructured" header field using
        general-purpose <code>value</code> attribute.
      </li>
      <li>
        <b>Tip:</b>
        Using tag attributes is recommended for "structured" header fields.
      </li>
      <li>
        <b>Tip:</b>
        Tag attributes take precedence over body content.
      </li>
      <li>
        <b>Tip:</b>
        General-purpose <code>value</code> attribute is recommended for
        "unstructured" header field like "Subject".
      </li>
      <li>
        <b>Tip:</b>
        Special-purpose tag attributes take precedence over general-purpose
        <code>value</code> attribute.
      </li>
    </ul>

    <h3>
      <span>HTML message</span>
      <a href="../showSource.jsp?filenames=/quickstart/htmlmessage.jsp,/send.jsp"><img
        src="../images/code.gif" title="Source Code" width="20" height="20" border="0"></a>
      <a href="../send.jsp?template=/quickstart/htmlmessage.jsp"><img
        src="../images/execute.gif" title="Execute" width="20" height="20" border="0"></a>
    </h3>
    <p>Defining an HTML message:</p>
    <ul>
      <li>
        Using "text/html" as the value of <code>contentType</code> attribute.
      </li>
      <li>
        <b>Tip:</b>
        Always specify <code>charset</code> parameter for
        <code>contentType</code> attribute to avoid encoding problems.
      </li>
    </ul>

    <h3>
      <span>Using resources</span>
      <a href="../showSource.jsp?filenames=/quickstart/usingurl.jsp,/quickstart/resource/html.jsp,/send.jsp"><img
        src="../images/code.gif" title="Source Code" width="20" height="20" border="0"></a>
      <a href="../send.jsp?name=Stephen&template=/quickstart/usingurl.jsp"><img
        src="../images/execute.gif" title="Execute" width="20" height="20" border="0"></a>
    </h3>
    <p>Using the resource specified by <code>url</code> attribute as content:</p>
    <ul>
      <li>
        Using <code>url</code> attribute of &lt;m:message&gt; tag to specify
        the resource from which the content of a message can be imported.
        The value of the <code>url</code> attribute has the same semantic
        with the one in the &lt;c:import&gt; tag of JSTL.
      </li>
      <li>
        <b>Tip:</b>
        <code>contentType</code> attribute could be automatically determined
        from the resource.
      </li>
      <li>
        <b>Tip:</b>
        The resource could also have dynamic content!
      </li>
    </ul>

    <h3>
      <span>Character Encoding</span>
      <a href="../showSource.jsp?filenames=/quickstart/charset.jsp,/send.jsp"><img
        src="../images/code.gif" title="Source Code" width="20" height="20" border="0"></a>
      <a href="../send.jsp?name=Stephen&template=/quickstart/charset.jsp"><img
        src="../images/execute.gif" title="Execute" width="20" height="20" border="0"></a>
    </h3>
    <p>
      Using <code>charset</code> tag attribute and <code>charset</code>
      parameter of <code>contentType</code> attribute to specify the character
      encoding.
    </p>
    <ul>
      <li>
        Using <code>charset</code> parameter of <code>contentType</code> 
        attribute to specify the character encoding for the "inline" text
        content of a message.
      </li>
      <li>
        Using <code>charset</code> attribute of any address tag to specify
        the character encoding for the personal part of the address
        containing non-ASCII characters.
      </li>
      <li>
        Using <code>charset</code> attribute of &lt;m:subject&gt; tag to 
        specify the character encoding for the text value of the subject
        containing non-ASCII characters.
      </li>
      <li>
        <b>Tip:</b>
        Character encoding for &lt;m:to&gt; tag is determined automatically
        from the <code>charset</code> parameter of the <code>contentType</code>
        attribute of the enclosing &lt;m:message&gt; tag.
      </li>
      <li>
        <b>Tip:</b>
        All header tags support <code>charset</code> attribute.
      </li>
      <li>
        <b>Note:</b>
        The implementaion of tag library tries best to determine appropriate
        character encoding automatically if it's not specified explicitly,
        But it's not always as expected, and the algorithm subjects to change.
        It's strong recommended to always specify <code>charset</code>
        attributes explicitly for header fields containing non-ASCII
        characters.
      </li>
    </ul>

  </body>
</html>