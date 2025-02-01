import java.io.Serializable;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L; // Для сериализации
    private String description;
    private double amount;
    private String type; // "income" или "expense"

    public Transaction(String description, double amount, String type) {
        this.description = description;
        this.amount = amount;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    // Геттеры и сеттеры
} 