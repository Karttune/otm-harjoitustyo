package pcrprimerdesignapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pcrprimerdesignapp.database.Database;
import pcrprimerdesignapp.domain.Templatesequence;

public class TemplatesequenceDao implements Dao<Templatesequence, Integer> {

    private Database database;

    public TemplatesequenceDao(Database database) {
        this.database = database;
    }

    /**
     * Metodi hakee tietokannasta yhden Templatesequence-luokan, joka haetaan
     * otsikkorivin perusteella.
     *
     * @param title Käyttäjän antama otsikkorivi, jolla Templatesequence
     * haetaan.
     *
     * @return palauttaa Templatesequence-luokan, joka on haettu tietokannasta.
     */
    public Templatesequence findOne(String title) throws SQLException, Exception {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Templatesequence WHERE title = ?");
        stmt.setObject(1, title);

        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            return null;
        }

        Templatesequence template = new Templatesequence();
        template.setId(rs.getInt("id"));
        template.setForwardPrimerId(rs.getInt("forwardprimer_id"));
        template.setReversePrimerId(rs.getInt("reverseprimer_id"));
        template.setSequenceTitle(rs.getString("title"));
        template.setTemplateSequence(rs.getString("sequence"));

        rs.close();
        stmt.close();
        connection.close();

        return template;
    }

    /**
     * Metodi määrittää, että tallennetaanko tietokantaan uusi
     * Templatesequence-luokka vai päivitetäänkö jo olemassaoleva luokka.
     *
     * @param templatesequence Käyttäjän antama Templatesequence-luokka.
     *
     * @return palauttaa joko save- tai update metodin.
     * @throws java.sql.SQLException
     */
    public Templatesequence saveOrUpdate(Templatesequence templatesequence) throws SQLException, Exception {

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id, title, sequence FROM Templatesequence WHERE sequence = ?"
                    + " COLLATE NOCASE");
            stmt.setString(1, templatesequence.getTemplateSequence());

            ResultSet result = stmt.executeQuery();

            conn.close();

            if (!result.next()) {
                return save(templatesequence);
            } else {
                return update(templatesequence);
            }
        }
    }

    /**
     * Metodi tallentaa tietokantaan parametrina annetun
     * Templatesequence-luokan.
     *
     * @param templatesequence Käyttäjän antama Templatesequence-luokka.
     *
     * @return palauttaa tallennetun Templatesequence-luokan.
     */
    private Templatesequence save(Templatesequence templatesequence) throws SQLException, Exception {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Templatesequence"
                + " (forwardprimer_id, reverseprimer_id, title, sequence)"
                + " VALUES (?, ?, ?, ?)");
        stmt.setInt(1, templatesequence.getForwardPrimerId());
        stmt.setInt(2, templatesequence.getReversePrimerId());
        stmt.setString(3, templatesequence.getSequenceTitle());
        stmt.setString(4, templatesequence.getTemplateSequence());

        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement("SELECT * FROM Templatesequence"
                + " WHERE title = ?"
                + " COLLATE NOCASE");
        stmt.setString(1, templatesequence.getSequenceTitle());

        ResultSet rs = stmt.executeQuery();
        rs.next();

        Templatesequence template = new Templatesequence();
        template.setId(rs.getInt("id"));
        template.setForwardPrimerId(rs.getInt("forwardprimer_id"));
        template.setReversePrimerId(rs.getInt("reverseprimer_id"));
        template.setSequenceTitle(rs.getString("title"));
        template.setTemplateSequence(rs.getString("sequence"));

        stmt.close();
        rs.close();
        conn.close();

        return template;
    }

    /**
     * Metodi päivittää tietokannassa jo olemassaolevan Templatesequence-luokan.
     *
     * @param templatesequence Käyttäjän antama Templatesequence-luokka.
     *
     * @return palauttaa tallennetun Templatesequence-luokan.
     */
    private Templatesequence update(Templatesequence templatesequence) throws SQLException, Exception {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE Templatesequence SET"
                + " sequence = ? WHERE id = ?");
        stmt.setString(1, templatesequence.getTemplateSequence());
        stmt.setInt(2, templatesequence.getId());

        stmt.executeUpdate();

        stmt.close();
        conn.close();

        return templatesequence;
    }

    /**
     * Metodi palauttaa kaikki tietokannassa olevat otsikkorivit, jotka
     * syötetään ui:ssa tietokantavalikkoon. * @return palauttaa listan
     * Templatesequence-otsikkoriveistä.
     * @throws java.sql.SQLException
     */
    public List<String> findAllTitles() throws SQLException, Exception {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Templatesequence");

        ResultSet rs = stmt.executeQuery();
        List<String> titles = new ArrayList<>();
        while (rs.next()) {

            titles.add(rs.getString("title"));
        }

        rs.close();
        stmt.close();
        conn.close();

        return titles;
    }

    /**
     * Metodi poistaa tietokannasta Templatesequence-luokan, joka haetaan
     * pääavaimen perusteella.
     *
     * @param key Käyttäjän antama avain.
     *
     */
    public void delete(Integer key) throws SQLException, Exception {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Templatesequence WHERE id = ?");
        stmt.setInt(1, key);

        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    public Templatesequence findOne(Integer key) throws SQLException, Exception {
        return null;
    }

}
