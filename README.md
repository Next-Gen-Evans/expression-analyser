# 🧮 Expression Analyzer (Java CLI)





A fast and lightweight Java command-line tool that evaluates mathematical expressions like a real calculator.  
Supports brackets, operator precedence, multiplication, division, and nested expressions — all without external libraries.

---

## 🚀 Features

- Evaluate expressions such as:  
  `8*(5+2)-4`, `10/2+6*3`, `(3+5)*(2-1)`
- Supports:
  - `+` addition  
  - `-` subtraction  
  - `*` multiplication  
  - `/` division  
  - `()` parentheses  
  - Unary + and -  
- Pure Java implementation — no ScriptEngine, no external dependencies.
- Instant results with accurate precedence and parsing.

---

## 📂 Project Structure

ExpAnalyzer/
│
├── src/
│   └── App.java          # Main Expression Analyzer logic
│
└── README.md

---

## 🛠️ How to Compile
Run this in the project root:

```bash
javac src/App.java

This will generate:
src/App.class
```

▶️ How to Run

```bash
java -cp src App
```

You will see:
🧮 Expression Analyzer Started!
Enter a math expression (e.g., 8*(5+2)):

📌 Example Usage
Input:
8*(5+2)-4
Output:
Final Result: 52.0

Input:
(3+7)/2 + 6*3
Output:
Final Result: 23.0

🔧 Future Enhancements
Step-by-step evaluation breakdown
Error highlighting for invalid expressions
Support for exponentiation (^)
GUI version using Java Swing
History of past calculations

📘 Built With
Java (JDK 8+)
No external libraries

# 👨‍💻 Author
- **Evans Mutharimi Buongo**

If you found this helpful, give it a ⭐!

<div align="center">

### 🌟 **Thanks for Visiting!** 🌟
