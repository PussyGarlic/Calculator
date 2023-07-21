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
        System.out.println(switch (system) {
            case ARABIC -> result;
            case ROMAN -> toRoman(result);
        });
    }

    public static String toRoman(int result) {
        if (result == 100)
            return "C";
        var tens = switch (result /10){
            case 0 -> "";
            case 1 -> "X";
            case 2 -> "XX";
            case 3 -> "XXX";
            case 4 -> "XL";
            case 5 -> "L";
            case 6 -> "LX";
            case 7 -> "LXX";
            case 8 -> "LXXX";
            case 9 -> "XC";
            default -> throw new RuntimeException();
        };
        var digits = switch (result %10){
            case 0 -> "";
            case 1 -> "I";
            case 2 -> "II";
            case 3 -> "III";
            case 4 -> "IV";
            case 5 -> "V";
            case 6 -> "VI";
            case 7 -> "VII";
            case 8 -> "VIII";
            case 9 -> "IX";
            default -> throw new RuntimeException();
        };
        return tens + digits;
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