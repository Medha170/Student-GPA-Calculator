# Zero-Cost On-Premise DevOps Pipeline: Student GPA Calculator

![CI/CD Status](https://github.com/Medha170/Student-GPA-Calculator/actions/workflows/ci.yml/badge.svg)
![Security Scan](https://img.shields.io/badge/Security-OWASP%20%26%20Trivy-green)
![Deployment](https://img.shields.io/badge/Deployment-Kubernetes%20(K3s)-blue)

## 📖 Project Overview
This project demonstrates a complete **DevOps Automation Pipeline** for a Java Spring Boot application. It simulates a real-world "On-Premise" deployment using **Vagrant** and **Kubernetes (K3s)**, orchestrated entirely by **GitHub Actions**.

The application itself is a RESTful service that calculates Student GPA and Grades, but the primary focus is on the **Secure Software Supply Chain**.

### 🚀 Key Features
* **Automated CI/CD:** Full automation from code commit to production deployment.
* **Strict Security Gates:**
    * **SCA:** OWASP Dependency Check (integrated with NVD API).
    * **SAST:** GitHub CodeQL Analysis.
    * **Container Security:** Trivy Image Scanning (OS & Application layers).
    * **Hardened Runtime:** Replaced Tomcat with **Undertow** to mitigate high-severity CVEs.
* **Infrastructure as Code:** Local infrastructure defined via `Vagrantfile` (Ubuntu 22.04 + Docker + K3s).
* **Self-Hosted Runner:** GitHub Actions runner executing commands inside the private network.

---

## 🛠️ Architecture



The pipeline follows a linear quality gate progression:
1.  **Commit** -> Trigger GitHub Actions.
2.  **Lint & Test** -> Checkstyle & JUnit 5 Unit Tests.
3.  **Security Scan** -> Block build if Critical Vulnerabilities (CVSS > 7.0) are found.
4.  **Build & Publish** -> Create Docker Image (Alpine Base) -> Push to DockerHub.
5.  **Deploy** -> Self-hosted runner pulls image -> Deploys to local K3s cluster.

---

## 🧰 Tech Stack

| Category | Technology |
| :--- | :--- |
| **Application** | Java 17, Spring Boot 3.4.5, Maven |
| **CI Orchestrator** | GitHub Actions |
| **Containerization** | Docker (Multi-stage builds) |
| **Orchestration** | Kubernetes (K3s - Lightweight) |
| **Infrastructure** | Vagrant, Oracle VirtualBox |
| **Security** | OWASP Dependency Check, Trivy, CodeQL |

---

## ⚙️ Setup Instructions

### 1. Prerequisites
Ensure you have the following installed on your local machine:
* [Oracle VirtualBox](https://www.virtualbox.org/)
* [Vagrant](https://www.vagrantup.com/)
* [Java JDK 17](https://adoptium.net/)

### 2. Clone the Repository
```bash
git clone https://github.com/Medha170/Student-GPA-Calculator.git
cd Student-GPA-Calculator

```

### 3. Infrastructure Setup (Simulated On-Premise)

Provision the local server and Kubernetes cluster using Vagrant.

```bash
cd infrastructure
vagrant up

```

*This creates a VM with Docker and K3s pre-installed.*

### 4. Connect the Self-Hosted Runner

1. SSH into the VM: `vagrant ssh`
2. Follow the instructions in **GitHub Settings > Actions > Runners > New Self-Hosted Runner** to install the agent inside the VM.
3. Ensure the runner is active and listening.

### 5. Configure Secrets

Add the following secrets in **Settings > Secrets and variables > Actions**:

* `DOCKERHUB_USERNAME`: Your DockerHub username.
* `DOCKERHUB_TOKEN`: Your DockerHub access token.
* `NVD_API_KEY`: API Key for OWASP Dependency Check.

---

## 📦 Pipeline Workflows

### 1. CI Pipeline (`ci.yml`)

Triggers on `push` to `main`.

* Compiles code.
* Runs Unit Tests (`GPAServiceTest`).
* Scans dependencies (fails on CVEs).
* Builds Docker Image and pushes to DockerHub.

### 2. CD Pipeline (`cd.yml`)

Triggers **after** CI completes successfully.

* Runs on the **Self-Hosted Runner** (inside Vagrant).
* Injects secrets into Kubernetes manifests.
* Performs a Rolling Update on the K3s cluster.
* Verifies Liveness Probes (`/actuator/health`).

---

## ✅ API Endpoints

Once deployed, the application is accessible at `http://localhost:8080` (mapped via Vagrant).

| Method | Endpoint | Description |
| --- | --- | --- |
| `GET` | `/actuator/health` | Health check for Kubernetes Probes. |
| `POST` | `/api/calculate` | Calculate GPA (Input: JSON List of Courses). |

---

## 🛡️ Security Implementation Details

* **Vulnerability Remediation:** The project explicitly uses **Spring Boot 3.4.5** with specific overrides for `spring-framework` (6.2.11) and `undertow` (2.3.20) to resolve `CVE-2025-41249` and `CVE-2025-9784`.
* **Zero-Trust Build:** The CI process runs entirely in ephemeral containers and halts immediately upon detecting security risks.

---

## 👤 Author

**Medha Shree** *Student ID: 23BCS10049*