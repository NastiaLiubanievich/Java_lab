
package practic1;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Cart {

    private List<Product> products;

    public Cart() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public double getTotalPrice() {
        double total = 0;

        for (Product product : products) {
            total += product.getPrice();
        }

        return total;
    }

    public void clear() {
        products.clear();
    }

    @Override
    public String toString() {

        if (products.isEmpty()) {
            return "Кошик порожній!";
        }

        StringBuilder sb = new StringBuilder("Кошик містить:\n");

        for (Product product : products) {
            sb.append(product).append("\n");
        }

        sb.append("Загальна вартість: ")
                .append(getTotalPrice())
                .append(" грн");

        return sb.toString();
    }
}
