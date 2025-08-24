# Roommate Finder Backend (Spring Boot, no Security)

Implements the functions from the software testing project for a roommate finder application.:

- **Authentication**: registerUser, loginUser, logoutUser, resetPassword
- **User Profile**: createUserProfile, editUserProfile, getUserProfile, validateProfileData
- **Matching**: getMatchedProfiles, calculateMatchScore, filterProfiles
- **Admin**: banUser, deleteUser, viewReportedUsers (+ report endpoint to create reports)

## Run
- **Prerequisites**: Java 17, Maven and need to create a database mysql name with roommate_finder

```bash
mvn spring-boot:run
```
- **Database**: MySQL (default port 3306)
  - Update `application.properties` with your MySQL username and password
  - The application will auto-create the necessary tables on startup

## Example Requests

Register:
```
POST /auth/register
{ "email":"u66xxxxx@au.edu", "password":"pass" }
```

Create profile:
```
POST /profiles/{userId}
{
  "age": 21, "gender":"M", "faculty":"Engineering",
  "lifestyle":"night-owl", "location":"Huamak",
  "budget": 6000, "petFriendly": true
}
```

Match:
```
GET /match/{userId}
```

Admin ban:
```
POST /admin/ban/{userId}
```
