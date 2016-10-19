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
    /**
     * REQFINDCURRENCYBYCODE.
     */
    private static final String REQFINDCURRENCYBYCODE = "SELECT c FROM"
            + " Currency c WHERE c.code = :code";
    /**
     * REQFINDALL.
     */
    private static final String REQFINDALL = "SELECT c FROM Currency c";
    /**
     * REQDELALL.
     */
    private static final String REQDELALL = "DELETE FROM Currency";
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
            throw new CurrenciesWSException("object creation failed",
                    CurrenciesWSError.IMPOSSIBLE_AJOUT_DANS_BASE);
        }
        return paramC;
    }

    @Override
    public Boolean update(Currency paramC) throws CurrenciesWSException {
        Boolean ret = false;
        if (paramC != null) {
            if (em.merge(paramC) != null) {
                ret = true;
            }
        }
        if (!ret) {
            throw new CurrenciesWSException("object update failed!",
                    CurrenciesWSError.UPDATE_NON_EFFECTUE_EN_BASE);
        }
        return ret;
    }

    @Override
    public Boolean delete(Currency paramC) throws CurrenciesWSException {
        Boolean ret = false;
        if (paramC != null) {
            em.remove(em.contains(paramC) ? paramC : em.merge(paramC));
            ret = true;
        }
        if (!ret) {
            throw new CurrenciesWSException("object removal failed",
                    CurrenciesWSError.IMPOSSIBLE_SUPPRESSION_DANS_BASE);
        }
        return ret;
    }

    @Override
    public Currency find(Integer paramId) throws CurrenciesWSException {
        Currency c = null;
        c = em.find(Currency.class, paramId);
        if (c == null) {
            throw new CurrenciesWSException("object not found!",
                    CurrenciesWSError.RECHERCHE_NON_PRESENTE_EN_BASE);
        }
        return c;
    }

    @Override
    public List<Currency> findAll() throws CurrenciesWSException {
        List<Currency> liste = new ArrayList<Currency>();
        liste = em.createQuery(REQFINDALL, Currency.class)
                .getResultList();
        if (liste.isEmpty()) {
            throw new CurrenciesWSException("objects not found!",
                    CurrenciesWSError.RECHERCHE_NON_PRESENTE_EN_BASE);
        }
        return liste;
    }

    @Override
    public Boolean deleteAllCurrencies() {
        em.createQuery(REQDELALL).executeUpdate();
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Currency findByCode(String paramCode) throws CurrenciesWSException {
        List<Currency> list = null;
        list = em.createQuery(REQFINDCURRENCYBYCODE)
                .setParameter("code", paramCode)
                .getResultList();
        if (list.isEmpty()) {
            throw new CurrenciesWSException("object not found!",
                    CurrenciesWSError.RECHERCHE_NON_PRESENTE_EN_BASE);
        }
        return list.get(0);
    }
}
