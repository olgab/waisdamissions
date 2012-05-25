/*--------------------------------------------------------------------
* JQuery Plugin: "EqualHeights" & "EqualWidths"
* by: Scott Jehl, Todd Parker, Maggie Costello Wachs (http://www.filamentgroup.com)
*
* Copyright (c) 2007 Filament Group
* Licensed under GPL (http://www.opensource.org/licenses/gpl-license.php)
*
* Description: Compares the heights or widths of the top-level children of a provided element
and sets their min-height to the tallest height (or width to widest width). Sets in px units.
* Dependencies: jQuery library
* Usage Example: $(element).equalHeights();
* Version: 2.0, 07.24.2008
* Changelog:
* 08.02.2007 initial Version 1.0
* Modified and extended by Johan Huijkman, q42.nl
* 05.07.2012 scans for specific class within an element (makes it more flexible, adds possibility to call it several times on one page)
* 05.08.2012 search for an element with class .leading. Adjust the heights of all the other element within a row according to this
* 05.09.2012 search for component scroll-box. Sets height of this element to fit and overflow-y = scroll.
* 05.10.2012 added function xHeight() which takes care of mozilla's different implementation of 'box-sizing'
--------------------------------------------------------------------*/

(function($) { 
	function xHeight(elt){
		var height = $.browser.mozilla ? elt.height() : elt.outerHeight();
		return height;
	}
	$.fn.equalHeights = function(col) {
		$(this).each(function(){
			var leadingCol = $(this).find('.leading').length > 0 ? $(this).find('.leading') : false;
			var currentTallest = 0;
			var elements = col ? $(this).find(col) : $(this).children();	
			elements.css({'min-height': 0, 'max-height': 'none'});
			
			if(leadingCol) {
				currentTallest = xHeight(leadingCol);
			}else{
				elements.each(function(i){
					if (xHeight($(this)) > currentTallest) { currentTallest = xHeight($(this)); }
				});
			}
			
			// scroll-box?
			if($(this).find('.scroll-box').length > 0){
				$(this).find('.scroll-box').each(function(){
					$(this).css({'height': '0'});
					var restHeight = xHeight($(this).closest('.box'));
					$(this).css({'height':(currentTallest - restHeight) + 'px'});
				});
			}
			
			// for ie6, set height since min-height isn't supported
			if ($.browser.msie && $.browser.version == 6.0) { elements.css({'height': currentTallest}); }
			elements.css({'min-height': currentTallest, 'max-height': currentTallest});
			
		});
		return this;
	};
})(jQuery);


