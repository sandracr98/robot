# 🤖 Robot Navigation – Hexagonal Architecture & DDD

---

## 👩‍💻 Author

Sandra Checa Ruiz  
Software Developer


This project implements a robot navigation system using **Domain-Driven Design (DDD)** and **Hexagonal Architecture (Ports & Adapters)**, built with **Java 21** and **Spring Boot 3**.

The goal is to simulate the movement of cleaning robots on a grid, allowing them to turn and advance according to instructions. Each robot operates sequentially within the same map.

This repository is used as a **learning and clean-architecture practice project**, with code structured in layers and developed following professional Git and testing practices.



### Assumptions

- Robots execute sequentially, not simultaneously.
- If a robot attempts to move out of bounds, the movement is ignored.
- Collision policy (extra funcionality): robots cannot occupy the same position. Attempts to move into an occupied cell are detects this potential collision and treats the cell as an obstacle.
  In such cases, the movement for that step is ignored. This prevents overlapping positions and ensures consistent simulation behavior.

---


### 🔄 Git History Notes

During development, the project followed a GitFlow-style branching model.
All features were developed in dedicated feature/* branches and merged into develop before being promoted to main.

In one of the early iterations, a feature branch was accidentally merged directly into main instead of develop.
Although this was quickly detected and corrected, the commit remains visible in the graph.

Instead of rewriting Git history (which is discouraged in collaborative environments), the fix was handled safely:

The feature was also merged into develop to keep branches consistent.

Development continued normally from develop.

The workflow was reinforced to avoid direct merges into main.

| Branch      | Purpose                       |
| ----------- | ----------------------------- |
| `main`      | stable release branch         |
| `develop`   | integration branch            |
| `feature/*` | isolated development branches |


---

## 🧠 Problem Description

The robot receives commands:

| Command | Meaning                   |
|---------|---------------------------|
| `L`     | Rotate 90° left           |
| `R`     | Rotate 90° right          |
| `M`     | Move forward one position |

Grid example:

- Bottom-left: `0 0`
- Orientation: `N, E, S, W`

Sample input:

5 5

1 2 N

LMLMLMLMM

3 3 E

MMRMMRMRRM


Expected output:

1 3 N

5 1 E


---

## 🧱 Architecture

This project follows **Hexagonal Architecture**:

domain/
application/
infrastructure/


| Layer          | Responsibility                             |
|----------------|--------------------------------------------|
| Domain         | Business rules, aggregates, value objects  |
| Application    | Use cases / orchestration                  |
| Infrastructure | Adapters (web, persistence, input parsing) |

Principles applied:

- Domain-Driven Design
- Immutable Value Objects
- Explicit domain language
- TDD where reasonable
- One feature per branch

---

## 🧠 Why a Rich Domain Model?

This project intentionally uses a **rich domain model** instead of an anemic one.

### ✅ What does that mean?

- The **business rules live inside the domain objects**
- Entities and Value Objects have **behavior**, not just data
- Domain logic is **not** pushed to controllers or services

### 🧩 Benefits

| Benefit                     | Explanation                                                                             |
|-----------------------------|-----------------------------------------------------------------------------------------|
| Strong business consistency | Rules and constraints always apply because the domain enforces them                     |
| High cohesion               | Each domain object knows how to manage its own logic                                    |
| Low coupling                | The domain does not depend on Spring or infrastructure                                  |
| Better testability          | Pure domain tests run fast and independently                                            |
| Expressive code             | Concepts like `Robot`, `Position`, `Grid`, `InstructionSequence` match the real problem |
| Easier to evolve            | Adding new rules (e.g. collision policies) doesn't break controllers or services        |
| Avoids "God services"       | Logic is distributed in the right domain objects, not in giant service classes          |

### 🎯 Result

The code is easier to understand, modify, and extend.  
Business rules are clear and centralized in the domain layer, making the system:

- more maintainable
- more robust
- more aligned with real-world behavior

In short: **the domain drives the system — not the framework**.
---

## 📦 Tech Stack

- Java **21**
- Spring Boot **3**
- Maven
- JUnit 5
- GitHub Flow + feature branches
- Conventional Commits

---

## 🧩 Domain Model Components

### ✔️ `Orientation`
- Represents robot facing direction
- Supports left/right turn
- Maps to character input/output
- Provides movement vector `(dx, dy)`
- Fully unit tested

### ✔️ `Position`
- Immutable coordinate `(x, y)`
- Moves according to orientation
- No boundary logic (handled by `Grid`)
- Fully unit tested
### ✔️ `Grid`
- Defines grid size and boundaries
- Checks if positions are within bounds
- Tracks occupied positions
- Fully unit tested
### ✔️ `Robot`
- Has `Position` and `Orientation`
- Executes instruction sequences
- Validates moves against `Grid`
- Fully unit tested
### ✔️ `Instruction`
- Enum for `L`, `R`, `M`
- Parses from characters
- Fully unit tested
### ✔️ `InstructionSequence`
- Parses and validates instruction strings
- Provides iterable instructions
- Fully unit tested
### ✔️ `Navigator`
- Orchestrates robot movement on grid
- Handles out-of-bounds and collisions
- Fully unit tested
### ✔️ `OutOfBoundsPolicy`
- Strategy for handling out-of-bounds moves
- Currently ignores invalid moves
- Easily extensible for other policies
- Fully unit tested
### ✔️ `DomainException`
- Domain violation error type
- Thrown on invalid state or business rules
- Mapped to HTTP 422 in API layer
- Verified via behavioral tests
### ✔️ `Occupancy`
- Tracks occupied positions during multi-robot execution
- Implementation: SetOccupancy
- Prevents two robots sharing a tile
- Fully unit tested
---

## 🧪 Testing Strategy

- JUnit 5
- Unit tests first for domain objects
- Meaningful test naming and assertions
- JavaDoc in tests to clarify intent

---

## 🚀 Testing the Application with Postman

This project exposes REST endpoints to simulate robot navigation.
Below are instructions to test the application using Postman.

✅ Supports JSON requests

✅ Supports RAW text input (as in the original challenge)

### 🛠️ Setup

1️⃣ Start the Application

    ./mvnw spring-boot:run


By default the server runs at:

    http://localhost:8080

2️⃣ Open Postman

3️⃣ Create a new POST request

#### 🔷 Ejecutar con RAW
POST /api/v1/robots/execute-raw-plain

Content-Type: text/plain
#### Body
```json
  5 5
  1 2 N
  LMLMLMLMM
  3 3 E
  MMRMMRMRRM
```


#### 🔷 Ejecutar con JSON
POST /api/v1/robots/execute
Content-Type: application/json

#### Body
```json
{
  "maxX": 5,
  "maxY": 5,
  "programs": [
    { "startX": 1, "startY": 2, "orientation": "N", "instructions": "LMLMLMLMM" },
    { "startX": 3, "startY": 3, "orientation": "E", "instructions": "MMRMMRMRRM" }
  ]
}
```


### 🧪 Ejecutar Tests
    ./mvnw test


---
## 🌟 Notes

This project is intentionally designed for:

- Passing the Volkswagen technical interview.

- Practicing professional backend patterns

- Gaining fluency in clean design & testing

- Understanding DDD without over-engineering

- The code evolves iteratively, with a focus on clarity and correctness over speed.

