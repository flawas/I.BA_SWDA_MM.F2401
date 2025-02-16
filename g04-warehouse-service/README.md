# Modul SWDA - Service Microservice Sample
Beispiel Microservice für SWDA, basierend auf RabbitMQ für g99.

## Build lokal
Der lokale Build setzt eine laufende Docker-Installation voraus!

* `mvn package` - erzeugt ein sharde-JAR (service.jar) und ein Docker-Image.
* `mvn verify` - führt Integrationstests (mit TestContainer) aus.

## Runtime
Vorausgesetzt, der backbone läuft, kann der Service lokal vielfältig gestartet werden:

### IDE
* In der IDE kann die Klasse `ch.hslu.swda.Application`-Klasse (mit `main()`-Methode) gestartet werden.

### Konsole
* Java pur: `java -jar target/service.jar`
* Maven pur: `mvn exec:java`
* Maven mit Docker (interaktiv): `mvn docker:run`
* Maven mit Docker (daemon): 
  * `mvn docker:start` - Start des Containers
  * `mvn docker:logs` - Anzeige der Logs
  * `mvn docker:stop` - Stoppen und löschen des Containers
* Docker pur: `docker run --rm -it -e "RMQ_HOST=host.docker.internal" swda-24fs01/service-sample`


### API Dokumentation
http://localhost:8090/swagger/views/swagger-ui/
