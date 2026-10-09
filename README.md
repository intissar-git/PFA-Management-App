# Plateforme de gestion et de suivi des projets académiques

![Spring Boot](https://img.shields.io/badge/Backend-Spring%20Boot-brightgreen)
![React](https://img.shields.io/badge/Frontend-React-blue)
![TypeScript](https://img.shields.io/badge/Language-TypeScript-3178c6)
![Tailwind CSS](https://img.shields.io/badge/Style-Tailwind%20CSS-38bdf8)
![JWT](https://img.shields.io/badge/Auth-JWT-orange)
![Maven](https://img.shields.io/badge/Build-Maven-C71A36)
![Node.js](https://img.shields.io/badge/Runtime-Node.js-339933)

Application web full-stack dédiée à la gestion et au suivi des projets académiques (PFA/PFE).  
Elle permet aux étudiants, encadrants et administrateurs de collaborer, de déposer des livrables, de suivre l'avancement des projets et d'échanger autour des tâches, livrables et validations.

---

## 📋 Table des matières

- [Présentation](#-présentation)
- [Fonctionnalités](#-fonctionnalités)
- [Architecture technique](#-architecture-technique)
- [Structure du projet](#-structure-du-projet)
- [Prérequis](#-prérequis)
- [Installation et lancement](#-installation-et-lancement)
- [Configuration](#-configuration)
- [Utilisation](#-utilisation)
- [Documentation](#-documentation)
- [Équipe](#-équipe)
- [Remerciements](#-remerciements)
- [Licence](#-licence)

---

## 🎯 Présentation

Cette plateforme centralise la gestion des projets académiques en offrant un espace unique pour :

- Gérer les demandes et informations relatives aux projets
- Organiser les groupes d'étudiants
- Suivre et valider les livrables
- Visualiser l'avancement des travaux
- Faciliter les échanges entre étudiants et encadrants
- Assurer un bon suivi administratif et pédagogique

Le système est conçu pour être simple, sécurisé et adapté aux besoins d'un environnement universitaire.

---

## 🚀 Fonctionnalités

### 👨‍🎓 Étudiants
- Authentification sécurisée via JWT
- Consultation des informations liées au projet
- Suivi de l'avancement général du projet
- Dépôt de livrables (PDF, code source, images, documents)
- Consultation des commentaires et retours de l'encadrant
- Messagerie interne avec l'encadrant
- Notifications concernant les échéances et validations
- Historique des livrables et des échanges

### 👨‍🏫 Encadrants
- Connexion sécurisée
- Consultation des projets encadrés et des groupes
- Suivi en temps réel des avancées
- Validation / rejet des livrables avec commentaires
- Gestion des groupes et des étudiants
- Communication directe avec les étudiants
- Suivi des notifications et des réunions
- Vue globale de l'état des projets

### 🛡️ Administrateurs
- Gestion complète des comptes utilisateur
- Attribution des rôles (étudiant, encadrant, administrateur)
- Création, modification et suppression de comptes
- Planification des périodes clés (dépôt, soutenance, validation)
- Vue d'ensemble sur tous les projets
- Statistiques et tableaux de bord
- Archivage des anciens comptes et projets

---

## 🏗️ Architecture technique

| Couche | Technologies |
|--------|--------------|
| **Backend** | Spring Boot, Spring Security, JWT, Spring Data JPA, Maven |
| **Frontend** | React.js, TypeScript, Tailwind CSS, Axios, React Router |
| **Base de données** | MySQL / PostgreSQL |
| **API** | REST API sécurisée |
| **Outils** | Git, GitHub, Postman, UML |

---

## 📁 Structure du projet

```text
PFA-Management-App/
├── backend/                          # API Spring Boot
│   ├── src/
│   ├── pom.xml
│   └── README.md
├── frontend/                         # Application React + TypeScript
│   ├── src/
│   ├── public/
│   ├── package.json
│   └── vite.config.ts
├── docs/                             # Documentation
│   ├── RAPPORT_PFE.pdf
│   ├── diagrams/
│   └── screenshots/
├── README.md
├── .gitignore
├── LICENSE
└── .env.example
```

---

## ⚙️ Prérequis

Avant de commencer, assurez-vous d'avoir installé :

- Java JDK 17 ou 11
- Maven 3.8+
- Node.js 18+
- npm
- MySQL ou PostgreSQL
- Git

---

## 🛠️ Installation et lancement

### 1. Cloner le dépôt

```bash
git clone https://github.com/intissar-git/PFA-Management-App.git
cd PFA-Management-App
```

### 2. Backend (Spring Boot)

#### a) Configurer la base de données

Créez une base de données (par exemple : `pfa_db`) puis modifiez le fichier :

`backend/src/main/resources/application.properties`

Exemple pour MySQL :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/pfa_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=VOTRE_UTILISATEUR
spring.datasource.password=VOTRE_MOT_DE_PASSE
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Configuration JWT
jwt.secret=VOTRE_CLE_SECRETE
jwt.expiration=86400000
```

> Adaptez la configuration selon votre base de données (MySQL, PostgreSQL, etc.).

#### b) Compiler et lancer le backend

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

Le backend sera accessible sur :

```text
http://localhost:8080
```

---

### 3. Frontend (React + TypeScript)

#### a) Installer les dépendances

```bash
cd frontend
npm install
```

#### b) Configurer l'URL de l'API

Créez un fichier `.env` à la racine du frontend :

```env
VITE_API_URL=http://localhost:8080/api
```

> Si vous utilisez Create React App, utilisez plutôt :
>
> ```env
> REACT_APP_API_URL=http://localhost:8080/api
> ```

#### c) Lancer le serveur de développement

```bash
# Avec Vite
npm run dev

# Ou avec Create React App
npm start
```

Le frontend sera accessible sur :

```text
http://localhost:5173   # Vite
http://localhost:3000   # CRA
```

---

## 🔧 Configuration

| Variable | Description | Exemple |
|----------|-------------|---------|
| `VITE_API_URL` | URL de l'API backend | `http://localhost:8080/api` |
| `spring.datasource.url` | URL JDBC de la base | `jdbc:mysql://localhost:3306/pfa_db` |
| `jwt.secret` | Clé secrète pour signer les JWT | `votre_cle_secrete` |

---

## 📖 Utilisation

1. Démarrer le backend Spring Boot
2. Démarrer le frontend React
3. Ouvrir le navigateur sur l'URL du frontend
4. Se connecter avec un compte utilisateur
5. Naviguer selon le rôle attribué

- Administrateur : gestion globale
- Encadrant : suivi des projets et validations
- Étudiant : dépôt de livrables et consultation du suivi

> Il est recommandé de créer un premier compte administrateur dans la base de données ou via un script d'initialisation.

---

## 📚 Documentation

- Rapport de projet : `docs/RAPPORT_PFE.pdf`
- Diagrammes UML : `docs/diagrams/`
- Captures d'écran : `docs/screenshots/`

---

## 👥 Équipe

- Ikram AZLANI
- Intissar FARHOUN
- Souaad AMRAOUI

Encadrant :
- Mr. Mohammed OUTANOUT

Filière :
- Génie Informatique — DUT

Année universitaire :
- 2024-2025

---

## 🙏 Remerciements

Nous tenons à remercier notre encadrant, Monsieur Mohammed OUTANOUT, pour sa disponibilité, ses conseils avisés et son accompagnement tout au long de ce projet.

Nous remercions également nos professeurs, nos familles et nos amis pour leur soutien précieux.



## 🧩 Améliorations possibles

- Ajout d'un système de notifications en temps réel
- Amélioration du tableau de bord administrateur
- Export des rapports PDF/Excel
- Gestion avancée des réunions et soutenances
- Ajout de rôles personnalisés et de permissions détaillées
- Intégration de tests unitaires et fonctionnels

---

## 🤝 Contribution

Les contributions sont les bienvenues.  
Pour améliorer le projet :

1. Forker le dépôt
2. Créer une branche
3. Appliquer vos modifications
4. Soumettre une pull request

---

## 📞 Contact

Pour toute question concernant le projet, vous pouvez ouvrir une issue sur le dépôt GitHub.
