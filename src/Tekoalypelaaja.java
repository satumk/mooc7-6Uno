
import java.util.ArrayList;

// luo tänne luokkaan oma tekoälysi Uno-peliä varten
public class Tekoalypelaaja implements Pelaaja {

    // Saat luoda tarvittaessa oliomuuttujia. Jos luot konstruktorin, varmista
    // että tekoäly toimii myös parametrittomalla konstruktorilla, eli kutsulla
    // Tekoalypelaaja pelaaja = new Tekoalypelaaja();
    
    @Override
    public int pelaa(ArrayList<Kortti> omatKortit, Kortti paallimmaisin, String vari, Pelitilanne tilanne) {
        // Jos jollain pelaajalla on alle 3 korttia, pelaa ensisijaisesti villi kortti
        // muuten pelaa ensisijaisesti värillinen erikoiskortti
        // sitten värilliset kortit, jossa on suuri numero
        
        // nyt pelaa ensimmäisen sopivan kortin tai, jos mikään ei sovi, nostaa uuden
        for (int i = 0; i < omatKortit.size(); i++) {
            if (omatKortit.get(i).saaPelataKortin(paallimmaisin, vari)) {
                return i;
            }
        }
        return -1; // -1 nostaa kortin
    } 
        
       

    @Override
    public String valitseVari(ArrayList<Kortti> omatKortit) {
        String[] okVarit = {"Punainen", "Vihreä", "Sininen", "Keltainen"};
        int punaiset = 0;
        int siniset = 0;
        int vihreat = 0;
        int keltaiset = 0;
        
        for (Kortti kortti : omatKortit) {
            if (kortti.getVari().equals("Vihreä")) {
                vihreat++;
            }
            if (kortti.getVari().equals("Sininen")) {
                siniset++;
            } 
            if (kortti.getVari().equals("Punainen")) {
                punaiset++;
            } 
            if (kortti.getVari().equals("Keltainen")) {
                keltaiset++;
            }
        }
        // valitse se väri, jota on eniten kädessä
        if (vihreat > keltaiset && vihreat > siniset && vihreat > punaiset) {
            return "Vihreä";
        } else if (siniset > vihreat && siniset > keltaiset && siniset > punaiset) {
            return "Sininen";
        } else if (keltaiset > siniset && keltaiset > vihreat && keltaiset > punaiset) {
            return "Keltainen";
        } else {
            return "Punainen";
        }  
    }

    @Override
    public String nimi() {
        // kirjoita tänne nimimerkkisi, jonka haluat mahdollisesti näkyvän 
        // myös muualla

        return "KokelasAI";
    }

}
