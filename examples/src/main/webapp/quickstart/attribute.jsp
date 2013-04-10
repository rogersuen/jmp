<%@taglib prefix="m" uri="http://www.javaplus.org/jmp"%>
<m:message contentType="text/plain; charset=iso-8859-1">
  <m:from address="${applicationScope['mail.from']}" personal="JMP Examples"/>
  <m:to address="${applicationScope['mail.to']}" personal="My Friend"/>
  <m:subject value="Using Attributes"/>
  This is a simple message that demonstrates basic usage of messaging tag attributes.
</m:message>