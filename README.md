# Data Structures & Algorithms in Java

A structured collection of Data Structures and Algorithms problems solved in **Java 22**, organized by topic and difficulty.

The objective is to develop strong problem-solving skills by understanding how algorithms are derived, optimized, implemented, and tested—not simply memorizing solutions.

## Learning Methodology

Every problem follows six stages:

1. **Understand** — Analyze the problem statement, constraints, inputs, and outputs.
2. **Derive** — Develop an initial brute-force approach.
3. **Optimize** — Identify the appropriate algorithmic pattern and improve efficiency.
4. **Implement** — Write clean, readable Java code.
5. **Test** — Validate correctness using JUnit, including edge cases.
6. **Document** — Explain the reasoning, dry runs, complexity, and interview insights.

## DSA Roadmap

| # | Topic | Difficulty | Status |
|---|---|---|---|
| 01 | [Arrays](arrays/) | Easy → Hard | In Progress |
| 02 | Strings | Easy → Hard | Planned |
| 03 | Hashing | Easy → Hard | Planned |
| 04 | Linked Lists | Easy → Hard | Planned |
| 05 | Stacks & Queues | Easy → Hard | Planned |
| 06 | Recursion & Backtracking | Easy → Hard | Planned |
| 07 | Sorting & Searching | Easy → Hard | Planned |
| 08 | Trees & Binary Search Trees | Easy → Hard | Planned |
| 09 | Heaps & Priority Queues | Easy → Hard | Planned |
| 10 | Graphs | Easy → Hard | Planned |
| 11 | Greedy Algorithms | Easy → Hard | Planned |
| 12 | Dynamic Programming | Easy → Hard | Planned |

## Solved Problems

| # | Problem | Topic | Difficulty | Solution | Explanation |
|---|---|---|---|---|---|
| 1 | Two Sum | Arrays | Easy | [Java](arrays/src/main/java/com/avishek/dsa/arrays/easy/TwoSum.java) | [README](arrays/problems/easy/two-sum/README.md) |

## Project Structure

```text
data-structures-algorithms/
├── README.md
├── pom.xml
├── .gitignore
├── arrays/
│   ├── pom.xml
│   ├── problems/
│   │   └── easy/
│   │       └── two-sum/
│   │           └── README.md
│   └── src/
│       ├── main/java/
│       └── test/java/
└── [additional Maven modules]
```

## Technology Stack

- Java 22
- Apache Maven 3.9.16
- JUnit 5
- Eclipse IDE
- Git and GitHub

## Build and Test

Run all modules:

```powershell
mvn clean test
```

Run only the Arrays module and required reactor projects:

```powershell
mvn -pl arrays -am test
```

## Progress Tracker

| Metric | Count |
|---|---:|
| Problems implemented | 1 |
| Easy | 1 |
| Medium | 0 |
| Hard | 0 |
| Topic modules created | 1 |

Counts are updated as problems are completed and verified.

## Repository Standards

Each problem should include:

- A Java implementation using meaningful names and clean code.
- A dedicated README explaining the problem-solving journey.
- A dry run with representative inputs.
- Time and space complexity analysis.
- JUnit tests covering normal and edge cases.
- Links between the explanation, implementation, and tests.

**Principle: Understand the algorithm before memorizing the code.**