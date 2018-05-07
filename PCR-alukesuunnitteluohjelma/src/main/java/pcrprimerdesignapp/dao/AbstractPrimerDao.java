/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcrprimerdesignapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import pcrprimerdesignapp.database.Database;
import pcrprimerdesignapp.domain.AbstractPrimerObject;

/**
 *
 * @author Konsta
 * @param <T>
 */
public abstract class AbstractPrimerDao<T extends AbstractPrimerObject>
        implements Dao<T, Integer> {

    private Database database;
    private String primerTable;

    public AbstractPrimerDao(Database database, String primerTable) {
        this.database = database;
        this.primerTable = primerTable;
    }

    /**
     * Metodi hakee tietokannasta yhden primer-luokan, joka haetaan id:n
     * perusteella.
     *
     * @param key Käyttäjän antama avain.
     *
     * @return palauttaa primer-luokan, joka on haettu tietokannasta.
     * @throws java.sql.SQLException
     */
    @Override
    public T findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM " + primerTable + " WHERE id = ?");
        stmt.setObject(1, key);

        try (ResultSet rs = stmt.executeQuery()) {
            rs.next();
            return createFromRow(rs);

        } catch (SQLException e) {
            System.err.println("Error when looking for a row in " + primerTable + " with id " + key);
            return null;
        }
    }

    /**
     * Metodi määrittää, että tallennetaanko tietokantaan uusi primer-luokka vai
     * päivitetäänkö jo olemassaoleva luokka.
     *
     * @param primer
     *
     * @return palauttaa joko save- tai update metodin.
     * @throws java.sql.SQLException
     */
    @Override
    public T saveOrUpdate(T primer) throws SQLException {

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id, sequence FROM " + primerTable
                    + " WHERE sequence = ?"
                    + " COLLATE NOCASE");
            stmt.setString(1, primer.getPrimer());

            ResultSet result = stmt.executeQuery();

            conn.close();

            if (!result.next()) {
                return save(primer);
            } else {
                return update(primer);
            }
        }
    }

    /**
     * Metodi tallentaa tietokantaan parametrina annetun primer-luokan.
     *
     * @param primer Käyttäjän antama primer-luokka.
     * @return palauttaa tallennetun primer-luokan.
     */
    private T save(T primer) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + primerTable
                + " (sequence, start)"
                + " VALUES (?, ?)");
        stmt.setString(1, primer.getPrimer());
        stmt.setInt(2, primer.getStart());

        stmt.executeUpdate();

        stmt = conn.prepareStatement("SELECT * FROM " + primerTable
                + " WHERE sequence = ?"
                + " COLLATE NOCASE");
        stmt.setString(1, primer.getPrimer());

        try (ResultSet rs = stmt.executeQuery()) {
            if (!rs.next()) {
                return null;
            }

            return createFromRow(rs);
        }
    }

    /**
     * Metodi päivittää tietokannassa jo olemassaolevan primer-luokan.
     *
     * @param primer Käyttäjän antama primer-luokka.
     * @return palauttaa tallennetun primer-luokan.
     */
    private T update(T primer) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE " + primerTable + " SET"
                + " sequence = ?, start = ? WHERE id = ?");
        stmt.setString(1, primer.getPrimer());
        stmt.setInt(2, primer.getStart());
        stmt.setInt(3, primer.getId());

        stmt.executeUpdate();

        stmt = conn.prepareStatement("SELECT * FROM " + primerTable
                + " WHERE sequence = ?"
                + " COLLATE NOCASE");
        stmt.setString(1, primer.getPrimer());

        try (ResultSet rs = stmt.executeQuery()) {
            if (!rs.next()) {
                return null;
            }

            return createFromRow(rs);
        }
    }

    /**
     * Metodi poistaa tietokannasta primer-luokan, joka haetaan pääavaimen
     * perusteella.
     *
     * @param key Käyttäjän antama avain.
     * @throws java.sql.SQLException
     */
    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + primerTable + " WHERE id = ?");
        stmt.setInt(1, key);

        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    /**
     * Metodi hakee tietokannasta primer-luokan viimeisimmän id-luvun lisättynä
     * yhdellä, jota käytetään UI-luokassa uuden primer-luokan id:nä.
     *
     * @return palauttaa kokonaislukuna id:n.
     * @throws java.sql.SQLException
     */
    public Integer returnNextIndex() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT MAX(id) FROM " + primerTable);

        ResultSet rs = stmt.executeQuery();

        rs.next();
        Integer i = rs.getInt("MAX(id)") + 1;

        rs.close();
        stmt.close();
        conn.close();

        return i;
    }

    /**
     * Metodin avulla luodaan uusi primer-luokka.
     *
     * @throws java.sql.SQLException
     */
    public abstract T createFromRow(ResultSet resultSet) throws SQLException;
}
