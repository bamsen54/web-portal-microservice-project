# Wigell Padel API

## USER BOOKING ENDPOINTS

ADMINS can use all endpoints below

## POST /api/v1/bookings

Creates Booking
USERS can only book for themselves so customerId is ignored
ADMINS can book for any customer

Request:
```json
{
  "customerId"       : 1,
  "courtId"          : 1,
  "bookingDate"      : "2026-04-06",
  "startTime"        : 13,
  "numberOfPlayers"  : 4
}
```

Response (201 CREATED)

```json
{
  "id"                : 1,
  "bookingReference"  : "53826746-5f2c-4726",
  "customerId"        : 1,
  "username"          : "bamsen54",
  "courtId"           : 1,
  "courtName"         : "Cardio B court",
  "bookingDate"       : "2026-04-06",
  "startTime"         : 12,
  "totalPriceSek"     : 400.0,
  "totalPriceEur"     : 40.0,
  "numberOfPlayers"   : 4
}
```

### PATCH /api/v1/bookings/{bookingId}  (Permitted fields: courtId, date, startTime, numberOfPlayers, )
Patches booking

Request:

```json
{
  
  "courtId" : 2
  
}
```

Resonse (200 OK)

```json
{
  "id"                : 1,
  "bookingReference"  : "182148fc-dc9a-45d4",
  "customerId"        : 1,
  "username"          : "bamsen54",
  "courtId"           : 2,
  "courtName"         : "WAP Court",
  "bookingDate"       : "2026-04-06",
  "startTime"         : 13,
  "totalPriceSek"     : 750.0,
  "totalPriceEur"     : 75.0,
  "numberOfPlayers"   : 4
}
```

### GET /api/v1/bookings?customerId={customerId}
Gets all bookings for customer

USERS can only get bookings for themselves. So customerId has to match the principal's id. 
ADMINS can get bookings for any customer

Response (200 OK)

```json
[
  {
    "id"               : 1,
    "bookingReference" : "182148fc-dc9a-45d4",
    "customerId"       : 1,
    "username"         : "bamsen54",
    "courtId"          : 2,
    "courtName"        : "WAP Court",
    "bookingDate"      : "2026-04-06",
    "startTime"        : 13,
    "totalPriceSek"    : 750.0,
    "totalPriceEur"    : 75.0,
    "numberOfPlayers"  : 4
 }
]
```