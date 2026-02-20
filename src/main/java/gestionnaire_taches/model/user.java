package gestionnaire_taches.model
public abstract class user{

}
   private int id;
    private String name;
    private LocalDataTime dateCreation ;
    private boolean actif;
    private String email;
    private String password;

    public user (int id,String name,String actif, String email, String password,LocalDataTime dateCreation) {
        this.id=id;
        this.name=name;
        this.actif=actif;
        this.password=password;
        this.email=email;
        this.dateCreation=dateCreation
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
     
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password=password;
    }
 public String getActif() {
        return actif;
    }
    public void setActif(String actif) {
        this.actif=actif;
    }
     public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email=email;
    }
}public String getDatecreatio() {
        return dateCreation;
    }
    public void setDateCreation(String dateCreation) {
        this.dateCreation=dateCreation;
         }
        public isActif() {
            return actif;
        }
        public void setActif(boolean actif) {
            this.actif=actif;
        } 
         public String toString() {
            return toString;
        }
        public void toString(String toString) {
            this.toString=toString;
        }  
        public boolean equals (Object 0 ) {
            if (this ==0)
            return true;
            if (!(0 instanceof user))
        }
        public void setActif(boolean actif) {
            this.actif=actif;
        } 