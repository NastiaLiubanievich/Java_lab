package practic4.threads;

import com.github.javafaker.Faker;
import practic4.processing.Clothing;
import practic4.processing.Electronics;
import practic4.processing.InvalidOrderException;
import practic4.processing.OrderProcessor;
import practic4.processing.Product;
import practic4.storage.OrderStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) {
        Faker faker = new Faker();

        List<Product> products = List.of(
                createClothing(faker, "M"),
                createElectronics(faker),
                createClothing(faker, "L"),
                createElectronics(faker),
                Clothing.builder()
                        .name("Товар для перевірки помилки")
                        .price(-100)
                        .size("S")
                        .build()
        );

        OrderStorage<Product> storage = new OrderStorage<>();

        try (ExecutorService workers = Executors.newFixedThreadPool(3)) {
            List<Future<Product>> tasks = new ArrayList<>();

            for (Product product : products) {
                OrderProcessor<Product> processor = new OrderProcessor<>(product);
                Callable<Product> process = processor::processOrder;

                tasks.add(workers.submit(() -> {
                    Product processed = process.call();
                    storage.save(processed);
                    return processed;
                }));
            }

            for (Future<Product> task : tasks) {
                try {
                    task.get();
                } catch (ExecutionException e) {
                    if (e.getCause() instanceof InvalidOrderException invalidOrder) {
                        System.out.println("Замовлення відхилено: " + invalidOrder.getMessage());
                    } else {
                        throw new IllegalStateException("Помилка обробки замовлення.", e.getCause());
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Обробку замовлень перервано.");
            return;
        }

        List<Product> saved = storage.findAll();
        System.out.println("\nУсього збережено замовлень: " + saved.size());
        System.out.println("Замовлення на електроніку:");

        saved.stream()
                .filter(product -> product instanceof Electronics)
                .map(Product::getName)
                .forEach(System.out::println);
    }

    private static Clothing createClothing(Faker faker, String size) {
        return Clothing.builder()
                .name(faker.commerce().productName())
                .price(faker.number().numberBetween(300, 2000))
                .size(size)
                .build();
    }

    private static Electronics createElectronics(Faker faker) {
        return Electronics.builder()
                .name(faker.commerce().productName())
                .price(faker.number().numberBetween(2000, 15000))
                .brand(faker.company().name())
                .build();
    }
}