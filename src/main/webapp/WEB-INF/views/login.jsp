<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="nf" uri="/WEB-INF/tld/NumberFormat.tld"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tt" %>

<tt:html>
	<tt:head title="Inloggen">
	</tt:head>
	<tt:body pageName="login">
		<div class="box span9">
			<h1 class="h2 form-shift">Inloggen</h1>
			<c:if test="${user.totalScore > 0}">
				<p class="form-shift">Uw <nf:format number="${user.totalScore}" /> behaalde punten worden na het inloggen bij uw bestaande score opgeteld.</p>
			</c:if>
	 		<f:form commandName="loginForm" action="/inloggen" id="loginForm" class="form-horizontal">
				<fieldset>

  					<f:errors path="*" cssClass="error-block" />

  					<div class="control-group">
  						<f:label path="emailaddress" cssClass="control-label">E-mailadres</f:label>
  						<div class="controls">
  							<f:input path="emailaddress" id="loginName" />
  						</div>
  					</div>
  					<div class="control-group">
  						<f:label path="password" cssClass="control-label">Wachtwoord</f:label>
  						<div class="controls">
  							<f:password path="password" id="loginPassword" />
  							<p class="help-block"><a href="/wachtwoord-vergeten">Wachtwoord vergeten?</a></p>
  						</div>
  					</div>
  					<div class="form-actions">
		          <a href="#" onclick="document.getElementById('loginForm').submit(); return false" id="submitLogin" class="btn btn-primary btn-large">Inloggen</a>
  						<input type="submit" value="Inloggen in mijn account" style="visibility:hidden"/>
  					</div>
	   			</fieldset>
			</f:form>
		</div>
	</tt:body>
	<script type="text/javascript">
	$('loginName').focus();
	</script>
</tt:html>