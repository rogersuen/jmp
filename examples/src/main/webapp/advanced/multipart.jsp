<%@taglib prefix="m" uri="http://www.javaplus.org/jmp"%>

<m:message>
  <m:from address="${applicationScope['mail.from']}" personal="JMP Examples"/>
  <m:to address="${applicationScope['mail.to']}" personal="My Friend"/>
  <m:subject value="Multipart Message"/>
  <m:multipart subtype="alternative">
    <m:bodyPart url="resource/plain.jsp"/>
    <m:bodyPart url="resource/html.jsp"/>
  </m:multipart>
</m:message>