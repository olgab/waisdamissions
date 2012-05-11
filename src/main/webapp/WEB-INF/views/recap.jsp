<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="nf" uri="/WEB-INF/tld/NumberFormat.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tt"%>
<tt:html>
<tt:head title="Resultaten">
</tt:head>
<tt:body pageName="recap">
<div class="equal-cols">
	
<div class="game-equal-cols">
	
	<div id="gameCanvas" class="box span8 relative game-col leading">		
		
		
		<header class="clear extended">
			<h1 class="h4 pull-left reset"><c:out value="${recap.game.video.title}" /></h1>			
			<span id="timer-remaining" class="small pull-right">Spel gespeeld op<strong class="clear-both">${recap.game.prettyStart}</strong></span>		
		</header>
		
		<section>
			<div class="row-fluid">
				<c:if test="${recap.owner.anonymous}">
				<!--div>
					<h3>Log in om uw punten te bewaren!</h3>
					<p>Zorg dat uw <nf:format number="${user.totalScore}" /> behaalde punten niet verloren gaan. 
					<a href="/inloggen">Log in</a> of <a href="/registreren">Registreer uzelf</a></p>
				</div-->
				</c:if>
				<div class="span6">
					<table class="table table-striped table-condensed-ext table-clean">
						<tr>
							<th colspan="2">Uw bijdrage in dit spel</th>
							<th class="text-right">punten</th>
						</tr>
						<tr>
							<td></td>
							<td><strong><nf:format number="${recap.summary.countEmptyTags}" /></strong> ${recap.summary.countEmptyTags == 1 ? 'tag' : 'tags' } zonder match <span class="help" title="Als een speler in een later spel alsnog op deze tag matcht dan levert u dat minstens 145 punten op">?</span></td>
							<td class="text-right"><nf:format number="${recap.summary.countEmptyTags * 5}" /></td>
						</tr>
						<tr>
							<td></td>
							<td><strong><nf:format number="${recap.summary.countDictionaryMatches}"/></strong> ${recap.summary.countDictionaryMatches == 1 ? 'woordenboekmatch' : 'woordenboekmatches' } <span class="help" title="Een geografische naam levert 25 punten op">?</span></td>
							<td class="text-right"><nf:format number="${recap.summary.countDictionaryMatches * 25}" /></td>
						</tr>
						<tr>
							<td><img src="/static/img/match-social.png" title="match met medespeler" /></td>
							<td><strong><nf:format number="${recap.summary.countMatchingTags}"/></strong> ${recap.summary.countMatchingTags == 1 ? 'match' : 'matches' } met medespelers <span class="help" title="Dezelfde tag als een medespeler invoeren levert 50 punten op">?</span></td>
							<td class="text-right"><nf:format number="${recap.summary.countMatchingTags * 50}" /></td>
						</tr>
						<tr>
							<td><img src="/static/img/match-pioneer.png" title="door uw ge�ntroduceerde match" /></td>
							<td><strong><nf:format number="${recap.summary.countPioneerTags}"/></strong> ${recap.summary.countPioneerTags == 1 ? 'pioniersmatch' : 'pioniersmatches' } <span class="help" title="Als jij als eerste een tag hebt ingevoerd dan levert de eerstvolgende match daarop minstens 145 punten extra op">?</span></td>
							<td class="text-right"><nf:format number="${recap.summary.countPioneerTags * 100}" /></td>
						</tr>
						<tr>
							<td></td>
							<td><strong>Puntentotaal</strong></td>
							<td class="text-right"><nf:format number="${recap.ownerScore}" /></td>
						</tr>
					</table>
					<p class="small spaced-min">Kijk hiernaast in het overzicht van 'Uw ingevoerde tags' voor een specifiekere toelichting per match.</p>
					
					<h3>Meer punten verdienen?</h3>
					<p>Daag vrienden uit om dit spel te spelen en vergroot uw eigen kans op een hogere score <span class="help" title="Als u als eerste een tag hebt ingevoerd dan levert de eerstvolgende match daarop in een later spel minstens 145 punten extra op">?</span></p>

					<!-- AddThis Button BEGIN -->
					<div id="addthis-bar" class="addthis_toolbox addthis_default_style spaced reserved-space" 
					addthis:url="http://waisda.nl/start-game/${recap.game.video.id}"
					addthis:title="Ik heb zojuist ${recap.ownerScore} punten behaald door ${fn:length(recap.tagEntries)} ${fn:length(recap.tagEntries) == 1 ? 'woord' : 'woorden'} aan &quot;${recap.game.video.title}&quot; toe te voegen #woordentikkertje"
					addthis:description="Speelt u mee?">
						<a class="addthis_button_preferred_1"></a>
						<a class="addthis_button_preferred_2"></a>
						<a class="addthis_button_preferred_3"></a>
						<a class="addthis_button_preferred_4"></a>
						<a class="addthis_button_compact"></a>
						<a class="addthis_counter addthis_bubble_style"></a>
					</div>
					<script type="text/javascript" src="http://s7.addthis.com/js/250/addthis_widget.js#pubid=xa-4dca79b617093d25"></script>
				</div>
						
				<div id="rankings" class="box span6">
					<header class="rich">
						<h2 class="h3 pull-left reset">Eindstand</h2>
					</header>
					<section class="reset">
						<ol class="unstyled reset">
						
						<c:forEach items="${recap.participants}" var="p">
							<li class="${p.user.id == recap.owner.id ? 'chart-entry extended highlight' : 'chart-entry extended'}">
							<tt:profileLink anonymous="${p.user.anonymous}" id="${p.user.id}">
									<span class="index pull-left">${p.position + 1}</span>
									<img src="${p.user.smallAvatarUrl}" />
									${fn:escapeXml(p.user.name)}
									<span class="score h5"><nf:format number="${p.score}" /></span><br />
									<small><nf:format number="${p.countTags}" /> ${p.countTags == 1 ? 'tag' : 'tags'} waarvan ${p.countMatches} ${p.countMatches == 1 ? 'match' : 'matches' }</small>
							</tt:profileLink>
							</li>
						</c:forEach>
						</ol>
					</section>
					
				</div>
			</div>
		</section>
	</div>

	<div id="rightColumn" class="box span4 game-col">
		<header class="rich extended">
			<h1 id="playerSessionScore" class="pull-left board span2"><nf:format number="${recap.ownerScore}"/></h1>				
			<h2 id="playerPosition" class="pull-right reset">
				<small class="h4">Positie</small>
				<span id="playerPositionMine">${recap.ownerPosition + 1}</span> / <span id="playerPositionTotal" class="h4">${fn:length(recap.participants)}</span>
			</h2>
		</header>
		<section class="reset">
			<h3 class="h4 sub-header">Uw ingevulde woorden:</h3>		
	
			<div id="tagList" class="tag-list scroll-box">
				<c:forEach items="${recap.tagEntries}" var="tag">
					<div class="tag" id="tagEntry3">
						<span class="points">+<nf:format number="${tag.score}" /></span>
						<c:if test="${tag.matchingTagEntry != null && tag.pioneer}">
							<img src="/static/img/match-pioneer.png" class="icon" />
						</c:if>
						<c:if test="${tag.matchingTagEntry != null && !tag.pioneer}">
							<img src="/static/img/match-social.png" class="icon" />
						</c:if>
						<span><c:out value="${tag.tag}"/></span>
						<span class="matching small">
							<c:if test="${tag.matchingTagEntry != null}">
								match met <strong><c:out value="${tag.matchingTagEntry.tag}"/></strong>

								<c:choose>
								<c:when test="${tag.matchingTagEntry.owner.id == tag.owner.id}">van jezelf</c:when>
								<c:otherwise>van <em><c:out value="${tag.matchingTagEntry.owner.name}"/></em></c:otherwise>
								</c:choose>

								<c:if test="${tag.pioneer}">. Jij hebt dit woord ge�ntroduceerd</c:if>
							</c:if>
						</span>
					</div>
				</c:forEach>
			</div>

		</section>
	</div>
</div>
	
</tt:body>
</tt:html>
