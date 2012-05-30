default: run

run:
	MAVEN_OPTS="-Xms512m -Xmx1024m -XX:PermSize=256m -XX:MaxPermSize=512m -Xdebug -Xnoagent -Djava.compiler=NONE -Dlog4j.configuration=file:./target/classes/log4j.properties -Xrunjdwp:transport=dt_socket,address=8989,server=y,suspend=n" mvn jetty:run -e

clean:
	mvn clean

install:
	mvn clean install -Dmaven.test.skip

dump:
	# dump the current database structure to sql/create-tables.sql
	mysqldump -u root --no-data waisda MatchingTag Video DictionaryEntry Game TagEntry User ResetPassword Participant > sql/create-tables.sql
	sed -i '' -E 's/AUTO_INCREMENT=[0-9]+ //' sql/create-tables.sql
