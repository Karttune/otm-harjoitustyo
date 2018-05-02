
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import pcrprimerdesignapp.dao.ForwardprimerDao;
import pcrprimerdesignapp.dao.ReverseprimerDao;
import pcrprimerdesignapp.database.Database;
import pcrprimerdesignapp.domain.Reverseprimer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Konsta
 */
public class ReverseprimerDaoTest {

    private Database database;
    private ReverseprimerDao reverseDao;

    public ReverseprimerDaoTest() throws ClassNotFoundException {
        database = new Database("jdbc:sqlite:test.db");
        reverseDao = new ReverseprimerDao(database);
    }

    @Test
    public void findOneWorks() throws Exception {

        Reverseprimer rev = reverseDao.findOne(5);

        assertEquals("GCTAGCTAGCTACTAGCTAC", rev.getReversePrimer());
    }

    @Test
    public void updateWorks() throws Exception {

        Reverseprimer rev = new Reverseprimer();
        rev.setId(5);
        rev.setReversePrimer("GCTAGCTAGCTACTAGCTAC");
        rev.setStart(200);

        assertEquals("GCTAGCTAGCTACTAGCTAC", reverseDao.saveOrUpdate(rev).getReversePrimer());
    }

    @Test
    public void saveWorks() throws Exception {

        Reverseprimer rev = new Reverseprimer();
        rev.setId(6);
        rev.setReversePrimer("GCATACGACCGATAGCT");
        rev.setStart(200);

        assertEquals("GCATACGACCGATAGCT", reverseDao.saveOrUpdate(rev).getReversePrimer());
    }

    @Test
    public void deleteWorks() throws Exception {

        reverseDao.delete(6);
        assertEquals(null, reverseDao.findOne(6));
    }
}
