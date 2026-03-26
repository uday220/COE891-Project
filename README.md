<!---
 Licensed to the Apache Software Foundation (ASF) under one or more
 contributor license agreements.  See the NOTICE file distributed with
 this work for additional information regarding copyright ownership.
 The ASF licenses this file to You under the Apache License, Version 2.0
 (the "License"); you may not use this file except in compliance with
 the License.  You may obtain a copy of the License at

      https://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
-->

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
