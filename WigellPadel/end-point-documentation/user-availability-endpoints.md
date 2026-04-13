# Wigell Padel API

ADMINS can use all endpoints below

## USER AVAILABILITY ENDPOINTS

### GET /api/v1/availability?date={YYYY-MM-DD}&courtId={courtId}

Gets all available slots for that datetime and that court

Response (200 OK)

```json
{
  "courtId": 1,
  "courtName": "Cardio B court",
  "date": "2026-04-07",
  "availableTimes": [
    8,
    9,
   10,
   11,
   12,
   13,
   14, 
   15,
   16,
   17,
   18,
   19,
   20,
   21
]
}
```
    