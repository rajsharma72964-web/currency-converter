public class Currency {
    private String code;
    private String name;
    private double rate; // Rate in INR per 1 unit of foreign currency

    public Currency(String code, String name, double rate) {
        this.code = code.toUpperCase();
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

    @Override
    public String toString() {
        return String.format("%-6s | %-20s | ₹%.2f", code, name, rate);
    }
}