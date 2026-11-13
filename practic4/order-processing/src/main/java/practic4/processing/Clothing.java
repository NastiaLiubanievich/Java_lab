package practic4.processing;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Clothing extends Product {
    private final String size;

    @Builder
    public Clothing(String name, double price, String size) {
        super(name, price);
        this.size = size;
    }
}