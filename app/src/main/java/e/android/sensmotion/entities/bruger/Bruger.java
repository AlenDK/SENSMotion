package e.android.sensmotion.entities.bruger;

public abstract class Bruger {

    private String brugernavn;
    private String adgangskode;


    public Bruger(String brugernavn, String adgangskode){
        this.brugernavn = brugernavn;
        this.adgangskode = adgangskode;
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
