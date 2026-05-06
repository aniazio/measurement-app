## Project Description

This project is a simple application that calculates statistics for a city based on measurements of pollutants. Major
features are:

- adding measurements of pollutants
- calculating statistics for last 3 hours
- generating month summary reports

## How to start the project?

To start db in the docker use following command:

```bash
docker-compose up -d
```

To start the project use following command:

```bash
mvn spring-boot:run
```

Profiles:

- 'testing' - for testing features. It provides new controller with additional endpoints
- 'local' - for local development (without integration with external service)
- 'default' - with integration with external service. Without additional endpoints

## Design decisions

- PostgreSQL database was used for storing data. Because of limited access patterns, PK was optimised for existing
  queries (PK = city_id, measurement_timestamp, sensor_id). It allows to proceed without additional index on db.
- Region data is not stored in the database, because this app is not responsible for maintaining that information.
  Instead, we fetch region data whenever needed, using approach similar to microservices architecture. Caching was
  introduced to reduce number of calls to external service during API usage.
- MapStruct for simple mapping between dto and entity. Dto for measurements introduced to decouple repository logic with
  service layer.
