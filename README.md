# COE891 Project – Apache Commons Compress Testing

## Overview

This project focuses on testing selected classes from Apache Commons Compress using:

* Input Space Partitioning (ISP)
* Graph-Based Testing (CFG/DFG)
* Logic-Based Testing
* Mutation Testing
* LLM-based test generation

---

## Requirements

* Java JDK (11 or higher)
* Maven (3.9+ recommended)

---

## Setup

Clone the repository:

```
git clone <repo-link>
cd commons-compress
```

---

## Build Project

```
mvn clean install
```

---

## Run Tests

Run all tests:

```
mvn test
```

Run specific test class:

```
mvn -Dtest=ZipArchiveEntryTest test
```

---

## Project Structure

```
src/main/java      → Source code  
src/test/java      → Test cases (our work)
```

---

## Notes

* Only custom test cases (written by the team) are used for analysis
* Existing project tests are not modified
* Focus is on coverage, mutation score, and test quality

---
