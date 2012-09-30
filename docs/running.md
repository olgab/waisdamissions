# Getting started

You will need at least Java 6, Maven and MySQL to run the server in debug mode. The scripts and examples below will look for the `java` and `mvn` executables.

## Downloading the source code

The source code can be found on the [GitHub project page](https://github.com/beeldengeluid/waisda).

You can either clone (or fork) the repository (`git clone git@github.com:beeldengeluid/waisda.git`), or you can [download the source as a ZIP file](https://github.com/beeldengeluid/waisda/zipball/master).

## Creating the database

The source tree contains a file `sql/create-database.sql` with a script that creates the database for you, specifying UTF-8 as the default character encoding and `utf8_general_ci` as the default collation. This is important when storing tag entries with accented characters. Also, for table indexes to work, the two columns on which SQL joins are executed need to be encoded with the same encoding and collation.

File `sql/create-tables.sql` creates all the tables for you, without any data in them. It specifies the tables with their columns and indices.

To run a `.sql` script, use `mysql`. For example:

```
$ mysql < sql/create-database.sql
```

To see how to specify username, password, hostname and other parameters, run `mysql --help`.

## Running in debug mode

The source tree has a Makefile which lists commands for various useful scenarios. The first (and default) target is called `run`, which runs the website in debug mode using the Jetty webserver available to Maven.

Before you do that, you have to point the webserver to the right database. Open `src/main/webapp/WEB-INF/jetty.xml` and find the tag with `name="driverProperties"`. Change the parameters to the appropriate values, then run `make` from the root of the source tree. The MySQL user you use will need selection and modification rights.

The first time, Maven will download lots of libraries the application depends on. It will take a few minutes. Subsequent times will be much faster.

## Deploying

To deploy the website in a proper J2EE container, run `make deploy`. This will create a `.war` file which you can then place, say, in a Tomcat container. The website expects to be the root website (all its URLs must be top-level), so you will probably have to rename the file to `ROOT.war` (at least in the case of Tomcat).

The database settings for running the website this way are read from `src/main/resources/config.properties` rather than `jetty.xml`. Look for the properties whose names start with `jdbc.`.

## Adding your own videos

To add your own videos, simply fill the `Video` table with records. They will then become available in the website for playing sessions with them and earning scores. The default video selection for the channels on the homepage chooses randomly (uniformly) from all available videos. The structure of the table is explained in the "Backend architecture" chapter.

If you have existing data available in SQL format, you can import it using various MySQL graphical tools, or using the command line:

```
$ mysql < videos.sql
```

If you have video data in CSV format, again you can use one of the many MySQL tools out there, or use the command line utility [`mysqlimport`](https://dev.mysql.com/doc/refman/5.0/en/mysqlimport.html). It allows you to map the various columns in the CSV file to specific columns in the `Video` table.

## Adding your own style

### Changing colors

All colors are defined in [variables.less](../src/main/webapp/static/styles/less/variables.less). The easiest way to change a color throughout the whole project is changing it's RGB-value in this less-file. To establish a link between the RGB-values and what you see on screen the variable-name should describe the color. This makes it easier to recognize and visualize the values and their on-screen representation. If a variable's name does not describe the new color very well please change it accordingly and run a simple search and replace action through all the less files.

### Adding your logo

The logo is placed in the header. To change the logo place your logo-image in the [images folder](../src/main/webapp/static/img). And change the path for the dummy-logo image to your logo in the file [body.tag](../src/main/webapp/WEB-INF/tags/body.tag). To find the dummy-logo image in the code search for alt="LOGO" within this file. The logo should leave enough space for the tag-line about the amount of tags and matches. In the current setup a logo should not be wider than 220 pixels. 

### Changing the grid

The document about [Front-end architecture](frontend.md) contains a description of how the grid can be accustomed to your specific needs. The current grid is based on 12 columns of 60 pixels wide divided by a 20 pixel wide gutter. This is also the document where you can find more in-debt descriptions about the front-end architecture. It's a starting point in case you need to make more changes to the visual design or layout. 
