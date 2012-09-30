# Customizing Waisda?

## Customizing channels

By default, the channels on the homepage show a uniform random selection of all available videos. If you would like to customize this, the place to do that is in method `getChannelContent` of class `VideoService`. This method is called by the `HomeController` to determine what channels to show. The `VideoService` talks to the `VideoRepository` to actually query the database. By writing your own SQL queries to select videos, you can make the video selection as complex as you prefer.

## Adding matching tags

Table `MatchingTag` contains tuples of normalized tags that *match* one another. When users enter tags, they receive higher scores if their tags match other tags, as explained in detail in the introduction of the documentation.

To supply your own matching tags (for example, pairs of synonyms), populate the `MatchingTag` table with them. Make sure that every tuple's elements are lexicographically sorted. E.g. use `('aalmoes', 'gift')` rather than `('gift', 'aalmoes')`.

## Adding dictionaries

Adding dictionaries with words is done by filling table `DictionaryEntry` with records. Each entry is tied to a dictionary which allows the scoring engine to differentiate between different dictionaries. For example, if you have entries for dictionaries `people` and `placenames`, the scoring engine could choose to award more points to place names than to people's names.

An alternative use for dictionaries is to make a dictionary `stopwords` and have the scoring engine award 0 points to all such tag entries.

## Translating pages

Because the project is not set up to support internationalization (where the language of the UI can be changed through a simple configuration setting), translating involves going through the source files and translating individual sentences.

Phrases to be translated can be found in these files:

* all views in `src/main/webapp/WEB-INF/views`
* some of the scripts in `src/main/webapp/static/script`
* the error messages in `src/main/resources/ValidationMessages.properties`

## Customizing scoring

Computing tags' scores is handled by class `ScoringService`. It takes care of doing the appropriate database queries and combining all information. After it sets the appropriate properties on the tag entries (`matchingTagEntry`, `dictionary` and `pioneer`), it calls `TagEntry.recomputeScore` to update the entry's `score` property. Then it stores the updated entry in the database.

## Modifying the database structure

The classes in package `nl.waisda.domain` model domain objects and have annotations on their properties that specify how they should be mapped to and from database records. This means that when you add, remove or change fields in a table, you should update the domain classes accordingly. If you don't, the website will report errors on startup.

Similarly, if you add a database table and would like to be able to access it from within the application using Hibernate, you need to create a class for it. Tag the class with `@Entity` and annotate the properties. Finally, add an entry for it in `src/main/webapp/META-INF/persistence.xml`.

Not all database tables are currently represented as Java classes: records in tables `DictionaryEntry` and `MatchingTag` don't need to exist as objects in the web application and so have no mappings.

## Adding new pages

To add a new kind of page to the application, create a JSP view. You can look at existing view pages in `src/main/webapp/WEB-INF/views` for examples. Then, in an existing or in a new controller, create a method with a `@RequestMapping` that listens to a specific URL, and return the name of the view. Use a `ModelMap` argument to supply the view with data. Again, you can look at existing controllers for plenty of examples.
