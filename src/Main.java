
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);

        ArrayList<Pelaaja> pelaajat = new ArrayList<>();
        pelaajat.add(new TekstipohjainenPelaaja(lukija, "eka"));
        pelaajat.add(new TekstipohjainenPelaaja(lukija, "toka"));

        // Tekoälypelaaja lisätään peliin kun poistat seuraavat kommentit:
        pelaajat.add(new Tekoalypelaaja());

        Pistetilanne pistetilanne = new Pistetilanne(pelaajat.size());
        Uno uno = new Uno(pistetilanne, pelaajat);

        int aloittajanIndeksi = 0;

        while (true) {

            System.out.println("");
            System.out.println("");
            System.out.println("Aloitetaan! ");

            uno.pelaaKierros(aloittajanIndeksi);

            if (pistetilanne.onkoVoittaja()) {
                break;
            }

            aloittajanIndeksi++;
            aloittajanIndeksi %= pelaajat.size();

            uno.tulostaPistetilanne();

            System.out.println("");
            System.out.println("Peli jatkuu! Seuraava kierros edessä...");
        }

        System.out.println("");
        System.out.println("Peli loppui -- voittaja: " + pelaajat.get(pistetilanne.voittajanIndeksi()));
        System.out.println("Loppupisteet: ");
        uno.tulostaPistetilanne();
    }
}
