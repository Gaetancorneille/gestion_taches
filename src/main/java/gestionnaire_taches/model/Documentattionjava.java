```java
/**
 * Represents an abstract user in the system
 */
public abstract class user{
    private String nom;
    private  String email;
    private String password;
    private LocalDataTime dateCreation;
    private boolean actif;
    /** 
     * Constructor a user with the specified details.
     * 
     * @param nom   the of the user
     * @param email the email of the user
     * @param password the password of the user
     * @param dateCreation the creation date of the account
     * @param actif whether the account is active
     */
    public user(String nom,string email,string password,LocalDataTime dateCreation,boolean actif){
        this.nom=nom;
        this.actif=actif;
        this.password=password;
        this.email=email;
        this.dateCreation=dateCreation
    }
  
    /**
     * Gets the  name of the user.
     * 
     *@return the name of the user
     */
    public string getNom() {
        return nom;
    }
        /**
         * Sets the name of the user.
         * @param nom the new name of the user
         * /
           public void setNom ( String nom){ 
           this.nom=nom ;
           }
  /**
     * Gets the  email of the user.
     * 
     *@return the email of the user
     */
    public string getEmail() {
        return email;
    }
         /**
         * Sets the email of the user.
         * @param email the new email of the user
         * /
           public void setEmail( String email){ 
           this.email=email ;
           }


 /**
     * Gets the  password of the user.
     * 
     *@return the password of the user
     */
    public string getPassword() {
        return password;
    }
         /**
         * Sets the password of the user.
         * @param password the new password of the user
         * /
           public void setPassword( String pasword){ 
           this.password=password;
           }

/**
     * Gets the  creation date of the user's account.
     * 
     *@return the creation date of the user
     */
    public LocalDataTime getDate creation() {
        return dateCreation;
    }
         /**
         * Sets the password of the user.
         * @param datecreation the new date creation of the user
         * /
           public void setCreation date (   LocalDate Time dateCreation ){ 
           this.dateCreation=dateCreation; 
           }


    }/**
     * Gets the  actif of the user.
     * 
     *@return the actif of the user
     */
    public string getActif() {
        return actif;
    }
         /**
         * Sets the actif of the user.
         * @param actif the new actif of the user
         * /
           public void setActif( String actif){ 
           this.actif=actif;
           }


 }   