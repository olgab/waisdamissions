# Frontend architecture

## Twitter Bootstrap

The front-end is based on the Twitter Bootstrap framework. This provides a flexible and robust base for the Waisda?-project. Full documentation on this framework can be found at [twitter.github.com/bootstrap](http://twitter.github.com/bootstrap/ "Twitter Bootstrap"). Because the framework offers way much more than needed for this version some options were stripped. But as you wil go through this project you will still encounter some styles and settings not being used. Those were left in to offer more flexibilty from the start. In order to extend the ability to customize the stylesheets to one`s specific needs they were not generated from the [Bootstrap site](http://twitter.github.com/bootstrap/download.html "Customize and download twitter bootstrap"). But the original .less files were taken from the github repository and changed and adapted to the specific needs for this project. Changes that were made to the original less-folder:

* less-files were removed. Compare the folder [/styles/less](https://github.com/beeldengeluid/waisda/tree/master/src/main/webapp/static/styles/less) and the full list at the [twitter/bootstrap repository](https://github.com/twitter/bootstrap/tree/master/less "Twitter bootsrap repository") on github to see which components have been removed
* almost all remaining .less files have been changed or extended. Most changes involve colors, margins, paddings, sizing
* components.less has been added. This contains specific styling for Waisda?® on top of the bootstrap-files

Less-files starting with the word ‘responsive-' contain specific styling in order to make your design act on different screen-sizes. Some changes were made to this files but it was not fully tested. The current version only has one fixed width. The responsive stylesheets are left to offer some examples of the grid-system. More information on how-to uses the styles for responsive design can be found in the section about [responsive layout](http://twitter.github.com/bootstrap/scaffolding.html#responsive "Bootstrap's responsive design") on the Bootstrap-site.

## jQuery

jQuery is a cross-browser JavaScript library designed to simplify the client-side scripting of HTML. Twitter bootstrap offers the possibility to use javascript plugins. None of these are being implemented right now. But all javascript throughout the project makes use of the JQuery library.

##Less

Less is a dynamic stylesheet language that extends CSS with dynamic behavior such as variables, mixins, operations and functions. For example colors and generic paddings and margins can be set from the variables.less file. From then on they can be imported and used in any other less-file.
Less compiles to css. This can be done server-side or client-side by adding a javascript-file. For Waisda?® running less server-side is recommended. Because a lot of javascript-processes already run on the client. So running less client-side may slow-down page rendering.
Less-files are placed in the folder [/styles/less](https://github.com/beeldengeluid/waisda/tree/master/src/main/webapp/static/styles/less). These are compiled to css in folder [/styles/css](https://github.com/beeldengeluid/waisda/tree/master/src/main/webapp/static/styles/css/). The latter also contains a file called [styles.css](https://github.com/beeldengeluid/waisda/tree/master/src/main/webapp/static/styles/css/styles.css). This is the only css file that is called from all pages. It contains the calls to all different stylesheets. More information and documentation can be found at [lesscss.org](lesscss.org).

Note: because less is compiled server-side [styles.css](https://github.com/beeldengeluid/waisda/tree/master/src/main/webapp/static/styles/css/styles.css) contains calls to .css files. When running less client-side this file should have the `.less` extension and contain calls to all other `.less` files.

## Grid

The flexible grid system in Twitter bootstrap shows the full potential of using less. It makes it very easy to adjust the layout according a predefined grid. Currently the grid is set to 12 columns of 60 pixels wide and a gutter of 20 pixels. This is all set in `variables.less`. The css for the grid can be generated using functions defined in `mixins.less`. Currently the grid is only generated in `grid.less`. But other examples can be found in the `responsive-[...].less` files. In order to change the grid for the whole site change the variables set in `variables.less`. Or create a function call to `#grid` with specific parameters as shown in `responsive-768px-979px.less` for example.
Based on the variables you have set several utility classes will be generated like `span-n` and `offset-n` (n being the amount of columns). You can use these to set specific widths or offsets to elements following the pre-defined grid.

Besides the pixel-defined grid-system there is also a possibility for a flexible percentage-based layout. This makes use of a predefined width and gutter in percentages set in `variables.less`. The percentage- and the pixel-based system can be used amongst each-other as you will see when going through this project. More information about the usage and definition of the grid-system can be found in the grid section on the Bootstrap site.

Note: currently the utility classes set in the html are based on the 12-columns grid systems. So when the variables for the grid are changed the utility-classes that are set in the html will most probably also have to be changed accordingly.

## OOCSS

The styling throughout Waisda?® is based on the principles of Object Oriented CSS. To understand how the styling is setup and to be able extend and customize it to your own needs it is essential to be familiar with these principles. So if you're not already then Smashing magazines offers a perfect primer with its Introduction to Object Oriented CSS.

## components.less

This stylesheet contains all style-definitions for components that are added on top of the Bootstrap styling specifically for Waisda?®. The most important addition is the introduction of the .box class. Because most components on the Waisda?® site will appear as box-like. containers of content there is an extensive set of styles to s presenttyle and shape these. See page `/static/box-component.html` for a full overview of the options. 
An overview of the other components, their usage and where-to-find them:

__Main page-layout__ (appear on every page)
* `.brand-marker`
The bar on top on every page containing the Waisda?-logo
* `header.site-header`
The main header containing the logo and login/register links
* `section.main`
Main content section
* `.site-footer`
The main footer containing links to static pages and the sponsors/supporters

__Components that can be used generically__
* `.channel`
_usage:_ Game entry showing a screencap, title and details.
_example(s):_ Home- and tagpage
* `.order`
_usage:_ Showing a set of steps in a process.
_example(s):_ Homepage
* `.chart-entry`
_usage:_ Display an entry in a high-scores list.
_example(s):_ Home-, profile-, game- and tagpage
* `.tag-entry`
_usage:_ Display a tag within `.tagcloud'.
_example(s):_ Homepage
* `.game-queue`
_usage:_ Display overview of active games. 
_example(s):_ Static on Homepage. Dynamic (showing in lower right corner) on every but gamepage
* `.tag-list`
_usage:_ Displaying tag-entries in a game
_example(s):_ Game- and recappage
* `.help`
_usage:_ Help-icon. Shows a text-bubble on hover (See section Id's vs Classes for more info).
_example(s):_ Recappage
* `.tooltip`
_usage:_ The text-bubble that shows when a help-icon is hovered. Generated by javascript (See section Id's vs Classes for more info).
_example(s):_ Recappage

__Components specific for gamepage__
Following components are only used on and specifically styled for the gamepage.
* `.input-mega-xxl`
The big `<input type='text'>` below the video
* `.compact-overlay`
Class is added to `#vid-overlay-screen` by javascript. Used to re-style the component so it appears as a white transparent overlay on top of input. 
Shown when video is initializing so users won't be able to enter text 
* `.video`
The container for the video-object.
* `.timer-intro`
Contains the countdown-text before the video starts.

__Components specific for profile-page__
Following components are only used on and specifically styled for the profilepage.
* `.pull-out-right`
Pull out the icon in the header of the 'pioneer-matches'-component

## utilities.less

This stylesheet is part of the bootstrap frame-work. It contains generic styles that can be used on every element to add quick specific style changes. For example changing an elements `float-property` to `left` or `right` by adding the class `.pull-left` or `pull-right`. This file has been extended with lots of classes for _positioning_, _alignment_, _spacing_ and _toggling visibility_. In _OOCSS_ it is prefered to add these generic classes instead of directly adding or changing style-properties for a specific component.

## Id's vs classes

All id`s that are used in the html are hooks for javascript code. So please leave them as they are. Classes can be changed, tossed around and accustomed to your needs. However there are some exceptions to this rule because some classes are used for generic javascript functionality. So they may be removed. But removing them may have some consequences. The classes that function as javascript hooks are:

* `class="help"`
    this class will generate a styled tooltip for the element that will show/hide on mouseover/mouseout. The title attribute contains the text that will be shown in the tooltip.
* `class="equal-cols"` together with `class="col"`
    all elements with class="col" within a containing elements with class="equal-cols" will get the same height. This is handled by the JQuery plugin ‘jquery.equalheights.js` which can be found in the ‘/javascript/plugins` folder. The function is called from ‘global.js` as followed:

    ```JavaScript
    $('.equal-cols').each(function(){
     $(this).equalHeights('.col');
    });
    ```

    There's also an option to choose one element's height as leading over the height of all other elements with `class="col"`. By adding `class="col leading"` all other elements with `class="col"` will adjust vertically to the same height as the `leading` element.
    The choice for the class-names `equal-cols` and `col` is arbitrary. The plugin provides in any combination of class names for the container and column elements.
* `class="scroll-box"`
    This class works only in the context of the `class="col"`. When an element within `class="col"` has `class="scroll-box"` it will size vertically to the remaining height of its container and the `overflow-y` will be set to `automatic`. When the content exceeds the vertical boundary, as set by JavaScript, a scrollbar will appear.

## JavaScript

### The games queue

### Submitting tag entries

* Validity of tag entries
