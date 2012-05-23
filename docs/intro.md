# Introduction

Waisda? is a video labelling game. Players watch short video fragments (typically between 1 and 10 minutes long) and add relevant tags to the video at specific moments in time. If a tag matches another player’s tag (perhaps from a previous session for the same video), both players are awarded points. By showing score lists on the home page and giving out various prizes, players are encouraged to collect more points and enter more tags.

In this way, video fragments are tagged, which makes them indexable and searchable. Whether a tag is trustworthy can be measured using heuristics, the most important one being how many matching tags exist. The game has been online in various versions and has been used to tag video archives of several public broadcasting associations.

Waisda? Whitelabel is version of the website with a neutral visual style and a backend intended to be extended and customized for your own video labelling game. You can then 

## Intended audience

This document is intended for programmers who would like to take the white-label version of Waisda? and customize it to their own needs. To get the most out of this document, the reader is expected to be familiar with the technologies used in this project:

* The backend is written in the [Java programming language][java] using the [Spring framework][spring]. The views are written using [JSP][jsp].
* The website is backed by a [MySQL][mysql] database. The code binds to the database using the [Java Persistence API][jpa].
* The frontend uses HTML, JavaScript and CSS based on [Twitter Bootstrap][bootstrap], [LESS][less] and [jQuery][jquery].
* [Maven][maven] is used as build environment.

[java]: http://docs.oracle.com/javase/
[spring]: http://www.springsource.org/
[jsp]: http://java.sun.com/products/jsp/syntax/2.0/syntaxref20.html
[mysql]: https://www.mysql.com/
[jpa]: https://en.wikipedia.org/wiki/Java_Persistence_API
[bootstrap]: http://twitter.github.com/bootstrap/
[less]: http://lesscss.org/
[jquery]: http://jquery.com/
[maven]: https://maven.apache.org/

## How Waisda? works

TODO List various pages

There are several important types of entities in the project:

* A **video** has a title, description, source, screencap, length and information on how to play it. There are currently two players available: the JWPlayer (Flash), and the NPO player (Silverlight) that specifically plays content from Dutch public broadcasting associations. Each video in the project specifies which of the two players it would like to use, in combination with the parameters required for the players.
* A **game** is a session in which one or more players (called participants in the code) watch a video simultaneously and enter zero or more tags at specific timestamps relative to the video. Players can see the other participants’ names and their relative positions based on their session scores.
* A **tag entry** for a specific game is owned by a user and contains information on what tag was entered exactly, at what timestamp relative to the video it was entered and has a score based on whether the tag matches other users’ tags and/or words in dictionaries.
* A **dictionary** is a set of words which award extra points when entered by a user. Examples are names of celebrities and names of geographical locations.

TODO: Explain scoring in more detail

* player matches
* synonyms
* dictionaries
* pioneers
