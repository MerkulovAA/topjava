#### Rest API

**For meals:**
```
- get all                    |-> curl http://localhost:8080/topjava/rest/meals
- create                     |-> curl -d '{"id": null, "dateTime": "2015-03-21T13:00:00", "description": "new", "calories": 725, "user": null}' -H "Content-Type: application/json" -X POST http://localhost:8080/topjava/rest/meals 
- get by id                  |-> curl http://localhost:8080/topjava/rest/meals/{id}.
- update                     |-> curl -d '{"id": null, "dateTime": "2015-05-18T13:00:00", "description": "update", "calories": 258, "user": null}' -H "Content-Type: application/json" -X PUT http://localhost:8080/topjava/rest/meals/{id}
- delete                     |-> curl -X DELETE http://localhost:8080/topjava/rest/meals/{id}.
- get filter by date and time|-> curl http://localhost:8080/topjava/rest/meals/filter?startDate={startDate}&startTime={startTime}&endDate={endDate}&endTime={endTime}.
```

**For users:**
```
- get all     |-> curl http://localhost:8080/topjava/rest/admin/users.
- create      |-> curl -d '{"id": null,"name": "newUser","email": "newUser@gmail.com","password": "newUser","roles": ["ROLE_USER"],"meals": null}' -H "Content-Type: application/json" -X POST http://localhost:8080/topjava/rest/admin/users
- get by id   |-> curl http://localhost:8080/topjava/rest/admin/users/{id}.
- update      |-> curl -d '{"id": null,"name": "updateUser","email": "updateUser@gmail.com","password": "updateUser","roles": ["ROLE_USER"],"meals": null}' -H "Content-Type: application/json" -X PUT http://localhost:8080/topjava/rest/admin/users/{id}
- delete      |-> curl -X DELETE http://localhost:8080/topjava/rest/admin/users/{id}.
- get by email|-> curl http://localhost:8080/topjava/rest/admin/users/by?email={email}.
```




