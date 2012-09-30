<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="nf" uri="/WEB-INF/tld/NumberFormat.tld"%>
<%@ taglib prefix="nf" uri="/WEB-INF/tld/NumberFormat.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tt"%>

<tt:html>
<tt:head title="Home">
</tt:head>
<tt:body cssClass="body" pageName="home">

	<!-- AddThis Button BEGIN -->
	<div id="addthis-bar" class="addthis_toolbox addthis_default_style pull-right spaced-bottom"
		addthis:url="http://waisda.nl/"
    	addthis:title="Waisda?"
		addthis:description="Would you like to play?">
		<a class="addthis_button_preferred_1"></a>
		<a class="addthis_button_preferred_2"></a>
		<a class="addthis_button_preferred_3"></a>
		<a class="addthis_button_preferred_4"></a>
		<a class="addthis_button_compact"></a>
		<a class="addthis_counter addthis_bubble_style"></a>
	</div>
	<script type="text/javascript" src="http://s7.addthis.com/js/250/addthis_widget.js#pubid=xa-4dca79b617093d25"></script>
	<!-- AddThis Button END -->

	<p class="spaced-bottom">
		By playing <strong>Waisda</strong>, you're helping us improve our archives by making them easier to search through. Thanks!
	</p>
	
	<!--h2>Kanalen</h2-->
	<ul class="row equal-cols unstyled clear-both">
		
		<c:forEach var="video" items="${videos}">
		
		<li class="span2">
			<a href="/mission/join/${mission.id}/${video.id}" title="${video.title}" rel="nofollow" class="box channel col">
				<div class="img">
					<img src="${video.imageUrl}" />
					<div class="overlay trigger"></div>
				</div>
				<h3 class="h5">${video.title}</h3>
			</a>
		</li>
		</c:forEach>		
	</ul>

</tt:body>
</tt:html>
