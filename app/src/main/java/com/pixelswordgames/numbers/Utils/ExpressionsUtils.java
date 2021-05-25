package com.pixelswordgames.numbers.Utils;

import java.text.DecimalFormat;

public class ExpressionsUtils {
    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch + "  Expression: " + str);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm();
                    else if (eat('-')) x -= parseTerm();
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor();
                    else if (eat('×')) x *= parseFactor();
                    else if (eat('/')) x /= parseFactor();
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor();
                if (eat('-')) return -parseFactor();

                double x;
                int startPos = this.pos;
                if (eat('(')) {
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') {
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    switch (func) {
                        case "sqrt":
                        case "√":
                            x = Math.sqrt(x);
                            break;
                        case "sin":
                            x = Math.sin(Math.toRadians(x));
                            break;
                        case "cos":
                            x = Math.cos(Math.toRadians(x));
                            break;
                        case "tan":
                            x = Math.tan(Math.toRadians(x));
                            break;
                        default:
                            throw new RuntimeException("Unknown function: " + func);
                    }
                } else {
                    throw new RuntimeException("Unexpected: (" + (char)ch + ") Expression: " + str);
                }

                if (eat('^')) x = Math.pow(x, parseFactor());

                return x;
            }
        }.parse();
    }

    public static String formatDouble(double value) {
        long iPart = (long) value;
        double fPart = value - iPart;

        if(fPart == 0.0)
            return (iPart + "");

        String fString = fPart + "";
        if(fString.length() > 5)
            return new DecimalFormat("#.##")
                    .format(value)
                    .replace(',','.')
                    .replace('/',' ');

        return (value + "")
                .replace(',','.')
                .replace('/',' ');
    }

    public static String formatExpression(String expression) {

        expression = expression.replace(" ^ 0", "⁰");
        expression = expression.replace(" ^ 1", "¹");
        expression = expression.replace(" ^ 2", "²");
        expression = expression.replace(" ^ 3", "³");
        expression = expression.replace(" ^ 4", "⁴");
        expression = expression.replace(" ^ 5", "⁵");
        expression = expression.replace(" ^ 6", "⁶");
        expression = expression.replace(" ^ 7", "⁷");
        expression = expression.replace(" ^ 8", "⁸");
        expression = expression.replace(" ^ 9", "⁹");
        expression = expression.replace(" ^  0", "⁰");
        expression = expression.replace(" ^  1", "¹");
        expression = expression.replace(" ^  2", "²");
        expression = expression.replace(" ^  3", "³");
        expression = expression.replace(" ^  4", "⁴");
        expression = expression.replace(" ^  5", "⁵");
        expression = expression.replace(" ^  6", "⁶");
        expression = expression.replace(" ^  7", "⁷");
        expression = expression.replace(" ^  8", "⁸");
        expression = expression.replace(" ^  9", "⁹");
        expression = expression.replace(" / ", "÷");

        return expression;
    }
}
