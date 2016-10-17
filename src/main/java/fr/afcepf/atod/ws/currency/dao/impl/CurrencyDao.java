package fr.afcepf.atod.ws.currency.dao.impl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import fr.afcepf.atod.ws.currency.dao.api.ICurrencyDao;
import fr.afcepf.atod.ws.currency.entity.Currency;
import fr.afcepf.atod.ws.currency.exception.CurrenciesWSError;
import fr.afcepf.atod.ws.currency.exception.CurrenciesWSException;

/**
 * Concrete implementation of Currency DAO.
 * @author nikko
 *
 */
@Stateless
public class CurrencyDao implements ICurrencyDao, Serializable {
    /*****************************************************
     * Requetes HQL.
     ****************************************************/
    private static final String REQFINDCURRENCYBYCODE = "SELECT c FROM Currency c WHERE c.code = :code";
    /**
     * setting injected entity manager..
     */
    @PersistenceContext(unitName = "OnWine-CurrenciesWS-Unit")
    private EntityManager em;
    /**
     * Serialization id.
     */
    private static final long serialVersionUID = 7851429551194737382L;

    @Override
    public Currency insert(Currency paramC) throws CurrenciesWSException {
        if (paramC != null) {
            em.persist(paramC);
        } else {
            throw new CurrenciesWSException(
                    CurrenciesWSError.IMPOSSIBLE_AJOUT_DANS_BASE,
                    "object creation failed");
        }
        return paramC;
    }

    @Override
    public Boolean update(Currency paramC) throws CurrenciesWSException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean remove(Currency paramC) throws CurrenciesWSException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Currency find(Integer paramId) throws CurrenciesWSException {
        Currency c = null;
        c = em.find(Currency.class, paramId);
        if (c == null) {
            throw new CurrenciesWSException(CurrenciesWSError.RECHERCHE_NON_PRESENTE_EN_BASE, "object not found!");
        }
        return c;
    }

    @Override
    public List<Currency> findAll() throws CurrenciesWSException {
        List<Currency> liste = new ArrayList<Currency>();
        liste = em.createNamedQuery("currency.findAll", Currency.class)
                .getResultList();
        if (liste.isEmpty()) {
            throw new CurrenciesWSException(
                    CurrenciesWSError.RECHERCHE_NON_PRESENTE_EN_BASE,
                    "object not found");
        }
        return liste;
    }

    @Override
    public Boolean deleteAllCurrencies() {
        em.createQuery("DELETE FROM Currency").executeUpdate();
        return true;
    }

    @Override
    public Currency findByCode(String paramCode) throws CurrenciesWSException {
        Currency c = null;
        c = (Currency) em.createQuery(REQFINDCURRENCYBYCODE)
                .setParameter("code", paramCode)
                .getSingleResult();
        if (c == null) {
            throw new CurrenciesWSException(CurrenciesWSError.RECHERCHE_NON_PRESENTE_EN_BASE, "object not found!");
        }
        return c;
    }
}
