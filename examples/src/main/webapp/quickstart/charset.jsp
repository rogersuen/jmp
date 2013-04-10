<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="m" uri="http://www.javaplus.org/jmp"%>
<m:message contentType="text/html; charset=GBK">
  <m:from address="${applicationScope['mail.from']}" personal="JMP 范例" charset="GBK"/>
  <m:to address="${applicationScope['mail.to']}" personal="我的朋友"/>
  <m:subject value="字符编码" charset="GBK"/>
  <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
  "http://www.w3.org/TR/html4/loose.dtd">
  <html>
    <body>
      <p>This is a simple HTML message containing chinese characters.</p>
      <p>这是一个包含中文字符的简单 HTML 消息。</p>
    </body>
  </html>
</m:message>