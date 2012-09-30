<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tt"%>

<tt:html>
<tt:head title="How does it work?"></tt:head>
<tt:body pageName="cookiesUitleg">
   	<h1>Cookies</h1>
	<p>To take part in Waisda? cookies have to be enabled in your browser. Enable cookies and <a href="${fn:escapeXml(targetUrl)}">try again</a>.</p>
</tt:body>
</tt:html>
