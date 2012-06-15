<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="nf" uri="/WEB-INF/tld/NumberFormat.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tt" %>

<tt:html>
	<tt:head title="Registreren">
	</tt:head>
	<tt:body pageName="register">
		<div class="box span9">
	 		<h1 class="h2 form-shift">Register</h1>
	 		<c:if test="${user.totalScore > 0}">
				<p  class="form-shift">Your <nf:format number="${user.totalScore}" /> will be added after you've registered.</p>
			</c:if>			
			<f:form commandName="form" autocomplete="off" id="meedoenForm" class="form-horizontal">
	  		<fieldset>
					<div class="control-group">
						<f:label path="email" cssClass="control-label">Emailaddress</f:label>
						<div class="controls" >
							<f:input path="email" tabindex="4" cssClass="input-xlarge" /><br/>
							<f:errors path="email" cssClass="help-inline"/>
						</div>
					</div>
					<div class="control-group">
						<f:label path="auth.password" cssClass="control-label">Password</f:label>
						<div class="controls">
		  				<f:password path="auth.password" tabindex="5" cssClass="input-xlarge" /><br/>
		  				<f:errors path="auth.password" cssClass="help-inline"/>
		  			</div>
		  		</div>
		  		<div class="control-group">   				
						<f:label path="auth.repeatPassword" cssClass="control-label">Repeat password</f:label>
						<div class="controls">
							<f:password path="auth.repeatPassword" tabindex="6" cssClass="input-xlarge" /><br/>
		  				<f:errors path="auth.repeatPassword" cssClass="help-inline"/>
		  			</div>
		  		</div>
					<div class="control-group">
						<f:label path="auth.name" cssClass="control-label">Username</f:label>
						<div class="controls">
		  				<f:input path="auth.name" tabindex="7" cssClass="input-xlarge" /><br/>
		  				<f:errors path="auth.name" cssClass="help-inline"/>
		  				<p class="help-block">Your username will be visible to other players. It cannot be changed after you've registered.</p>
					</div>
				</fieldset>
				<fieldset>
					<div class="control-group">
						<div class="controls">
							<label for="agree_tos" class="checkbox">
								<f:checkbox value="" path="agreeTos" id="agree_tos" tabindex="8"/>I agree with the <a href="#" target="_blank">terms and conditions</a>
							</label>
							<f:errors path="agreeTos" cssClass="help-inline"/>
						</div>
					</div>
				

					<div class="form-actions">
						<a href="#" onclick="document.getElementById('meedoenForm').submit(); return false" id="submitMeedoen" class="btn btn-primary btn-large" tabindex="9">Start</a>
					</div>
	
					<input type="submit" value="Beginnen" tabindex="10" style="position:absolute;top:0;left:-10000px;"/>
				</fieldset>  
			</f:form>
		</div>

		
	</tt:body>
</tt:html>
