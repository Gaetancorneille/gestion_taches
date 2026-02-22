#Script de données de test 

#1.Insertion des administrateurs dont un super admin 
INSERT INTO Administrator(nom_admin,email_admin,password_admin,isSuperAdmin)
VALUES ("Sabrina","sabrina@equip7.com","hash_admin_123",TRUE),
       ("Tientcheu","tientcheu@equip7.com","hash_admin_456",FALSE);

#2.Insertion des services de test 
INSERT INTO Service(nom_service,description_service,administrator_id)
VALUES ("RH","Ressources Humaines",1),
       ("IT","Support technique",1),
       ("Finance","Gestion de la comptabilité",2),
       ("Marketing","Ventes et Pub",2);

#3.Insertion des employés assignés aux services
INSERT INTO Employee(nom_employee,email_employee,password_employee,service_id,poste_employee,dateEmbauche)
VALUES ("Jean","jean@equip7.com","pass123",1,"Chargé de recrutement","2025-01-10"),
       ("Marie","marie@equip7.com","pass456",2,"Développeur JAVA","2025-02-01"),
       ("Paul","paul@equip7.com","pass789",3,"Comptable","2025-02-15");

#4.Insertion de tâches avec differents statuts et priorités
INSERT INTO Task(titre_task,description_task,employee_id,service_task_id,priorite_task,statut_task,dateLimite_task)
VALUES ("Fixer bug MAVEN","Problème de compilation",2,2,"URGENTE","EN_COURS","2026-03-01"),
       ("Audit financier","Bilan annuel",3,3,"HAUTE","A_FAIRE","2026-04-15"),
       ("Entretien L3","Stagiaires RH",1,1,"NORMALE","TERMINEE","2026-02-25");

#5. Insertion de sous taches
INSERT INTO Subtask(titre_subtask,description_subtask,task_id,ordre_subtask,statut_subtask)
VALUES ("Verifier Path","Check environnement",1,1,"TERMINEE"),
       ("Recharger VS Code","Redémarrer éditeur",1,2,"A_FAIRE");