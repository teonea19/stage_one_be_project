# stage_one_be_project
# Profile Intelligence Service (Backend)

A RESTful backend service that accepts a name, enriches it using multiple external APIs, processes the data, stores it in a MySQL database, and exposes endpoints for retrieval and management.

---

## 🚀 Live API

Base URL:

```
https://yourapp.up.railway.app
```

---

## 📌 Features

* Integrates with external APIs:

  * Gender prediction
  * Age estimation
  * Nationality prediction
* Processes and normalizes data
* Stores enriched profiles in MySQL
* Idempotent profile creation (no duplicates)
* Filtering support
* Clean and consistent JSON responses
* Proper error handling
* UUID-based identification
* CORS enabled for public access

---

## 🛠️ Tech Stack

* Java (Spring Boot)
* MySQL
* Spring Data JPA (Hibernate)
* Maven
* Railway (Deployment)

---

## 🌐 External APIs Used

* Genderize → `https://api.genderize.io?name={name}`
* Agify → `https://api.agify.io?name={name}`
* Nationalize → `https://api.nationalize.io?name={name}`

---

## 📦 API Endpoints

### 1. Create Profile

**POST** `/api/profiles`

Request:

```json
{
  "name": "ella"
}
```

Success (201):

```json
{
  "status": "success",
  "data": {
    "id": "uuid",
    "name": "ella",
    "gender": "female",
    "gender_probability": 0.99,
    "sample_size": 1234,
    "age": 46,
    "age_group": "adult",
    "country_id": "US",
    "country_probability": 0.85,
    "created_at": "2026-04-01T12:00:00Z"
  }
}
```

Idempotent Response:

```json
{
  "status": "success",
  "message": "Profile already exists",
  "data": { ... }
}
```

---

### 2. Get Profile by ID

**GET** `/api/profiles/{id}`

Response (200):

```json
{
  "status": "success",
  "data": { ... }
}
```

---

### 3. Get All Profiles (with Filters)

**GET** `/api/profiles`

Optional query parameters:

```
?gender=male&country_id=NG&age_group=adult
```

Response:

```json
{
  "status": "success",
  "count": 2,
  "data": [
    {
      "id": "id-1",
      "name": "emmanuel",
      "gender": "male",
      "age": 25,
      "age_group": "adult",
      "country_id": "NG"
    }
  ]
}
```

---

### 4. Delete Profile

**DELETE** `/api/profiles/{id}`

Response:

```
204 No Content
```

---

## ⚠️ Error Handling

All errors follow this structure:

```json
{
  "status": "error",
  "message": "Error description"
}
```

### Status Codes

* 400 → Bad Request (missing/empty name)
* 422 → Invalid input type
* 404 → Profile not found
* 502 → External API failure
* 500 → Server error

---

## 🧠 Processing Logic

* Gender:

  * Extracted from Genderize API
  * `count` → renamed to `sample_size`

* Age Group Classification:

  * 0–12 → child
  * 13–19 → teenager
  * 20–59 → adult
  * 60+ → senior

* Country:

  * Selected based on highest probability from Nationalize API

---

## 🗄️ Database Schema

Table: `profiles`

| Field               | Type             |
| ------------------- | ---------------- |
| id                  | VARCHAR          |
| name                | VARCHAR (unique) |
| gender              | VARCHAR          |
| gender_probability  | DOUBLE           |
| sample_size         | INT              |
| age                 | INT              |
| age_group           | VARCHAR          |
| country_id          | VARCHAR          |
| country_probability | DOUBLE           |
| created_at          | TIMESTAMP        |

---

## 🔁 Idempotency

* If the same name is submitted multiple times:

  * No new record is created
  * Existing profile is returned

---

## 🌍 CORS

CORS is enabled globally:

```
Access-Control-Allow-Origin: *
```

---

## ⚙️ Running Locally

### 1. Clone Repository

```
git clone https://github.com/yourusername/profile-service.git
cd profile-service
```

### 2. Configure Database

Update `application.properties`:

```
spring.datasource.url=jdbc:mysql://localhost:3306/profiledb
spring.datasource.username=root
spring.datasource.password=yourpassword
```

### 3. Run Application

```
mvn spring-boot:run
```

---

## ☁️ Deployment

Deployed using Railway with MySQL service.

Environment variables:

```
MYSQL_URL
MYSQLUSER
MYSQLPASSWORD
PORT
```

---

## ✅ Submission Checklist

* ✔ Public GitHub repository
* ✔ Live API URL
* ✔ All endpoints functional
* ✔ Proper error handling
* ✔ Idempotency implemented
* ✔ Filtering supported
* ✔ Response format consistent

---

## 📌 Author

Developed as part of Backend Assessment (Stage 1)

---
