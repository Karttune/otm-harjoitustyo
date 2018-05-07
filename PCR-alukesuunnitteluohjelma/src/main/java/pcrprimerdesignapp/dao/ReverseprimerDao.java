package pcrprimerdesignapp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import pcrprimerdesignapp.database.Database;
import pcrprimerdesignapp.domain.Reverseprimer;

public class ReverseprimerDao extends AbstractPrimerDao<Reverseprimer> {

    public ReverseprimerDao(Database database, String primerTable) {
        super(database, primerTable);
    }

    /**
     * Metodi toteuttaa abstraktin AbstractPrimerObject -luokan metodin, jonka
     * avulla se luo uuden Reverseprimer-luokan.
     *
     * @param rs AbstractPrimerObject -luokassa parametrina annettava ResultSet
     * -luokka.
     * @return palauttaa uuden Reverseprimer-luokan.
     * @throws java.sql.SQLException
     */
    @Override
    public Reverseprimer createFromRow(ResultSet rs) throws SQLException {
        Reverseprimer rev = new Reverseprimer();
        rev.setId(rs.getInt("id"));
        rev.setPrimer(rs.getString("sequence"));
        rev.setStart(rs.getInt("start"));
        return rev;
    }
}
