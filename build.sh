mvn clean install assembly:single
mvn jacoco:report
readlink -f target/site/jacoco/index.html
