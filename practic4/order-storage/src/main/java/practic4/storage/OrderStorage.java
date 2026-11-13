package practic4.storage;

import practic4.processing.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderStorage<T extends Product> {
    private final List<T> orders = new ArrayList<>();

    public synchronized void save(T product) {
        orders.add(product);
    }

    public synchronized List<T> findAll() {
        return List.copyOf(orders);
    }
}