
import java.util.ArrayList;
import java.util.Scanner;

public class TekstipohjainenPelaaja implements Pelaaja {

    private Scanner lukija;
    private String nimi;

    public TekstipohjainenPelaaja(Scanner lukija, String nimi) {
        this.lukija = lukija;
        this.nimi = nimi;
    }

    @Override
    public int pelaa(ArrayList<Kortti> omatKortit, Kortti paallimmaisin, String vari, Pelitilanne tilanne) {
        System.out.println("---");
        System.out.println("Pelaaja " + this.nimi);
        System.out.println("Päällimmäinen kortti: " + paallimmaisin);
        if (paallimmaisin.onVillikortti()) {
            System.out.println("\tValittu väri: " + vari);
        }
        System.out.println("");
        System.out.println("Kädessä:");
        for (int i = 0; i < omatKortit.size(); i++) {
            System.out.println("\t" + i + ": " + omatKortit.get(i));
        }

        while (true) {
            System.out.print("Mikä pelataan (0-" + (omatKortit.size() - 1) + ", -1 = ota kortti)? ");
            int pelattava = Integer.parseInt(lukija.nextLine());

            if (pelattava < 0 || pelattava >= omatKortit.size()) {
                System.out.println("Valittiin ei mitään");
                return -1;
            }

            if (!omatKortit.get(pelattava).saaPelataKortin(paallimmaisin, vari)) {
                System.out.println("Korttia " + omatKortit.get(pelattava) + " ei saa pelata tässä.");
                continue;
            }

            return pelattava;
        }
    }

    @Override
    public String valitseVari(ArrayList<Kortti> omatKortit) {
        String[] okVarit = {"Punainen", "Vihreä", "Sininen", "Keltainen"};

        System.out.println("Värivaihtoehdot: ");
        for (int i = 0; i < okVarit.length; i++) {
            System.out.println("\t" + i + ": " + okVarit[i]);
        }

        System.out.print("Mikä väri valitaan (0-3)? ");
        return okVarit[Integer.parseInt(lukija.nextLine())];
    }

    @Override
    public String nimi() {
        return this.nimi;
    }

}
