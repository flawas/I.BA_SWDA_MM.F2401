# Modul SWDA - Service Microservice Sample
Beispiel Microservice für SWDA, basierend auf RabbitMQ für g04.

## Build lokal
Der lokale Build setzt eine laufende Docker-Installation voraus!

* `mvn package` - erzeugt ein sharde-JAR (service.jar) und ein Docker-Image.
* `mvn verify` - führt Integrationstests (mit TestContainer) aus.

## Runtime
Vorausgesetzt, der backbone läuft, kann der Service lokal vielfältig gestartet werden:

### IDE
* In der IDE kann die Klasse `ch.hslu.swda.micro.Application`-Klasse (mit `main()`-Methode) gestartet werden.

### Konsole
* Java pur: `java -jar target/service.jar`
* Maven pur: `mvn exec:java`
* Maven mit Docker (interaktiv): `mvn docker:run`
* Maven mit Docker (daemon): 
  * `mvn docker:start` - Start des Containers
  * `mvn docker:logs` - Anzeige der Logs
  * `mvn docker:stop` - Stoppen und löschen des Containers
* Docker pur: `docker run --rm -it -e "RMQ_HOST=host.docker.internal" swda-24fs01/service-sample`

## Micronaut 4.3.7 Documentation

- [User Guide](https://docs.micronaut.io/4.3.7/guide/index.html)
- [API Reference](https://docs.micronaut.io/4.3.7/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/4.3.7/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

- [Micronaut Maven Plugin documentation](https://micronaut-projects.github.io/micronaut-maven-plugin/latest/)
## Feature serialization-jackson documentation

- [Micronaut Serialization Jackson Core documentation](https://micronaut-projects.github.io/micronaut-serialization/latest/guide/)


## Feature micronaut-aot documentation

- [Micronaut AOT documentation](https://micronaut-projects.github.io/micronaut-aot/latest/guide/)


## Feature maven-enforcer-plugin documentation

- [https://maven.apache.org/enforcer/maven-enforcer-plugin/](https://maven.apache.org/enforcer/maven-enforcer-plugin/)

## API Documentation (OpenAPI)

- http://localhost:8084/swagger-ui/
- http://localhost:8084/redoc/
- http://localhost:8084/rapidoc/

## Used guides
- [ACCESS A MONGODB DATABASE WITH MICRONAUT DATA MONGODB](https://guides.micronaut.io/latest/micronaut-data-mongodb-synchronous-maven-java.html)
- [Micronaut Data MongoDB](https://micronaut-projects.github.io/micronaut-data/latest/guide/#mongo)
- [API Versioning](https://docs.micronaut.io/latest/guide/#apiVersioning)
- [Micronaut Test](https://micronaut-projects.github.io/micronaut-test/latest/guide/)
- [Micronaut Bean Mappers](https://docs.micronaut.io/latest/guide/#beanMappers)
