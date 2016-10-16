package fr.afcepf.atod.ws.currency.dao.api;
import java.util.List;
import javax.ejb.Local;
import fr.afcepf.atod.ws.currency.entity.Currency;
import fr.afcepf.atod.ws.currency.exception.CurrenciesWSException;

/**
 * API of the currency DAO.
 * @author nikko
 *
 */
@Local
public interface ICurrencyDao {
    /**
     * Create method for {@link DTCurrency} objects.
     * @param c the currency object to persist
     * @return Currency the currency object persisted
     * @throws CurrenciesWSException custom exception
     */
    Currency insert(Currency c)  throws CurrenciesWSException;
    /**
    * Update method for {@link DTCurrency} objects.
    * @param c the currency object to update
    * @return Boolean return the result of the currency object update
    * @throws CurrenciesWSException custom exception
    */
    Boolean update(Currency c)   throws CurrenciesWSException;
    /**
     * delete method for {@link DTCurrency} objects.
     * @param c the currency object to delete
     * @return Boolean return the result of the currency object deletion
     * @throws CurrenciesWSException custom exception
     */
    Boolean remove(Currency c)   throws CurrenciesWSException;
    /**
     * Retrieve method for a {@link DTCurrency} object.
     * @param id The Integer identifier of the {@link DTCurrency}
     * @return a {@link DTCurrency} object
     * @throws CurrenciesWSException custom exception
     */
    Currency find(Integer id)    throws CurrenciesWSException;
    /**
     * Retrieve method for all {@link DTCurrency} objects.
     * @return a List of {@link DTCurrency} objects
     * @throws CurrenciesWSException custom exception
     */
    List<Currency> findAll()     throws CurrenciesWSException;
    /**
     * Delete method for all {@link DTCurrency} objects. Test Purpose.
     * @return Boolean did it work?
     * @throws CurrenciesWSException custom exception
     */
    Boolean deleteAllCurrencies();
}
