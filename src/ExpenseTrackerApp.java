import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExpenseTrackerApp {
    private Wallet wallet;
    private Scanner scanner;
    private List<User> users = new ArrayList<>();
    private User currentUser;

    public ExpenseTrackerApp() {
        wallet = new Wallet();
        scanner = new Scanner(System.in);
    }

    public void start() {
        int choice;
        do {
            System.out.println("1. Добавить доход");
            System.out.println("2. Добавить расход");
            System.out.println("3. Показать баланс");
            System.out.println("4. Показать все транзакции");
            System.out.println("5. Удалить транзакцию");
            System.out.println("6. Выход");
            System.out.print("Выберите опцию: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера

            switch (choice) {
                case 1:
                    addTransaction("income");
                    break;
                case 2:
                    addTransaction("expense");
                    break;
                case 3:
                    showBalance();
                    break;
                case 4:
                    showTransactions();
                    break;
                case 5:
                    removeTransaction();
                    break;
                case 6:
                    System.out.println("Выход из приложения.");
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        } while (choice != 6);
    }

    private void addTransaction(String type) {
        System.out.print("Введите описание: ");
        String description = scanner.nextLine();
        double amount;
        do {
            System.out.print("Введите сумму: ");
            amount = scanner.nextDouble();
            if (amount <= 0) {
                System.out.println("Сумма должна быть положительной. Попробуйте снова.");
            }
        } while (amount <= 0);
        scanner.nextLine(); // Очистка буфера

        Transaction transaction = new Transaction(description, amount, type);
        wallet.addTransaction(transaction);
        System.out.println("Транзакция добавлена.");
    }

    private void showBalance() {
        System.out.println("Текущий баланс: " + wallet.getBalance());
    }

    private void showTransactions() {
        System.out.println("Список транзакций:");
        for (int i = 0; i < wallet.getTransactions().size(); i++) {
            Transaction t = wallet.getTransactions().get(i);
            System.out.println(i + ": " + t.getType() + " - " + t.getDescription() + " - " + t.getAmount());
        }
    }

    private void removeTransaction() {
        showTransactions();
        System.out.print("Введите индекс транзакции для удаления: ");
        int index = scanner.nextInt();
        wallet.removeTransaction(index);
        System.out.println("Транзакция удалена.");
    }

    private void registerUser() {
        System.out.print("Введите имя пользователя: ");
        String username = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        users.add(new User(username, password));
        System.out.println("Пользователь зарегистрирован.");
    }

    private boolean loginUser() {
        System.out.print("Введите имя пользователя: ");
        String username = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                return true;
            }
        }
        System.out.println("Неверное имя пользователя или пароль.");
        return false;
    }

    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.dat"))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении пользователей: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("users.dat"))) {
            users = (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка при загрузке пользователей: " + e.getMessage());
        }
    }

    // Другие методы для управления приложением
} 