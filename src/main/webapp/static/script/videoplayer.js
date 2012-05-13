var NPOPlayer = base2.Base.extend({
			constructor : function(elementId, config) {
				this.evtHandles = [];
				this.elementId = elementId;

				var seekTime = Math.max(0, config.startTime);
				var autoplay = config.startTime >= 0
						&& config.startTime < config.duration;

				this.config = {
					width : vidPlayerWidth,
					height : vidPlayerHeight,
					volume : '100',
					showBorder : 'no',
					embedEnabled : 'yes',
					controlBarEnabled : 'no',
					viewMode : 'video',
					playMode : autoplay ? 'play' : 'pause',
					playlistEnabled : 'no',
					playlistAdvanceEnabled : 'no',
					fragmentID : config.fragmentID,
					callbackFunction : 'ugCallbackFunction'
				};
				if (autoplay) {
					this.config.seekTime = Utils.unparseTime(seekTime);
				}

				this.player = new UGSLPlayer(document.getElementById(elementId),
						'http://embed.player.omroep.nl/slg/ugslplayer.xap',
						this.config);
			},

			// update variables
			onTick : function(elapsed, duration) {
				this.elapsed = Math.ceil(elapsed * 1000);
				this.duration = Math.ceil(duration * 1000);

				// check whether the videoplayer initialized completely
				if (this.duration != 0) {
					this
							.dispatchEvents("tick", [ this.elapsed,
									this.duration ]);
				}
			},

			getElapsed : function() {
				return this.elapsed;
			},

			addEvent : function(evtName, handle) {
				this.evtHandles.push({
					evtName : evtName,
					handle : handle
				});
			},

			dispatchEvents : function(evtName, params) {
				// loop through all event handles, match event names and fire
				for ( var i = 0; i < this.evtHandles.length; i++) {
					if (this.evtHandles[i].evtName == evtName) {
						this.evtHandles[i].handle.apply(this, params);
					}
				}
			},

			moveTo : function(sec) {
				// this.player.sendEvent("SCRUB", sec);
			},

			play : function() {
				this.player.setConfigProperty('playMode', 'play');
			},

			stop : function() {
				this.player.setConfigProperty('playMode', 'pause');
			}
		});

var ugCallbackFunction = function(eventType, args) {
	if (eventType = 'PropertyChanged') {
//		console.log('property %s changed from %s to %s', args.name, args.oldvalue, args.newvalue);
		var vp = game.videoplayer;
		if (args.name == 'seekTime' || args.name == 'videoStatus') {
			var streamStatus = vp.player.getConfigProperty('videoStatus');
			if (streamStatus != 'playing') {
				return;
			}
			var seekTime = Utils.parseTime(vp.player
					.getConfigProperty('seekTime'));
			var startTime = Utils.parseTime(vp.player
					.getConfigProperty('startTime'));
			var endTime = Utils.parseTime(vp.player
					.getConfigProperty('endTime'));
			if (!startTime || !endTime) {
				return;
			}
			var duration = endTime - startTime;
			var elapsed = seekTime;
			if (elapsed > duration - 5) {
				vp.nearEnd = true;
			}
			vp.onTick(elapsed, duration);
		} else if (args.name == 'playMode' && args.newvalue == 'pause' && vp.nearEnd) {
			vp.dispatchEvents("fragmentEnd");
		}
	}
};

var onSilverlightLoad = function() {
	game.initVideoPlayer();
};

var JWPlayer = base2.Base.extend({
	constructor : function(elementId, imageUrl, sourceUrl) {
		this.evtHandles = [];
		this.elementId = elementId;

		var self = this;

		this.player = jwplayer(elementId).setup({
			flashplayer: '/static/mediaplayer-5.9/player.swf',
			file: sourceUrl,
			image: imageUrl,
			height: 351,
			width: 618,
			events: {
				onComplete: function() { self.dispatchEvents("fragmentEnd"); },
				onTime: function() {
					var elapsed = Math.ceil(self.player.getPosition() * 1000);
					var duration = Math.ceil(self.player.getDuration() * 1000);

					// check whether the videoplayer initialized completely
					if (duration != 0) {
						self.dispatchEvents("tick", [ elapsed, duration ]);
					}
				}
			}
		});
	},

	getElapsed : function() {
		return Math.ceil(this.player.getPosition() * 1000);
	},

	addEvent : function(evtName, handle) {
		this.evtHandles.push({
			evtName : evtName,
			handle : handle
		});
	},

	dispatchEvents : function(evtName, params) {
		// loop through all event handles, match event names and fire
		for ( var i = 0; i < this.evtHandles.length; i++) {
			if (this.evtHandles[i].evtName == evtName) {
				this.evtHandles[i].handle.apply(this, params);
			}
		}
	},

	moveTo : function(sec) {
		this.player.seek(sec);
	},

	play : function() {
		this.player.play(true);
	},

	stop : function() {
		this.player.pause(true);
	}
});
