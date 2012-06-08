# Customizing Waisda?

## Adding your own videos

* In SQL format
* In CSV format

## Customizing channels

## Adding matching tags

## Adding dictionaries

## Adding your own style
### Changing colors
All colors are defined in [variables.less](https://github.com/beeldengeluid/waisda/tree/master/src/main/webapp/static/styles/less/variables.less). The easiest way to change a color throughout the project is changing the RGB-value in this less-file. To make it easier to connect the RGB-values to the visual design the variable-name depicts some sort of description of the value. If the new value no longer represents the RGB-value please change it accordingly and run a simple Search and Replace through all the less files.
Example:...

### Adding your logo
The logo is placed in the header. To change the logo place your logo-image in the folder [](). And change the path  for the dummy-logo image to your logo in the file [body.tag](https://github.com/beeldengeluid/waisda/blob/master/src/main/webapp/WEB-INF/tags/body.tag). To find the dummy-logo image in the code search for alt="LOGO" within this file. The logo should leave enough space for the tag-line about the amount of tags and matches. In the current setup a logo should not be wider than 220 pixels. 

### Changing the grid

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
