package pcrprimerdesignapp.tests;


import static org.junit.Assert.assertEquals;
import org.junit.Test;
import pcrprimerdesignapp.dao.ForwardprimerDao;
import pcrprimerdesignapp.database.Database;
import pcrprimerdesignapp.domain.Forwardprimer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Konsta
 */
public class ForwardprimerDaoTest {

    private Database database;
    private ForwardprimerDao forwardDao;

    public ForwardprimerDaoTest() throws ClassNotFoundException {
        database = new Database("jdbc:sqlite:test.db");
        forwardDao = new ForwardprimerDao(database, "Forwardprimer");
    }

    @Test
    public void findOneWorks() throws Exception {

        Forwardprimer fwd = forwardDao.findOne(5);

        assertEquals("CATCGATGCTAGCGATGCTA", fwd.getPrimer());
    }

    @Test
    public void updateWorks() throws Exception {

        Forwardprimer fwd = new Forwardprimer();
        fwd.setId(5);
        fwd.setPrimer("CATCGATGCTAGCGATGCTA");
        fwd.setStart(50);

        assertEquals("CATCGATGCTAGCGATGCTA", forwardDao.saveOrUpdate(fwd).getPrimer());
    }

    @Test
    public void saveWorks() throws Exception {

        Forwardprimer fwd = new Forwardprimer();
        fwd.setId(6);
        fwd.setPrimer("GTAGCTAGCTAGCTAGCTA");
        fwd.setStart(30);

        assertEquals("GTAGCTAGCTAGCTAGCTA", forwardDao.saveOrUpdate(fwd).getPrimer());
    }

    @Test
    public void deleteWorks() throws Exception {

        forwardDao.delete(6);
        assertEquals(null, forwardDao.findOne(6));
    }
}
