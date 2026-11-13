package practic3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorTest {
    private final Calculator calculator = new Calculator();

    @Test
    void calculatesCorrectly() throws InvalidInputException {
        assertEquals(7.0, calculator.add(3, 4));
        assertEquals(2.0, calculator.subtract(5, 3));
        assertEquals(12.0, calculator.multiply(3, 4));
        assertEquals(2.5, calculator.divide(5, 2));
    }

    @Test
    void rejectsDivisionByZero() {
        assertThrows(ArithmeticException.class, () -> calculator.divide(10, 0));
    }

    @Test
    void rejectsTextInsteadOfNumber() {
        assertThrows(NumberFormatException.class, () -> Main.parseNumber("abc"));
    }

    @Test
    void rejectsNaNWithCustomException() {
        assertThrows(InvalidInputException.class, () -> calculator.add(Double.NaN, 2));
    }
}