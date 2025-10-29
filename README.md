### 🎥 Démonstration (Vidéo)
- [Lien vers la vidéo de démonstration](https://youtu.be/fCsxYqu1Pno)  
- Ce qui est montré dans la vidéo :
  - Navigation dans les pages.  
  - Création, modification et suppression d’un enregistrement.  
  - Recherche / filtrage.  
  - Dashboard / statistiques.  


# 1. Description du projet
- **Contexte fonctionnel :** Gestion d’un cinéma avec films, séances et billets.
- **Objectif de l'application :** Fournir une interface pour gérer les films, les séances, les billets et visualiser les statistiques.
- **Public cible / cas d'usage :** Gestionnaires de cinéma ou personnel administratif.
- **Ce que l'application permet concrètement :** Permet de gérer les films, les séances, les billets et de suivre les indicateurs clés via un tableau de bord et des statistiques.

# 2. Architecture technique
## 2.1 Stack technologique
- **Backend :** Spring Boot 3.x, Spring Data JPA / Hibernate.
- **Frontend :** Thymeleaf, HTML/CSS, Bootstrap.
- **Base de données :** MySQL.
- **Build :** Maven.

## 2.2 Structure du code
- `entity/` : classes JPA (`Film`, `Seance`, `Billet`).
- `repository/` : interfaces Spring Data JPA pour chaque entité.
- `service/` : logique métier (calcul recettes, taux de remplissage, etc.).
- `controller/` : contrôleurs web MVC pour films, séances, billets et dashboard.
- `templates/` : vues Thymeleaf (`film.html`, `seance.html`, `billet.html`, `dashboard.html`, `statistique.html`).
- `static/` : fichiers CSS, JS et images.

## 2.3 Diagramme d’architecture
- **Flux :** Navigateur → Contrôleur Spring → Service → Repository → Base de données → Vue Thymeleaf.

# 3. Fonctionnalités principales
- **CRUD sur les entités principales :**
  - Films : ajout, modification, suppression, consultation.
  - Séances : ajout, modification, suppression, consultation.
  - Billets : ajout, consultation et suppression.
- **Recherche / filtrage :**
  - Film : filtrage par titre et genre.
  - Séance : filtrage par film et date.
  - Billet : filtrage par séance et statut.
- **Tableau de bord / statistiques :**
  - Nombre total de films, séances et billets.
  - Recettes totales.
  - Taux de remplissage par séance.
  - Recettes par film.
- **Gestion des statuts :**
  - Billets : confirmés / annulés.
  - Séances : disponibles / complètes.

# 4. Modèle de données
## 4.1 Entités
- **Film :** id, titre, genre, durée, description, date de sortie.
- **Seance :** id, film (relation ManyToOne), date et heure, salle, nombre de places disponibles.
- **Billet :** id, séance (relation ManyToOne), prix, statut (confirmé / annulé), date d’achat.

## 4.2 Relations
- **Film ↔ Séance :** `@OneToMany` (un film peut avoir plusieurs séances).  
- **Séance ↔ Billet :** `@OneToMany` (une séance peut avoir plusieurs billets).  
- **Schéma ER (texte) :**


## 4.3 Configuration base de données
- **URL de connexion :** `jdbc:mysql://localhost:3306/gestioncinema`
- **Identifiants / mot de passe :** `root` / `` (pour tests)
- **Stratégie de génération des tables :** `spring.jpa.hibernate.ddl-auto=update`

# 5. Lancer le projet
## 5.1 Prérequis
- Java 17 ou supérieur.
- Maven 3.x.

## 5.2 Installation
- Cloner le dépôt.
- Configurer `application.properties` / `application.yml`.
- Lancer l'application : `mvn spring-boot:run` ou exécuter la classe main `GestionCinemaApplication.java`.

## 5.3 Accès

- **Tableau de bord / statistiques :** `http://localhost:8080/dashboard` et `http://localhost:8080/statistique`

# 6. Jeu de données initial (optionnel)
- Chargement via `data.sql` ou `CommandLineRunner`.
- Exemple :
```sql
INSERT INTO film (titre, genre, duree) VALUES ('Inception', 'Science-fiction', 148);
INSERT INTO seance (film_id, date, heure, salle, places_disponibles) VALUES (1, '2025-10-30', '20:00', 'Salle 1', 100);
INSERT INTO billet (seance_id, prix, statut) VALUES (1, 50, 'confirmé');

```

# 7. Auteurs / Encadrement
- RIHAL Mohamed.
- Pr LACHGAR Mohamed / Programmation web avance / ecole normale superieur marrakech.
