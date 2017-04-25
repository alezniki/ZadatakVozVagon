package zadaci;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import model.Vagon;
import model.Voz;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by androiddevelopment on 25.4.17..
 */
public class Zadatak4BrisanjeVrednosti {

    static Dao<Voz,Integer> vozDao;
    static Dao<Vagon,Integer> vagonDao;

    public static void main(String[] args) {
        ConnectionSource source = null;

        try {
            source = new JdbcConnectionSource("jdbc:sqlite:vozVagon.db");

            vozDao = DaoManager.createDao(source, Voz.class);
            vagonDao = DaoManager.createDao(source, Vagon.class);

            // Sve vrednosti iz tabele vagon
            List<Vagon> listaVagona = vagonDao.queryForAll();
            System.out.println("Svi vagoni iz baze pre brisanja: ");
            for (Vagon vagon : listaVagona) System.out.println(vagon);

            Vagon vagonZaBrisanje = vagonDao.queryForEq(Vagon.POLJE_NOSIVOST, "10").get(0);
            vagonDao.delete(vagonZaBrisanje);

            listaVagona = vagonDao.queryForAll();
            System.out.println("Svi vagoni iz baze posle brisanja: ");
            for (Vagon vagon : listaVagona) System.out.println(vagon);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (source != null) {
                try {
                    source.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
