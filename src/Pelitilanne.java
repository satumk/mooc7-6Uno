
import java.util.HashMap;

public class Pelitilanne {

    private String suunta;
    private int omaIndeksi;
    private int pelaajia;

    private HashMap<Integer, Integer> pelaajienPisteet;
    private HashMap<Integer, Integer> pelaajienKorttienLukumaarat;
    private HashMap<Integer, String> pelaajienViimeksiPelaamatVarit;

    public Pelitilanne(int pelaajia) {
        this.pelaajienPisteet = new HashMap<>();
        this.pelaajienKorttienLukumaarat = new HashMap<>();
        this.pelaajienViimeksiPelaamatVarit = new HashMap<>();
        this.pelaajia = pelaajia;
    }

    public String getSuunta() {
        return suunta;
    }

    public void asetaSuunta(String suunta) {
        this.suunta = suunta;
    }

    public int getOmaIndeksi() {
        return omaIndeksi;
    }

    public void setOmaIndeksi(int omaIndeksi) {
        this.omaIndeksi = omaIndeksi;
    }

    public HashMap<Integer, Integer> getPelaajienPisteet() {
        return pelaajienPisteet;
    }

    public void setPisteet(int pelaajanIndeksi, int pisteet) {
        this.pelaajienPisteet.put(pelaajanIndeksi, pisteet);
    }

    public HashMap<Integer, Integer> getPelaajienKorttienLukumaarat() {
        return new HashMap<>(pelaajienKorttienLukumaarat);
    }

    public void paivitaKorttienLukumaara(int pelaajanIndeksi, int kortteja) {
        this.pelaajienKorttienLukumaarat.put(pelaajanIndeksi, kortteja);
    }

    public HashMap<Integer, String> getPelaajienViimeksiPelaamatVarit() {
        return pelaajienViimeksiPelaamatVarit;
    }

    public void paivitaViimeksiPelattuVari(int pelaajanIndeksi, String vari) {
        this.pelaajienViimeksiPelaamatVarit.put(pelaajanIndeksi, vari);
    }

    // pelitilanteesta tehdään kopio, jotta tekoäly ei pelitilannetta muuttaessaan
    // vaikuta oikeaan pelitilanteeseen
    public Pelitilanne kopio() {
        Pelitilanne kopio = new Pelitilanne(this.pelaajia);
        kopio.suunta = this.suunta;
        kopio.omaIndeksi = this.omaIndeksi;

        for (int i = 0; i < this.pelaajia; i++) {
            kopio.pelaajienKorttienLukumaarat.put(i, this.pelaajienKorttienLukumaarat.getOrDefault(i, 0));
            kopio.pelaajienPisteet.put(i, this.pelaajienPisteet.getOrDefault(i, 0));
            kopio.pelaajienViimeksiPelaamatVarit.put(i, this.pelaajienViimeksiPelaamatVarit.getOrDefault(i, ""));
        }

        return kopio;
    }
}
