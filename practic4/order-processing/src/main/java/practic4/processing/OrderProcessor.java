package practic4.processing;

public class OrderProcessor<T extends Product> {
    private final T product;

    public OrderProcessor(T product) {
        this.product = product;
    }

    public T processOrder() throws InvalidOrderException {
        if (product == null) {
            throw new InvalidOrderException("У замовленні немає товару.");
        }

        if (product.getName() == null || product.getName().isBlank()) {
            throw new InvalidOrderException("Вкажіть назву товару.");
        }

        if (!Double.isFinite(product.getPrice()) || product.getPrice() <= 0) {
            throw new InvalidOrderException("Ціна товару має бути скінченною і більшою за нуль.");
        }

        System.out.printf("[%s] Оброблено замовлення на %s%n",
                Thread.currentThread().getName(), product.getName());

        return product;
    }
}