public class Administrator extends User{
    private boolean isSuperAdmin;

    public Administrator(){
        super();
        this.isSuperAdmin=false;
    }

    public Administrator(String nom, String email, String password){
        super(nom, email, password);
        this.isSuperAdmin=false;
    }

    public boolean isSuperAdmin(){
        return this.isSuperAdmin;
    }

    public void setSuperAdmin(boolean isSuperAdmin){
        this.isSuperAdmin=isSuperAdmin;
    }
    
    public boolean canManagerAdministrators(){
        return this.isSuperAdmin;
    }

    public boolean canManagerAllServices(){
        return this.isSuperAdmin;
    }

    public String toString(){
        return "Administrateur:"+"Nom"+getnom()+", Email"+getemail()+", isSuperAdmin"+isSuperAdmin
    }
}