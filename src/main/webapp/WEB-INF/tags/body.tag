<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tt" %>
<%@ taglib prefix="nf" uri="/WEB-INF/tld/NumberFormat.tld"%>

<%@ attribute name="cssClass" required="false" %>
<%@ attribute name="pageName" required="false" %>

<body class="${cssClass}">
	<script>
		jQuery("body").css('opacity', 0);
		var fadeInTimeout = setTimeout(function() {
			jQuery("body").animate({ 'opacity' : 1 }, 350);
		}, 1000);
	</script>
    
    <div class="brand-marker">
    	<div class="container">
      	<p class="pull-left">Een <a href="http://blog.waisda.nl" target="_newwindow"><img src="/static/img/logo-waisda-small.png" alt="Waisda" /></a> project</p>
      	<c:if test="${globalStats != null}">
					<c:if test="${user != null || user.playerBarVisible}">
					<div class="stats pull-right">
						<c:if test="${user == null || user.anonymous}">
							<p >Al <strong><nf:format number="${globalStats.totalTags}" /></strong> <!-- ${globalStats.totalTags} --> tags ingevoerd en <strong><nf:format number="${globalStats.totalMatches}" /></strong> <!-- ${globalStats.totalMatches} --> matches gemaakt</p>
						</c:if>
						<c:if test="${user != null && !user.anonymous}">
							<p>Uw bijdrage: <strong><nf:format number="${user.totalTags}"/></strong> van <strong ><nf:format number="${globalStats.totalTags}" /></strong> tags en <strong><nf:format number="${user.totalMatches}" /></strong> van <strong><nf:format number="${globalStats.totalMatches}"  /></strong> matches in totaal</p>	
						</c:if>		
					</div>
					</c:if>
				</c:if>
    	</div>
    </div>

    <header class="site-header">
  		<div class="container row relative equal-cols">
	    	<h1 class="reset span3"><a href="/" class="logo reset col"><img src="/static/img/logo-dummy.png" alt="LOGO"></a></h1>
	  		
	  		<c:if test="${globalStats != null}">
	  			<c:if test="${user == null || !user.playerBarVisible}">
					<div class="stats span6 offset3">
						<c:if test="${user == null || user.anonymous}">
							<p>Al <strong><nf:format number="${globalStats.totalTags}" /></strong> <!-- ${globalStats.totalTags} --> tags ingevoerd en <strong><nf:format number="${globalStats.totalMatches}" /></strong> <!-- ${globalStats.totalMatches} --> matches gemaakt</p>
						</c:if>
						<c:if test="${user != null && !user.anonymous}">
							<p>Uw bijdrage: <strong><nf:format number="${user.totalTags}"/></strong> van <strong ><nf:format number="${globalStats.totalTags}" /></strong> tags en <strong><nf:format number="${user.totalMatches}" /></strong> van <strong><nf:format number="${globalStats.totalMatches}"  /></strong> matches in totaal</p>	
						</c:if>		
					</div>
					</c:if>
				</c:if>
				
				<c:if test="${user != null && user.playerBarVisible}">
					<div class="span3 box personal col">
						<tt:profileLink anonymous="${user.anonymous}" id="${user.id}">
							<c:if test="${user.anonymous}">
								<img src="${user.avatarUrl}" class="pull-left" />
								<h2 class="h5 reset">Hallo, anoniempje!</h2>
							</c:if>
							<c:if test="${!user.anonymous}">
								<img src="${user.avatarUrl}"class="pull-left" />
								<h2 class="h5 reset">Hallo, <c:out value="${user.name}"/>!</h2>
							</c:if>								
							<p class="reset"><strong class="h1"><nf:format number="${user.totalScore}" /></strong></p>
							<p class="reset small">Uw puntentotaal</p>
						</tt:profileLink>
					</div>
					<c:if test="${user != null && user.countNewPioneerMatches > 0}">
						<div class="span3 box personal col">
							<a href="/profiel/${user.id}#pionier" class="unstyled">
								<img src="/static/img/match-pioneer-xl.png" class="pull-left" />
								<p class="reset">toelichting &raquo;</p>	
								<p class="reset"><strong class="h1">+ <nf:format number="${user.countNewPioneerMatches * 150}" /></strong></p>
								<p class="reset small">sinds uw laatste spel</p>
							</a>
						</div>
					</c:if>
				</c:if>
					<ul class="span3 box unstyled reset text-right col pull-right">		
						<c:if test="${user != null && !user.anonymous}">
							<li><a href="/uitloggen">uitloggen &raquo;</a></li>
						</c:if>
						<c:if test="${user == null || user.anonymous}">		
							<li><a href="/inloggen">inloggen &raquo;</a></li>
							<li><a href="/registreren">registreren &raquo;</a></li>
						</c:if>
					</ul>

				<c:if test="${globalStats.currentlyPlaying > 0}">
					<p class="online">
						<strong>${globalStats.currentlyPlaying} ${globalStats.currentlyPlaying == 1 ? 'speler' : 'spelers'}</strong> nu online
					</p>
				</c:if>
			</div>
   	</header>

	<section class="main container">
		<jsp:doBody/>
	</section>

	<div class="site-footer">
	 	<div class="container">
				<ul class="unstyled horizontal pull-left">
					<li><a href="/">Home</a></li>
		  		<li><a href="/over-het-spel">Over het spel</a></li>
		  		<li><a href="/spelinstructies">Spelinstructies</a></li>
		  		<li><a href="/voorwaarden">Algemene voorwaarden</a></li>
		  	</ul>
	  		<ul class="unstyled horizontal pull-right">
	  			<li>Vind ons ook op</li>
		  		<li><a href="http://www.facebook.com/pages/Waisda/187799419727">Facebook</a></li>
		  		<li><a href="http://twitter.com/waisda">Twitter</a></li>
		  		<li><a href="http://blog.waisda.nl">Blog</a></li>
		  	</ul>
		</div>
		<div class="container spaced-ext">
			<p class="pull-left">
				Mogelijk gemaakt door 
				<a href="http://beeldengeluid.nl" target="_nieuw"><img src="/static/img/logo-beeldengeluid.png" alt="Beeld en Geluid /"></a>
				<a href="http://beeldenvoordetoekomst.nl/" target="_nieuw"><img src="/static/img/logo-future.png" alt="Images for the future" /></a>
				<a href="http://vu.nl" target="_nieuw"><img src="/static/img/logo-vu.png" alt="Vrije Universiteit" /></a>
				<a href="http://q42.nl" target="_nieuw"><img src="/static/img/logo-q42.png" alt="Q42" /></a>
			</p>
		</div>		
	</div>

	<c:if test="${cssClass != 'body' && cssClass != 'game'}">
		<tt:games-queue dynamic="true"/>
	</c:if>

</body>
