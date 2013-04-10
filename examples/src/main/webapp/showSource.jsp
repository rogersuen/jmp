<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Displays the content of the source files specified in request
  parameter "filenames" (a comma separated list).
--%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Example source code for
      <c:forEach
        var="filename"
        items="${param['filenames']}">${filename}</c:forEach>
    </title>
  </head>
  <body>
    <c:forEach var="filename" items="${param['filenames']}">
      <h3>${filename}:</h3>
      <%
      String filename = (String) pageContext.getAttribute("filename");
      pageContext.setAttribute("filepath",
              application.getResource(filename).toExternalForm());
      %>
      <c:import var="source" url="${filepath}" charEncoding="UTF-8"/>
      <pre><c:out value="${source}" escapeXml="true"/></pre>
    </c:forEach>
  </body>
</html>
