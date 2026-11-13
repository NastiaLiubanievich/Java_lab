
package practic1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Створення категорій
        Category electronics = new Category(1, "Електроніка");
        Category smartphones = new Category(2, "Смартфони");
        Category accessories = new Category(3, "Аксесуари");

        // Створення товарів
        Product product1 = new Product(
                1, "Ноутбук", 19999.99,
                "Високопродуктивний ноутбук для роботи та ігор", electronics
        );

        Product product2 = new Product(
                2, "Смартфон", 12999.50,
                "Смартфон з великим екраном та високою автономністю", smartphones
        );

        Product product3 = new Product(
                3, "Навушники", 2499.00,
                "Бездротові навушники з шумозаглушенням", accessories
        );

        // Список усіх товарів
        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);

        // Історія замовлень
        List<Order> orderHistory = new ArrayList<>();

        // Кошик
        Cart cart = new Cart();

        while (true) {

            System.out.println("\n===== ІНТЕРНЕТ-МАГАЗИН =====");
            System.out.println("1 - Переглянути список товарів");
            System.out.println("2 - Додати товар до кошика");
            System.out.println("3 - Переглянути кошик");
            System.out.println("4 - Видалити товар з кошика");
            System.out.println("5 - Зробити замовлення");
            System.out.println("6 - Історія замовлень");
            System.out.println("7 - Пошук товарів");
            System.out.println("0 - Вийти");

            System.out.print("Ваш вибір: ");
            int choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    System.out.println("\nСписок товарів:");
                    for (Product product : products) {
                        System.out.println(product);
                    }
                    break;

                case 2:
                    System.out.print("Введіть ID товару: ");
                    int addId = scanner.nextInt();

                    boolean added = false;

                    for (Product product : products) {
                        if (getProductId(product) == addId) {
                            cart.addProduct(product);
                            System.out.println("Товар додано до кошика!");
                            added = true;
                            break;
                        }
                    }

                    if (!added) {
                        System.out.println("Товар з таким ID не знайдено!");
                    }
                    break;

                case 3:
                    System.out.println("\nВаш кошик:");
                    System.out.println(cart);
                    break;

                case 4:
                    System.out.print("Введіть ID товару для видалення: ");
                    int removeId = scanner.nextInt();

                    boolean removed = false;

                    for (Product product : products) {
                        if (getProductId(product) == removeId) {
                            if (cart.getProducts().contains(product)) {
                                cart.removeProduct(product);
                                System.out.println("Товар видалено з кошика!");
                                removed = true;
                            }
                            break;
                        }
                    }

                    if (!removed) {
                        System.out.println("Такого товару немає у кошику!");
                    }
                    break;

                case 5:
                    if (cart.getProducts().isEmpty()) {
                        System.out.println("Кошик порожній!");
                    } else {
                        Order order = new Order(cart);

                        orderHistory.add(order);

                        System.out.println("\nЗамовлення оформлено!");
                        System.out.println(order);

                        cart.clear();
                    }
                    break;

                case 6:
                    System.out.println("\n===== ІСТОРІЯ ЗАМОВЛЕНЬ =====");

                    if (orderHistory.isEmpty()) {
                        System.out.println("Історія замовлень порожня.");
                    } else {
                        for (int i = 0; i < orderHistory.size(); i++) {
                            System.out.println("\nЗамовлення №" + (i + 1));
                            System.out.println(orderHistory.get(i));
                        }
                    }
                    break;

                case 7:
                    scanner.nextLine();

                    System.out.print(
                            "Введіть назву товару або категорію для пошуку: "
                    );

                    String search = scanner.nextLine().toLowerCase();

                    boolean found = false;

                    for (Product product : products) {

                        String name = product.toString().toLowerCase();

                        String category = "";

                        if (product == product1) {
                            category = electronics.toString().toLowerCase();
                        } else if (product == product2) {
                            category = smartphones.toString().toLowerCase();
                        } else if (product == product3) {
                            category = accessories.toString().toLowerCase();
                        }

                        if (name.contains(search) ||
                                category.contains(search)) {

                            System.out.println(product);
                            found = true;
                        }
                    }

                    if (!found) {
                        System.out.println("Товарів за вашим запитом не знайдено.");
                    }
                    break;

                case 0:
                    System.out.println(
                            "Дякуємо, що використовували наш магазин!"
                    );
                    scanner.close();
                    return;

                default:
                    System.out.println("Невідома опція. Спробуйте ще раз.");
                    break;
            }
        }
    }

    // Отримання ID товару
    private static int getProductId(Product product) {
        return product.getId();
    }
}
