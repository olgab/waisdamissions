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
					<div id="timer-intro" class="timer-intro"><small>Spel start in</small><strong>00:15</strong></div>
				
					<h2 class="h4">Uitleg</h2>
					<ul>
						<li>Voer zoveel mogelijk woorden in die beschrijven wat u ziet en hoort</li>
						<li>Bevestig een woord door op de [enter] toets op uw toetsenbord te drukken</li>
						<li>Punten verdient u met <strong>matches</strong>, wanneer u hetzelfde woord invoert als een medespeler</li>
						<li>Op deelname aan het spel zijn de <a href="/voorwaarden" target="_blank">algemene voorwaarden</a> van toepassing</li>
					</ul>				
					<h3 class="h5">Succes!</h3>
				</div>
				<div class="box clean span6">
					<div id="playerList" class="box">
						<header class="rich">
							<h2 class="h3 pull-left reset">Deelnemers</h2>
							<a href="/game/${game.id}/recap/${user.id}" class="btn btn-primary pull-right">stoppen</a>
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
				<small class="h4">Positie</small>
				<span id="playerPositionMine">-</span> / <span id="playerPositionTotal" class="h4">-</span>
			</h2>
		</header>
		<section class="reset">
			<h3 class="h4 sub-header">Uw ingevulde woorden:</h3>		
			<div id="tagList" class="tag-list scroll-box">
			</div>
		</section>
	</div>

</div>

<script type="text/javascript">
	jQuery(function() {
		window.game = new Game(${game.id}, ${game.video.fragmentID}, ${game.video.startTime}, ${game.video.duration}, ${game.elapsed});
	});
</script>

</tt:body>
</tt:html>