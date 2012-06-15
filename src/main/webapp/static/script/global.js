/* hide/show blocks on profile-page */
var details;
// global variables that hold the height and width of the videoplayer
var vidPlayerWidth = 618;
var vidPlayerHeight = 351;

jQuery(function() {
	
	// on-click of ...:
	details = $('#personal-details');
	
	if (details) {
		var editDetailsButton = $('a#edit-details', details);
		var submitDetailsButton = $('a#submit-details', details);
		var closeDetailsButton = $('a#close-details', details);
		var detailsTable = $('table', details);
		var detailsForm = $('#form', details);

		editDetailsButton.click(function(evt) {
			editDetailsButton.addClass('hide');
			submitDetailsButton.removeClass('hide');
			closeDetailsButton.removeClass('hide');
			detailsTable.addClass('hide');
			detailsForm.removeClass('hide');
			return false;
		});

		closeDetailsButton.click(function(evt){
			editDetailsButton.removeClass('hide');
			submitDetailsButton.addClass('hide');
			closeDetailsButton.addClass('hide');
			detailsTable.removeClass('hide');
			detailsForm.addClass('hide');
			return false;
		});

		submitDetailsButton.click(function(evt){
			$("#form").submit();
			return false;
		});
		
		function setPasswordFormState() {
			if(!$('#trigger-password').hasClass('opened')){
				$('#trigger-password').text('+ Change password');
				$('#change-password').addClass('hide');
				$('input[type=password]', '#change-password').val('');
			} else {
				$('#trigger-password').html('- <strong>Cancel</strong> change password');
				$('#change-password').removeClass('hide');
			}
		}
		
		if ($('#trigger-password')) {
			setPasswordFormState();
		}
		
		$('a#trigger-password', details).click(function(evt) {
			$(this).toggleClass('opened');
			$('#form').toggleClass('expanded');
			setPasswordFormState();
			return false;
		});
	}
	
	if (Silverlight.isInstalled) {
		$('#silverlight-message').hide();
	}

	$('.help').each(function(){
		//Grab the title attribute's value and assign it to a variable
		$(this).attr('data-title', $(this).attr('title'));
	     
    //Remove the title attribute's to avoid the native tooltip from the browser
    $(this).attr('title','');
    
		$(this).mouseover(function(e) {
	     
	    //Append the tooltip template and its value
	    $('body').append('<div class="tooltip">' + $(this).attr('data-title') + '</div>');    
	     
	    //Set the X and Y axis of the tooltip
	    $('.tooltip').css('top', e.pageY + 10 );
	    $('.tooltip').css('left', e.pageX + 20 );
	    
		}).mousemove(function(e) {
			
	    //Keep changing the X and Y axis for the tooltip, thus, the tooltip move along with the mouse
	    $('.tooltip').css('top', e.pageY + 10 );
	    $('.tooltip').css('left', e.pageX + 20 );
	    
	  }).mouseout(function() {
	  		    
	    //Remove the appended tooltip template
	    $('.tooltip').remove();
	    
	  });
	});
	
});
$(window).load(function(){
	$('.equal-cols').each(function(){
		$(this).equalHeights('.col');
	});
	$('.equal-cols-game').each(function(){
		$(this).equalHeights('.col-game');
	});

	// Now that everything has been loaded and resized,
	// we can fade everything in.
	clearTimeout(fadeInTimeout);
	fadeInTimeout = null;
	$("body").animate({ 'opacity' : 1 }, 350);
});
