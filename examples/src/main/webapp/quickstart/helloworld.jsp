<%@taglib prefix="m" uri="http://www.javaplus.org/jmp"%>
<m:message>
  <m:from>${applicationScope["mail.from"]}</m:from>
  <m:to>${applicationScope["mail.to"]}</m:to>
  <m:subject>Hello World!</m:subject>
  Hello world!
</m:message>