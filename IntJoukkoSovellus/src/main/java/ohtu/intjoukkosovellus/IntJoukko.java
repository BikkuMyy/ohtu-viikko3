package ohtu.intjoukkosovellus;

public class IntJoukko {

    public final static int KAPASITEETTI = 5, // aloitustalukon koko
            OLETUSKASVATUS = 5;  // luotava uusi taulukko on 
    // näin paljon isompi kuin vanha
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] ljono;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden_määrä on nolla. 

    public IntJoukko() {
        ljono = new int[KAPASITEETTI];
        alkioidenLkm = 0;
        this.kasvatuskoko = OLETUSKASVATUS;
    }

    public IntJoukko(int kapasiteetti) {
        this();
        if (kapasiteetti > 0) {
            ljono = new int[kapasiteetti];
        }
    }

    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        this();
        if (kapasiteetti > 0) {
            ljono = new int[kapasiteetti];
        }
        if (kasvatuskoko > 0) {
            this.kasvatuskoko = kasvatuskoko;
        }

    }

    public boolean lisaa(int luku) {

        if (!kuuluu(luku)) {
            ljono[alkioidenLkm] = luku;
            alkioidenLkm++;

            if (alkioidenLkm % ljono.length == 0) {
                luoUusiTaulukko();
            }
            return true;
        }
        return false;
    }

    public boolean kuuluu(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == ljono[i]) {
                return true;
            }
        }
        return false;
    }

    public void luoUusiTaulukko() {
        int[] uusi = new int[alkioidenLkm + kasvatuskoko];
        for (int i = 0; i < alkioidenLkm; i++) {
            uusi[i] = ljono[i];
        }
        ljono = uusi;
    }

    public boolean poista(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == ljono[i]) {
                for (int j = i; j < alkioidenLkm - 1; j++) {
                    ljono[j] = ljono[j + 1];
                }
                alkioidenLkm--;
                return true;
            }
        }
        return false;
    }

    public int mahtavuus() {
        return alkioidenLkm;
    }

    @Override
    public String toString() {
        String tulostus = "{";

        if (alkioidenLkm > 0) {
            for (int i = 0; i < alkioidenLkm - 1; i++) {
                if (ljono[i] != 0) {
                    tulostus += ljono[i] + ", ";
                }
            }
            tulostus += ""+ljono[alkioidenLkm -1];
        }
        return tulostus += "}";
    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        for (int i = 0; i < taulu.length; i++) {
            taulu[i] = ljono[i];
        }
        return taulu;
    }

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko uusi = new IntJoukko();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < a.mahtavuus(); i++) {
            a.lisaa(bTaulu[i]);
        }
        return uusi;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko uusi = new IntJoukko();
        int[] taulu = yhdiste(a, b).toIntArray();
        
        for (int i = 0; i < taulu.length; i++) {
            int alkio = taulu[i];
            if (a.kuuluu(alkio) && b.kuuluu(alkio)){
                uusi.lisaa(alkio);
            }
        }
        return uusi;
    }

    public static IntJoukko erotus(IntJoukko a, IntJoukko b) {
        IntJoukko uusi = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            if (!b.kuuluu(aTaulu[i])){
              uusi.lisaa(aTaulu[i]);  
            }   
        }
        return uusi;
    }

}
