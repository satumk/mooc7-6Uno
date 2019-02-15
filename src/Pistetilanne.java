
import java.util.ArrayList;

public class Pistetilanne {

    private ArrayList<Integer> pisteet;

    public Pistetilanne(int pelaajia) {
        this.pisteet = new ArrayList<>();
        for (int i = 0; i < pelaajia; i++) {
            this.pisteet.add(0);
        }
    }

    public int pelaajanPisteet(int indeksi) {
        return this.pisteet.get(indeksi);
    }

    public void lisaaPisteita(int pelaajanIndeksi, int paljonko) {
        this.pisteet.set(pelaajanIndeksi, this.pisteet.get(pelaajanIndeksi) + paljonko);
    }

    public void paivitaPisteet(ArrayList<Integer> pisteMuunnokset) {
        for (int i = 0; i < pisteMuunnokset.size(); i++) {
            lisaaPisteita(i, pisteMuunnokset.get(i));
        }
    }

    public boolean onkoVoittaja() {
        return this.pisteet.stream().filter(p -> p >= 500).count() > 0;
    }

    public int voittajanIndeksi() {
        for (int i = 0; i < this.pisteet.size(); i++) {
            if (this.pisteet.get(i) >= 500) {
                return i;
            }
        }

        return -1;
    }
}
