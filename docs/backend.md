# Backend architecture

## Database overview

The following sections discuss the individual tables and their fields.

### Video

Table `Video` has the following fields:

* `id`: auto-incrementing ID
* `title`: title of video for display in hyperlinks
* `duration`: duration of video in ms
* `imageUrl`: absolute URL of preview image
* `enabled`: whether video is available for new games
* `playerType`: either 'JW' or 'NPO' for the JWPlayer and Silverlight NPO player, respectively
* `sourceUrl`: for playerType 'JW', absolute URL of video to play
* `fragmentID`: for playerType 'NPO'
* `sectionNid`: for playerType 'NPO'
* `startTime`: for playerType 'NPO'

### User

Table `User` models players who have registered or played at least one game. It has the following fields:

* `id`: auto-incrementing ID
* `creationDate`: date/time at which user record was created

The following fields are for registered users only and are mandatory:

* `email`: email address of user
* `name`: username for public display
* `password`: encrypted and salted password

The following fields are for registered users only and are optional; they are only used in a user's public profile:

* `dateOfBirth`: date of birth of user
* `usernameFacebook`: Facebook username or profile URL
* `usernameHyves`: Hyves username
* `usernameTwitter`: Twitter username
* `gender`: user's gender

### Game

Table `Game` models games which link tag entries to videos in gaming sessions. It has the following fields:

* `id`: auto-incrementing ID
* `start`: date/time at which game starts (usually 20 seconds after game is created, to allow users to queue)
* `initiator_id`: ID of user who created the game
* `video_id`: ID of video tags are added to
* `countExistingVideoTags`: number of tags that were added to the video the moment the game was created; used to determine whether the "players from the past" should be shown while players are waiting for the game to start

### Participant

Whenever a user joins a game, a record is created in this table. It is used to draw the participants list while players wait for a game to begin.

It has the following fields:

* `id`: auto-incrementing ID
* `user_id`: ID of user who joined the game
* `game_id`: ID of game joined
* `joinedOn`: date/time when user joined game

### TagEntry

Table `TagEntry` keeps track of what users entered what tags for what games. It has the following fields:

* `id`: auto-incrementing ID
* `dictionary`: name of dictionary normalizedTag was found in the moment the entry was created (see table DictionaryEntry below)
* `normalizedTag`: normalized version of tag entered
* `score`: score as computed by the score engine; might change later on as more matches become available
* `tag`: tag as entered by user (unchanged)
* `gametime`: time at which tag was entered, relative to start of video (in ms)
* `typingDuration`: how long it took the user to type the tag (in ms)
* `game_id`: ID of game for which tag was entered (join with table Game to find out video)
* `owner_id`: ID of user who entered the tag
* `matchingTagEntry_id`: ID of oldest matching tag, if any
* `pioneer`: whether no matching tag was found the moment the tag was entered (see chapter Introduction for a detailed explanation)
* `creationDate`: absolute date/time at which tag was entered

### MatchingTag

Table `MatchingTag` contains pairs of normalized tags that allow tag entries to match and score more points. For example, the table could contain synonym pairs.

It has the following fields:

* `lo`: low normalized element of the pair
* `hi`: high normalized element of the pair

For each pair `(lo, hi)`, `lo < hi` must be true, where `<` is the MySQL comparison operator, based on the database's current character encoding and collation.

### DictionaryEntry

Table `DictionaryEntry` is queried whenever a new tag is entered, to determine whether the normalized version of the entered tag matches any of the records on this table. If it does, field `dictionary` is set for the `TagEntry` record and the player is awarded extra points.

It has the following fields:

* `normalizedTag`: the normalized version of the tag to match
* `dictionary`: name of the dictionary to which the tag belongs

### ResetPassword

Table `ResetPassword` contains a record for each password reset request. It has the following fields:

* `id`: auto-incrementing ID
* `user_id`: ID of user for which reset was requested
* `resetKey`: encrypted and salted key
* `creationDate`: date/time at which request was created
* `resetDate`: date/time at which the request was honored (by user following link in email)

## Webserver

The webserver is a Java Spring application and consists of Java classes, JSP templates (views), static resources and some configuration files. The configuration files can be found in directories `src/main/resources`, `src/main/webapp/META-INF` and `src/main/webapp/WEB-INF` and tell the application where to find the database, views and services, among other things. The static resources consist of images, JavaScript files and CSS files which are downloaded by the browser and can be found in `src/main/webapp/static`.

### Package overview

The classes are grouped into the following Java packages:

* `nl.waisda.controllers` contains the *controllers*: classes with entry points into the web application. URLs are mapped to method calls using the `@RequestMapping` annotations on the methods.
* `nl.waisda.domain` contains the domain classes that map to the database tables. Not all tables are necessarily mapped; only the ones that Hibernate needs to know about, for example because they are used in HQL queries. All mapped tables are listed in `src/main/webapp/META-INF/persistence.xml`.
* `nl.waisda.exceptions` contains custom `Exception` types.
* `nl.waisda.forms` contains classes that model `GET`/`POST` parameters for various requests to the controllers.
* `nl.waisda.interceptors` contains Spring *interceptors* that modify requests before they are passed to the controllers.
* `nl.waisda.model` contains model classes for passing data around between controllers, repositories, services and views.
* `nl.waisda.repositories` is responsible for exposing database queries as Java methods; most database tables have a corresponding class in this package.
* `nl.waisda.services` contains *service* classes: utility methods grouped into classes used by controllers and other services.
* `nl.waisda.tags` contains custom JSP tags.
* `nl.waisda.validators` contains validators that check posted form objects for consistency before they are given to controllers.

### Views

The JSP views can be found in `src/main/webapp/WEB-INF/views`. There is usually one view for each type of page.

Almost every page has a header and a footer, so these parts are separated into their own reuseable subviews in the form of custom JSP tags. They can be found in `src/main/webapp/WEB-INF/tags`.
