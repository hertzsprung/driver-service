# driver-service
Manages driver details for a hypothetical insurance company

## Compile and test
To compile, and run the test suite:
````
mvn verify
````

## Running
To run:
````
mvn spring-boot:run
````

## REST API clarifications
* A driver's `created` field is stored with date and time down to the second.
* `/drivers/byDate` returns all drivers from the specified date and time onwards, down to the second.

## Assumptions
* The CSV database is sufficiently small to fit into memory.  If this were no longer the case, then the `/drivers` endpoint should enforce pagination.
 