# Flow of the rate limiter:

- A user makes a request to the client server 
- The client server now wants to check if it should proceed with the request or if the user should be rate limited
- the client sends an API request to the RLaaS service
- The request body must contain an identiier that identifies a user:
- Sample API Request Body: 
{
    "identifier" : "the users ip/username/credential",
    "endpoint" : "/getUsers" (each endpoint may have a different rate limit),
}
- Sample API Request Header:
X-API-KEY: "API Key Issued to that particular Client"

- The Service then checks if the user is has exhausted their limit or not

- If the user is not rate limited then simply return a JSON response to the client

- If the user is rate limited then return a false response to the client.

# Additional Features:
- [ ] A client can choose to have multiple identifiers that are unique and can create a request body for their own use case. For example: A client may want to use IP as an idnetifier, a client may want to use API-Key as an identfier, a client may want to use user as an identifier and a client may want a combination on these

- [ ] An API Endpoint that returns the configurations of that specific client.