<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tt"%>

<tt:html>
<tt:head title="Password forgotten" />
<tt:body pageName="wachtwoord-vergeten">

	<div class="box span9">
	<h1 class="h2 form-shift">Forgot your password?</h1>
	<c:choose>
	<c:when test="${form.user == null}">
		<p class="help-block form-shift">If you forgot your password then please fill out the email address you provided during registration below.
			You will receive an email with instructions on how to change your password.</p>
		<f:form commandName="form" action="/wachtwoord-vergeten" id="form"
			class="form-horizontal" method="post">
			<fieldset>
				<div class="control-group">
					<f:label path="email" class="control-label">Email address</f:label>
					<div class="controls">
						<f:input path="email" id="email" /><br/>
						<f:errors path="*" cssClass="help-inline" />
					</div>
				</div>		
				<div class="form-actions">
					<a href="#"
					onclick="document.getElementById('form').submit(); return false"
					id="submitLogin" class="btn btn-primary btn-large">Request new password</a> <input
					type="submit" value="Inloggen in mijn account"
					style="position: absolute; top: 0; left: -10000px;" />
				</div>
			</fieldset>
		</f:form>
	</c:when>
	<c:otherwise>
		<p class="help-block form-shift">In a few moments you will receive an email with the instructions.
		<br/>
		<br/>
		<a href="/">Back to the homepage</a></p>
	</c:otherwise>
	</c:choose>
	</div>

</tt:body>
<script type="text/javascript">
	$('#email').focus();
	</script>
</tt:html>