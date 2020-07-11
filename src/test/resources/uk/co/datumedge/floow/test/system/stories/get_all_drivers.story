Given the drivers:
|firstName|lastName|
|Chloe|Ball|
|Rose|Gill|
|Owen|Parsons|
When I GET /drivers
Then the JSON response is:
{
    "drivers": [
        {
            "id": "00000000-0000-0000-0000-000000000001",
            "firstname": "Chloe",
            "lastname": "Ball",
            "created": "1970-01-01T00:00:00Z"
        },
        {
            "id": "00000000-0000-0000-0000-000000000002",
            "firstname": "Rose",
            "lastname": "Gill",
            "created": "1970-01-01T00:00:00Z"
        },
        {
            "id": "00000000-0000-0000-0000-000000000003",
            "firstname": "Owen",
            "lastname": "Parsons",
            "created": "1970-01-01T00:00:00Z"
        }
    ]
}