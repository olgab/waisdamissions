<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="nf" uri="/WEB-INF/tld/NumberFormat.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tt" %>
<c:set var="you" value="${user != null && user.id == profile.user.id}"/>

<tt:html>
<tt:head title="${fn:escapeXml(profile.user.name)}'s profile'"></tt:head>
<tt:body pageName="profile">
<c:choose>
<c:when test="${you}">
<h1>Your profile</h1>
<p class="spaced">This profile is visible to all visitors of this site</p>		

</c:when>
<c:otherwise>
<h1>${fn:escapeXml(profile.user.name)}'s profile</h1>
<p>Playing since ${profile.user.prettyCreationDate}</p>
</c:otherwise>
</c:choose>
<div class="row equal-cols">

	<div class="box media blow-up span4 col">
		<div class="box-inner">
			<img src="${profile.user.avatarUrl}" class="pull-left" />
			<h2 class="pull-left h3"><c:out value="${profile.user.name}"/></h2>	
			<p class="h1 reset clear-both"><nf:format number="${profile.user.totalScore}" /><span class="small">Total score</span></p>
		</div>
	</div>	
	
	<div id="personal-details" class="box span4 col ${errors ? 'edit-mode' : ''}">
		<header class="rich">
			<h2 class="h3 reset pull-left">Details</h2>
			<c:if test="${you}">
				<a href="#" class="btn btn-primary pull-right" id="edit-details">Edit</a>
				<a href="#" class="btn btn-primary pull-right hide" id="submit-details">Save</a>
				<a href="#" class="btn pull-right hide" id="close-details">Cancel</a>
			</c:if>
		</header>
		
		<section class="reset relative">
			<c:if test="${user != null && user.id == profile.user.id}">
			<f:form commandName="form" autocomplete="off" xxaction="/profiel" cssClass="form-horizontal boxed hide">
				<fieldset>
					
					<f:hidden path="id"/>
					
					<div class="control-group">
						<f:label path="auth.name" cssClass="control-label">Name</f:label>
						<div class="controls">
							<f:input path="auth.name" cssClass="input-medium" />
							<f:errors path="auth.name" cssClass="help-block"/>
						</div>
					</div>
					
					<%--
					<div class="control-group">
						<label for="email" cssClass="control-label">Email</label>
						<div class="controls">
							<input id="email" type="text" value="${form.auth.email}" placeholder="voorbeeld: you@website.nl" class="input-medium" />
							<p class="help-block">Not a valid emailaddress. Example: you@website.nl</p>
						</div>
					</div>
					 --%>
					 
					<div class="control-group">
						<f:label path="dateOfBirth" cssClass="control-label">Date of birth</f:label>
						<div class="controls">
							<f:input path="dateOfBirth" placeholder="30-04-1980" cssClass="input-medium" />
							<f:errors path="dateOfBirth" cssClass="help-block" />
						</div>
					</div>
					
					<div class="control-group reset genderEditor">
						<f:label path="gender" cssClass="control-label">Gender</f:label>
						<div class="inline-boxes controls">
							<f:radiobuttons path="gender" itemLabel="prettyLongName"/>
						</div>
					</div>
					
					<a href="" id="trigger-password" class="indent-form spaced-min ${showPassword ? 'opened' : '' }">+ Change password</a>
					
					<div id="change-password">
						<div class="control-group">
							<f:label path="currentPassword" cssClass="control-label compact">Current password</f:label>
							<div class="controls">
								<f:password path="currentPassword" cssClass="input-medium" />
								<f:errors path="currentPassword" cssClass="help-block" />
							</div>
						</div>
						
						<div class="control-group">
							<f:label path="auth.password" cssClass="control-label">Password</f:label>
							<div class="controls">
								<f:password path="auth.password" cssClass="input-medium"/>
								<f:errors path="auth.password" cssClass="help-block"/>
							</div>
						</div>
						
						<div class="control-group">
							<f:label path="auth.repeatPassword" cssClass="control-label compact">Repeat password</f:label>
							<div class="controls">
								<f:password path="auth.repeatPassword" cssClass="input-medium"/>
								<f:errors path="auth.repeatPassword" cssClass="help-block"/>
							</div>
						</div>
					</div>
					
					<div class="control-group clear-both">
						<f:label path="usernameTwitter" cssClass="control-label"><img src="/static/img/ico-twitter.png" title="Twitter" /></f:label>
						<div class="controls">
							<f:input path="usernameTwitter" cssClass="input-medium" />
							<f:errors path="usernameTwitter" cssClass="help-block"/>
						</div>
					</div>
					
					<div class="control-group">
						<f:label path="usernameHyves" cssClass="control-label"><img src="/static/img/ico-hyves.png" title="Hyves" /></f:label>
						<div class="controls">
							<f:input path="usernameHyves" cssClass="input-medium" />
							<f:errors path="usernameHyves" cssClass="help-block"/>
						</div>
					</div>
					
					<div class="control-group">
						<f:label path="usernameFacebook" cssClass="control-label"><img src="/static/img/ico-facebook.png" title="Facebook" /></f:label>
						<div class="controls">
							<f:input path="usernameFacebook" cssClass="input-medium" />
							<f:errors path="usernameFacebook" cssClass="help-block"/>
						</div>
					</div>
				</fieldset>
				<input type="submit" style="position:absolute;top:0;left:-10000px;"/>
			</f:form>
			</c:if>
			<table class="table table-condensed table-clean">
				<tr>
					<th class="span2 indent">Name</th>
					<td>${fn:escapeXml(profile.user.name)}</td>
				</tr>
				<c:if test="${profile.user.dateOfBirth != null}">
					<tr>
						<th class="span2 indent">Age</th>
						<td>${profile.user.age}</td>
					</tr>
				</c:if>
				<c:if test="${not empty profile.user.gender.prettyLongName}">
					<tr>
						<th class="span2 indent">Gender</th>
						<td>${profile.user.gender.prettyLongName}</td>
					</tr>
				</c:if>
				<c:if test="${not empty profile.user.usernameTwitter || not empty profile.user.usernameFacebook || not empty profile.user.usernameHyves}">
					<tr>
						<th class="span2 indent">Find me on</th>
						<td>
						<c:if test="${not empty profile.user.usernameTwitter}">
							<a href="http://twitter.com/${profile.user.usernameTwitter}" title="Twitter: @${fn:escapeXml(profile.user.usernameTwitter)}" target="_blank"><img src="/static/img/ico-twitter.png" /></a>
						</c:if>
						<c:if test="${not empty profile.user.usernameFacebook}">
							<a href="http://facebook.com/${profile.user.usernameFacebook}" title="Facebook: facebook.com/${fn:escapeXml(profile.user.usernameFacebook)}" target="_blank"><img src="/static/img/ico-facebook.png" /></a>
						</c:if>
						<c:if test="${not empty profile.user.usernameHyves}">
							<a href="http://${profile.user.usernameHyves}.hyves.nl" target="_blank" title="Hyves: ${fn:escapeXml(profile.user.usernameHyves)}.hyves.nl"><img src="/static/img/ico-hyves.png" /></a>
						</c:if>
						</td>
					</tr>
				</c:if>	
			</table>
		</section>
	</div>
