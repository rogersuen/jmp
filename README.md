JavaMail Pages Tag Library (JMP)
================================

Thanks for using JavaMail Pages Tag Library (JMP), a tag library based on
JavaMail API, and used to build JSP-based templates for JavaMail messages.

Using the Tag Library
---------------------

To use the tag library with your own web applications, simply copy the JAR
files in the 'lib' directory to your application's WEB-INF/lib directory. 
Then, import the template tag library into your pages with the following directives:

    <%@ taglib prefix="m" uri="http://www.javaplus.org/jmp" %>

or introducing a new namespace with the above URI in a JSP document
in XML syntax.

The following is n simple JSP page from the examples web application, 
demonstrating typical usage of the tag library.

    <%@taglib prefix="m" uri="http://www.javaplus.org/jmp"%>
    <m:message>
      <m:from address="${applicationScope['mail.from']}" personal="JMP Examples"/>
      <m:to address="${applicationScope['mail.to']}" personal="My Friend"/>
      <m:subject value="Embedded Image"/>
      <m:multipart subtype="related">
        <m:bodyPart url="resource/embedded.jsp"/>
        <m:bodyPart url="resource/duke.jpg" disposition="attachment" contentID="duke.jpg"/>
      </m:multipart>
    </m:message>

This is used to generate a multipart message with an embedded image. With the code snippet
like the following, you can build a `javax.mail.Message` instance from the page:

    MessageTemplate template = MessageTemplate.getInstance(servletContext, path);
    Message message = template.render(request, response);

Now, you can send the message in any way you like.

Examples Web Application
------------------------

In the source code distribution, an standalone examples web application is provided.
You can build it, deploy the application to your favorite container, and follow 
the examples to familiarize yourself with the usage of JMP.

Rumtime Dependencies
--------------------

This version of JMP has the following runtime dependencies provided
by Java EE containers:

- JavaMail 1.4
- Servlet 2.4
- JSP 2.0
- JSTL 1.2 (needed only by examples web application)

Documentation
-------------

See documentation in 'docs/' directory for more information.

Building from Source Code
-------------------------

Download a source distribution. Build it from the source code with Maven.

Comment and Feedback
--------------------

Any comment, question and feedback should go to rogersuen@live.com.

Enjoy!
