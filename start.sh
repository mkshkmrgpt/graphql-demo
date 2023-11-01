./gradlew clean build --no-daemon
docker build -t graphql-demo .
docker run -d -p 8080:8080 --name graphql-demo graphql-demo