import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Wallet {
    private double balance;
    private List<Transaction> transactions;

    public Wallet() {
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
        loadTransactions();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        if (transaction.getType().equals("income")) {
            balance += transaction.getAmount();
        } else {
            balance -= transaction.getAmount();
        }
        saveTransactions();
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
            saveTransactions();
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

    private void saveTransactions() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("transactions.dat"))) {
            oos.writeObject(transactions);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении транзакций: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadTransactions() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("transactions.dat"))) {
            transactions = (List<Transaction>) ois.readObject();
            for (Transaction transaction : transactions) {
                if (transaction.getType().equals("income")) {
                    balance += transaction.getAmount();
                } else {
                    balance -= transaction.getAmount();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка при загрузке транзакций: " + e.getMessage());
        }
    }

    // Другие методы для управления транзакциями
} 