# Backend architecture

## Database overview

The following sections discuss the individual tables and their fields.

### Video

Table Video has the following fields:

* id: auto-incrementing ID
* title: title of video for display in hyperlinks
* duration: duration of video in ms
* imageUrl: absolute URL of preview image
* enabled: whether video is available for new games
* playerType: either 'JW' or 'NPO' for the JWPlayer and Silverlight NPO player, respectively
* sourceUrl: for playerType 'JW', absolute URL of video to play
* fragmentID: for playerType 'NPO'
* sectionNid: for playerType 'NPO'
* startTime: for playerType 'NPO'

### User

* id: auto-incrementing ID
* creationDate: date/time at which user record was created

The following fields are for registered users only and are mandatory:

* email: email address of user
* name: username for public display
* password: encrypted and salted password

The following fields are for registered users only and are optional; they are only used in a user's public profile:

* dateOfBirth: date of birth of user
* usernameFacebook: Facebook username or profile URL
* usernameHyves: Hyves username
* usernameTwitter: Twitter username
* gender: user's gender

### Game

* id: auto-incrementing ID
* start: date/time at which game starts (usually 20 seconds after game is created, to allow users to queue)
* initiator_id: ID of user who created the game
* video_id: ID of video tags are added to
* countExistingVideoTags: number of tags that were added to the video the moment the game was created; used to determine whether the "players from the past" should be shown while players are waiting for the game to start

### Participant

### TagEntry

### MatchingTag

### DictionaryEntry

### ResetPassword

## Webserver

### Overview

* Classes
* Views
* Static resources
* Configuration files

### Package and class overview

#### Controllers

#### Services

#### Repositories

Not all tables are mapped as a Java class

### Views

* The html, head and body tags
