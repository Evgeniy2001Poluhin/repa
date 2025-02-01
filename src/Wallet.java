// src/Wallet.java
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Wallet implements Serializable {
    private double balance;
    private List<Transaction> transactions;
    private Map<String, Double> budgets; // Хранение бюджетов по категориям

    public Wallet() {
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
        this.budgets = new HashMap<>(); // Инициализация бюджета
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        if (transaction.getType().equals("income")) {
            balance += transaction.getAmount();
        } else {
            balance -= transaction.getAmount();
        }
    }

    public void removeTransaction(int index) {
        if (index >= 0 && index < transactions.size()) {
            Transaction transaction = transactions.get(index);
            if (transaction.getType().equals("income")) {
                balance -= transaction.getAmount();
            } else {
                balance += transaction.getAmount();
            }
            transactions.remove(index);
        } else {
            System.out.println("Неверный индекс транзакции.");
        }
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setBudget(String category, double budget) {
        budgets.put(category, budget);
    }

    public Map<String, Double> getBudgets() {
        return budgets;
    }

    public double getBudget(String category) {
        return budgets.getOrDefault(category, -1.0); // Возвращает -1, если категория не найдена
    }
}