
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import pcrprimerdesignapp.dao.TemplatesequenceDao;
import pcrprimerdesignapp.database.Database;
import pcrprimerdesignapp.domain.Templatesequence;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Konsta
 */
public class TemplatesequenceDaoTest {

    private Database database;
    private TemplatesequenceDao templateDao;

    public TemplatesequenceDaoTest() throws ClassNotFoundException {
        database = new Database("jdbc:sqlite:test.db");
        templateDao = new TemplatesequenceDao(database);
    }

    @Test
    public void findOneWorks() throws Exception {

        Templatesequence temp = templateDao.findOne("testi5");

        assertEquals("catctagctacgatcgatcgatcgcatcgtagctactagctactagctagcatcgatgctagcgatgctacgatcgatcgatcgatgcatcgatcgatcgatcgatcgatcgatgcgactagctagctgactgatcg", temp.getTemplateSequence());
    }

    @Test
    public void updateWorks() throws Exception {

        Templatesequence temp = new Templatesequence();
        temp.setSequenceTitle("testi5");
        temp.setId(5);
        temp.setTemplateSequence("catctagctacgatcgatcgatcgcatcgtagctactagctactagctagcatcgatgctagcgatgctacgatcgatcgatcgatgcatcgatcgatcgatcgatcgatcgatgcgactagctagctgactgatcg");

        assertEquals("catctagctacgatcgatcgatcgcatcgtagctactagctactagctagcatcgatgctagcgatgctacgatcgatcgatcgatgcatcgatcgatcgatcgatcgatcgatgcgactagctagctgactgatcg", templateDao.saveOrUpdate(temp).getTemplateSequence());
    }

    @Test
    public void saveWorks() throws Exception {

        Templatesequence temp = new Templatesequence();
        temp.setId(6);
        temp.setForwardPrimerId(6);
        temp.setReversePrimerId(6);
        temp.setSequenceTitle("testiUUSI");
        temp.setTemplateSequence("gcacgatcgatcgatcactct");

        assertEquals("gcacgatcgatcgatcactct", templateDao.saveOrUpdate(temp).getTemplateSequence());
    }

    @Test
    public void deleteWorks() throws Exception {

        templateDao.delete(6);
        assertEquals(null, templateDao.findOne("testiUUSI"));

    }
}
