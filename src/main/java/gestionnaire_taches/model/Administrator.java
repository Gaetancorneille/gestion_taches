package gestionnaire_taches.model;

public class Administrator extends user {
    private boolean isSuperAdmin;

    public Administrator() {
        super(0, "", "", "", "", "");
        this.isSuperAdmin = false;
    }

    public Administrator(String nom, String email, String password) {
        super(0, nom, email, password, "", "");
        this.isSuperAdmin = false;
    }

    public Administrator(int id, String nom, String email, String password, String role, String departement) {
        super(id, nom, email, password, role, departement);
        this.isSuperAdmin = false;
    }

    public boolean isSuperAdmin() {
        return this.isSuperAdmin;
    }

    public void setSuperAdmin(boolean isSuperAdmin) {
        this.isSuperAdmin = isSuperAdmin;
    }

    public boolean canManagerAdministrators() {
        return this.isSuperAdmin;
    }

    public boolean canManagerAllServices() {
        return this.isSuperAdmin;
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "nom='" + getName() + '\'' +
                ", email='" + getMail() + '\'' +
                ", isSuperAdmin=" + isSuperAdmin +
                '}';
    }

    public String getPassword() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getDepartement() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getRole() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}