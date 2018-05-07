package pcrprimerdesignapp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import pcrprimerdesignapp.database.Database;
import pcrprimerdesignapp.domain.Forwardprimer;

public class ForwardprimerDao extends AbstractPrimerDao<Forwardprimer> {

    public ForwardprimerDao(Database database, String primerTable) {
        super(database, primerTable);
    }

    /**
     * Metodi toteuttaa abstraktin AbstractPrimerObject -luokan metodin, jonka
     * avulla se luo uuden Forwardprimer-luokan.
     *
     * @param rs AbstractPrimerObject -luokassa parametrina annettava ResultSet
     * -luokka.
     * @return palauttaa uuden Forwardprimer-luokan.
     * @throws java.sql.SQLException
     */
    @Override
    public Forwardprimer createFromRow(ResultSet rs) throws SQLException {
        Forwardprimer fwd = new Forwardprimer();
        fwd.setId(rs.getInt("id"));
        fwd.setPrimer(rs.getString("sequence"));
        fwd.setStart(rs.getInt("start"));
        return fwd;
    }
}
