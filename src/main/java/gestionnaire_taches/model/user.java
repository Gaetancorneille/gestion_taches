package gestionnaire_taches.model
public class user
 private int id;
    private String name;
    private String prenom;
    private String matricule;
    private String mail;
    private String telphone;

    public employe(int id,string name,string prenom,string matricule, String mail, String telephone) {
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
    public String getId() {
        return id;
    }
    public void setId(String id) {
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
    public void setMail(String name) {
        this.mail=mail;
    }
} 