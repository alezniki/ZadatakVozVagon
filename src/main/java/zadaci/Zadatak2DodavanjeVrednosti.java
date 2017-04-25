package zadaci;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import model.Vagon;
import model.Voz;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by androiddevelopment on 25.4.17..
 */
public class Zadatak2DodavanjeVrednosti {

    static Dao<Voz,Integer> vozDao;
    static Dao<Vagon,Integer> vagonDao;

    public static void main(String[] args) {
        ConnectionSource source = null;

        try {
            source = new JdbcConnectionSource("jdbc:sqlite:vozVagon.db");

            vozDao = DaoManager.createDao(source, Voz.class);
            vagonDao = DaoManager.createDao(source, Vagon.class);

            TableUtils.clearTable(source, Voz.class);
            TableUtils.clearTable(source, Vagon.class);

            Voz voz1 = new Voz("Voz1", "Dizel");
            Voz voz2 = new Voz("Voz2", "Elektricni");

            vozDao.create(voz1);
            vozDao.create(voz2);

            Vagon vagon1 = new Vagon("Vagon1", "Za prenos goriva", 10); // voz1
            Vagon vagon2 = new Vagon("Vagon2", "Za prenos toksicnih materijala", 5); // voz1
            Vagon vagon3 = new Vagon("Vagon3", "Za prenos psenice", 20); // voz1
            Vagon vagon4 = new Vagon("Vagon4", "Za spavanje", 5); // voz2
            Vagon vagon5 = new Vagon("Vagon5", "Restoran", 3); // voz2

            vagon1.setVoz(voz1);
            vagonDao.create(vagon1);
            vagon2.setVoz(voz1);
            vagonDao.create(vagon2);
            vagon3.setVoz(voz1);
            vagonDao.create(vagon3);
            vagon4.setVoz(voz2);
            vagonDao.create(vagon4);
            vagon5.setVoz(voz2);
            vagonDao.create(vagon5);
            
            List<Voz> listaVozova = vozDao.queryForAll();
            System.out.println("Svi vozovi iz baze: ");
            for (Voz voz : listaVozova) System.out.println(voz);

            List<Vagon> listaVagona = vagonDao.queryForAll();
            System.out.println("Svi vagoni iz baze: ");
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
