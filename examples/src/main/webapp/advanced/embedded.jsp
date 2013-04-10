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