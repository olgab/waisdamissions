# Downloading and running the software

## Downloading the source code

The source code can be found on the [GitHub project page](https://github.com/beeldengeluid/waisda).

You can either clone (or fork) the repository, or you can download the source as a ZIP file.

## Creating the database

The source tree contains a file `sql/create-database.sql` with a script that creates the database for you, specifying UTF-8 as the default character encoding and utf8_general_ci as the default collation. This is important when storing tag entries with accented characters. Also, for table indexes to work, the two columns on which SQL joins are executed need to be encoded with the same encoding and collation.

File `sql/create-tables.sql` creates all the tables for you, without any data in them. It specifies the tables with their columns and indices.

TODO supply example data sets?

## Running in debug mode

The source tree has a Makefile which lists commands for various useful scenarios. The first (and default) target is called run, which runs the website in debug mode using the Jetty webserver available to Maven.

TODO explain how to configure database connection parameters

## Deploying
