# Introduction

Waisda? is a video labelling game. Players watch short video fragments (typically between 1 and 10 minutes long) and add relevant tags to the video at specific moments in time. If a tag matches another player’s tag (perhaps from a previous session for the same video), both players are awarded points. By showing score lists on the home page and giving out various prizes, players are encouraged to collect more points and enter more tags.

In this way, video fragments are tagged, which makes them indexable and searchable. Whether a tag is trustworthy can be measured using heuristics, the most important one being how many matching tags exist. The game has been online in various versions and has been used to tag video archives of several public broadcasting associations.

Waisda? Whitelabel is version of the website with a neutral visual style and a backend intended to be extended and customized for your own video labelling game. You can then 

## Intended audience

This document is intended for programmers who would like to take the white-label version of Waisda? and customize it to their own needs. To get the most out of this document, the reader is expected to be familiar with the technologies used in this project:

* The backend is written in the [Java programming language][java] using the [Spring framework][spring]. The views are written using [JSP][jsp].
* The website is backed by a [MySQL][mysql] database. The code binds to the database using the [Java Persistence API][jpa] and [Hibernate][hibernate].
* The frontend uses HTML, JavaScript and CSS based on [Twitter Bootstrap][bootstrap], [LESS][less] and [jQuery][jquery].
* [Maven][maven] is used as build environment.

[java]: http://docs.oracle.com/javase/
[spring]: http://www.springsource.org/
[jsp]: http://java.sun.com/products/jsp/syntax/2.0/syntaxref20.html
[mysql]: https://www.mysql.com/
[jpa]: https://en.wikipedia.org/wiki/Java_Persistence_API
[hibernate]: http://www.hibernate.org/
[bootstrap]: http://twitter.github.com/bootstrap/
[less]: http://lesscss.org/
[jquery]: http://jquery.com/
[maven]: https://maven.apache.org/

## How Waisda? works

There are several important types of entities in the project:

* A **video** has a title, description, source, screencap, length and information on how to play it. There are currently two players available: the JWPlayer (Flash), and the NPO player (Silverlight) that specifically plays content from Dutch public broadcasting associations. Each video in the project specifies which of the two players it would like to use, in combination with the parameters required for the players.
* A **game** is a session in which one or more players (called participants in the code) watch a video simultaneously and enter zero or more tags at specific timestamps relative to the video. Players can see the other participants’ names and their relative positions based on their session scores.
* A **tag entry** for a specific game is owned by a user and contains information on what tag was entered exactly, at what timestamp relative to the video it was entered and has a score based on whether the tag matches other users’ tags and/or words in dictionaries.
* A **dictionary** is a set of words which award extra points when entered by a user. Examples are names of celebrities and names of geographical locations.

To give you a good idea of what is possible on the website, here is an overview of all the pages:

* The header, visible on each page, shows:
  * how many tags have been entered in total, and how many of those match another tag;
  * options to login, logout and register if appropriate;
  * your total score and tag counts, if any.
* Almost every page shows the games queue in the bottom right corner. When a player starts a new game, all other visitors are alerted and invited to join.
* The footer, visible on each page, shows:
  * a link to the home page;
  * links to various pages with information;
  * links to Waisda?'s presence on social media sites;
  * credits in the form of company logos.
* The homepage shows:
  * a list of videos immediately available for play;
  * a four-step explanation of how to get started and score points;
  * a list of top scoring players based on games from the last seven days;
  * popular tags entered in games from the last seven days.
* The game page is where a single game is played:
  * the video to watch;
  * a text area to enter tags;
  * a list of participating players, showing their relative positions based on score;
  * a list of tags entered, with scores and information on what kind of match it is (if any).
* After playing a game, the player is taken to a page that recaps the game just played:
  * the title of the video watched;
  * a summary of tags entered, grouped by match type;
  * a list of participants, their scores and tag statistics;
  * a full list of every tag entered, with scores and match information.
* Clicking on a user's name anywhere on the site leads to a user's profile page with:
  * general information about the user (name and some contact information if the user chose to enter it);
  * score total;
  * current position based on games played in the last 7 days;
  * summaries of recent games played;
  * recent pionieer matches (which award more points than normal matches; see below).
* Clicking on one of the popular tags on the homepage brings you to a page dedicated to that tag, containing:
  * who entered the tag the first time, and for what video;
  * a top six of videos the tag appears in, with for each video a list of other popular tags for that video.
* Pages to login, register, request a password reset and actually reset a password.
* Some information pages: general information, how to play the game, terms of service.

## When do two tags *match*?

Tags are first normalized before they are compared to other tags. To normalize a tag, make it lowercase and remove all non-alphanumeric characters from it. Then remove any accents on any of the letters. Some examples:

* `Street` -> `street`
* `new york` -> `newyork`
* `séaNCe` -> `seance`
* `Route 66` -> `route66`
* `well-known` -> `wellknown`

Normalizing tags before comparing them makes differences in capitals, whitespace use and accents irrelevant.

Two normalized tag entries `(t1, t2)` match if all of the following conditions are met:

* The tags are for games of the same video;
* the tags were not entered by the same user;
* the times (relative to the video) at which the tags are entered are not more than 10 seconds apart;
* at least one of the following conditions is met:
 * `t1` is identical to `t2`;
 * a pair of normalized tag enties `(lo, hi)` exists in table `MatchingTagEntry` where `(t1, t2) == (lo, hi)` or `(t2, t1) == (lo, hi)`.

## How points are awarded

Whenever a player enters a tag, points are awarded for that specific tag entry. If the tag is found in one of the dictionaries (database table `DictionaryEntry`), the player is awarded 25 points. If the tag *matches* one of the existing tags, the player receives 50 points. Together this gives the player a potential 75 points. If the player would receive 0 points, they receive 5 points anyway for the effort, unless the tag is an identical match with another tag entry by the same player. Awarding points this way encourages players to enter tags that are relevant for the video, and to be original if they watch the same video multiple times.

Points awarded to tags might change at a later point. If a tag `t` did not match any other tags when it was first entered, but later a newly entered tag matches `t`, `t`'s score is increased. In this case, `t` does not get 50 points for matching, but a whopping 150. Tag `t` is said to be a *pioneer* because the player was the first to introduce this tag for this specific video. Awarding pioneers more points encourages players to watch and tag videos that don't have many tags entered yet: their initial scores may be low, but the potential score when other players watch and tag the video is substantial. Pioneer tags are given special attention on users' profiles and in the page header for players who are logged in.

TODO: Explain under what circumstances a tag's score is lowered.
