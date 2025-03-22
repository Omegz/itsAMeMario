import java.util.ArrayList;
import java.util.List;

public class Bestilling {
    int ordreNr;
    List<Menu> varer;

    public Bestilling(int ordreNr) {
        this.ordreNr = ordreNr;
        this.varer = new ArrayList<>();
    }

    public void tilfojVare(Menu vare) {
        varer.add(vare); // ✅ Adds a selected pizza to the order
    }

    public int getOrdreNr() {
        return ordreNr;
    }

    public List<Menu> getVarer() {
        return varer;
    }



}
