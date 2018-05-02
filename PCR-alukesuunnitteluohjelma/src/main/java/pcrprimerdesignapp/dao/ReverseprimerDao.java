package pcrprimerdesignapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pcrprimerdesignapp.database.Database;
import pcrprimerdesignapp.domain.Forwardprimer;
import pcrprimerdesignapp.domain.Reverseprimer;

public class ReverseprimerDao implements Dao<Reverseprimer, Integer> {

    private Database database;

    public ReverseprimerDao(Database database) {
        this.database = database;
    }

    /**
     * Metodi hakee tietokannasta yhden Reverseprimer-luokan, joka haetaan id:n
     * perusteella.
     *
     * @param key Käyttäjän antama avain.
     *
     * @return palauttaa Reverseprimer-luokan, joka on haettu tietokannasta.
     */
    public Reverseprimer findOne(Integer key) throws SQLException, Exception {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Reverseprimer WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            return null;
        }

        Reverseprimer primer = new Reverseprimer();
        primer.setId(rs.getInt("id"));
        primer.setReversePrimer(rs.getString("sequence"));
        primer.setStart(rs.getInt("start"));

        rs.close();
        stmt.close();
        connection.close();

        return primer;
    }

    /**
     * Metodi määrittää, että tallennetaanko tietokantaan uusi
     * Reverseprimer-luokka vai päivitetäänkö jo olemassaoleva luokka.
     *
     * @param reverseprimer Käyttäjän antama Reverseprimer-luokka.
     *
     * @return palauttaa joko save- tai update metodin.
     */
    public Reverseprimer saveOrUpdate(Reverseprimer reverseprimer) throws SQLException, Exception {

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id, sequence FROM Reverseprimer WHERE sequence = ?"
                    + " COLLATE NOCASE");
            stmt.setString(1, reverseprimer.getReversePrimer());

            ResultSet rs = stmt.executeQuery();

            conn.close();

            if (!rs.next()) {
                return save(reverseprimer);
            } else {
                return update(reverseprimer);
            }
        }
    }

    /**
     * Metodi tallentaa tietokantaan parametrina annetun Reverseprimer-luokan.
     *
     * @param reverseprimer Käyttäjän antama Reverseprimer-luokka.
     *
     * @return palauttaa tallennetun Reverseprimer-luokan.
     */
    private Reverseprimer save(Reverseprimer reverseprimer) throws SQLException, Exception {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Reverseprimer"
                + " (sequence, start)"
                + " VALUES (?, ?)");
        stmt.setString(1, reverseprimer.getReversePrimer());
        stmt.setInt(2, reverseprimer.getStart());

        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement("SELECT * FROM Reverseprimer"
                + " WHERE sequence = ?"
                + " COLLATE NOCASE");
        stmt.setString(1, reverseprimer.getReversePrimer());

        ResultSet rs = stmt.executeQuery();
        rs.next();

        Reverseprimer primer = new Reverseprimer();
        primer.setId(rs.getInt("id"));
        primer.setReversePrimer(rs.getString("sequence"));
        primer.setId(rs.getInt("start"));

        stmt.close();
        rs.close();

        conn.close();

        return primer;
    }

    /**
     * Metodi päivittää tietokannassa jo olemassaolevan Reverseprimer-luokan.
     *
     * @param reverseprimer Käyttäjän antama Reverseprimer-luokka.
     *
     * @return palauttaa tallennetun Reverseprimer-luokan.
     */
    private Reverseprimer update(Reverseprimer reverseprimer) throws SQLException, Exception {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE Reverseprimer SET"
                + " sequence = ?, start = ? WHERE id = ?");
        stmt.setString(1, reverseprimer.getReversePrimer());
        stmt.setInt(2, reverseprimer.getStart());
        stmt.setInt(3, reverseprimer.getId());

        stmt.executeUpdate();

        stmt.close();
        conn.close();

        return reverseprimer;
    }

    /**
     * Metodi poistaa tietokannasta Reverseprimer-luokan, joka haetaan
     * pääavaimen perusteella.
     *
     * @param key Käyttäjän antama avain.
     *
     */
    public void delete(Integer key) throws SQLException, Exception {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Reverseprimer WHERE id = ?");
        stmt.setInt(1, key);

        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    /**
     * Metodi hakee tietokannasta Reverseprimer-luokan viimeisimmän id-luvun
     * lisättynä yhdellä, jota käytetään ui: luokassa uuden Reverseprimer-luokan
     * id:nä.
     *
     * @return palauttaa kokonaislukuna id:n.
     */
    public Integer returnNextIndex() throws SQLException, Exception {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT MAX(id) FROM Reverseprimer");

        ResultSet rs = stmt.executeQuery();

        rs.next();
        Integer i = rs.getInt("MAX(id)") + 1;

        rs.close();
        stmt.close();
        conn.close();

        return i;
    }
}
