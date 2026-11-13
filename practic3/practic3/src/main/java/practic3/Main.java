package practic3;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введіть перше число:");
            double a = parseNumber(scanner.nextLine());

            System.out.println("Введіть друге число:");
            double b = parseNumber(scanner.nextLine());

            System.out.println("Введіть операцію (+, -, *, /):");
            String operation = scanner.nextLine().trim();

            double result;
            switch (operation) {
                case "+":
                    result = calculator.add(a, b);
                    break;
                case "-":
                    result = calculator.subtract(a, b);
                    break;
                case "*":
                    result = calculator.multiply(a, b);
                    break;
                case "/":
                    result = calculator.divide(a, b);
                    break;
                default:
                    throw new InvalidInputException("Невідома операція.");
            }

            System.out.println("Результат: " + result);
        } catch (NumberFormatException e) {
            System.out.println("Помилка: потрібно ввести число.");
        } catch (ArithmeticException | InvalidInputException e) {
            System.out.println("Помилка: " + e.getMessage());
        } finally {
            System.out.println("Обробку запиту завершено.");
        }
    }

    static double parseNumber(String text) {
        return Double.parseDouble(text.trim().replace(',', '.'));
    }
}