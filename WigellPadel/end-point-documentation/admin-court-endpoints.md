# Wigell Padel API

## ADMIN COURT ENDPOINTS

### GET /api/v1/courts
Gets all courts
Request:
Response (200 OK)

### GET /api/v1/courts/{courtId}
Gets court with id courtId
Request:
Response (200 OK)


## POST /api/v1/courts

Creates court

Request:
```json
{
  "courtName"       : "Cardio B Court",
  "pricePerHourSek" : 400.0
}
```

Response (201 CREATED):
```json
{
  "id"              : 1,
  "courtName"       : "Cardio B Court",
  "pricePerHourSek" : 400.0,
  "pricePerHourEur" : 40.0
}
```

## PUT /api/v1/courts/{courtId}

Updates Court

Request:
```json
{
  "courtName"       : "Cardi B Court",
  "pricePerHourSek" : 400.0
}
```

Response (200 OK):
```json
{
  "id"              : 1,
  "courtName"       : "Cardio B Court",
  "pricePerHourSek" : 400.0,
  "pricePerHourEur" : 4.0
}
```
### DELETE /api/v1/courts/{courtId}
Deletes court with id courtId 
Request:
Response: (204 NO CONTENT)

