# Smart Console-Based Expense Tracker (Core Java Only)

🧩 Objective
This project is a console-based Expense Tracker built entirely in Core Java. It does not use Java Collections or external libraries and involves implementing custom data structures, file-based persistence, and real-world expense tracking features.

🚀 Features Implemented
User Registration & Login with simple password hashing.

Add Expenses with amount, category, description, date, recurring flag.

Generate Monthly Reports (category totals, total expense, highest expense).

Category Insights (most used category, average per category).

Savings Goal Feature with goal comparison.

Recurring Expense Tracking.

Sorting of Expenses by Amount and Date (custom sorting logic).

Data Persistence in .txt files.

🏗️ Folder Structure
php-template
Copy
Edit
ExpenseTracker/
│
├── main/
│   └── MainApp.java
│
├── models/
│   ├── User.java
│   ├── Expense.java
│
├── datastructures/
│   ├── MyLinkedList.java
│   ├── MyHashMap.java
│
├── utils/
│   ├── FileUtil.java
│   ├── InputValidator.java
│   ├── Hasher.java
│
├── service/
│   ├── UserService.java
│   ├── ExpenseService.java
│   ├── ReportService.java
│   ├── GoalService.java
│
├── reports/  (Generated Reports)
├── expenses_<username>.txt
├── users.txt
├── goal_<username>.txt
🔧 Custom Data Structures
1. MyLinkedList<T>
A custom implementation of a singly linked list.

Supports methods like add(), get(), set(), remove(), and custom forEach().

Used for managing expense lists, user lists, and reading file lines.

2. MyHashMap<K, V>
A basic Hash Map with separate chaining.

Custom put(), get(), getOrDefault(), and keySet() methods.

Used to track category totals, counts for insights, and more.

🧠 Approach
All expense and user data are persisted in .txt files.

Passwords are hashed using a simple multiplication-based hashing algorithm.

No Java Collections are used. All lists and maps are custom-built.

Recurring expenses are marked with a flag and shown during monthly reports.

Savings goals are stored per user and checked during report generation.

Sorting is implemented using a custom bubble sort on MyLinkedList.

📝 Sample Input/Output
User Flow Example:
vbnet
Copy
Edit
1. Register
Username: john
Password: john123

2. Login
Username: john
Password: john123

3. Add Expense
Amount: 1500
Category: Food
Description: Lunch at Café
Date: 15-07-2025
Recurring: No

4. Generate Monthly Report
> Report saved as reports/monthly_john_07-2025.txt

5. View Category Insights
> Most Used Category: Food
> Average Spending: ₹1500.00

6. Set Savings Goal
> Goal set to ₹10000
Report File Example:
yaml
Copy
Edit
==== Monthly Expense Summary ====
User: john
Month: 07-2025

Category-wise Breakdown:
- Food: ₹1500.0

Total Monthly Expense: ₹1500.0
Highest Expense: ₹1500.0 (Food)

Expense Details:
- ₹1500.0 | Food | Lunch at Café | 15-07-2025
