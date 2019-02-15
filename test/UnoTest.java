
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class UnoTest {

    private String testitapauksetTiedosto = "testitapaukset.txt";
    private ArrayList<String> testirivit;
    private Tekoalypelaaja pelaaja;

    @Before
    public void setUp() {
        try {
            testirivit = Files.lines(Paths.get(testitapauksetTiedosto)).collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException ex) {
            Logger.getLogger(UnoTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        pelaaja = new Tekoalypelaaja();
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
            }
        }));
    }

    @Test
    @Points("07-06.1 07-06.2")
    public void onParametritonKonstruktori() {
        Reflex.reflect("Tekoalypelaaja").ctor().takingNoParams().requirePublic();
    }

    @Test
    @Points("07-06.1 07-06.2")
    public void testaaKadet() throws Exception {
        int testattujaKasia = 0;

        for (int i = 0; i < testirivit.size() / 5; i += 5) {
            String kasi = testirivit.get(i);
            String edellinenPelattuKortti = testirivit.get(i + 1);
            String valittuVari = testirivit.get(i + 2);

            String okValinnat = testirivit.get(i + 3);

            Kortti edellinen = lueKortti(edellinenPelattuKortti);
            ArrayList<Kortti> pelaajanKasi = lueKortit(kasi);

            ArrayList<Integer> okIndeksit = Arrays.stream(okValinnat.split(",")).map(s -> Integer.parseInt(s)).collect(Collectors.toCollection(ArrayList::new));

            testaa(pelaajanKasi, edellinen, valittuVari, okIndeksit);
            testattujaKasia++;

            if (testattujaKasia % 100 == 0) {
                System.out.println("\t" + testattujaKasia + " testattua kättä.");
            }
        }
    }

    private Kortti lueKortti(String kortti) {
        String[] palat = kortti.split(" ");
        String vari = palat[0].trim();
        String numero = palat[palat.length - 1].trim();

        String korttityyppi = "";
        for (int i = 1; i < palat.length - 1; i++) {
            korttityyppi += palat[i] + " ";
        }
        korttityyppi = korttityyppi.trim();

        return new Kortti(korttityyppi, vari, Integer.parseInt(numero));
    }

    private ArrayList<Kortti> lueKortit(String kasi) {
        ArrayList<Kortti> kortit = new ArrayList<>();
        Arrays.stream(kasi.split(",")).map(s -> lueKortti(s)).forEach(k -> kortit.add(k));
        return kortit;
    }

    private void testaa(ArrayList<Kortti> kasi, Kortti edellinen, String valittuVari, ArrayList<Integer> okIndeksit) throws Exception {

        int pelattuIndeksi = pelaaja.pelaa(kasi, edellinen, valittuVari, new Pelitilanne(1));

        if (!okIndeksit.contains(pelattuIndeksi)) {
            String virhe = "Metodi pelaa luokassa TekoalyPelaaja ei toiminut oikein.\n";
            virhe += "Kortit olivat:\n";
            for (int i = 0; i < kasi.size(); i++) {
                virhe += "  " + i + ". " + kasi.get(i) + "\n";
            }

            virhe += "Viimeksi pelattu oli: " + edellinen + "\n";

            if (edellinen.onVillikortti()) {
                virhe += "ja valittu väri oli: " + valittuVari + "\n";
            }

            virhe += "\nValitsit indeksin " + pelattuIndeksi + ", joka ei ollut sallittu.\nOlisit voinut pelata kortin.\n";

            fail(virhe);
        }

        if (pelattuIndeksi != -1 && kasi.get(pelattuIndeksi).onVillikortti()) {
            kasi.remove(pelattuIndeksi);
            String vari = pelaaja.valitseVari(kasi);

            List<String> okVarit = Arrays.asList("Punainen", "Vihreä", "Sininen", "Keltainen");
            if (okVarit.contains(vari)) {
                return;
            }

            String virhe = "Metodi valitseVari luokassa TekoalyPelaaja ei toiminut oikein.\n";
            virhe += "Kortit olivat:\n";
            for (int i = 0; i < kasi.size(); i++) {
                virhe += "  " + i + ". " + kasi.get(i) + "\n";
            }

            virhe += "Viimeksi pelattu oli: " + edellinen + "\n";

            if (edellinen.onVillikortti()) {
                virhe += "ja valittu väri oli: " + valittuVari + "\n";
            }

            virhe += "Valitsit virheellisen värin: " + vari + "\n";
            virhe += "Laillisia oli: " + okVarit.toString().replace("[", "").replace("]", "") + "\n";

            fail(virhe);
        }

    }
}
