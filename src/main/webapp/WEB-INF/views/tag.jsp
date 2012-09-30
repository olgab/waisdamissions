<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tt" %>

<tt:html>
<tt:head title="Tag ${tagEntryStats.normalizedTag}"></tt:head>
<tt:body pageName="tag" cssClass="body">

<h1>Tag <span class="mark">${tagEntryStats.normalizedTag}</span></h1>

<p>Used first by
<a href="/profiel/${tagEntryStats.firstEntry.owner.id}">${tagEntryStats.firstEntry.owner.name}</a>
during <a href="/start-game/${tagEntryStats.firstEntry.game.video.id}">${tagEntryStats.firstEntry.game.video.title}</a>
on ${tagEntryStats.firstEntry.prettyCreationDate}</p>

<h2 class="spaced">Clips featuring <span class="mark">${tagEntryStats.normalizedTag}</span></h2>
<ul class="row equal-cols unstyled">
	<c:forEach items="${tagEntryStats.videoStats}" var="vs">
		<li class="span2">
			<div class="box channel col">
				<a href="/start-game/${vs.video.id}" rel="nofollow" class="reset">
					<div class="img">
						<img src="${vs.video.imageUrl}" />
						<div class="overlay trigger"></div>
					</div>
					<h3 class="h5">${vs.video.title}</h3>
				</a>
				<p class="small">
					Also tagged: <c:forEach items="${vs.topTags}" var="topTag" varStatus="topTagStatus"><a href="/tag/${topTag}" class="reset">${topTag}</a><c:if test="${topTagStatus.index < fn:length(vs.topTags) - 1}">, </c:if></c:forEach>
				</p>
			</div>
		</li>
	</c:forEach>
</ul>


</tt:body>
</tt:html>