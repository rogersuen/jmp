<%@taglib prefix="m" uri="http://www.javaplus.org/jmp"%>
<m:message url="resource/html.jsp">
  <m:from address="${applicationScope['mail.from']}" personal="JMP Examples"/>
  <m:to address="${applicationScope['mail.to']}" personal="My Friend"/>
  <m:subject value="HTML Message"/>
</m:message>