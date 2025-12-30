<<<<<<< HEAD
# Personal-Finance-Management-System
A Java-based personal finance management system built with Java Servlets and MySQL, enabling users to manage income and expenses, track spending categories, and view financial summaries securely.

Project Structure

## 📂 Web Pages
- `META-INF / WEB-INF`: Configuration and secure web metadata.
- `index.html`: Application entry point.
- `home.html / dashboard.html`: User interface views.

## 📦 Source Packages (Java)
- **com.pfms.controller**: Contains Servlets that handle HTTP requests.
- **com.pfms.model**: Contains POJO classes (User, Transaction, Category).
- **com.pfms.dao**: Data Access Objects for Database interaction.
- **com.pfms.util**: Utility classes (e.g., Database connection helper).

# Personal Finance Management System

## 🚀 Git Rules
1. **Never push to main.** Always create a branch for your task: `git checkout -b feature-yourname`.
2. **Pull before you push.** Run `git pull origin main` daily to stay updated.
3. **Merge via Pull Request.** Upload your branch to GitHub and ask the lead to merge it.

## 📂 File Classification
- **Controller:** All Servlets go in `com.pfms.controller`.
- **DAO:** Database logic goes in `com.pfms.dao`.
- **Model:** Java Beans (User, Transaction) go in `com.pfms.model`.
- **UI:** All HTML/CSS/JS goes in the `Web Pages` folder.

## 🔑 Database Setup
- Import the `database.sql` file (found in /db folder).
- Update your local credentials in `com.pfms.util.DBConnection`.
=======
# Personal-management-system
A Java-based web application designed to help users manage their personal finances efficiently
>>>>>>> 49e50f98085ad65b008ab0dcafd5ba29e49f115c
