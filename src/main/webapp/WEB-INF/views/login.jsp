<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="nf" uri="/WEB-INF/tld/NumberFormat.tld"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tt" %>

<tt:html>
	<tt:head title="Log in">
	</tt:head>
	<tt:body pageName="login">
		<div class="box span9">
			<h1 class="h2 form-shift">Log in</h1>
			<c:if test="${user.totalScore > 0}">
				<p class="form-shift">Your <nf:format number="${user.totalScore}" /> score will be added to your overall score after you've logged in.</p>
			</c:if>
	 		<f:form commandName="loginForm" action="/inloggen" id="loginForm" class="form-horizontal">
				<fieldset>

  					<f:errors path="*" cssClass="error-block" />

  					<div class="control-group">
  						<f:label path="emailaddress" cssClass="control-label">Email</f:label>
  						<div class="controls">
  							<f:input path="emailaddress" id="loginName" />
  						</div>
  					</div>
  					<div class="control-group">
  						<f:label path="password" cssClass="control-label">Password</f:label>
  						<div class="controls">
  							<f:password path="password" id="loginPassword" />
  							<p class="help-block"><a href="/wachtwoord-vergeten">Forgot your password?</a></p>
  						</div>
  					</div>
  					<div class="form-actions">
		          <a href="#" onclick="document.getElementById('loginForm').submit(); return false" id="submitLogin" class="btn btn-primary btn-large">Log in</a>
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