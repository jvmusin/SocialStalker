web: java -Dserver.port=$PORT -Dspring.profiles.active=production $JAVA_OPTS -jar target/vkseeker-0.0.1-SNAPSHOT.jar
dev-no-telegram: java -Dserver.port=$PORT -Dspring.profiles.active=dev $JAVA_OPTS -jar target/vkseeker-0.0.1-SNAPSHOT.jar
dev-telegram-proxy: java -Dserver.port=$PORT -Dspring.profiles.active=dev,telegram-proxy $JAVA_OPTS -jar target/vkseeker-0.0.1-SNAPSHOT.jar