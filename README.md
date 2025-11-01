# 🤖 Robot Navigation – Hexagonal Architecture & DDD

This project implements a robot navigation system using **Domain-Driven Design (DDD)** and **Hexagonal Architecture (Ports & Adapters)**, built with **Java 21** and **Spring Boot 3**.

The goal is to simulate the movement of cleaning robots on a grid, allowing them to turn and advance according to instructions. Each robot operates sequentially within the same map.

This repository is used as a **learning and clean-architecture practice project**, with code structured in layers and developed following professional Git and testing practices.

---

## 🧠 Problem Description

The robot receives commands:

| Command | Meaning |
|--------|---------|
| `L` | Rotate 90° left |
| `R` | Rotate 90° right |
| `M` | Move forward one position |

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


| Layer | Responsibility |
|------|----------------|
Domain | Business rules, aggregates, value objects |
Application | Use cases / orchestration |
Infrastructure | Adapters (web, persistence, input parsing) |

Principles applied:

- Domain-Driven Design
- Immutable Value Objects
- Explicit domain language
- TDD where reasonable
- One feature per branch

---

## 📦 Tech Stack

- Java **21**
- Spring Boot **3**
- Maven
- JUnit 5
- GitHub Flow + feature branches
- Conventional Commits

---

## ✅ Progress

| Feature | Status |
|--------|-------|
Project setup | ✅ Done  
Git branching strategy | ✅ develop + feature branches  
Domain layer started | ✅  Done
Orientation enum | ✅ + tests + JavaDoc  
Position value object | ✅ + tests + JavaDoc  
Grid | ✅  Done
Robot aggregate | ✅  Done
Application services | ✅  Done
REST adapters | ✅   Done
Parser for console input | ✅ Done
End-to-end tests | ✅ Done

---

## 🧩 Domain Model Components (so far)

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

---

## 🧪 Testing Strategy

- JUnit 5
- Unit tests first for domain objects
- Meaningful test naming and assertions
- JavaDoc in tests to clarify intent
- Defensive programming (null checks, value semantics)


## 🧱 Proyect Structure

src/main/java/com/example/robot
├── domain/ # Modelo puro de dominio (sin Spring)
│ ├── Robot.java
│ ├── Position.java
│ ├── Orientation.java
│ ├── Instruction.java
│ ├── InstructionSequence.java
│ ├── Grid.java
│ ├── Navigator.java
│ ├── OutOfBoundsPolicy.java
│ └── exception/DomainException.java
│
├── application/ # Casos de uso (sin framework)
│ ├── port/in/ProcessScenarioUseCase.java
│ ├── port/in/command/.java
│ ├── port/in/result/.java
│ └── service/RobotScenarioService.java
│
└── infrastructure/ # Adaptadores (Spring)
├── controller/RobotController.java
├── controller/ApiExceptionHandler.java # Manejo global de errores
├── parser/RawScenarioParser.java # Formato texto plano
├── dto/*.java
└── config/ApplicationWiring.java # Inyección de dependencias


✅ Dominio sin dependencias  
✅ Aplicación sin Spring  
✅ Infraestructura como capa externa

---

## 🌐 REST API

### 🔷 Ejecutar con JSON
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
### 🔷 Ejecutar con RAW
POST /api/v1/robots/execute-raw
Content-Type: text/plain
#### Body
5 5
1 2 N
LMLMLMLMM
3 3 E
MMRMMRMRRM

### 🧪 Ejecutar Tests
./mvnw test

### ▶️ Ejecutar aplicación
./mvnw spring-boot:run

---
## 🌟 Notes

This project is intentionally designed for:

practicing professional backend patterns

gaining fluency in clean design & testing

understanding DDD without over-engineering

The code evolves iteratively, with a focus on clarity and correctness over speed.

