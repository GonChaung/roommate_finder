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

Sample data
```
-- Users
INSERT INTO users (id, email, password_hash, role, banned, created_at)
VALUES
(1, 'alice@student.au.edu', '5f4dcc3b5aa765d61d8327deb882cf99', 'USER', false, NOW()),
(2, 'bob@student.au.edu',   '5f4dcc3b5aa765d61d8327deb882cf99', 'USER', false, NOW()),
(3, 'carol@student.au.edu', '5f4dcc3b5aa765d61d8327deb882cf99', 'USER', false, NOW()),
(4, 'admin@au.edu',         '5f4dcc3b5aa765d61d8327deb882cf99', 'ADMIN', false, NOW());

-- Profiles
INSERT INTO profiles (id, age, gender, faculty, lifestyle, location, budget, pet_friendly, user_id)
VALUES
(1, 21, 'F', 'Business', 'early-bird', 'Huamak', 5000, true, 1),
(2, 22, 'M', 'Engineering', 'night-owl', 'Bangna', 6000, false, 2),
(3, 23, 'F', 'Arts', 'early-bird', 'Huamak', 5500, true, 3);

-- Reports
INSERT INTO reports (id, reported_user_id, reporter_user_id, reason, created_at)
VALUES
(1, 2, 1, 'Too noisy', NOW()),
(2, 3, 2, 'Unpaid bills', NOW());
```

