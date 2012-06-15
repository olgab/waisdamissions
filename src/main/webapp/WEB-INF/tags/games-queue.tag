<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<%@ attribute name="dynamic" required="true" %>
<div id="games-queue" class="game-queue box span4 ${dynamic == 'true' ? 'dynamic' : 'col'}">
	<header class="header rich"><h2 class="h3 reset">Join now <small>(<span id="games-queue-count">0 games</span>)</small></h2></header>
	<section class="reset">
		<!-- Als game-queue class="empty" heeft verschijnt de onderstaande boodschap en wordt de tabel verborgen -->
		<p class="box-inner show">Currently there are no games to join. Start one yourself by  selecting one of the channels on the <a href="/">homepage</a> .</p>
		<div class="hide">
			<div class="row box-inner">
				<h3 class="h5 pull-left">Click to select a game</h3>
				<h3 class="h5 pull-right">Starts in</h3>
			</div>
			<div class="scroll-box bordered">
				<table class="table table-condensed-ext table-striped table-clean"></table>
			</div>
		</div>
	</section>
	
</div>	
