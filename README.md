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
