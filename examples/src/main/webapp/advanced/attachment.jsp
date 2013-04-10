<%@taglib prefix="m" uri="http://www.javaplus.org/jmp"%>

<m:message>
  <m:from address="${applicationScope['mail.from']}" personal="JMP Examples"/>
  <m:to address="${applicationScope['mail.to']}" personal="My Friend"/>
  <m:subject value="Attachment"/>
  <m:multipart>
    <m:bodyPart url="resource/attachment.jsp"/>
    <m:bodyPart url="resource/duke.jpg" disposition="attachment" fileName="duke.jpg"/>
  </m:multipart>
</m:message>