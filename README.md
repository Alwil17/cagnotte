## Cagnotte en ligne - API REST

### Description

Cette application est une API REST pour un système de cagnotte en ligne, permettant aux utilisateurs de créer, gérer et participer à des cagnottes.

### Technologies utilisées

- Java 17
- Spring Boot 3
- Spring Security & JWT
- Hibernate & JPA
- MySQL
- Maven

### Prérequis

Avant de commencer, assurez-vous d'avoir installé :
- Java 17
- Maven
- MySQL

### Installation
#### 1. Cloner le projet

```
git clone https://github.com/votre-repo/cagnotte.git
cd cagnotte
```

#### 2. Configurer la base de données

Dans `src/main/resources/application.yml` :

```
spring.datasource.url=[Your DB_URL]
spring.datasource.username=[DB_USERNAME]
spring.datasource.password=[DB_PASSWORD]
spring.jpa.hibernate.ddl-auto=update
```

#### 3. Lancer l'application

`mvn spring-boot:run`

L'API sera disponible à l'adresse : [http://localhost:8084](http://localhost:8084)

### Authentification avec JWT

#### 1. Inscription

Endpoint : `POST /auth/register`

```json
{
  "nom": "string",
  "prenoms": "string",
  "email": "string",
  "tel1": "string",
  "tel2": "string",
  "adresse": "string",
  "password": "string",
  "type": "string",
  "username": "string",
  "enabled": true,
  "password": "string",
  "is_active": true
}
```

2. Connexion

Endpoint : `POST /auth/login`

```json
{
"username": "johndoe",
"password": "password123"
}
```

Réponse :
```json
{
"accessToken": "eyJhbGciOiJIUzI1...",
"tokenType": "Bearer"
}
```


Utiliser le token dans l'en-tête des requêtes :

`Authorization: Bearer eyJhbGciOiJIUzI1...`

### Contribution

Les contributions sont les bienvenues !

1. Forker le projet
2. Créer une branche feature
3. Commit & push vos modifications
4. Ouvrir une Pull Request

### Licence

Ce projet est sous licence MIT.

