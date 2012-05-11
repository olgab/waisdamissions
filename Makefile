default: run

run:
	MAVEN_OPTS="-Xms512m -Xmx1024m -XX:PermSize=256m -XX:MaxPermSize=512m -Xdebug -Xnoagent -Djava.compiler=NONE -Dlog4j.configuration=file:./target/classes/log4j.properties -Xrunjdwp:transport=dt_socket,address=8989,server=y,suspend=n" mvn jetty:run -e

clean:
	mvn clean

install:
	mvn clean install -Dmaven.test.skip

upload: install
	# uploading WAR to remote /root
	# run `make deploy` on remote to desploy
	scp target/manbijthond-web-0.0.1-SNAPSHOT.war mbh:

dump:
	mysqldump -u root --no-data        waisda-whitelabel MatchingTag Video DictionaryEntry Game TagEntry User ResetPassword Participant > mbh-create.sql
	mysqldump -u root --no-create-info waisda-whitelabel MatchingTag Video DictionaryEntry > mbh-data.sql
	sed -i '' -E 's/AUTO_INCREMENT=[0-9]+ //' mbh-create.sql
	zip mbh-data.sql.zip mbh-data.sql
	rm mbh-data.sql