</div>

<div class="row equal-cols spaced-top">

	<!-- #rankings -->
	<div id="rankings" class="box span4 col leading">
		<header class="rich">
			<h2 class="h3 reset">Rank this week</h2>
		</header>
		<section class="reset">
			<ol class="unstyled reset">
			<c:forEach items="${profile.ranking}" var="userScore">
				<li class="${userScore.user.id == profile.user.id ? 'chart-entry highlight' : 'chart-entry'}"><a href="/profiel/${userScore.user.id}">
					<span class="index pull-left"><nf:format number="${userScore.position + 1}" /></span>
					<img src="${userScore.user.smallAvatarUrl}" />
					<c:out value="${userScore.user.name}"/>
					<span class="score pull-right h5"><nf:format number="${userScore.score}" /></span>
				</a></li>
			</c:forEach>
			</ol>
		</section>
		
	</div>
	
	<!-- #your-games -->
	<div class="span4 box col">
		<header class="rich">
			<h2 class="h3 reset">Most recent games</h2>
		</header>
		<section class="reset scroll-box">
			<table class="table table-condensed table-striped table-clean">
			<c:forEach items="${profile.recentGames}" var="gameScore">
				<tr>
					<td class="indent">
							<img src="${gameScore.game.video.imageUrl}" class="span1 bordered">
					</td>
					<td>
						<span class="small">${gameScore.game.prettyStart}</span>
						
						<c:choose>
						<c:when test="${you}">
						<h3 class="h5 reset compact"><a href="/game/${gameScore.game.id}/recap/${profile.user.id}" class="title">${gameScore.game.video.title}</a></h3>
						</c:when>
						<c:otherwise>
						<h3 class="h5 reset">${gameScore.game.video.title}</h3>
						</c:otherwise>
						</c:choose>
						
						<span class="small"><nf:format number="${gameScore.score}" /> points | <nf:format number="${gameScore.countTags}" /> ${gameScore.countTags == 1 ? 'tag' : 'tags' }</span>
					</td>
				</tr>
			</c:forEach>
			</table>
		</section>
	</div>
	
	<div id="pioneer-matches" class="box span4">
		<header class="rich relative ${you && user.countNewPioneerMatches > 0 ? 'highlight' : ''}">
			<h2 class="h3 reset">Most recent pioneer matches</h2>
			<img src="/static/img/match-pioneer-l.png" title="" class="pull-out-right" />
		</header>
		<section class="reset">
			<p class="small box-inner"><strong>Pioneer matches</strong> are made when you are first to enter a word that gets matched. This can only happen once per word.</p>
			<div class="row box-inner">
				<h3 class="h5 span1 pull-left">match</h3>
				<h3 class="h5 pull-left">in game</h3>
			</div>
			<div class="scroll-box bordered">
				<table class="table table-condensed table-striped table-clean">
					<tbody>
					<c:forEach items="${profile.pioneerMatches}" var="t" varStatus="status">
					<tr class="${you && status.index < user.countNewPioneerMatches ? 'highlight' : ''}">
						<td class="span1 indent"><c:out value="${t.tag}"/></td>
						<td>
						<c:choose>
						<c:when test="${you}">
							<a href="/game/${t.game.id}/recap/${profile.user.id}#tag-${t.id}" class="h5"><c:out value="${t.game.video.title}"/></a>
						</c:when>
						<c:otherwise>
							<h4 class="h5 reset"><c:out value="${t.game.video.title}"/></h4>
						</c:otherwise>
						</c:choose>
						</td>
						<td>+150</td>
					</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</section>
		
	</div>
</div>
</tt:body>
</tt:html>