# Machine à Café

## Aperçu

Le système de machine à café est une application Java simulant le fonctionnement d'une machine à café automatique. Il gère les interactions des utilisateurs pour le paiement, la sélection du café et la distribution, tout en prenant en charge les erreurs telles que les paiements insuffisants, le manque d'ingrédients, les pannes de courant ou l'insuffisance d'eau. L'architecture est modulaire, extensible et suit les principes orientés objet pour garantir maintenabilité et évolutivité.

## Architecture du Système

Le système repose sur une architecture modulaire avec une séparation claire des responsabilités. Il adhère aux principes de conception orientée objet, notamment l'encapsulation, la responsabilité unique et l'injection de dépendances. L'architecture est divisée en plusieurs composants, chacun dédié à un aspect spécifique du fonctionnement de la machine.

### Composants

1. **Module de Paiement**

   - **Responsabilité** : Gère le traitement des paiements pour les commandes de café.
   - **Fonctionnalités** :
     - Accepte les paiements (jetons, paiement numérique, etc.).
     - Vérifie si le paiement est suffisant pour le café sélectionné.
     - Communique l'état du paiement au module de sélection de café.
   - **Gestion des erreurs** :
     - Affiche un message d'erreur si le paiement est insuffisant.

2. **Module de Sélection de Café**

   - **Responsabilité** : Gère la sélection des types de café et vérifie la disponibilité des ressources.
   - **Fonctionnalités** :
     - Présente les options de café disponibles (ex. : espresso, latte, cappuccino).
     - Vérifie la disponibilité des ingrédients (café, lait, sucre, etc.).
     - Contrôle le niveau d'eau (considéré comme une ressource distincte).
     - Vérifie si la machine est sous tension.
   - **Gestion des erreurs** :
     - Signale si les ingrédients ou l'eau sont insuffisants, ou si la machine est hors tension.
   - **Dépendances** : Module d'Inventaire des Ingrédients, Module de Réservoir d'Eau, Module d'Alimentation.

3. **Module d'Inventaire des Ingrédients**

   - **Responsabilité** : Suit et gère l'inventaire des ingrédients nécessaires à la préparation des cafés.
   - **Fonctionnalités** :
     - Gère les niveaux de stock des ingrédients (café, lait, sucre, etc.).
     - Met à jour l'inventaire après chaque préparation.
     - Fournit l'état de disponibilité des ingrédients au module de sélection.
   - **Gestion des erreurs** :
     - Signale lorsque les ingrédients sont épuisés.

4. **Module de Réservoir d'Eau**

   - **Responsabilité** : Gère l'approvisionnement en eau pour la préparation des cafés.
   - **Fonctionnalités** :
     - Suit le niveau d'eau dans le réservoir de la machine.
     - Met à jour le niveau après chaque préparation.
     - Fournit l'état de disponibilité de l'eau au module de sélection.
   - **Gestion des erreurs** :
     - Signale un manque d'eau si le réservoir est insuffisant.

5. **Module d'Alimentation**

   - **Responsabilité** : Surveille l'état de l'alimentation électrique de la machine.
   - **Fonctionnalités** :
     - Vérifie si la machine est sous tension avant toute opération.
     - Fournit l'état de l'alimentation au module de sélection.
   - **Gestion des erreurs** :
     - Signale si la machine est hors tension.

6. **Module de Distribution de Café**

   - **Responsabilité** : Gère la préparation et la distribution du café sélectionné.
   - **Fonctionnalités** :
     - Exécute l'opération `getCoffee` pour préparer et distribuer le café.
     - Coordonne avec les modules d'inventaire et de réservoir d'eau pour consommer les ressources.
     - Confirme la préparation et la distribution réussies à l'utilisateur.
   - **Gestion des erreurs** :
     - S'appuie sur le module de sélection pour s'assurer que toutes les conditions (paiement, ingrédients, eau, alimentation) sont remplies.
   - **Dépendances** : Module de Sélection de Café, Module d'Inventaire des Ingrédients, Module de Réservoir d'Eau.

7. **Module d'Interface Utilisateur**

   - **Responsabilité** : Fournit l'interface pour les interactions avec l'utilisateur.
   - **Fonctionnalités** :
     - Affiche les options de café, les invites de paiement et les messages d'erreur.
     - Collecte les entrées utilisateur (sélection de café, paiement).
     - Transmet les entrées aux modules de paiement et de sélection.
   - **Dépendances** : Module de Paiement, Module de Sélection de Café, Module de Distribution de Café.

### Flux Opérationnel

1. **Démarrage** : L'utilisateur interagit pour sélectionner un café de son choix.
2. **Paiement** : Le module de paiement traite le paiement et vérifie s'il est suffisant et conforme.
   - En cas d'insuffisance, un message d'erreur est affiché, demandant un complément.
3. **Sélection de Café** : Le module de sélection vérifie :
   - La disponibilité des ingrédients via le module d'inventaire.
   - La disponibilité de l'eau via le module de réservoir.
   - L'état de l'alimentation via le module d'alimentation.
   - En cas d'échec, un message d'erreur approprié est affiché via l'interface utilisateur.
4. **Distribution** : Si toutes les vérifications sont réussies, le module de distribution prépare et distribue le café, mettant à jour les modules d'inventaire et de réservoir.
5. **Finalisation** : L'interface utilisateur informe l'utilisateur du succès de la préparation ou de toute erreur.

