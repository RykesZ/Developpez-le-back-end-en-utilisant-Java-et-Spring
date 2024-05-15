# Projet 3: Développez le back-end en utilisant Java et Spring

Ce projet consiste à mettre en place le back-end d'une application en utilisant Java et Spring. Suivez les instructions ci-dessous pour importer le projet, configurer la base de données, lancer le front-end et le back-end, et accéder à la documentation Swagger.

## Importer le repo

1. Importez le repo [https://github.com/RykesZ/Developpez-le-back-end-en-utilisant-Java-et-Spring.git](https://github.com/RykesZ/Developpez-le-back-end-en-utilisant-Java-et-Spring.git) dans un dossier nommé "Projet_3".
2. Assurez-vous de vous placer sur la branche `release`.

## Création de la BDD

1. Créez une base de données MySQL et nommez-la "chatop".
2. Exécutez les requêtes SQL se trouvant dans `Projet_3/ressources/sql/script.sql` pour générer les tables nécessaires au fonctionnement de l'API.
3. Dans `Projet_3/portal/resources`, utilisez le fichier `.env.template` pour créer un fichier `.env` en remplissant les champs suivants :
  - `DB_URL` : l'URL vers la base de données MySQL créée. Si elle a été créée localement, l'URL sera de la forme `jdbc:mysql://localhost:{port}/chatop?serverTimezone=UTC`, en remplaçant `{port}` par le port de votre base de données.
  - `DB_USERNAME` : le nom d'utilisateur ayant les droits d'insertion et de lecture de la base de données.
  - `DB_PASSWORD` : le mot de passe associé à l'utilisateur.

## Front End

1. À l'aide d'un terminal, accédez au dossier `Projet_3`.
2. Exécutez la commande `npm install` pour installer les dépendances.
3. Exécutez la commande `npm run start` pour lancer l'application.
4. Accédez à [http://localhost:4200/](http://localhost:4200/) pour utiliser l'application.

## Back End

1. À l'aide d'un IDE, ouvrez le dossier `Projet_3/portal`.
2. Exécutez la commande `mvn install` pour installer les dépendances.
3. Exécutez la commande `mvn spring-boot:run` pour lancer l'API.

## Swagger

Une fois l'API lancée, accédez à [http://localhost:3001/swagger-ui/index.html](http://localhost:3001/swagger-ui/index.html) pour ouvrir la documentation Swagger.
