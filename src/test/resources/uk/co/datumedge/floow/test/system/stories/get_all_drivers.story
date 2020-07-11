Given the drivers:
|firstname|lastname|date_of_birth|
|Chloe|Ball|1980-04-12|
|Rose|Gill|1973-08-01|
|Owen|Parsons|1992-02-03|
When I GET /drivers
Then the JSON response is:
{
    "drivers": [
        {
            "id": "00000000-0000-0000-0000-000000000001",
            "firstname": "Chloe",
            "lastname": "Ball",
            "date_of_birth": "1980-04-12",
            "created": "1970-01-01T00:00:00Z"
        },
        {
            "id": "00000000-0000-0000-0000-000000000002",
            "firstname": "Rose",
            "lastname": "Gill",
            "date_of_birth": "1973-08-01",
            "created": "1970-01-01T00:00:00Z"
        },
        {
            "id": "00000000-0000-0000-0000-000000000003",
            "firstname": "Owen",
            "lastname": "Parsons",
            "date_of_birth": "1992-02-03",
            "created": "1970-01-01T00:00:00Z"
        }
    ]
}