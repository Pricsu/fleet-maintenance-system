# Fleet Maintenance System

A backend REST API for managing a vehicle fleet: vehicles, technicians, suppliers, parts inventory, and maintenance history. Built as a learning project to actually understand how a real Spring Boot backend gets built, secured, tested, containerized, and deployed, not just how to make CRUD endpoints work.

There is no frontend. This is meant to be tested with curl/Postman or consumed by a client app. See the API reference below.

Live demo: `http://<elastic ip>:8080`

## What it does

* Fleet management: vehicles, technicians, suppliers, and parts inventory, with full CRUD
* Maintenance logging: records what work was done on a vehicle, by whom, and which parts were used. Logging maintenance automatically deducts stock from inventory, and the whole operation is wrapped in a database transaction. If anything fails partway through (like not having enough stock for one of several parts), everything rolls back, not just the failed part.
* Authentication: JWT based login and registration, passwords hashed with BCrypt, never stored in plain text
* Role based access: three roles (Admin, Technician, Manager). Viewing data is open to all three roles; creating, editing, and deleting is mostly Admin only, except logging maintenance, which is a Technician's actual job. Managers get read access to cost and inventory reports.
* Reports: cost per vehicle, low stock parts, vehicles due for service soon

## Tech stack

| | |
|---|---|
| Language / Framework | Java 17, Spring Boot |
| Database | PostgreSQL (RDS in production, Docker locally) |
| Security | Spring Security, JWT (jjwt), BCrypt |
| Testing | JUnit 5, Mockito, MockMvc, H2 (in memory test DB) |
| Containerization | Docker (multi stage build), Docker Compose |
| CI/CD | GitHub Actions |
| Cloud | AWS (EC2 + RDS) |

## Architecture

```
Client (curl / Postman)
        |
        v
Spring Boot REST API  ------>  PostgreSQL
   (Controller -> Service -> Repository)
        |
        v
   JWT Auth Filter + Role based
   Spring Security rules
```

Deployed version:

```
Internet
   |
   v
EC2 (Ubuntu, Docker)
   |  runs the app container
   v
RDS (managed Postgres, not publicly reachable, only the EC2 instance can reach it)
```

I deliberately used RDS instead of running Postgres in a container on EC2, since that's closer to how real production systems are set up: a managed database instead of one you have to patch and back up yourself.

## Domain model

* Vehicle: plate, make, model, year, mileage, next service date
* Technician: name, specialty
* Supplier: name, contact info
* Part: name, part number, stock, reorder threshold, unit cost, linked supplier
* MaintenanceRecord: vehicle, technician, date, description, labor cost, parts used (with quantities)

## Running it locally

You'll need Docker installed.

```bash
git clone https://github.com/Pricsu/fleet-maintenance-system.git
cd fleet-maintenance-system
docker-compose up --build
```

That's it. This starts Postgres and the app together, with the app waiting for the database to actually be ready before it starts (there's a healthcheck for this). The API is available at `http://localhost:8080`.

If you'd rather run it without Docker, you'll need Postgres running separately and matching config in `application.yml`, then:
```bash
mvn spring-boot:run
```

### Running tests
```bash
mvn test
```

## Trying the API

Register a user (first one, make yourself an admin):
```bash
curl -X POST "http://localhost:8080/api/auth/register?role=ADMIN" \
  -H "Content-Type: application/json" \
  -d '{"username":"admin1","password":"yourpassword"}'
```

Log in:
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin1","password":"yourpassword"}'
```

Use the token you get back on everything else:
```bash
curl http://localhost:8080/api/vehicles \
  -H "Authorization: Bearer <token>"
```

### Main endpoints

| Method | Endpoint | Who |
|---|---|---|
| POST | `/api/auth/register?role=X` | public |
| POST | `/api/auth/login` | public |
| GET | `/api/auth/me` | any logged in user |
| GET | `/api/vehicles`, `/api/parts`, `/api/suppliers` | Admin, Manager, Technician |
| POST/PUT/DELETE | `/api/vehicles`, `/api/parts`, `/api/suppliers` | Admin only |
| GET/POST | `/api/maintenances` | Admin, Technician (write) |
| GET | `/api/reports/*` | Admin, Manager |

## What I actually learned building this

This was my first real Spring Boot project, so a lot of this was new:

* Layered architecture (Controller to Service to Repository) and why you don't just return JPA entities directly from a controller. Response DTOs exist to stop internal database details (like Hibernate's lazy loading proxies) from leaking into your API.
* JWT auth from scratch: hashing, token generation and validation, a custom security filter, and getting role based access rules right (I broke this a few times before it worked)
* Database transactions aren't just theory. I actually tested that a multi step operation (deducting stock for several parts) rolls back completely if any single part fails, not just the failed step.
* Docker: multi stage builds, running as a non root user, and the fact that containers don't share `localhost` with each other (I hit this exact bug going from local dev to Docker Compose, and again going from Compose to AWS)
* Testing at three different levels: mocked unit tests, a real database integration test (specifically to prove the transaction rollback), and MockMvc tests for the actual HTTP layer including security and validation
* Deploying to AWS for real: RDS, EC2, security groups, and a handful of very real debugging sessions (Ubuntu being too new for Docker's repo, a Bash `!` character crashing a command, a stale password that took actual log reading to track down)

## Known limitations

* No frontend, API only
* Anyone can register themselves as Admin via the `?role=` param. Fine for a demo, not something I'd ship to real users without locking that down.
* No password reset, logout, or token revocation flow
* MaintenanceRecord has no update or delete. Reversing stock changes on edit felt like a separate feature, not core to what I was trying to demonstrate.
* Test coverage is solid but not exhaustive. Vehicle and Technician have full test suites as a reference pattern; Supplier and Part follow the same shape but weren't duplicated one to one.

---

Built by Alfred. Feel free to poke around, break it, or ask me about any part of it.