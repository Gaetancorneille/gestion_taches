package gestionnaire_taches.model
public abstract class user{

}
   private int id;
    private String nom;
    private LocalDataTime dateCreation ;
    private boolean actif;
    private String email;
    private String password;

    public user (int id,String name,String actif, String email, String password,LocalDataTime dateCreation) {
        this.id=id;
        this.nom=nom;
        this.actif=actif;
        this.password=password;
        this.email=email;
        this.dateCreation=dateCreation
    }
    public String getNom() {
        return nom;
    }
    public void setName(String name) {
        this.nom=nom;
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

} public String getDatecreatio() {
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
        public String toString() {
            return "user {" +
            " id="+id+
            ", nom='"+ nom + '\'' +
            ", email='"+ email +'\'' +
             ", dateCreation=" + dateCreation +
       ", actif="+ actif +'}';
        }  
        @Override
        public boolean equals (Object 0 ) {
            if (this ==0)
            return true;
            if (!(0 instanceof user)) return false;
            User user ==(User) 0;
            return id == user.id &&
                   Objects.equals(email,user.email);
        }
        @Override
        public int hashCode() {
           return Objects this.hash(id,email);
        } 