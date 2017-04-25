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
import java.util.Random;

/**
 * Created by androiddevelopment on 25.4.17..
 */
public class VagonNit extends Thread{


    static Dao<Voz,Integer> vozDao;
    static Dao<Vagon,Integer> vagonDao;


    private Vagon vagon;
    private String oznaka;



    public VagonNit(String oznaka, Vagon vagon){
        this.oznaka = oznaka;
        this.vagon = vagon;
    }


    @Override
    public void run() {
        Double teret = vagon.getTeret();
        System.out.println(oznaka + " krece utovar vagona " + vagon.getOpis());

        do {
            synchronized (teret){
                teret+=1;
                System.out.println(oznaka + " tovari u vagon " + vagon.getOpis() + ". Tezina je sada " + teret);

                Random rand = new Random();
                try {

                    this.sleep(rand.nextInt(2000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(oznaka + ": zavrsen utovar vagona " + vagon.getOpis() + ". Tezina je sada " + teret);
            }
        } while (vagon.getTeret() <= vagon.getNosivost());
    }

    public static void main(String[] args) {
        ConnectionSource source = null;

        try {
            source = new JdbcConnectionSource("jdbc:sqlite:vozVagon.db");

            vozDao = DaoManager.createDao(source, Voz.class);
            vagonDao = DaoManager.createDao(source, Vagon.class);

            List<Vagon> listaVagona = vagonDao.queryForAll();

            Voz voz1 = vozDao.queryForAll().get(0);


            VagonNit nit1 = new VagonNit("Vagon 1", listaVagona.get(0));
            VagonNit nit2 = new VagonNit("Vagon 2", listaVagona.get(1));
            VagonNit nit3 = new VagonNit("Vagon 3", listaVagona.get(2));

            nit1.start();
            nit2.start();
            nit3.start();

            try {
                nit1.join();
                nit2.join();
                nit3.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Svi vagoni natovareni");

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
