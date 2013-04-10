<%@taglib prefix="m" uri="http://www.javaplus.org/jmp"%>
<m:message contentType="text/html; charset=iso-8859-1">
  <m:from address="${applicationScope['mail.from']}" personal="JMP Examples"/>
  <m:to address="${applicationScope['mail.to']}" personal="My Friend"/>
  <m:subject value="HTML Message"/>

  <p>A simple <b>HTML</b> message</p>

</m:message>