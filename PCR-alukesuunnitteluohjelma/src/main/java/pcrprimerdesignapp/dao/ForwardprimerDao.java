package pcrprimerdesignapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pcrprimerdesignapp.database.Database;
import pcrprimerdesignapp.domain.Forwardprimer;

public class ForwardprimerDao implements Dao<Forwardprimer, Integer> {

    private Database database;

    public ForwardprimerDao(Database database) {
        this.database = database;
    }

    /**
     * Metodi hakee tietokannasta yhden Forwardprimer-luokan, joka haetaan id:n
     * perusteella.
     *
     * @param key Käyttäjän antama avain.
     *
     * @return palauttaa Forwardprimer-luokan, joka on haettu tietokannasta.
     */
    public Forwardprimer findOne(Integer key) throws SQLException, Exception {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Forwardprimer WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Forwardprimer primer = new Forwardprimer();
        primer.setId(rs.getInt("id"));
        primer.setForwardPrimer(rs.getString("sequence"));
        primer.setStart(rs.getInt("start"));

        rs.close();
        stmt.close();
        connection.close();

        return primer;
    }

    /**
     * Metodi määrittää, että tallennetaanko tietokantaan uusi
     * Forwardprimer-luokka vai päivitetäänkö jo olemassaoleva luokka.
     *
     * @param forwardprimer Käyttäjän antama Forwardprimer-luokka.
     *
     * @return palauttaa joko save- tai update metodin.
     */
    public Forwardprimer saveOrUpdate(Forwardprimer forwardprimer) throws SQLException, Exception {

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id, sequence FROM Forwardprimer WHERE sequence = ?"
                    + " COLLATE NOCASE");
            stmt.setString(1, forwardprimer.getForwardPrimer());

            ResultSet result = stmt.executeQuery();

            conn.close();

            if (!result.next()) {
                return save(forwardprimer);
            } else {
                return update(forwardprimer);
            }
        }
    }

    /**
     * Metodi tallentaa tietokantaan parametrina annetun Forwardprimer-luokan.
     *
     * @param forwardprimer Käyttäjän antama Forwardprimer-luokka.
     *
     * @return palauttaa tallennetun Forwardprimer-luokan.
     */
    private Forwardprimer save(Forwardprimer forwardprimer) throws SQLException, Exception {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Forwardprimer"
                + " (sequence, start)"
                + " VALUES (?, ?)");
        stmt.setString(1, forwardprimer.getForwardPrimer());
        stmt.setInt(2, forwardprimer.getStart());

        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement("SELECT * FROM Forwardprimer"
                + " WHERE sequence = ?"
                + " COLLATE NOCASE");
        stmt.setString(1, forwardprimer.getForwardPrimer());

        ResultSet rs = stmt.executeQuery();
        rs.next();

        Forwardprimer primer = new Forwardprimer();
        primer.setId(rs.getInt("id"));
        primer.setForwardPrimer(rs.getString("sequence"));
        primer.setStart(rs.getInt("start"));

        stmt.close();
        rs.close();

        conn.close();

        return primer;
    }

    /**
     * Metodi päivittää tietokannassa jo olemassaolevan Forwardsequence-luokan.
     *
     * @param forwardprimer Käyttäjän antama Forwardprimer-luokka.
     *
     * @return palauttaa tallennetun Forwardprimer-luokan.
     */
    private Forwardprimer update(Forwardprimer forwardprimer) throws SQLException, Exception {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE Forwardprimer SET"
                + " sequence = ?, start = ? WHERE id = ?");
        stmt.setString(1, forwardprimer.getForwardPrimer());
        stmt.setInt(2, forwardprimer.getStart());
        stmt.setInt(3, forwardprimer.getId());

        stmt.executeUpdate();

        stmt.close();
        conn.close();

        return forwardprimer;
    }

    /**
     * Metodi poistaa tietokannasta Forwardprimer-luokan, joka haetaan
     * pääavaimen perusteella.
     *
     * @param key Käyttäjän antama avain.
     *
     */
    public void delete(Integer key) throws SQLException, Exception {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Forwardprimer WHERE id = ?");
        stmt.setInt(1, key);

        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    /**
     * Metodi hakee tietokannasta Forwardprimer-luokan viimeisimmän id-luvun
     * lisättynä yhdellä, jota käytetään ui: luokassa uuden Forwardprimer-luokan
     * id:nä.
     *
     * @return palauttaa kokonaislukuna id:n.
     */
    public Integer returnNextIndex() throws SQLException, Exception {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT MAX(id) FROM Forwardprimer");

        ResultSet rs = stmt.executeQuery();

        rs.next();
        Integer i = rs.getInt("MAX(id)") + 1;

        rs.close();
        stmt.close();
        conn.close();

        return i;
    }
}
