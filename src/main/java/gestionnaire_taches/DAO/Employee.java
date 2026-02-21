public class Employee extends user{
    private int ServiceId;
    private String Poste;
    private LocalDate DateEmbauche ;
    private Service service ;

    // Constructeur vide 
    public Employee() {
        this.Employee ;
    }

    // Constructeur principal
    public Employee(nom,email,pwd,srvId) {
        this.Employee(nom,email,pwd,srvId);

    }

    // Getters et Setters 
    public int getServiceId() {
        return ServiceId;
    }

    public void setServiceId(int ServiceId) {
        this.ServiceId = ServiceId;
    }

    public String getPoste() {
        return Poste ;
    }

    public void setPoste(String Poste) {
        this.Poste = Poste;
    }

    public LocalDate getDateEmbauche() {
        return DateEmbauche;
    }

    public void setDateEmbauche(LocalDate) {
        this.DateEmbauche = DateEmbauche;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

     // To String
    public String toString() {
        return "Employee:" + "Nom" + getnom() + ",Email" +getemail() + ",Poste" +getPoste() +",service" + getService()
         
    }

}