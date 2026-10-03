import java.util.Map;
import java.util.Scanner;

public class CurrencyConverterApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CurrencyService service = new CurrencyService();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            displayMenu();
            int choice = readIntInput("Enter your choice: ");

            switch (choice) {
                case 1 -> handleInrToForeign();
                case 2 -> handleForeignToInr();
                case 3 -> handleAddCurrency();
                case 4 -> handleUpdateRate();
                case 5 -> handleDisplayAll();
                case 6 -> handleDeleteCurrency();
                case 7 -> {
                    System.out.println("\nThank you for using Currency Converter. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Error: Invalid option. Please select a number between 1 and 7.");
            }
            System.out.println();
        }
    }

    private static void displayMenu() {
        System.out.println("========== CURRENCY CONVERTER ==========");
        System.out.println("1. Convert INR to Foreign Currency");
        System.out.println("2. Convert Foreign Currency to INR");
        System.out.println("3. Add New Currency");
        System.out.println("4. Update Currency Rate");
        System.out.println("5. Display All Currency Rates");
        System.out.println("6. Delete Currency");
        System.out.println("7. Exit");
    }

    private static void handleInrToForeign() {
        double amount = readPositiveDoubleInput("Enter INR amount: ");
        String code = readNonEmptyStringInput("Enter currency code: ").toUpperCase();

        if (!service.currencyExists(code)) {
            System.out.println("Error: Currency code '" + code + "' is not supported.");
            return;
        }

        double converted = service.convertInrToForeign(amount, code);
        System.out.printf("Conversion Result: %.2f %s\n", converted, code);
    }

    private static void handleForeignToInr() {
        String code = readNonEmptyStringInput("Enter currency code: ").toUpperCase();

        if (!service.currencyExists(code)) {
            System.out.println("Error: Currency code '" + code + "' is not supported.");
            return;
        }

        double amount = readPositiveDoubleInput("Enter " + code + " amount: ");
        double converted = service.convertForeignToInr(amount, code);
        System.out.printf("Conversion Result: ₹%.2f\n", converted);
    }

    private static void handleAddCurrency() {
        String code = readNonEmptyStringInput("Enter currency code: ").toUpperCase();

        if (service.currencyExists(code)) {
            System.out.println("Error: Currency code '" + code + "' already exists.");
            return;
        }

        String name = readNonEmptyStringInput("Enter currency name: ");
        double rate = readPositiveDoubleInput("Enter INR conversion rate: ");

        if (service.addCurrency(code, name, rate)) {
            System.out.println("Currency " + code + " added successfully.");
        } else {
            System.out.println("Error: Failed to add currency.");
        }
    }

    private static void handleUpdateRate() {
        String code = readNonEmptyStringInput("Enter existing currency code: ").toUpperCase();

        if (!service.currencyExists(code)) {
            System.out.println("Error: Currency code '" + code + "' does not exist.");
            return;
        }

        double newRate = readPositiveDoubleInput("Enter new INR conversion rate: ");

        if (service.updateRate(code, newRate)) {
            System.out.println(code + " rate updated successfully.");
        } else {
            System.out.println("Error: Failed to update rate.");
        }
    }

    private static void handleDisplayAll() {
        Map<String, Currency> currencies = service.getAllCurrencies();
        if (currencies.isEmpty()) {
            System.out.println("No currencies found.");
            return;
        }

        System.out.println("\n--- Supported Currencies ---");
        System.out.printf("%-6s | %-20s | %s\n", "CODE", "CURRENCY NAME", "INR RATE");
        System.out.println("----------------------------------------");
        for (Currency c : currencies.values()) {
            System.out.println(c);
        }
    }

    private static void handleDeleteCurrency() {
        String code = readNonEmptyStringInput("Enter currency code to delete: ").toUpperCase();

        if (!service.currencyExists(code)) {
            System.out.println("Error: Currency code '" + code + "' does not exist.");
            return;
        }

        if (service.deleteCurrency(code)) {
            System.out.println("Currency " + code + " deleted successfully.");
        } else {
            System.out.println("Error: Failed to delete currency.");
        }
    }

    private static int readIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid numeric input. Please enter a valid whole number.");
            }
        }
    }

    private static double readPositiveDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (value > 0) return value;
                System.out.println("Error: Value must be greater than zero.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid numeric input. Please enter a valid number.");
            }
        }
    }

    private static String readNonEmptyStringInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("Error: Input cannot be blank.");
        }
    }
}