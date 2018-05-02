package pcrprimerdesignapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public Reverseprimer findOne(Integer key) throws SQLException, Exception {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Reverseprimer WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
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

    public Reverseprimer saveOrUpdate(Reverseprimer reverseprimer) throws SQLException, Exception {

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id, sequence FROM Reverseprimer WHERE sequence = ?"
                    + " COLLATE NOCASE");
            stmt.setString(1, reverseprimer.getReversePrimer());

            ResultSet result = stmt.executeQuery();

            if (!result.next()) {
                return save(reverseprimer);
            } else {
                return update(reverseprimer);
            }
        }
    }

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

    @Override
    public List<Reverseprimer> findAll() throws SQLException, Exception {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Reverseprimer");

        ResultSet rs = stmt.executeQuery();
        List<Reverseprimer> primers = new ArrayList<>();
        while (rs.next()) {

            Reverseprimer primer = new Reverseprimer();
            primer.setId(rs.getInt("id"));
            primer.setReversePrimer(rs.getString("sequence"));
            primer.setStart(rs.getInt("start"));
            primers.add(primer);
        }

        rs.close();
        stmt.close();
        connection.close();

        return primers;
    }

    public void delete(Integer key) throws SQLException, Exception {
    }
}
