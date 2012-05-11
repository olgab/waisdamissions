<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<%@ attribute name="anonymous" required="true"%>
<%@ attribute name="id" required="true"%>

<c:choose>
	<c:when test="${anonymous}">
		<jsp:doBody />
	</c:when>
	<c:otherwise>
		<a href="/profiel/${id}"><jsp:doBody /></a>
	</c:otherwise>
</c:choose>
