<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<%@ attribute name="dynamic" required="true" %>
<div id="games-queue" class="game-queue box span4 ${dynamic == 'true' ? 'dynamic' : 'col'}">
	<header class="header rich"><h2 class="h3 reset">Doe direct mee <small>(<span id="games-queue-count">0 spellen</span>)</small></h2></header>
	<section class="reset">
		<!-- Als game-queue class="empty" heeft verschijnt de onderstaande boodschap en wordt de tabel verborgen -->
		<p class="box-inner show">Op dit moment zijn er geen spellen waar u aan mee kunt doen. Start zelf een spel door &eacute;&eacute;n van de kanalen op de <a href="/">homepage</a> te selecteren.</p>
		<div class="hide">
			<div class="row box-inner">
				<h3 class="h5 pull-left">Klik op een spel om mee te doen</h3>
				<h3 class="h5 pull-right">Start in</h3>
			</div>
			<div class="scroll-box bordered">
				<table class="table table-condensed-ext table-striped table-clean"></table>
			</div>
		</div>
	</section>
	
</div>	
