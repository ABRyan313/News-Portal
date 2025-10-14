# 📰 Online News Portal

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![MySQL](https://img.shields.io/badge/Database-MySQL-blue)
![Status](https://img.shields.io/badge/Sprint-In_Progress-yellow)

---

## 📋 Overview

**Online News Portal** is a full-featured web application built using **Java**, **Spring Boot**, **Hibernate**, and **MySQL**. It allows users to read, publish, and manage news articles with role-based access. The platform supports image uploads, advertising management, analytics, and a responsive frontend.

The project follows the **Scrum** methodology and is managed using **GitHub Projects** for backlog, sprint tracking, and issue management.

---

## 🧭 Table of Contents

- [Features & Roadmap](#features)
- [Functional Requirements](#functional-requirements)
- [Non-Functional Requirements](#non-functional-requirements)
- [Advertising Feature](#advertising-feature)
- [Agile / Scrum Workflow](#agile--scrum-workflow)
- [Tech Stack](#tech-stack)
- [Project Setup](#project-setup)
- [Usage](#usage)
- [License](#license)

## ⚙️ Tech Stack

* **Backend:** Spring Boot, Hibernate, Spring Security
* **Frontend:** React (planned for later version)
* **Database:** MySQL
* **Build Tool:** Maven
* **Version Control:** Git & GitHub
* **Project Management:** GitHub Projects (Scrum workflow)

---

## 🚀 Features & Version Roadmap

### 🟡 **V.0.1 – Content Management (In Progress)**

* Article management: create, edit, delete, draft/publish, schedule publishing.
* Categorization and tagging of articles.
* Article listing with pagination, search, and filtering.
* Image upload and embedding within articles.

---

### 🟢 **V.0.2 – Advertising Management**

* Advertising management for banners and promotions.

---

### 🔵 **V.0.3 – Report Generation**

* Logging and analytics for article views and actions.
* Secure authentication and input validation.

---

### 🟣 **V.0.4 – Frontend Development**

* Responsive UI for both desktop and mobile.

---

### 🔴 **V.0.5 – Security (Authentication & Authorization)**

* User registration, login/logout, and password reset.
* Role-based access control: **Reader**, **Author/Editor**, **Admin**.

---

## 🧩 Scrum Workflow

### Sprint Planning

* Each version (`V.0.x`) represents a **sprint**.
* Tasks are managed via **GitHub Projects** under columns:

  * **To Do** → **In Progress** → **In Review** → **Done**

### Daily Work

* Use **Issues** for task tracking.
* Link commits and pull requests to issues using:

  ```bash
  git commit -m "Implement article scheduling #12"
  ```

  (where `#12` is the issue number)

### Sprint Review & Retrospective

* Conduct reviews at the end of each version (sprint).
* Update README and backlog for the next version.

---

## ⚙️ Functional Requirements

### 1. User Management
- Register, login/logout, reset password.
- Role-based permissions: Reader, Author/Editor, Admin.

### 2. Article / Content Management
- Create, edit, delete, and publish/draft articles.
- Schedule publishing for future dates.
- Embed images and multimedia in content.

### 3. Categories / Tags / Topics
- Assign articles to categories and tags.
- Filter or browse by category or tag.

### 4. Article Listing / Browsing
- Homepage with latest news.
- Pagination for large lists.
- Detailed article pages (title, author, date, content, media).

### 5. Search
- Search articles by title and content.
- Filter results by category and date.

### 6. Media Handling
- Upload and serve images.
- Validate image format and size.

### 7. Advertising Feature
- Admins can upload and schedule **advertisements** (banners, popups, sidebar ads).
- Track ad views and clicks.
- Target ads by category or article tags.
- Display ads dynamically in homepage and article pages.

### 8. Security / Authorization
- Only editor/admin can create/edit/delete content.
- Readers access public news freely.
- Protection against XSS, SQL injection, and CSRF.

### 9. UI / Frontend
- Reader view: browse, read, and search articles.
- Editor view: create and manage drafts.
- Admin dashboard: manage users, ads, and site activity.
- Fully responsive and mobile-friendly.

### 10. Logging & Analytics
- Track article and advertisement view counts.
- Log critical actions (publish, edit, delete).

---

## 🧩 Non-Functional Requirements

- **Performance:** Page load <2–3s, search <1s.  
- **Reliability:** 99%+ uptime, graceful error handling.  
- **Security:** HTTPS, hashed passwords, safe input handling.  
- **Usability:** Simple, clean, and intuitive design.  
- **Maintainability:** Modular code with documentation.  
- **Data Integrity:** Transaction-safe publishing and saving.  
- **Scalability:** Handle thousands of articles and users.  
- **Compatibility:** Works on major browsers (Chrome, Edge, Firefox, Safari).  
- **Monitoring:** Track errors and performance metrics.

---

## 💰 Advertising Feature

| Feature | Description |
|----------|-------------|
| **Ad Management** | Admin can upload, edit, or remove ad banners. |
| **Scheduling** | Ads can be set to run between specific dates. |
| **Targeting** | Ads can be linked to categories or tags. |
| **Display Areas** | Homepage banners, sidebars, article sections. |
| **Analytics** | Track impressions, clicks, and performance. |

---

## 🧑‍💻 Agile / Scrum Workflow

This project follows the **Scrum framework** for iterative and incremental development.

- **Sprints:** Each sprint lasts **2 weeks**.
- **Board:** Managed via **GitHub Projects (Kanban board)**.
- **Issues:** Each task or feature is tracked as a GitHub Issue.
- **Branches:**
  - `main` – production-ready code.
  - `dev` – ongoing development.
  - `feature/*` – individual features (e.g., `feature/advertising`).
- **Pull Requests (PR):** Every feature branch must be merged via PR after review.
- **Sprint Ceremonies:**
  - **Planning:** Define sprint goals.
  - **Daily Stand-ups:** Quick updates (optional for solo devs).
  - **Review & Retrospective:** End-of-sprint evaluation and improvements.

📊 **GitHub Project Example:**  
> [Projects → News Portal Scrum Board](https://github.com/<your-username>/news-portal/projects)

---

## 🧠 Tech Stack

| Layer | Technology |
|-------|-------------|
| **Backend** | Java 17, Spring Boot 3.x, Hibernate/JPA |
| **Frontend** | Thymeleaf, HTML, CSS, JavaScript |
| **Database** | MySQL 8.x |
| **Security** | Spring Security, JWT, HTTPS |
| **Build Tool** | Maven |
| **Version Control** | Git + GitHub |
| **Deployment (optional)** | Docker / AWS / Render |

---

## ⚙️ Project Setup

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/<your-username>/news-portal.git
cd news-portal
````

### 2️⃣ Configure the Database

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/news_portal
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3️⃣ Build and Run

```bash
mvn clean install
mvn spring-boot:run
```

### 4️⃣ Access the Application

Go to:
👉 [http://localhost:8080](http://localhost:8080)

---

## 🧭 Usage

### 👤 Reader

* Browse, search, and read articles.
* See relevant ads based on article topics.

### ✍️ Author/Editor

* Log in to write, edit, or schedule articles.

### 🛠️ Admin

* Manage users, review content, and control advertisements.

---

## 📜 License

This project is licensed under the **MIT License** – see the [LICENSE](LICENSE) file for details.

---

* Being Developed with ❤️ using Java, Spring Boot, and Agile principles.*

```


