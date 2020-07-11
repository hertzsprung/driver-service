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
            "firstName": "Chloe",
            "lastName": "Ball",
            "created": "1970-01-01T00:00:00Z"
        },
        {
            "firstName": "Rose",
            "lastName": "Gill",
            "created": "1970-01-01T00:00:00Z"
        },
        {
            "firstName": "Owen",
            "lastName": "Parsons",
            "created": "1970-01-01T00:00:00Z"
        }
    ]
}