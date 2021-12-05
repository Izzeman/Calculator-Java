import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class Calculator {

    public static void main(String[] args) {
        while (true) {
            try {
                String[] userInput = input(); // обработка строки ввода с консоли в список строк
                if (Check.spaceInOperation(userInput)) { // проверка ввода на вероятность ввода операции без пробелов
                    System.out.println("throws Exception // т.к. операнды введены без пробела");
                    break;
                } else if (Check.mathOperation(userInput)) { // проверка ввода на соответствие математической операции
                    System.out.println("throws Exception //т.к. строка не является математической операцией");
                    break;
                }else if (!Check.numberValues(userInput)){ // проверка ввода на наличие двух операндов
                    // и одного оператора
                    System.out.println("throws Exception //т.к. формат математической операции не удовлетворяет " +
                                        "заданию - два операнда и один оператор (+, -, /, *)");
                    break;
                }else if (!Check.operator(userInput)){ // проверка оператора на соответсвие условию -
                    // обработка операций +, -, /, *
                    System.out.println("throws Exception //т.к. формат математической операции не удовлетворяет " +
                            "заданию - два операнда и один оператор (+, -, /, *)");
                    break;
                } else if (Check.operand(userInput[0]) | Check.operand(userInput[2])){ // проверка операндов на
                    // соответсвие условию - использование только арабских или римских цифр от 1 до 10
                    System.out.println("throws Exception //т.к. формат математической операции не удовлетворяет " +
                                    "заданию - два операнда от 1 до 10 и один оператор (+, -, /, *)");
                    break;
                }else if (Check.arabicNum(userInput[0]) & Check.arabicNum(userInput[2])) { // проверка операндов на
                    // соответсвие условию - использование одновремено только арабские цифры
                    try {
                        int num1 = Arabic.stingToInt(userInput[0]);
                        int num2 = Arabic.stingToInt(userInput[2]);
                        String operand = userInput[1];
                        System.out.println(Arabic.resultOperation(num1, operand, num2));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } // выполнение математической операции и вывод результата
                } else if (Check.romanNum(userInput[0]) & Check.romanNum(userInput[2])) { //проверка операндов на
                    // соответсвие условию - использование одновремено только римских цифр
                    try {
                        int num1 = Roman.romanToArabicNum(userInput[0]);
                        int num2 = Roman.romanToArabicNum(userInput[2]); // перевод чисел из римской в арабскую
                        // систему исчисления
                        String operand = userInput[1];
                        if (Arabic.resultOperation(num1, operand, num2) > 0) {
                            int result = Arabic.resultOperation(num1, operand, num2); // выполнение математической
                            // операции и вывод результата при соблюдении условия - римские числа не могут быть нулём и
                            // отрицательным числом
                            System.out.println(Roman.arabicToRomanNum(result)); // перевод результата из арабской\
                            // системы в римксую и вывод на консоль
                        } else {
                            System.out.println("throws Exception //т.к. в римской системе нет нуля и отрицательных чисел");
                            break;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else { // вывод исключения при условии, что оба операнда из разных систем исчисления
                    System.out.println("throws Exception //т.к. используются одновременно разные системы исчисления");
                    break;
                }


            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
}

    static String[] input() throws IOException {
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        String input = userInput.readLine();
        return input.split(" ");
    }
}

class Check {
    private static final String[] symbolArray = {"+", "-", "*", "/"};
    private static final String[] arabicArray = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private static final String[] romanArray = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};

    public static boolean numberValues(String[] inputArray) {
        String[] array;
        array = inputArray;
        return array.length == 3;
    }

    public static boolean mathOperation(String[] inputArray) {
        String[] array;
        array = inputArray;
//        String[] mathArray = {"0", "1"};
        String inputString = String.join("", array);
        boolean containSymbol = false;
        if (array.length != 3) {
            for (String i : symbolArray)
                if (inputString.contains(i)) {
                    containSymbol = inputString.startsWith(i) | inputString.endsWith(i);
                    break;
                }
        }
        return containSymbol;
    }

    public static boolean operator(String[] inputArray) {
        String[] array;
        array = inputArray;
        return Arrays.asList(symbolArray).contains(array[1]);
    }

    public static boolean operand(String inputString) {
        return !(Arrays.asList(arabicArray).contains(inputString) || Arrays.asList(romanArray).contains(inputString));
    }

    public static boolean spaceInOperation(String[] inputString) {
        String[] array;
        array = inputString;
        String value = array[0];
        if (array.length == 1) {
            for (String i : symbolArray) {
                if (value.contains(i)) {
                    if (!value.startsWith(i) && !value.endsWith(i)) {
                        return true;
                    }
                }

            }
        }
        return false;
    }

        public static boolean arabicNum (String inputNum){
              return Arrays.asList(arabicArray).contains(inputNum);
        }

        public static boolean romanNum (String inputNum){
            return Arrays.asList(romanArray).contains(inputNum);
        }

    }

class Arabic {
    public static int stingToInt(String inputNum) {
        return Integer.parseInt(inputNum.trim());
    }

    public static int resultOperation(int num1, String operand, int num2) {
        int result = 0;
        switch (operand) {
            case "+" -> result = num1 + num2;
            case "-" -> result = num1 - num2;
            case "*" -> result = num1 * num2;
            case "/" -> result = num1 / num2;
            default -> System.out.println("throws Exception //т.к. формат математической операции не удовлетворяет " +
                    "заданию - два операнда и один оператор (+, -, /, *)");
        }
        return result;
    }


}

class Roman {
    private static final Map<Integer, String> arabicRomanMap;
    private static final Map<String, Integer> romanArabicMap;

    static {
        arabicRomanMap = Map.of(
                1, "I",
                4, "IV",
                5, "V",
                9, "IX",
                10, "X",
                40, "XL",
                50, "L",
                90, "XC",
                100, "C"
        );
    }
    static {
        romanArabicMap = Map.of(
                "I", 1,
                "II", 2,
                "III", 3,
                "IV", 4,
                "V", 5,
                "VI", 6,
                "VII", 7,
                "VIII", 8,
                "IX", 9,
                "X", 10
        );
    }

    public static String arabicToRomanNum(int arabicNum) {
        int num = arabicNum;
        StringBuilder romanNum = new StringBuilder();
        List<Integer> keysArabicRomanMap = new ArrayList<>(arabicRomanMap.keySet());
        keysArabicRomanMap.sort(Collections.reverseOrder());
        for (Integer key : keysArabicRomanMap) {
            while (num >= key) {
                num -= key;
                romanNum.append(arabicRomanMap.get(key));
            }
        }
        return romanNum.toString();
    }

    public static int romanToArabicNum(String RomanNum){
        return romanArabicMap.get(RomanNum);
    }
}
