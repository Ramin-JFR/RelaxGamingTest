# Relax Gaming Test

This project is a Java-based game server that simulates a grid-based cascading slot machine, complete with cluster detection, avalanche mechanics, and payout calculation.

---

## ✅ Requirements

- **Java 17+** (tested with JDK 22)
- **Docker** (for MySQL container)
- **MySQL** (can be run locally or via Docker)

---

## 🧰 Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/RelaxGamingTest.git
cd RelaxGamingTest
```

### 2. Ensure Docker is Running
Make sure Docker is installed and running.

### 3. Start the MySQL Database (Optional Docker Setup)

```bash
docker-compose up -d
```

### 4. Configure Database Connection
Database connection details are configured in:

```
src/Model/db/Db.java
```

## 🚀 Running the Server

You can start the server by running:

```
GameServer.java
```
