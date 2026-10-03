import java.util.HashMap;
import java.util.Map;

public class CurrencyService {
    private final Map<String, Currency> currencies = new HashMap<>();

    public CurrencyService() {
        initializeSampleData();
    }

    // Initialize at least five sample currencies as required
    private void initializeSampleData() {
        addCurrencyDirect(new Currency("USD", "US Dollar", 83.00));
        addCurrencyDirect(new Currency("EUR", "Euro", 90.00));
        addCurrencyDirect(new Currency("GBP", "British Pound", 105.00));
        addCurrencyDirect(new Currency("AED", "UAE Dirham", 22.60));
        addCurrencyDirect(new Currency("JPY", "Japanese Yen", 0.55));
    }

    private void addCurrencyDirect(Currency currency) {
        currencies.put(currency.getCode(), currency);
    }

    public boolean currencyExists(String code) {
        return code != null && currencies.containsKey(code.toUpperCase());
    }

    public boolean addCurrency(String code, String name, double rate) {
        String upperCode = code.toUpperCase();
        if (currencies.containsKey(upperCode)) {
            return false;
        }
        currencies.put(upperCode, new Currency(upperCode, name, rate));
        return true;
    }

    
    public boolean deleteCurrency(String code) {
    String upperCode = code.toUpperCase();
    if (!currencies.containsKey(upperCode)) {
        return false;
    }
    currencies.remove(upperCode);
    return true;
    }

    public boolean updateRate(String code, double newRate) {
        String upperCode = code.toUpperCase();
        if (!currencies.containsKey(upperCode)) {
            return false;
        }
        currencies.get(upperCode).setRate(newRate);
        return true;
    }

    public Currency getCurrency(String code) {
        return currencies.get(code.toUpperCase());
    }

    public Map<String, Currency> getAllCurrencies() {
        return currencies;
    }

    public double convertInrToForeign(double inrAmount, String code) {
        Currency currency = getCurrency(code);
        if (currency == null || currency.getRate() <= 0) return -1;
        return inrAmount / currency.getRate();
    }

    public double convertForeignToInr(double foreignAmount, String code) {
        Currency currency = getCurrency(code);
        if (currency == null) return -1;
        return foreignAmount * currency.getRate();
    }

    public static class Currency {
        private final String code;
        private final String name;
        private double rate;

        public Currency(String code, String name, double rate) {
            this.code = code;
            this.name = name;
            this.rate = rate;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }
    }
}