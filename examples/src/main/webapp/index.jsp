<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JavaMail Pages Tag Library - Examples Application</title>
  </head>
  <body>
    <%@include file="/WEB-INF/jspf/header.jspf"%>
    <h1>JavaMail Pages (JMP) Tag Library</h1>
    <h2>Examples Web Application </h2>
    <p>
      Welcome to the jmp-examples web application! This standalone
      application includes a variety of basic JSP pages that demonstrate
      using JavaMail Pages (JMP) Tag Library.
    </p>
    <p>The examples have been divided into some categories as follow:</p>
    <ul>
      <li><a href="quickstart/index.jsp">Quick Start</a></li>
      <li><a href="advanced/index.jsp">Advanced</a></li>
    </ul>
    <p>
      When navigating the examples, you can always return back to this index
      page by following the link <a href="index.jsp">Examples Index</a>
      at the top of every example page. If you need change your SMTP server
      configuration, follow the link <a href="config.jsp">Configuration</a>.
    </p>
    <p>
      For each example, the following icons will allow you to examine the
      source code as well as execute the example JSP page, generate a message
      and send it.
    </p>
    <table  border="1" cellspacing="2" cellpadding="2">
      <tr>
        <td width="20">
          <img src="images/execute.gif" alt="Execute" title="Execute" width="20" height="20">
        </td>
        <td>Execute the example JSP page</td>
      </tr>
      <tr>
        <td width="20">
          <img src="images/code.gif" alt="Source Code" title="Source Code" width="20" height="20">
        </td>
        <td>Look at the source code of the example JSP pages</td>
      </tr>
    </table>
    <p>Enjoy!</p>
  </body>
</html>
