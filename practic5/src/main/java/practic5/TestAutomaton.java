package practic5;

public class TestAutomaton {
    public String recognize(String input) {
        String state = "S";

        for (int index = 0; index < input.length(); index++) {
            char symbol = input.charAt(index);

            switch (state) {
                case "S":
                    state = symbol == 'T' ? "1" : "S";
                    break;
                case "1":
                    if (symbol == 'E') {
                        state = "2";
                    } else {
                        state = symbol == 'T' ? "1" : "S";
                    }
                    break;
                case "2":
                    if (symbol == 'S') {
                        state = "3";
                    } else {
                        state = symbol == 'T' ? "1" : "S";
                    }
                    break;
                case "3":
                    state = symbol == 'T' ? "F" : "S";
                    break;
                case "F":
                    break;
            }
        }

        return state;
    }
}