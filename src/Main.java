import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var s = new Scanner(System.in);
        while (true) calc(s.nextLine());
    }

    public static void calc(String input) {
        var inputs = input.split(" ");
        if (inputs.length != 3)
            throw new RuntimeException();
        var parseResultOne = parseInt(inputs[0]);
        var parseResultTwo = parseInt(inputs[2]);
        if (parseResultOne.system != parseResultTwo.system)
            throw new RuntimeException();
        var system = parseResultOne.system;
        var operandOne = parseResultOne.number;
        var operandTwo = parseResultTwo.number;
        if (operandOne < 1 || operandOne > 10 || operandTwo < 1 || operandTwo > 10)
            throw new RuntimeException();
        var result = switch (inputs[1]) {
            case "+" -> operandOne + operandTwo;
            case "-" -> operandOne - operandTwo;
            case "*" -> operandOne * operandTwo;
            case "/" -> operandOne / operandTwo;
            default -> throw new RuntimeException();
        };
        if (system == NumeralSystem.ROMAN && result < 1)
            throw new RuntimeException();
        System.out.println(result);
//        System.out.println(switch (system) {
//            case ARABIC -> result;
//            case ROMAN -> {
//
//            };
//        });
    }

    enum NumeralSystem { ARABIC, ROMAN }

    record ParseResult(NumeralSystem system, int number) {}

    public static ParseResult parseInt(String number) {
        if (number.startsWith("-"))
            throw new RuntimeException();
        var result = 0;
        NumeralSystem system;
        try {
            result = Integer.parseInt(number);
            system = NumeralSystem.ARABIC;
        } catch (NumberFormatException e) {
            result = parseRome(number);
            system = NumeralSystem.ROMAN;
        }
        return new ParseResult(system, result);
    }

    public static int parseRome(String s) {
        var result = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case 'I' -> result++;
                case 'V' -> result = 5 - result;
                case 'X' -> result = 10 - result;
                default -> throw new RuntimeException();
            }
        }
        return result;
    }
}