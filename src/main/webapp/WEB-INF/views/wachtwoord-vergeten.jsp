<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tt"%>

<tt:html>
<tt:head title="Wachtwoord vergeten" />
<tt:body pageName="wachtwoord-vergeten">

	<div class="box span9">
	<h1 class="h2 form-shift">Wachtwoord vergeten</h1>
	<c:choose>
	<c:when test="${form.user == null}">
		<p class="help-block form-shift">Als u uw wachtwoord vergeten bent, kunt u hieronder het
		emailadres invullen waarmee u zich geregistreerd heeft. U ontvangt dan
		een e-mail met instructies om uw wachtwoord opnieuw in te stellen.</p>
		<f:form commandName="form" action="/wachtwoord-vergeten" id="form"
			class="form-horizontal" method="post">
			<fieldset>
				<div class="control-group">
					<f:label path="email" class="control-label">E-mailadres</f:label>
					<div class="controls">
						<f:input path="email" id="email" /><br/>
						<f:errors path="*" cssClass="help-inline" />
					</div>
				</div>		
				<div class="form-actions">
					<a href="#"
					onclick="document.getElementById('form').submit(); return false"
					id="submitLogin" class="btn btn-primary btn-large">Nieuw wachtwoord aanvragen</a> <input
					type="submit" value="Inloggen in mijn account"
					style="position: absolute; top: 0; left: -10000px;" />
				</div>
			</fieldset>
		</f:form>
	</c:when>
	<c:otherwise>
		<p class="help-block form-shift">U ontvangt binnen enkele momenten een email met verdere
		instructies.
		<br/>
		<br/>
		<a href="/">Terug naar de homepage</a></p>
	</c:otherwise>
	</c:choose>
	</div>

</tt:body>
<script type="text/javascript">
	$('email').focus();
	</script>
</tt:html>