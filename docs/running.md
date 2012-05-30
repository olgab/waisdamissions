# Downloading and running the software

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

Before you do that, you have to point the webserver to the right database. Open `src/main/webapp/WEB-INF/jetty.xml` and find the tag with `name="driverProperties"`. Change the parameters to the appropriate values, then run `make` from the root of the source tree.

The first time, Maven will download lots of libraries the application depends on. It will take a few minutes. Subsequent times will be much faster.

## Deploying

To deploy the website in a proper J2EE container, run `make deploy`. This will create a `.war` file which you can then place, say, in a Tomcat container. The website expects to be the root website (all its URLs must be top-level), so you will probably have to rename the file to `ROOT.war` (at least in the case of Tomcat).

The database settings for running the website this way are read from `src/main/resources/config.properties` rather than `jetty.xml`. Look for the properties whose names start with `jdbc.`.