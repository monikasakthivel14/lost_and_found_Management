# 🔎 Smart Lost & Found Management System

A simple **Smart Lost & Found Management System** developed using **JavaFX, JDBC, and MySQL**.
The system helps users manage lost and found items through a simple graphical interface.

## 📌 Features

* 🏠 Dashboard for easy navigation
* ➕ Add lost or found items
* 📋 View stored items
* 🗄️ MySQL database integration
* 🔗 JDBC connectivity
* 🖥️ JavaFX-based graphical user interface

## 🛠️ Technologies Used

* **Java**
* **JavaFX**
* **JDBC**
* **MySQL**
* **MySQL Connector/J**
* **VS Code**

## 📂 Project Structure

```text
LostAndFound/
│
├── src/
│   └── *.java
│
├── lib/
│   └── mysql-connector-j.jar
│
└── README.md
```

## ⚙️ Database Configuration

Create a MySQL database named:

```sql
CREATE DATABASE lost_found;
```

Update the database connection details in the Java code if required:

```text
Database: lost_found
Host: localhost
Port: 3306
```

## ▶️ How to Run

1. Install **JDK 21**.
2. Install **JavaFX SDK**.
3. Install **MySQL Server**.
4. Create the `lost_found` database.
5. Add the **MySQL Connector/J** `.jar` file to the project.
6. Configure the JavaFX library path.
7. Compile and run the main Java class.

## 🖥️ Main Modules

### Dashboard

Provides navigation to the main sections of the application.

### Add Item

Allows users to enter details about a lost or found item.

### View Items

Displays the items stored in the database.

## 🎯 Project Objective

The main objective of this project is to develop a simple desktop application for managing lost and found items while demonstrating the use of **Object-Oriented Programming, JavaFX, JDBC, and MySQL**.

## 👩‍💻 Developed By

**Monika S**

B.Tech Artificial Intelligence and Data Science
National Engineering College

---

⭐ This project was developed as an academic mini-project to understand JavaFX GUI development and database connectivity using JDBC.
