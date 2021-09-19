package com.filestotest;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class BracketChecker {

    public Boolean checkBracket(String bracketString) {
        if (bracketString.length() == 0) {
            return false;
        }
        List<String> openBrackets = Arrays.asList("{", "[", "(", "<");
        List<String> allBrackets = Arrays.asList("{", "[", "(", "<", "}", "]", ")", ">");
        Stack<String> stack = new Stack<>();
        for (char x : bracketString.toCharArray()) {
            String y = Character.toString(x);
            if (!allBrackets.contains(y)) {
                return false;
            } else if (openBrackets.contains(y)) {
                stack.push(y);
            } else if (y.equals("}")) {
                if (stack.empty()) {
                    return false;
                }
                if (stack.peek().equals("{")) {
                    stack.pop();
                } else {
                    return false;
                }
            } else if (y.equals("]")) {
                if (stack.empty()) {
                    return false;
                }
                if (stack.peek().equals("[")) {
                    stack.pop();
                } else {
                    return false;
                }
            } else if (y.equals(")")) {
                if (stack.empty()) {
                    return false;
                }
                if (stack.peek().equals("(")) {
                    stack.pop();
                } else {
                    return false;
                }
            } else {
                if (stack.empty()) {
                    return false;
                }
                if (stack.peek().equals("<")) {
                    stack.pop();
                } else {
                    return false;
                }
            }
        }
        return stack.empty();
    }
}
