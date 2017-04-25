package zadaci;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import model.Vagon;
import model.Voz;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by androiddevelopment on 25.4.17..
 */
public class Zadatak1KreiranjeTabela {
    public static void main(String[] args) {
        ConnectionSource source = null;

        try {
            source = new JdbcConnectionSource("jdbc:sqlite:vozVagon.db");

            TableUtils.dropTable(source, Vagon.class, true);
            TableUtils.dropTable(source, Voz.class, true);

            TableUtils.createTable(source, Voz.class);
            TableUtils.createTable(source, Vagon.class);

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
