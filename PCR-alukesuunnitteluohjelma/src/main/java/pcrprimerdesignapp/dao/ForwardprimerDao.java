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

    public Forwardprimer findOne(Integer key) throws SQLException, Exception {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Forwardprimer WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String sequence = rs.getString("sequence");

        Forwardprimer primer = new Forwardprimer();
        primer.setId(id);
        primer.setForwardPrimer(sequence);

        rs.close();
        stmt.close();
        connection.close();

        return primer;
    }

    public Forwardprimer saveOrUpdate(Forwardprimer forwardprimer) throws SQLException, Exception {

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id, sequence FROM Forwardprimer WHERE sequence = ?"
            + " COLLATE NOCASE");
            stmt.setString(1, forwardprimer.getForwardPrimer());

            ResultSet result = stmt.executeQuery();

            if (!result.next()) {
                return save(forwardprimer);
            } else {
                return update(forwardprimer);
            }
        }
    }

    private Forwardprimer save(Forwardprimer forwardprimer) throws SQLException, Exception {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Forwardprimer"
                + " (sequence)"
                + " VALUES (?)");
        stmt.setString(1, forwardprimer.getForwardPrimer());

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

        stmt.close();
        rs.close();

        conn.close();

        return primer;
    }

    private Forwardprimer update(Forwardprimer forwardprimer) throws SQLException, Exception {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE Forwardprimer SET"
                + " sequence = ? WHERE id = ?");
        stmt.setString(1, forwardprimer.getForwardPrimer());
        stmt.setInt(2, forwardprimer.getId());

        stmt.executeUpdate();

        stmt.close();
        conn.close();

        return forwardprimer;
    }

    @Override
    public List<Forwardprimer> findAll() throws SQLException, Exception {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Forwardprimer");

        ResultSet rs = stmt.executeQuery();
        List<Forwardprimer> primers = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String sequence = rs.getString("sequence");

            Forwardprimer primer = new Forwardprimer();
            primer.setId(id);
            primer.setForwardPrimer(sequence);
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
