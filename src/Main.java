import java.util.Scanner;

public class Main {

    static String integerToRoman(int num) {

        String string = null;
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanLiterals = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder roman = new StringBuilder();
        if (num == 0) {
            string = "0";
        } else {
            for (int i = 0; i < values.length; i++) {
                while (num >= values[i]) {
                    num -= values[i];
                    roman.append(romanLiterals[i]);
                }
            }
            string = roman.toString();
        }
        return string;
    }

    static String[] romanToNumber(String[] values) {

        String[][] converttable = new String[2][10];
        converttable[0] = new String[]{"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        converttable[1] = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < converttable[0].length; j++) {

                if (values[i].equals(converttable[0][j])) {
                    values[i] = converttable[1][j];

                }
            }
        }

        return values;
    }

    static int checkSameType(String[] values) {
        int z = 0;
        String[][] converttable = new String[2][10];
        converttable[0] = new String[]{"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        converttable[1] = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < converttable[0].length; j++) {

                if (values[i].equals(converttable[0][j])) {
                    values[i] = converttable[1][j];
                    z++;
                }
            }
        }
        if (z == 1) {
            throw new IllegalStateException("Both numbers should be same type");
        }
        return z;
    }

    static String[] findDelim(String command) {
        String action = null;
        String delim = null;
        for (int i = 0; i < command.length(); i++) {
            char x = command.charAt(i);

            switch (x) {
                case '/':
                    action = "DIV";
                    delim = String.valueOf(x);
                    break;
                case '*':
                    action = "MULT";
                    delim = "\\" + String.valueOf(x);
                    break;
                case '+':
                    action = "ADD";
                    delim = "\\" + String.valueOf(x);
                    break;
                case '-':
                    action = "DEL";
                    delim = String.valueOf(x);
                    break;
                default:
                    break;
            }

        }
        if (action == null) {
            throw new IllegalStateException("Not supported action: missing +,-,/,*");
        }
        return new String[]{delim, action};
    }

    public static String calc(String input) {

        //Remove spaces, find delimiter and actiontype
        input = input.replaceAll("\\s", "");
        String[] operation = findDelim(input);
        String delim = operation[0];
        String action = operation[1];

        // String to array for futher analysis
        String[] values = input.split(delim);
        if (values.length > 2) throw new IllegalStateException("More then 2 numbers");

        //Check if numbers are same type
        int x = checkSameType(values);

        //Convert roman to numbers
        values = romanToNumber(values);

        //Calculate
        String result = null;
        int tmpresult = 0;
        int sum = 0;
        int tmp = 0;

        for (int i = 0; i < values.length; i++) {

            if (Integer.parseInt(values[i]) < 1 || Integer.parseInt(values[i]) > 10) {
                throw new IllegalStateException("Number is not in range: " + values[i] + " <--should between 1 and 10");
            }
            switch (action) {
                case "ADD":
                    sum = sum + Integer.parseInt(values[i]);
                    tmpresult = sum;
                    break;
                case "DEL":
                    if (i == 0) {
                        tmp = Integer.parseInt(values[i]);
                        continue;
                    } else {
                        tmpresult = tmp - Integer.parseInt(values[i]);
                        break;
                    }
                case "MULT":
                    if (i == 0) {
                        tmp = Integer.parseInt(values[i]);
                        continue;
                    } else {
                        tmpresult = tmp * Integer.parseInt(values[i]);
                        break;
                    }
                case "DIV":
                    if (i == 0) {
                        tmp = Integer.parseInt(values[i]);
                        continue;
                    } else {
                        tmpresult = tmp / Integer.parseInt(values[i]);
                        break;
                    }
                default:

            }

        }

        if (x == 0) {

            result = String.valueOf(tmpresult);

        } else if (x == 2 && tmpresult >= 0) {
            result = integerToRoman(tmpresult);
        } else if (x == 2 && tmpresult < 0) {
            throw new IllegalStateException("Roman result is  below 0");
        }
        return result;
    }


    public static void main(String[] args) throws IllegalStateException {
        //  Read console
        String answer;
        Scanner console = new Scanner(System.in);
        System.out.printf("Please insert command: ");
        String command = console.nextLine();

        /* Run calculator */
        answer = calc(command);

        //Print results

        System.out.println("Result: " + answer);

    }
}


