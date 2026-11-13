package practic5;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введіть рядок:");
            if (scanner.hasNextLine()) {
                TestAutomaton automaton = new TestAutomaton();
                System.out.println(automaton.recognize(scanner.nextLine()));
            }
        }
    }
}