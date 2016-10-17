package fr.afcepf.atod.ws.currency.dao.test;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import fr.afcepf.atod.ws.currency.dao.api.ICurrencyDao;
import fr.afcepf.atod.ws.currency.dao.impl.CurrencyDao;
import fr.afcepf.atod.ws.currency.entity.Currency;
import fr.afcepf.atod.ws.currency.exception.CurrenciesWSError;
import fr.afcepf.atod.ws.currency.exception.CurrenciesWSException;

/**
 * Integration Test of method findAll from DaoCurrency.
 * @author nikko
 *
 */
@RunWith(Arquillian.class)
public class TestCurrencyDao {
    /**
     * Setup Arquillian on deploy.
     * @return a JavaArchive for Arquillian
     */
    @Deployment
    public static JavaArchive createDeploy() {
        JavaArchive jar = ShrinkWrap.create(
                JavaArchive.class, "wsCurrenciesDaoTest.jar");
        jar.addClass(Currency.class);
        jar.addClass(ICurrencyDao.class);
        jar.addClass(CurrencyDao.class);
        jar.addClass(CurrenciesWSException.class);
        jar.addClass(CurrenciesWSError.class);
        jar.addAsManifestResource("persistence-test.xml",
                ArchivePaths.create("persistence.xml"));
        jar.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        return jar;
    }
    /**
     * la definition du service à tester.
     */
    @EJB
    private ICurrencyDao dao;
    /**
     * wait timer.
     */
    private static final int MILLIS_TIMER = 1000;
    /**
     * an expected currency in bdd.
     */
    private Currency validCurrency1;
    /**
    * expected currency id.
    */
   private static final int ID_VALID_CURRENCY1 = 1;
   /**
    * expected currency rate.
    */
   private static final Double RATE_VALID_CURRENCY1 = 1D;
    /**
     * an expected currency in bdd.
     */
    private Currency validCurrency2;
    /**
     * expected currency id.
     */
    private static final int ID_VALID_CURRENCY2 = 2;
    /**
     * expected currency rate.
     */
    private static final Double RATE_VALID_CURRENCY2 = 1.2D;
    /**
     * an expected currency in bdd.
     */
    private Currency validCurrency3;
    /**
     * expected currency id.
     */
    private static final int ID_VALID_CURRENCY3 = 3;
    /**
     * expected currency rate.
     */
    private static final Double RATE_VALID_CURRENCY3 = 1.1D;
    /**
     * an unexpected currency in bdd.
     */
    private Currency invalidCurrency;
    /**
     * unexpected currency id.
     */
    private static final int ID_INVALID_CURRENCY = 4;
    /**
     * expected currency rate.
     */
    private static final Double RATE_INVALID_CURRENCY = 0.012D;
    /**
     * a test list.
     */
    private List<Currency> list = new ArrayList<Currency>();
    /**
     * Default Constructor.
     */
    public TestCurrencyDao() {
        validCurrency1 = new Currency(ID_VALID_CURRENCY1, "dollar",
                "USD", RATE_VALID_CURRENCY1);
        validCurrency2 = new Currency(ID_VALID_CURRENCY2, "livre sterling",
                "GBP", RATE_VALID_CURRENCY2);
        validCurrency3 = new Currency(ID_VALID_CURRENCY3, "euro",
                "EUR", RATE_VALID_CURRENCY3);
        invalidCurrency = new Currency(ID_INVALID_CURRENCY, "yen",
                "JPY", RATE_INVALID_CURRENCY);
    }
    /**
     * insert method success Test.
     * @throws CurrenciesWSException custom exception
     */
    @Test
    public void testInsertSuccess() throws CurrenciesWSException {
        dao.deleteAllCurrencies();
        Currency c = dao.insert(validCurrency1);
        Assert.assertNotNull(c);
        Assert.assertNotNull(c.getId());
        Assert.assertNotNull(c.getName());
        Assert.assertNotNull(c.getCode());
        Assert.assertNotNull(c.getRate());
        Assert.assertNotNull(c.getCreatedAt());
        Assert.assertNotNull(c.getUpdatedAt());
        Assert.assertEquals(c.getCreatedAt(), c.getUpdatedAt());
    }
    /**
     * update method success Test.
     * @throws CurrenciesWSException custom exception
     */
    @Test
    public void testUpdateSuccess() throws CurrenciesWSException {
        insertSampleRecords();
        validCurrency1.setName("toto");
        Currency c = dao.find(validCurrency1.getId());
        c.setName("toto");
        try {
            Thread.sleep(MILLIS_TIMER);
        } catch (InterruptedException paramE) {
            // TODO Auto-generated catch block
            paramE.printStackTrace();
        }
        Boolean ret = dao.update(c);
        Currency c2 = dao.find(validCurrency1.getId());
        Assert.assertEquals(true, ret);
        Assert.assertNotNull(c2);
        Assert.assertNotNull(c2.getId());
        Assert.assertNotNull(c2.getName());
        Assert.assertNotNull(c2.getCode());
        Assert.assertNotNull(c2.getRate());
        Assert.assertNotNull(c2.getCreatedAt());
        Assert.assertNotNull(c2.getUpdatedAt());
        Assert.assertNotEquals(c2.getCreatedAt(), c2.getUpdatedAt());
        Assert.assertEquals(c2.getId(), validCurrency1.getId());
        Assert.assertEquals(c2.getName(), validCurrency1.getName());
        Assert.assertEquals(c2.getCode(), validCurrency1.getCode());
        Assert.assertEquals(c2.getRate(), validCurrency1.getRate());
    }
    /**
     * delete method success Test.
     * @throws CurrenciesWSException custom exception
     */
    @Test(expected = CurrenciesWSException.class)
    public void testDeleteSuccess() throws CurrenciesWSException {
        insertSampleRecords();
        Currency c = dao.find(validCurrency1.getId());
        Boolean ret = dao.delete(c);
        Assert.assertEquals(true, ret);
        dao.find(validCurrency1.getId());
    }
    /**
     * find method success Test.
     * @throws CurrenciesWSException custom exception
     */
    @Test
    public void testFindSuccess() throws CurrenciesWSException {
        insertSampleRecords();
        Currency c = dao.find(validCurrency1.getId());
        Assert.assertNotNull(c);
        Assert.assertNotNull(c.getId());
        Assert.assertNotNull(c.getName());
        Assert.assertNotNull(c.getCode());
        Assert.assertNotNull(c.getRate());
        Assert.assertNotNull(c.getCreatedAt());
        Assert.assertNotNull(c.getUpdatedAt());
        Assert.assertEquals(c.getId(), validCurrency1.getId());
        Assert.assertEquals(c.getName(), validCurrency1.getName());
        Assert.assertEquals(c.getCode(), validCurrency1.getCode());
        Assert.assertEquals(c.getRate(), validCurrency1.getRate());
    }
    /**
     * find method failure Test.
     * @throws CurrenciesWSException custom exception
     */
    @Test(expected = CurrenciesWSException.class)
    public void testFindFailure() throws CurrenciesWSException {
        insertSampleRecords();
        dao.find(invalidCurrency.getId());
    }
    /**
     * succes case test with a valid list of result.
     * @throws CurrenciesWSException ne doit pas passer ici.
     */
    @Test
    public void testFindAllSuccess() throws CurrenciesWSException {
        insertSampleRecords();
        list.add(validCurrency1);
        list.add(validCurrency2);
        list.add(validCurrency3);
        List<Currency> l = dao.findAll();
        Assert.assertNotNull(l);
        for (int i = 0; i < list.size(); i++) {
            Assert.assertNotNull(l.get(i));
            Assert.assertNotNull(l.get(i).getId());
            Assert.assertNotNull(l.get(i).getName());
            Assert.assertNotNull(l.get(i).getCode());
            Assert.assertNotNull(l.get(i).getRate());
            Assert.assertEquals(list.get(i).getId(), l.get(i).getId());
            Assert.assertEquals(list.get(i).getName(), l.get(i).getName());
            Assert.assertEquals(list.get(i).getCode(), l.get(i).getCode());
            Assert.assertEquals(list.get(i).getRate(), l.get(i).getRate());
        }
    }
    /**
     * Failure test case 1.
     */
    @Test
    public void testFindAllEchec() {
        try {
            insertSampleRecords();
            list.add(validCurrency1);
            list.add(validCurrency2);
            list.add(invalidCurrency);
            List<Currency> l = dao.findAll();
            Assert.assertNotNull(l);
            for (int i = 0; i < list.size(); i++) {
                Assert.assertNotNull(l.get(i));
                Assert.assertNotNull(l.get(i).getId());
                Assert.assertNotNull(l.get(i).getName());
                Assert.assertNotNull(l.get(i).getCode());
                Assert.assertNotNull(l.get(i).getRate());
                if (i < 2) {
                    Assert.assertEquals(list.get(i).getId(), l.get(i).getId());
                    Assert.assertEquals(list.get(i).getName(),
                            l.get(i).getName());
                    Assert.assertEquals(list.get(i).getCode(),
                            l.get(i).getCode());
                    Assert.assertEquals(list.get(i).getRate(),
                            l.get(i).getRate());
                } else {
                    Assert.assertNotEquals(list.get(i).getId(),
                            l.get(i).getId());
                    Assert.assertNotEquals(list.get(i).getName(),
                            l.get(i).getName());
                    Assert.assertNotEquals(list.get(i).getCode(),
                            l.get(i).getCode());
                    Assert.assertNotEquals(list.get(i).getRate(),
                            l.get(i).getRate());
                }
            }
        } catch (CurrenciesWSException paramE) { }
    }
    /**
     * Failure test case 2.
     * @throws CurrenciesWSException custom exception
     */
    @Test(expected = CurrenciesWSException.class)
    public void testFindAllEchecE() throws CurrenciesWSException {
        dao.deleteAllCurrencies();
        dao.findAll();
    }
    /**
     * Loading test db.
     */
    public void insertSampleRecords() {
        // clear database
        System.out.println("Deleting records...");
        dao.deleteAllCurrencies();
        // insert records
        System.out.println("Inserting records...");
        validCurrency1 = new Currency(ID_VALID_CURRENCY1, "dollar",
                "USD", RATE_VALID_CURRENCY1);
        validCurrency2 = new Currency(ID_VALID_CURRENCY2, "livre sterling",
                "GBP", RATE_VALID_CURRENCY2);
        validCurrency3 = new Currency(ID_VALID_CURRENCY3, "euro",
                "EUR", RATE_VALID_CURRENCY3);
        try {
            dao.insert(validCurrency1);
            dao.insert(validCurrency2);
            dao.insert(validCurrency3);
        } catch (CurrenciesWSException paramE) {
            // TODO Auto-generated catch block
            paramE.printStackTrace();
        }
    }
}
