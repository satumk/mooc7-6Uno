
public class Kortti {

    /*
    Mahdolliset korttityypit:
     - "Numero"
     - "Ohitus"
     - "Suunnanvaihto"
     - "Nosta 2"
     - "Villi kortti"
     - "Villi kortti + Nosta 4"
     */
    private String korttityyppi;
    /*
    Mahdolliset värit:
    - "Punainen"
    - "Vihreä"
    - "Sininen"
    - "Keltainen"
     */
    private String vari;
    /*
    Mahdolliset numerot:
    - -1 kuvaa ei-numerokorttia
    - 0-9
     */
    private int numero;

    public Kortti(String korttityyppi, String vari, int numero) {
        this.korttityyppi = korttityyppi;
        this.vari = vari;
        this.numero = numero;
    }

    public String getKorttityyppi() {
        return korttityyppi;
    }

    public int getNumero() {
        return numero;
    }

    public String getVari() {
        return vari;
    }

    public boolean onVillikortti() {
        return this.korttityyppi.contains("Villi kortti");
    }

    public boolean onSuunnanvaihto() {
        return this.korttityyppi.equals("Suunnanvaihto");
    }

    public boolean onOhitus() {
        return onNosta() || this.korttityyppi.equals("Ohitus");
    }

    public boolean onNosta() {
        return this.korttityyppi.contains("Nosta");
    }

    public int montakoNostetaan() {
        if (!onNosta()) {
            return 0;
        }

        return Integer.parseInt(this.korttityyppi.substring(this.korttityyppi.length() - 1));
    }

    /**
     * Kertoo saako nykyisen kortin (this) pelata parametrina annetun kortin
     * päälle.
     *
     * Jos kortti on villi kortti, parametrina annetaan myös väri, jonka villin
     * kortin pelannut pelaaja valitsi.
     */
    public boolean saaPelataKortin(Kortti edellinen, String vaihdettuVari) {
        // villit kortit saa pelata minkä tahansa kortin päälle
        if (this.korttityyppi.equals("Villi kortti + Nosta 4") || this.korttityyppi.equals("Villi kortti")) {
            return true;
        }

        // kortin saa laittaa samanvärisen kortin päälle
        if (this.vari.equals(edellinen.vari)) {
            return true;
        }

        // jos edellisen pelaama erikoiskortti on vaihtanut väriä, kortin 
        // saa pelata jos väri on se, mihin on vaihdettu
        if (this.vari.equals(vaihdettuVari)) {
            return true;
        }

        // erikoiskortin päälle saa pelata saman erikoiskortin, riippumatta
        // erikoiskortin väristä
        if (this.korttityyppi.equals(edellinen.korttityyppi) && this.numero < 0) {
            return true;
        }

        // saman numeroisen kortin saa pelata toisen samannumeroisen kortin päälle
        if (this.numero >= 0 && this.numero == edellinen.getNumero()) {
            return true;
        }

        return false;
    }

    /**
     * Kertoo kortin pistearvon.
     */
    public int pisteet() {
        if (this.numero >= 0) {
            return this.numero;
        }

        if (korttityyppi.equals("Ohitus")) {
            return 20;
        }

        if (korttityyppi.equals("Suunnanvaihto")) {
            return 20;
        }

        if (korttityyppi.equals("Nosta 2")) {
            return 20;
        }

        if (korttityyppi.equals("Villi kortti")) {
            return 50;
        }

        if (korttityyppi.equals("Villi kortti + Nosta 4")) {
            return 50;
        }

        System.out.println("Epäkelpo kortti!");
        return -10000;
    }

    /**
     * Kertoo tuleeko pelaajan kertoa väri kortin pelaamisen jälkeen.
     */
    public boolean pelaajanTuleeKertoaVari() {
        if (this.korttityyppi.equals("Villi kortti + Nosta 4") || this.korttityyppi.equals("Villi kortti")) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        if (this.korttityyppi.equals("Numero")) {
            return this.vari + " " + this.numero;
        }

        if (this.korttityyppi.equals("Ohitus") || this.korttityyppi.equals("Suunnanvaihto") || this.korttityyppi.equals("Nosta 2")) {
            return this.vari + " " + this.korttityyppi;
        }

        if (this.korttityyppi.contains("Villi kortti")) {
            return this.korttityyppi;
        }

        return "Tuntematon kortti";
    }
}
