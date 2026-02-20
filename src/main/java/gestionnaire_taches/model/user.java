<<<<<<< HEAD
public class User{
=======
package gestionnaire_taches.model;
public class user{
>>>>>>> e41be5618c20d3f7f310270c8a629e3f702c5d1a
    private int id;
    private String name;
    private String prenom;
    private String matricule;
    private String mail;
    private String telephone;

<<<<<<< HEAD
    public User(int id,string name,string prenom,string matricule, String mail, String telephone) {
=======
    public user(int id, String name, String prenom, String matricule, String mail, String telephone) {
>>>>>>> e41be5618c20d3f7f310270c8a629e3f702c5d1a
        this.id=id;
        this.name=name;
        this.prenom=prenom;
        this.matricule=matricule;
        this.mail=mail;
        this.telephone=telephone;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name=name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id=id;
    }
     
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom=prenom;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone=telephone;
    }
     public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail=mail;
    }
<<<<<<< HEAD
} 

package gestionnaire_taches.model
=======

    public String getEmail() {
        return mail;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }
} 
>>>>>>> e41be5618c20d3f7f310270c8a629e3f702c5d1a
