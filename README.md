# Documentation du Projet de Gestion de Rendez-vous et Quiz

## Description du Modèle (UML)
Le modèle de données du projet inclut principalement les entités suivantes :
- **RendezVous** : Représente un rendez-vous, incluant des attributs comme `id`, `date`, et `description`.
- **Question** : Représente les questions du quiz, incluant un identifiant unique, une question courte, et éventuellement des options de réponse.

### Relation entre les Entités
- **RendezVous** a une relation directe avec les utilisateurs impliqués, bien que le service `Personne` ne soit pas implémenté dans cette version.
- **Question** est une entité utilisée dans le cadre du quiz, permettant de gérer une liste de questions courtes.

## Description de l'API (RendezVous et Quiz)

### API RendezVous
L'API pour `RendezVous` inclut les opérations CRUD basiques permettant de gérer les rendez-vous via des endpoints REST.

| Méthode | Endpoint                | Description                                      |
|---------|--------------------------|--------------------------------------------------|
| GET     | `/api/rendezvous`        | Récupère tous les rendez-vous.                   |
| GET     | `/api/rendezvous/{id}`   | Récupère un rendez-vous par son identifiant.     |
| POST    | `/api/rendezvous`        | Crée un nouveau rendez-vous.                     |
| PUT     | `/api/rendezvous/{id}`   | Modifie un rendez-vous existant.                 |
| DELETE  | `/api/rendezvous/{id}`   | Supprime un rendez-vous par son identifiant.     |

### API Quiz et Servlet Question
L'API Quiz permet de gérer des questions courtes et inclut une servlet pour les questions, permettant la récupération et la manipulation de données via HTTP.

| Méthode | Endpoint                | Description                                      |
|---------|--------------------------|--------------------------------------------------|
| GET     | `/api/question`          | Récupère toutes les questions du quiz.           |
| GET     | `/api/question/{id}`     | Récupère une question spécifique.                |
| POST    | `/api/question`          | Ajoute une nouvelle question au quiz.            |
| PUT     | `/api/question/{id}`     | Met à jour une question existante.               |
| DELETE  | `/api/question/{id}`     | Supprime une question spécifique.                |

## Technologies et Bibliothèques Utilisées
- **Java**
- **Jetty** pour le serveur de développement léger.
- **JAX-RS** pour l’implémentation des services REST.
- **JPA** pour la gestion des entités et l'accès aux données.
- **Lombok** pour la réduction du code standardisé (`@Getter`, `@Setter`, etc.).
- **Servlets** pour la gestion des questions du quiz.

## Structure du Projet

- **Contrôleurs** : Contient les endpoints REST pour `RendezVous` et `Question`.
- **Services** : Fournit les logiques métier et les interactions avec le niveau de persistance.
- **Repositories** : Utilise JPA pour effectuer les opérations CRUD sur la base de données.
- **Servlet Question** : Gestion des questions pour le quiz via des opérations CRUD.

---

### Exécution du Projet
1. Cloner le dépôt.
2. Lancer le projet via le serveur Jetty.
3. Utiliser Postman ou un autre client HTTP pour interagir avec l'API REST.

### Exemple de Requête
```bash
curl -X GET http://localhost:8080/api/rendezvous
