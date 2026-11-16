# Library Management System (LMS) – Incremental Java Labs 1 to 4

**Course:** SENG 12233 – University of Kelaniya  
**Architecture:** Object-Oriented Design in Java, evolving from basic encapsulation to polymorphism, abstraction, and custom exception handling.

---

## 1. Executive Summary
This project implements the Library Management System (LMS) developed across four incremental laboratory assignments (Weeks 2 through 5). The system transitions from a flat, tightly-coupled object model to a robust, polymorphic architecture featuring inheritance, abstract classes, interfaces, generic collections, comparable sorting, and a comprehensive custom exception handling subsystem.

---

## 2. Git Branching Strategy
The project follows a disciplined feature-branch workflow on Git:

```text
main (Initial setup with .gitignore, IntelliJ project configuration, and README.md)
 ├── week2-encapsulation (Lab 1: Classes, Objects & Encapsulation)
 └── week3-inheritance-polymorphism (Lab 2: Inheritance & Polymorphism)
      └── week4-abstraction-interfaces (Lab 3: Abstract Classes & Interfaces)
           └── week5-exceptions (Lab 4: Exception Handling)
```

### Branch Summary Table
| Phase / Week | Git Branch Name | Core Architectural Milestone | Deliverables / UML Artifacts |
|---|---|---|---|
| **Week 2** | `week2-encapsulation` | Encapsulation, private fields, validated setters, composition. | `docs/uml-week2.png`, `Book.java`, `Member.java`, `Library.java`, `Main.java` |
| **Week 3** | `week3-inheritance-polymorphism` | Class hierarchies, method overriding, dynamic/static binding, `instanceof`. | `docs/uml-week3.png`, `LibraryItem` superclass, `Book`, `DVD`, `Student`, `Staff` |
| **Week 4** | `week4-abstraction-interfaces` | Abstract classes, interfaces (`Loanable`, `Reservable`), `Comparable<Member>`, `Magazine`. | `docs/uml-week4.png`, `lms.contract.*`, `Magazine.java` |
| **Week 5** | `week5-exceptions` | Checked & unchecked custom exceptions, `try/catch/finally` control flow. | `docs/uml-week5.png`, `lms.exception.*`, refactored workflows |

---

## 3. IntelliJ IDEA Setup & Running
This repository is pre-configured as an IntelliJ IDEA project.

### Opening in IntelliJ IDEA:
1. Open IntelliJ IDEA.
2. Select **File -> Open...** and browse to this directory (`LibraryManagementSystem`).
3. IntelliJ IDEA will automatically recognize the module, `src` source root, and SDK configuration.
4. Run `lms.Main` by clicking the green Run icon or pressing `Shift+F10`.

### Command-Line Compilation & Execution (Windows PowerShell / Bash):
```powershell
# Create output directory
New-Item -ItemType Directory -Force -Path out

# Compile all source files
javac -d out (Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName })

# Run Main
java -cp out lms.Main
```
