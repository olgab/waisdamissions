# Customizing Waisda?

## Adding your own videos

* In SQL format
* In CSV format

## Customizing channels

## Adding matching tags

## Adding dictionaries

## Adding your own style
### Changing colors
All colors are defined in [variables.less](https://github.com/beeldengeluid/waisda/tree/master/src/main/webapp/static/styles/less/variables.less). The easiest way to change a color throughout the whole project is changing it's RGB-value in this less-file. To establish a link between the RGB-values and the visual design the variable-name should describe the color. This makes it easier to recognize the values and their representation on the screen. If a variable's name does not describe the new color very well please change it accordingly and run a simple search and replace action through all the less files.

### Adding your logo
The logo is placed in the header. To change the logo place your logo-image in the [images folder](https://github.com/beeldengeluid/waisda/tree/master/src/main/webapp/static/img). And change the path  for the dummy-logo image to your logo in the file [body.tag](https://github.com/beeldengeluid/waisda/blob/master/src/main/webapp/WEB-INF/tags/body.tag). To find the dummy-logo image in the code search for alt="LOGO" within this file. The logo should leave enough space for the tag-line about the amount of tags and matches. In the current setup a logo should not be wider than 220 pixels. 

### Changing the grid
The document about [Front-end architecture](https://github.com/beeldengeluid/waisda/blob/master/docs/frontend.md) contains a description of how the grid can be accustomed to your specific needs. The current grid is based on 12 columns of 60 pixels wide divided by a 20 pixel wide gutter.

## Translating pages

## Customizing scoring

## Modifying the database structure

* Adding tables
* Adding columns
* Hibernate mappings

## Adding new pages

* Add controller method
* Add JSP view
* Possibly create new view model class
