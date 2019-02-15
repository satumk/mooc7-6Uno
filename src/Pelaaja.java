
import java.util.ArrayList;

/*
Pelaaja-rajapinta määrittelee niiden metodien nimet, jotka jokaisen tekoälyn 
tulee toteuttaa. Palaamme termiin rajapinta kurssin myöhemmissä osissa.

Täällä olevissa metodeissa ei ole toteutusta.
 */
public interface Pelaaja {

    /**
     * Metodia pelaa kutsutaan kun on pelaajan vuoro ja pelaajan tulee valita
     * seuraavaksi pelattava kortti.
     *
     * Parametri omatKortit sisältää pelaajan omat kortit, parametri
     * paallimmainen kertoo päällimmäisenä näkyvän kortin ja parametri vari
     * kertoo odotetun värin. Väri kerrotaan esimerkiksi jos edellinen pelaaja
     * on pelannut Villin kortin, jolla voi valita värin uudestaan.
     *
     * Metodin tulee palauttaa sen kortin indeksi, jonka haluat pelata. Jos et 
     * voi pelata mitään korttia, palauta arvo -1. Tässä tapauksessa käteesi 
     * lisätään uusi kortti.
     */
    public int pelaa(ArrayList<Kortti> omatKortit, Kortti paallimmainen, String vari, Pelitilanne tilanne);

    /**
     * Metodia valitseVari kutsutaan jos olet juuri pelannut villin kortin.
     * Tällöin sinun tulee kertoa mihin väriin haluat vaihtaa.
     *
     * Värin tulee olla sallittu väri, eli joku joukosta "Punainen", "Vihrea",
     * "Sininen", "Keltainen"
     */
    public String valitseVari(ArrayList<Kortti> omatKortit);

    /**
     * Metodi nimi kertoo minkä nimiseksi haluat tekoälyäsi kutsuttavan. Nimi
     * saattaa näkyä myöhemmin jonkinlaisessa turnauslistauksessa -- ei
     * törkeyksiä kiitos :).
     */
    public String nimi();
}
