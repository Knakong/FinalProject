# FinalProject




### Team
- Sandra Harpole -  Scrum Master
- Forrest January - Team Member
- Kna Kong - Repo Owner
- Natasha Gough - Database Administator

### Overview
Collaborative project starting with database schema creation, and wireframe buildout. Project monitoring was achieved through trello board user stories. 

Our team decided to make an application called Paseo. Paseo is a meant to be a community where a walk loving user can connect with other likeminded users. With Paseo, users can search for others in their area in accordance with a variety of preferences selected by the user. On the one hand, if a user wanted a one on one connection, they can do so by connecting with other individuals via private messageing. On the other, if a user is open to meeting up with mutiple people with a variety of preferences they can create a "walkers wanted ad" which is a white pages of sorts, intended for groups or individuals to connect. 

At the onset of the project, our team thought that it would require very little in the way of database tables. As we went through the planning process, we realized in order to acheive a product with the functionality we want, we would need a robust database. We ended with 10 tables and 5 join tables. 

![image](https://user-images.githubusercontent.com/74070200/218876920-4112dc05-549e-4571-910d-ee3d65c70d88.png)


After completing all of the required 29 entities classes, including several key classes, and jUnit tests, we moved on to completing user stories from our trello board. As we worked through each story, we built out required repository, services, serviceImpls and controllers. We also tested necessary routing via Postman ensuring we were getting the right data. 

Deployed application: 

http://35.83.15.242:8080/Paseo/#/home

### Expected Routes

| Return Type | Route            | Functionality |
| ----------- | ---------------- |---------------|
|User | GET /authenticate| User can sign in|
|List< User > | GET api/users    |Get all users |
| User   | GET api/users/{id}       |Get one user by id|
| User   | POST api/register       |Register/Create a new user|
| User   | PUT api/users/{id}       |Update an existing user by id|
| void   | DELETE api/users/{id}      |Delete an existing user by id|
|List < Address >| GET api/address| Returns a list of address|

| Return Type | Route            | Functionality |
| ----------- | ---------------- |---------------|
| Address| POST  api/user/{UserId}| Create user address|
|Address| POST api/address|Create address|
|Address| PUT api/address/{addressId} | Update address|

| Return Type | Route            | Functionality |
| ----------- | ---------------- |---------------|
|List<WalkLocation>| GET api/walkLocation | Get all WalkLocations|
|WalkLocation| GET api/walkLocation/{locationId} | Get WalkLocation {locationId}|
|WalkLocation| POST api/walkLocation | Create WalkLocation|
|WalkLocation| PUT api/walkLocation/{locationId} | Update WalkLocation {locationId}|
|WalkLocation| DELETE api/walkLocation/{locationId} | Delete WalkLocation {locationId}|
 
| Return Type | Route            | Functionality |
| ----------- | ---------------- |---------------|  
|List< WalkType > | GET api/walkType    |Get all WalkTypes |
|List< Category > | GET api/walkCategory    |Get all WalkLocations |

 | Return Type | Route            | Functionality |
| ----------- | ---------------- |---------------| 
| FollowedUser | POST api/followedUsers/{id}| Create followed users|
|FollowedUser| DELETE api/followedUsers/{id}| Delete a followed user|
  
  | Return Type | Route            | Functionality |
| ----------- | ---------------- |---------------|
|List < Gender > | GET api/genders | Returns a list of genders|
  
  | Return Type | Route            | Functionality |
| ----------- | ---------------- |---------------|
  |List < Messages > | GET api/messages | Returns a list of messages|  
  | Message   | GET api/messages /{id}       |Get one message by id|
  | List < Messages >  | POST api/messages/recevied      |Returns a list of received messages|
| Message   | POST api/messages/create      |Register/Create a new message|
|  Message   | PUT api/messages /{messageId}       |Update an existing messages  by id|
| void   | DELETE api/messages /{messageId}      |Delete an existing messages  by id|
  
| Return Type | Route            | Functionality |
| ----------- | ---------------- |---------------|
|List<Walk>| GET api/walks | Get all walks|
|List<Walk>| GET api/walks/search | Search walks|
|Walk| GET api/walks/{walkId} | Get walk {walkId}|
|List<Walk>| GET api/walks/user/{userId} | Search walks by user id|
|Walk| POST api/walks| Create a new walk|
|Walk| PUT api/walks/{walkId}| Update walks by id|
|Walk| DELET api/walks/{walkId}|Delete walk by id|
|List<Walk>| GET api/walks/joined/{id}| Returns of list of joined walks by user id|
  

  
  ### Technologies User
  - Java
  - Angular
  - MAMP
- SQL
- HTML
- Gradle
- Maven
- JPA/Hibernate
- JUnit Testing
- Bootstrap
- CSS
- MySQLWorkbench
- Trello
- WireFrame
- GitHub
- SpringToolSuite
- Terminal
  
  ## LessonsLearned
  
 - Ran into JSON ignore issues during front end development
- Importance of composite key classes for join tables
- Importance of status codes in API
- Learning how to nest async methods so values are not null
  
  
### Future Goals
  - Map integration allowing users to search by a map.
