<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="org.javaplus.jmp.MessageTemplate" %>
<%@page import="javax.mail.*" %>
<%@page import="javax.mail.internet.*" %>
<%!
  private Throwable send(Message message) {
    ServletContext context = getServletContext();
    Session mailSession = (Session) context.getAttribute("mail.session");
    Transport transport = null;
    Throwable throwable = null;
    try {
      transport = mailSession.getTransport();
      transport.connect(
              mailSession.getProperty("mail.user"),
              mailSession.getProperty("mail.password"));
      transport.sendMessage(message, message.getAllRecipients());
    } catch (MessagingException ex) {
      throwable = ex;
    } finally {
      try {
        transport.close();
      } catch (MessagingException ex) {
      }
    }
    return throwable;
  }
%>
<%
      String path = request.getParameter("template");

      MessageTemplate template = MessageTemplate.getInstance(application, path);
      Message message = template.render(request, response);

      Throwable throwable = send(message);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JavaMail Pages Tag Library - Send Message</title>
  </head>
  <body>
    <% if (throwable == null) {
        String recipients = InternetAddress.toString(message.getAllRecipients());
        recipients = MimeUtility.decodeText(recipients);
        pageContext.setAttribute("recipients", recipients);
    %>
    <p>
      Message sent successfully to the recipient:
      <c:out escapeXml="true" value="${recipients}"/>
    </p>
    <%} else {%>
    <p style="color:#ff0000">
      Failed to send message: <%= throwable.toString()%>
    </p>
    <%}%>
  </body>
</html>
