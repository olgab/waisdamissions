<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="nf" uri="/WEB-INF/tld/NumberFormat.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tt" %>

<tt:html>
	<tt:head title="Registreren">
	</tt:head>
	<tt:body pageName="register">
		<div class="box span9">
	 		<h1 class="h2 form-shift">Registreren</h1>
	 		<c:if test="${user.totalScore > 0}">
				<p  class="form-shift">Uw <nf:format number="${user.totalScore}" /> behaalde punten worden na het registreren bewaard.</p>
			</c:if>			
			<f:form commandName="form" autocomplete="off" id="meedoenForm" class="form-horizontal">
	  		<fieldset>
					<div class="control-group">
						<f:label path="email" cssClass="control-label">E-mailadres</f:label>
						<div class="controls" >
							<f:input path="email" tabindex="4" cssClass="input-xlarge" /><br/>
							<f:errors path="email" cssClass="help-inline"/>
						</div>
					</div>
					<div class="control-group">
						<f:label path="auth.password" cssClass="control-label">Wachtwoord</f:label>
						<div class="controls">
		  				<f:password path="auth.password" tabindex="5" cssClass="input-xlarge" /><br/>
		  				<f:errors path="auth.password" cssClass="help-inline"/>
		  			</div>
		  		</div>
		  		<div class="control-group">   				
						<f:label path="auth.repeatPassword" cssClass="control-label">Wachtwoord herhalen</f:label>
						<div class="controls">
							<f:password path="auth.repeatPassword" tabindex="6" cssClass="input-xlarge" /><br/>
		  				<f:errors path="auth.repeatPassword" cssClass="help-inline"/>
		  			</div>
		  		</div>
					<div class="control-group">
						<f:label path="auth.name" cssClass="control-label">Gebruikersnaam</f:label>
						<div class="controls">
		  				<f:input path="auth.name" tabindex="7" cssClass="input-xlarge" /><br/>
		  				<f:errors path="auth.name" cssClass="help-inline"/>
		  				<p class="help-block">Uw gebruikersnaam is zichtbaar voor andere spelers. U kunt uw gebruikersnaam later niet meer wijzigen.</p>
					</div>
				</fieldset>
				<fieldset>
					<div class="control-group">
						<div class="controls">
							<label for="agree_tos" class="checkbox">
								<f:checkbox value="" path="agreeTos" id="agree_tos" tabindex="8"/>Ik ga akkoord met de <a href="/voorwaarden" target="_blank">algemene voorwaarden</a>
							</label>
							<f:errors path="agreeTos" cssClass="help-inline"/>
						</div>
					</div>
				

					<div class="form-actions">
						<a href="#" onclick="document.getElementById('meedoenForm').submit(); return false" id="submitMeedoen" class="btn btn-primary btn-large" tabindex="9">Beginnen</a>
					</div>
	
					<input type="submit" value="Beginnen" tabindex="10" style="position:absolute;top:0;left:-10000px;"/>
				</fieldset>  
			</f:form>
		</div>

		
	</tt:body>
</tt:html>
