var gamesQueue;

jQuery(function() {
	gamesQueue = jQuery('#games-queue');

	if (gamesQueue.length > 0) {
		GameQueue.start();		
	}
	// set y-pos
	GameQueue.positioning();
	
	$(window).bind('scroll resize',function(){
		GameQueue.positioning();
	})
	gamesQueue.bind('mouseenter', function(){ GameQueue.showing(); });
	gamesQueue.bind('mouseleave', function(){ GameQueue.hiding(); });
});

var GameQueue = {
	games : {},

	timeDelta : 0,

	lastEffectAt : 0,
	
	previouslyEmpty : true,

	start : function() {
		GameQueue.update();
		setInterval(GameQueue.updateAllHTML, 250);
	},

	update : function() {
		jQuery.ajax({
			url : "/current-queues",
			success : function(games) {
				if (games) {
					GameQueue.timeDelta = new Date().getTime()
							- games.serverTime;
					jQuery.each(games.games, function(i, game) {
						GameQueue.games[game.id] = game;
					});
					setTimeout(GameQueue.update, 2500);
				}
			}
		});
	},

	updateAllHTML : function() {
		var size = 0;
		jQuery.each(GameQueue.games, function(i, game) {
			if (GameQueue.getRemainingMs(game) > 0) {
				size++;
			}
		});
		var empty = size == 0;

		// Update class
		var now = new Date().getTime();
		if (empty != GameQueue.previouslyEmpty && now - GameQueue.lastEffectAt > 500) {
			
			jQuery('#games-queue .show').fadeOut('fast', function() {
				jQuery('#games-queue .show').removeClass('show').addClass('to-be-hidden');
				jQuery('#games-queue .hide').fadeIn('fast', function(){
					jQuery('#games-queue .hide').removeClass('hide').addClass('show');
					jQuery('#games-queue .to-be-hidden').removeClass('to-be-hidden').addClass('hide');
				});
			});
			
			GameQueue.previouslyEmpty = empty;
			GameQueue.lastEffectAt = now;
			var h3El = jQuery('#games-queue header');
			h3El.animate({'backgroundColor': empty ? '#999' : '#f60'}, 350);
			
			var h3spanEl = jQuery('#games-queue small');
			h3spanEl.animate({'color': empty ? '#eee' : '#fff'}, 350);
			
			if(empty) gamesQueue.removeClass('attention');
			else if(!gamesQueue.hasClass('attention')) gamesQueue.addClass('attention');
		}

		// Update count
		var countText = size == 1 ? '1 game' : size + ' games';
		jQuery('#games-queue-count').text(countText);

		// Update individual game <tr>s
		jQuery.each(GameQueue.games, function(i, game) {
			GameQueue.updateGameHTML(game);
		});
	},

	getRemainingMs : function(game) {
		// Compute how many more milliseconds before the game starts.
		var now = new Date().getTime();
		var remainingMs = game.start - now + GameQueue.timeDelta;
		return remainingMs;
	},

	updateGameHTML : function(game) {
		var elId = 'game-' + game.id;
		var el = jQuery('#' + elId);
		var remainingMs = GameQueue.getRemainingMs(game);

		// Create element if it doesn't exist yet.
		if (el.length == 0 && remainingMs > 0) {
			el = jQuery('<tr/>', { 'id' : elId });

			var imageTdEl = jQuery('<td/>', { 'class' : 'span1 indent' });
			imageTdEl.append('<a href="/game/' + game.id
					+ '">'
					+ '<img src="' + game.video.imageUrl
					+ '" class="span1 bordered"></a>');
			el.append(imageTdEl);

			var gameUrl = '/game/' + game.id;

			var titleTdEl = jQuery('<td/>', { 'class' : 'text-left' });
			titleTdEl.append('<a href="' + gameUrl
					+ '"><h4 class="h5">' + game.video.title
					+ '</h4></a>');
			el.append(titleTdEl);

			var timeTdEl = jQuery('<td/>', { 'class' : 'span1 text-right' });
			timeTdEl.append('<span class="small time-remaining nowrap"></span>');
			el.append(timeTdEl);

			el.click(function() {
				document.location.href = gameUrl;
			});

			jQuery('#games-queue table').append(el);
			
		}

		if (remainingMs > 0) {
			// Update remaining time
			jQuery('.time-remaining', el).text(Math.ceil(remainingMs / 1000) + ' sec...');
		} else {
			if (el) {
				el.detach();
			}
			delete GameQueue.games[game.id];
		}
	},
	positioning : function(){
		var headerheight = gamesQueue.find('header').outerHeight();
		var top = $(window).height() - headerheight;
		gamesQueue.css({'top':top+'px', 'height':headerheight+'px'});
	},
	showing : function(){
		var headerheight = gamesQueue.find('header').outerHeight();
		gamesQueue.css({'top':'99999px', 'height':'auto'});
		var height = gamesQueue.outerHeight()
		var top = $(window).height() - gamesQueue.outerHeight();
		var animtop = $(window).height() - headerheight;
		gamesQueue.css({'top':animtop+'px', 'height':headerheight+'px'});
		
		gamesQueue.stop().animate({
			height:height+'px',
			top : top+'px'
		},{
			duration:250
		});
	},
	hiding : function(){
		var headerheight = gamesQueue.find('header').outerHeight();
		gamesQueue.stop().animate({
			height:headerheight + 'px',
			top : $(window).height() - headerheight+'px'
		},{
			duration:250
		});
	}
};