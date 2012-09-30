<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="tt" tagdir="/WEB-INF/tags"%>
<tt:html>
<tt:head title="${game.video.title}">
	<script src="/static/script/ugslplayer.js"></script>
	<script src="/static/script/utils.js"></script>
	<script src="/static/script/videoplayer.js"></script>
	<script src="/static/script/taggingHistory.js"></script>
	<script src="/static/script/game.js"></script>
</tt:head>
<tt:body cssClass="game" pageName="game">

<div class="equal-cols-game">
	
	<div id="gameCanvas" class="box span8 relative col-game leading">		
	
		<header class="clear extended">
			<h1 class="h4 pull-left reset"><c:out value="${game.video.title}" /></h1>			
			<span id="timer-remaining" class="pull-right"></span>	
		</header>
		
		<section class="reset">
			<div id="vid-overlay-screen" class="row-fluid show">
				<div id="explanation" class="box clean span6">
					<div id="timer-intro" class="timer-intro"><small>The game starts in</small><strong>00:15</strong></div>
				
					<h2 class="h4">Instructions</h2>
					<ul>
						<li>Try to enter as many words as possible to describe what you see or hear</li>
						<li>Confirm a word by pushing [enter] on your keyboard</li>
						<li>Earn points by <strong>matching</strong>, which happens when you enter the same word as another player</li>
						<li>By joining a game, you submit to the following <a href="/voorwaarden" target="_blank">terms and conditions</a></li>
					</ul>				
					<h3 class="h5">Good luck!</h3>
				</div>
				<div class="box clean span6">
					<div id="playerList" class="box">
						<header class="rich">
							<h2 class="h3 pull-left reset">Players</h2>
							<a href="/game/${game.id}/recap/${user.id}" class="btn btn-primary pull-right">stop</a>
						</header>
						<section class="reset">
							<ul class="unstyled reset">
							</ul>
						</section>
						
					</div>		
				</div>
			</div>
			
			<div id="videoFrame" class="outside">
				<div id="video" class="video"></div>
			</div>
		</section>

		<footer class="outside">
			<input type="text" maxlength="42" class="input-mega-xxl" id="inputField" />
		</footer>
		
	</div>
	
	<div id="rightColumn" class="box span4 col-game">
		<header class="rich extended">
			<h1 id="playerSessionScore" class="pull-left board span2">0</h1>				
			<h2 id="playerPosition" class="pull-right reset">
				<small class="h4">Rank</small>
				<span id="playerPositionMine">-</span> / <span id="playerPositionTotal" class="h4">-</span>
			</h2>
		</header>
		<section class="reset">
			<h3 class="h4 sub-header">Your tags:</h3>		
			<div id="tagList" class="tag-list scroll-box">
			</div>
		</section>
	</div>

</div>

<script type="text/javascript">
	jQuery(function() {
		<c:choose>
			<c:when test="${game.video.playerType == 'NPO'}">
				var video = {
						playerType : 'NPO',
						fragmentId : ${game.video.fragmentID},
						startTimeWithinEpisode : ${game.video.startTime},
						duration : ${game.video.duration}
					};
			</c:when>
			<c:when test="${game.video.playerType == 'JW'}">
				var video = {
						playerType : 'JW',
						sourceUrl : '${game.video.sourceUrl}',
						imageUrl : '${game.video.imageUrl}'
					};
			</c:when>
			<c:otherwise>
			var video = null;
			</c:otherwise>
		</c:choose>
		window.game = new Game(${game.id}, video, ${game.elapsed});
	});
</script>

</tt:body>
</tt:html>