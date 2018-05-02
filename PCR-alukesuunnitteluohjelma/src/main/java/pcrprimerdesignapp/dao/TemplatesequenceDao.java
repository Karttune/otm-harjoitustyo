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

    public Templatesequence findOne(Integer key) throws SQLException, Exception {
        return null;
    }

    public Templatesequence findOne(String title) throws SQLException, Exception {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Templatesequence WHERE title = ?");
        stmt.setObject(1, title);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
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

    public Templatesequence saveOrUpdate(Templatesequence templatesequence) throws SQLException, Exception {

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id, title, sequence FROM Templatesequence WHERE sequence = ?"
                    + " COLLATE NOCASE");
            stmt.setString(1, templatesequence.getTemplateSequence());

            ResultSet result = stmt.executeQuery();

            if (!result.next()) {
                return save(templatesequence);
            } else {
                return update(templatesequence);
            }
        }
    }

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

    @Override
    public List<Templatesequence> findAll() throws SQLException, Exception {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Templatesequence");

        ResultSet rs = stmt.executeQuery();
        List<Templatesequence> sequences = new ArrayList<>();
        while (rs.next()) {

            Templatesequence template = new Templatesequence();
            template.setId(rs.getInt("id"));
            template.setForwardPrimerId(rs.getInt("forwardprimer_id"));
            template.setReversePrimerId(rs.getInt("reverseprimer_id"));
            template.setSequenceTitle(rs.getString("title"));
            template.setTemplateSequence(rs.getString("sequence"));

            sequences.add(template);
        }

        rs.close();
        stmt.close();
        connection.close();

        return sequences;
    }

    public List<String> findAllTitles() throws SQLException, Exception {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Templatesequence");

        ResultSet rs = stmt.executeQuery();
        List<Templatesequence> sequences = new ArrayList<>();
        while (rs.next()) {

            Templatesequence template = new Templatesequence();
            template.setId(rs.getInt("id"));
            template.setForwardPrimerId(rs.getInt("forwardprimer_id"));
            template.setReversePrimerId(rs.getInt("reverseprimer_id"));
            template.setSequenceTitle(rs.getString("title"));
            template.setTemplateSequence(rs.getString("sequence"));

            sequences.add(template);
        }

        rs.close();
        stmt.close();
        connection.close();

        ArrayList<String> titles = new ArrayList<>();

        for (int i = 0; i < sequences.size(); i++) {
            titles.add(sequences.get(i).getSequenceTitle());
        }
        return titles;
    }

    public void delete(Integer key) throws SQLException, Exception {
    }
}
