# driver-service
Manages driver details for a hypothetical insurance company.

## Compile and test
To compile, and run the test suite:
````shell script
mvn verify
````
JBehave tests require localhost port 8080.

## Running
To run:
````shell script
mvn spring-boot:run
````

### Example usage

Create two drivers:
````shell script
curl --header "Content-Type: application/json" -H "Accept: application/json" \
  --request POST \
  --data '{"firstname":"Lucas", "lastname":"Rees", "date_of_birth":"1980-05-01"}' \
  http://localhost:8080/driver/create

curl --header "Content-Type: application/json" -H "Accept: application/json" \
  --request POST \
  --data '{"firstname":"Jessica", "lastname":"Greene", "date_of_birth":"1974-02-23"}' \
  http://localhost:8080/driver/create
````

Find all drivers:
````shell script
curl -H "Accept: application/json" http://localhost:8080/drivers
````

Find drivers by date:
````shell script
curl -H "Accept: application/json" http://localhost:8080/drivers/byDate?date=2020-07-12T08:29:30Z
````

## REST API clarifications
* A driver's `created` field is stored with date and time.
* `/drivers/byDate` returns all drivers from the specified date and time onwards, down to the second.
* `firstname` and `lastname` cannot be blank and cannot contain commas.
* All driver fields are mandatory.
* Driver IDs are UUID strings.

## Assumptions
* The CSV database is sufficiently small to fit into memory.  If this were no longer the case, then the `/drivers` endpoint should enforce pagination.
* The workload is read-intensive, hence the choice of a `StampedLock` for concurrent reads, exclusive writes.

## Implementation
The driver service is implemented with Spring Boot and Jakarta Bean Validation.
Integration tests are implemented with JUnit 5 and Mockito.  System tests are implemented in JBehave.

### CSV database
Data is stored in a CSV file at the default path `db.csv`.  To specify a custom path, use:
````shell script
mvn spring-boot:run -Dspring-boot.run.arguments=--csvFile=<customPath>
````

The CSV file is formatted as:
````
id,firstname,lastname,date_of_birth,creationInstant
...
````
with no header line and no quoted fields. The <code>date_of_birth</code> is formatted as an `ISO_LOCAL_DATE`.
The <code>creationInstant</code> is formatted as an `ISO_INSTANT`.