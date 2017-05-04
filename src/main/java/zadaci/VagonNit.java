package zadaci;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import model.Vagon;
import model.Voz;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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



    public VagonNit( Vagon vagon, String oznaka){
        this.vagon = vagon;
        this.oznaka = oznaka;

    }

    @Override
    public void run() {
        System.out.println(oznaka + " krece utovar vagona " + vagon);
        Random rand = new Random();
        Double teret = vagon.getTeret();

        do {
            try {
                this.sleep(rand.nextInt(2000));
                synchronized (vagon){
                    vagon.setTeret(vagon.getTeret() + 1);
                    System.out.println(oznaka + " tovari u vagon " + vagon.getOznaka() +
                            ". Tezina je sada " + vagon.getTeret());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (vagon.getTeret() <= vagon.getNosivost());

        System.out.println(oznaka + " : zavrsen utovar vagona " + vagon.getOznaka());
    }

    public static void main(String[] args) {
        ConnectionSource source = null;

        try {
            source = new JdbcConnectionSource("jdbc:sqlite:vozVagon.db");

            vozDao = DaoManager.createDao(source, Voz.class);
            vagonDao = DaoManager.createDao(source, Vagon.class);

            List<Voz> listaVozova = vozDao.queryForEq(Voz.POLJE_OZNAKA, "Voz1");
            List<VagonNit> listaVagonNiti = new ArrayList<VagonNit>();

            int i = 1;
            for (Voz voz: listaVozova) {
                ForeignCollection<Vagon> collection  = voz.getVagon();
                CloseableIterator<Vagon> iterator = collection.closeableIterator();

                while ((iterator.hasNext())){
                    Vagon vagon = iterator.next();
                    String oznaka = "Vagon " + i;
                    VagonNit nit = new VagonNit(vagon, oznaka);

                    listaVagonNiti.add(nit);
                    i++;
                }
            }

            for (int j = 0; j < listaVagonNiti.size() ; j++) {
                listaVagonNiti.get(j).start();
            }

            for (int j = 0; j < listaVagonNiti.size() ; j++) {
                try {
                    listaVagonNiti.get(j).join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            System.out.println("Svi vagoni su natovareni");

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
