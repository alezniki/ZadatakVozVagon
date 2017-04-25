package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by androiddevelopment on 25.4.17..
 */

@DatabaseTable(tableName = "vagon")
public class Vagon {

    public static final String POLJE_OZNAKA = "oznaka";
    public static final String POLJE_OPIS = "opis";
    public static final String POLJE_NOSIVOST = "nosivost";
    public static final String POLJE_TERET = "teret";

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = POLJE_OZNAKA, canBeNull = false)
    private String oznaka;
    @DatabaseField(columnName = POLJE_OPIS, canBeNull = false)
    private String opis;
    @DatabaseField(columnName = POLJE_NOSIVOST, canBeNull = false)
    private double nosivost;
    @DatabaseField(columnName = POLJE_TERET, canBeNull = false)
    private double teret = 0.0;

    // jedan vagon moze da bude zakacen za jedan voz
    @DatabaseField(foreign = true, foreignAutoRefresh = true, canBeNull = false)
    private Voz voz;

    public Vagon() {

    }

    public Vagon(String oznaka, String opis, double nosivost) {
        this.oznaka = oznaka;
        this.opis = opis;
        this.nosivost = nosivost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOznaka() {
        return oznaka;
    }

    public void setOznaka(String oznaka) {
        this.oznaka = oznaka;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public double getNosivost() {
        return nosivost;
    }

    public void setNosivost(double nosivost) {
        this.nosivost = nosivost;
    }

    public double getTeret() {
        return teret;
    }

    public void setTeret(double teret) {
        this.teret = teret;
    }

    public Voz getVoz() {
        return voz;
    }

    public void setVoz(Voz voz) {
        this.voz = voz;
    }


    @Override
    public String toString() {
        return "VAGON: " + "Id: " + id + ", Oznaka: " + oznaka + ", Opis: " + opis +
                ", Nosivost: " + nosivost + ", Teret: " + teret;
    }
}
