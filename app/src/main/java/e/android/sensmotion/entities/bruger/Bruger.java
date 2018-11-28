package e.android.sensmotion.entities.bruger;

public abstract class Bruger {

    private String id;
    private String brugernavn;
    private String adgangskode;


    public Bruger(String id, String brugernavn, String adgangskode){
        this.id = id;
        this.brugernavn = brugernavn;
        this.adgangskode = adgangskode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrugernavn() {
        return brugernavn;
    }

    public void setBrugernavn(String brugernavn) {
        this.brugernavn = brugernavn;
    }

    public String getAdgangskode() {
        return adgangskode;
    }

    public void setAdgangskode(String adgangskode) {
        this.adgangskode = adgangskode;
    }
}
