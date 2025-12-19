# 🏡 Village Portal Backend

Village Portal Backend is a secure, role-based REST API built using **Spring Boot**.  
It provides **user authentication**, **JWT-based security**, **admin & user role management**, and **village information management**.

This project follows **industry-standard backend architecture** and security practices.

---

## 🚀 Features

- User Signup & Login
- Password Encryption using BCrypt
- JWT Authentication (Stateless)
- Role-Based Authorization (ADMIN / USER)
- Admin-only protected APIs
- Village Information Management
- Token Expiry Handling
- Clean Controller → Service → Repository architecture

---

## 🛠️ Tech Stack

- **Java**: 17
- **Spring Boot**: 3.x
- **Spring Security**
- **JWT (Auth0 Library)**
- **Hibernate / JPA**
- **MySQL**
- **Maven**

---

## 📂 Project Structure

src/main/java  
└── com.example.main  
&nbsp;&nbsp;&nbsp;&nbsp;├── configuration  
&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;├── SecurityConfig.java  
&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;└── JwtFilter.java  
&nbsp;&nbsp;&nbsp;&nbsp;├── controller  
&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;└── VillageUserSignupController.java  
&nbsp;&nbsp;&nbsp;&nbsp;├── entity  
&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;└── VillageUserSignup.java  
&nbsp;&nbsp;&nbsp;&nbsp;├── payload  
&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;├── VillageUserSignupDto.java  
&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;├── VillageUserLoginDto.java  
&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;└── TokenDto.java  
&nbsp;&nbsp;&nbsp;&nbsp;├── reposetry  
&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;└── VillageUserSignupRepository.java  
&nbsp;&nbsp;&nbsp;&nbsp;└── service  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├── JwtService.java  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└── VillageUserSignupImpl.java

---

## 🔐 Authentication & Authorization Flow (JWT)

### 1️⃣ User Signup
User registers with required details.  
Password is encrypted using **BCrypt** before saving to database.

POST `/api/v1/admin/create`

---

### 2️⃣ Login & JWT Token Generation
User logs in using **username & password**.  
On successful authentication, backend generates a **JWT token**.

POST `/api/v1/admin/login`

Response Example:
```json
{
  "jwt": "JWT",
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

### 3️⃣ Access Protected APIs
For secured APIs, client must send the JWT token in request header:

Authorization:  
`Bearer <JWT_TOKEN>`

The token is validated by a custom `JwtFilter` on every request.

---

### 4️⃣ Role-Based Authorization

| Role  | Permissions |
|------|-------------|
| ADMIN | All admin APIs and village management |
| USER  | Limited access |

---

## 🔓 Logout & Token Expiry

JWT authentication is **stateless**.

- Backend does **not store tokens**
- Logout is handled by frontend by removing the token
- Token automatically expires after configured duration

```properties
jwt.expiry.duration=30000
```

After token expiry, user must login again.

---

## ⚙️ Security Configuration Highlights

- CSRF disabled
- CORS disabled
- Stateless session management
- Custom JWT filter applied

---

## 🗃️ Database Configuration

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/villageportal
spring.datasource.username=root
spring.datasource.password=******
spring.jpa.hibernate.ddl-auto=update
```

---

## ▶️ How to Run the Project

1️⃣ Clone Repository
```bash
git clone https://github.com/sangam-maurya/village-portal.git
```

2️⃣ Create Database
```sql
CREATE DATABASE villageportal;
```

3️⃣ Run Application
```bash
mvn spring-boot:run
```

---

## 🧪 API Testing

- Tool: **Postman**
- Header for secured APIs:

Authorization:  
`Bearer <JWT_TOKEN>`

---

## 📌 Current Project Status

- Authentication completed
- JWT security implemented
- Role-based authorization working
- Admin APIs protected
- Backend is production-ready (basic level)

---

## 🔮 Future Enhancements

- Refresh Token
- Swagger / OpenAPI
- Global Exception Handling
- Pagination & Search
- Unit & Integration Tests
- Docker Support

---

## 👤 Author

**Sangam Maurya**  
GitHub: https://github.com/sangam-maurya

---

⭐ If you like this project, please give it a star!
