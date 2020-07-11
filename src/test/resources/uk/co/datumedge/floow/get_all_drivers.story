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
            "lastName": Ball"
        },
        {
            "firstName": "Rose",
            "lastName": "Gill"
        },
        {
            "firstName": "Owen",
            "lastName": "Parsons"
        }
    ]
}