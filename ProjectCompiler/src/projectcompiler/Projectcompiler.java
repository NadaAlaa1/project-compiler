package projectcompiler;

import java.util.Arrays;
import java.util.Scanner;
public class Projectcompiler {
     private static String input;
    private static int position;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose an option:");
        System.out.println("1. Arithmetic Expression Syntax Analysis");
        System.out.println("2. Tokenizer");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                performArithmeticExpressionAnalysis();
                break;
            case 2:
                performTokenization();
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }

    private static void performArithmeticExpressionAnalysis() {
         Scanner scanner = new Scanner(System.in);
        System.out.print("Enter an arithmetic expression: ");
        input = scanner.nextLine();
        position = 0;
        try {
            expr();
            if (position == input.length()) {
                System.out.println("Syntax analysis successful. The expression is valid.");
            } else {
                System.out.println("Syntax error: Unexpected tokens after the expression.");
            }
        } catch (Exception e) {
            System.out.println("Syntax error: " + e.getMessage());
        }
    } 
private static void expr() throws Exception {
        term();
        while (position < input.length() && (input.charAt(position) == '+' || input.charAt(position) == '-')) {
            position++;
            term();
        }
    }

    private static void term() throws Exception {
        factor();
        while (position < input.length() && (input.charAt(position) == '*' || input.charAt(position) == '/')) {
            position++;
            factor();
        }
    }

    private static void factor() throws Exception {
        if (Character.isDigit(input.charAt(position))) {
            position++;
        } else if (input.charAt(position) == '(') {
            position++;
            expr();
            if (position >= input.length() || input.charAt(position) != ')') {
                throw new Exception("Missing closing parenthesis ')'");
            }
            position++;
        } else {
            throw new Exception("Invalid character '" + input.charAt(position) + "'");
        }
    }

    private static void performTokenization() {
    final int BUFFER_SIZE = 15;
    char[] operators = {'+', '-', '*', '/', '%', '=', '<', '>'};
    char[] specialCharacter = {'(', ')', '{', '}', '[', ']', ',', '.', ';'};

    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter input: (To exit enter \"$\")");

    
        try {
            int j = 0;
            char[] buffer = new char[BUFFER_SIZE];
            int ch;
            boolean shouldExit = false; // Flag to control loop exit

            while ((ch = System.in.read()) != -1 && (char) ch != '$') {
                for (char operator : operators) {
                    if ((char) ch == operator) {
                        System.out.println(" OPERATOR:     " + (char) ch);
                    }
                }

                for (char separator : specialCharacter) {
                    if ((char) ch == separator) {
                        System.out.println(" Special Character:    " + (char) ch);
                    }
                }

                if (Character.isLetterOrDigit(ch)) {
                    buffer[j++] = (char) ch;
                } else if ((ch == ' ' || ch == '\n') && (j != 0)) {
                    String token = new String(buffer, 0, j);
                    j = 0;
                    if (isKeyword(token)) {
                        System.out.println(" KEYWORD:      " + token);
                    } else if (token.matches("-?\\d+(\\.\\d+)?")) {
                        System.out.println(" NUMERIC CONSTANT: " + token);
                    } else {
                        System.out.println(" IDENTIFIER: " + token);                        
                    }
                } 

                if ((char) ch == '$') {
                    shouldExit = true;
                    break; // Exit the loop if '$' is encountered
                } 
            }

            if (shouldExit) {
                System.out.println("Exiting tokenization...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
}

    private static boolean isKeyword(String buffer) {
        String[] keywords = {"auto", "break", "case", "char", "const", "continue", "default", "do", "double", "else", "enum", "extern", "float", "for", "goto",
                "if", "int", "long", "register", "return", "short", "signed", "sizeof", "static", "struct", "switch", "typedef", "union", "unsigned", "void", "volatile", "while"};

        return Arrays.asList(keywords).contains(buffer);
    }
  
}
