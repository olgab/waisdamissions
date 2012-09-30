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
		
		<c:forEach var="channel" items="${channels}">
		
		<li class="span2">
			<a href="/start-game/${channel.video.id}" title="${channel.video.title}" rel="nofollow" class="box channel col">
				<div class="img">
					<img src="${channel.video.imageUrl}" />
					<div class="overlay trigger"></div>
				</div>
				<h3 class="h5">${channel.video.title}</h3>
				<p class="small">games: ${channel.video.timesPlayed}<br/>high score: <nf:format number="${channel.highscore}" /></p>
			</a>
		</li>
		</c:forEach>		
	</ul>
	
	<h2 class="h3 reset">Join a mission</h2>
	<ul class="row equal-cols unstyled clear-both">
		<c:forEach var="mission" items="${missions}">
		<li class="span2">
			<a href="/mission/${mission.id}" title="${mission.title}">
				<p>${mission.title}</p>
			</a>
		</li>
		</c:forEach>		
	</ul>

	<!--h2 class="spaced">Hoe werkt het?</h2-->
	<div class="row equal-cols">
		<ol class="unstyled pull-left">
			<li class="box rich order span3 col"><span class="index">1</span>Select a video above or from the queue</li>
			<li class="box rich order span3 col"><span class="index">2</span>Enter as many words as you can to describe what you see and hear</li>
			<li class="box rich order span3 col"><span class="index">3</span>Score points by entering the same words as other players.</li>
			<li class="box rich order span3 col"><span class="index">4</span>Read the <a href="/spelinstructies">instructions</a> and <a href="/registreren">register</a> to save your score.</li>
		</ol>
		<p id="silverlight-message" class="clear-both small">In order to be able to join <a href="http://www.silverlight.net/">Microsoft Silverlight plugin</a> has to be installed.</p>
	</div>
	<!--div id="prizebanner">
		<a href="/spelinstructies#wedstrijdregels">
			<img src="/static/img/prijzenbanner.png" alt="" />
		</a>
	</div-->
	
	<div class="row equal-cols">
		<div class="box span4 col">
			<header class="header rich"><h2 class="h3 reset">Top Scorers <small>last 7 days</small></h2></header>
			<section class="reset scroll-box">
				<ol class="unstyled reset">
				<c:forEach items="${globalStats.topScores.topTen}" var="u" varStatus="status">
					<li class="chart-entry"><a href="/profiel/${u.user.id}">
					<span class="index pull-left">${status.index + 1}</span>
					<img src="${u.user.smallAvatarUrl}" />
					${fn:escapeXml(u.user.name)}
					<span class="score pull-right h5"><nf:format number="${u.score}" /></span>				
					</a></li>
				</c:forEach>
				</ol>
			</section>
		</div>
	
		<!-- #tagcloud -->
		<div class="box span4 col leading">

			<header class="header rich"><h2 class="h3 reset">Most popular tags <small>last 7 days</small></h2></header>
			<section class="reset fixed-low">
				<ul class="tagcloud unstyled">
					<c:forEach items="${globalStats.tagCloud}" var="tag">
						<li class="tag-entry size-${tag.relativeSize}"><a href="/tag/${tag.normalizedTag}">${tag.normalizedTag}</a></li>
					</c:forEach>
				</ul>
			</section>
		</div>
		
		<tt:games-queue dynamic="false"/>
		
	</div>

</tt:body>
</tt:html>
