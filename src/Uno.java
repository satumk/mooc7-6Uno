
import java.util.ArrayList;

public class Uno {

    private Pistetilanne pistetilanne;
    private ArrayList<Pelaaja> pelaajat;

    public Uno(Pistetilanne pistetilanne, ArrayList<Pelaaja> pelaajat) {
        this.pistetilanne = pistetilanne;
        this.pelaajat = pelaajat;
    }

    public void tulostaPistetilanne() {
        System.out.println("Pistetilanne: ");
        for (int i = 0; i < pelaajat.size(); i++) {
            System.out.println("\tPelaaja " + pelaajat.get(i).nimi() + ", pisteet: " + pistetilanne.pelaajanPisteet(i));
        }
    }

    public void pelaaKierros(int kenestaAloitetaan) {
        Pakka pakka = new Pakka();
        pakka.alusta();

        String suunta = "Myötäpäivään";

        ArrayList<ArrayList<Kortti>> pelaajienKortit = alustaPelaajienKortit(pakka);

        // pelataan kierros        
        int pelaajanIndeksi = kenestaAloitetaan;

        String valittuVari = "";
        Kortti paallimmainen = pakka.otaKortti();

        // Luokkaa Pelitilanne käytetään antamaan lisätietoa pelistä
        Pelitilanne pelitilanne = new Pelitilanne(pelaajat.size());
        pelitilanne.asetaSuunta(suunta);

        while (true) {
            pelitilanne.setOmaIndeksi(pelaajanIndeksi);
            Pelaaja pelaaja = pelaajat.get(pelaajanIndeksi);
            ArrayList<Kortti> pelaajanKortit = pelaajienKortit.get(pelaajanIndeksi);

            // kysytään pelattavaa korttia
            int kortinIndeksi = pelaaja.pelaa(pelaajanKortit, paallimmainen, valittuVari, pelitilanne.kopio());

            // pelaaja otti uuden kortin
            if (kortinIndeksi == -1) {
                pelaajanKortit.add(pakka.otaKortti());
                pelitilanne.paivitaKorttienLukumaara(pelaajanIndeksi, pelaajanKortit.size());
                pelaajanIndeksi = siirraVuoroa(suunta, pelaajanIndeksi);
            } else {

                Kortti pelattuKortti = pelaajanKortit.get(kortinIndeksi);
                
                pelitilanne.paivitaKorttienLukumaara(pelaajanIndeksi, pelaajanKortit.size() - 1);
                pelitilanne.paivitaViimeksiPelattuVari(pelaajanIndeksi, pelattuKortti.getVari());

                if (pelattuKortti.onVillikortti()) {
                    valittuVari = pelaaja.valitseVari(pelaajanKortit);
                    pelitilanne.paivitaViimeksiPelattuVari(pelaajanIndeksi, valittuVari);
                }

                if (pelattuKortti.onSuunnanvaihto()) {
                    suunta = vaihdaSuuntaa(suunta);
                    pelitilanne.asetaSuunta(suunta);
                }

                pelaajanIndeksi = siirraVuoroa(suunta, pelaajanIndeksi);

                if (pelattuKortti.onNosta()) {
                    int montakoNostetaan = pelattuKortti.montakoNostetaan();
                    while (montakoNostetaan > 0) {
                        pelaajienKortit.get(pelaajanIndeksi).add(pakka.otaKortti());
                        montakoNostetaan--;
                    }

                    pelitilanne.paivitaKorttienLukumaara(pelaajanIndeksi, pelaajienKortit.get(pelaajanIndeksi).size());
                }

                if (pelattuKortti.onOhitus()) {
                    pelaajanIndeksi = siirraVuoroa(suunta, pelaajanIndeksi);
                }

                pelaajanKortit.remove(kortinIndeksi);
                pakka.palautaKortti(paallimmainen);
                paallimmainen = pelattuKortti;

            }

            if (pelaajanKortit.isEmpty()) {
                break;
            }
        }

        int voittaja = -1;
        for (int i = 0; i < this.pelaajat.size(); i++) {
            if (pelaajienKortit.get(i).isEmpty()) {
                voittaja = i;
                break;
            }
        }

        int pisteet = pelaajienKortit.stream().mapToInt(kortit -> kortit.stream().mapToInt(k -> k.pisteet()).sum()).sum();
        pistetilanne.lisaaPisteita(voittaja, pisteet);
    }

    private String vaihdaSuuntaa(String suunta) {
        if (suunta.equals("Myötäpäivään")) {
            suunta = "Vastapäivään";
        } else {
            suunta = "Myötäpäivään";
        }
        return suunta;
    }

    public ArrayList<ArrayList<Kortti>> alustaPelaajienKortit(Pakka pakka) {
        ArrayList<ArrayList<Kortti>> pelaajienKortit = new ArrayList<>();
        for (int i = 0; i < this.pelaajat.size(); i++) {
            pelaajienKortit.add(new ArrayList<>());
        }

        // jokaisella pelaajalla on aluksi 7 korttia
        for (int kortteja = 0; kortteja < 7; kortteja++) {
            for (int pelaaja = 0; pelaaja < this.pelaajat.size(); pelaaja++) {
                pelaajienKortit.get(pelaaja).add(pakka.otaKortti());
            }
        }

        return pelaajienKortit;
    }

    public int siirraVuoroa(String suunta, int nykyisenPelaajanIndeksi) {
        if (suunta.equals("Myötäpäivään")) {
            nykyisenPelaajanIndeksi++;
            nykyisenPelaajanIndeksi %= this.pelaajat.size();
        } else {
            nykyisenPelaajanIndeksi--;
            nykyisenPelaajanIndeksi += this.pelaajat.size();
            nykyisenPelaajanIndeksi %= this.pelaajat.size();
        }

        return nykyisenPelaajanIndeksi;
    }
}
