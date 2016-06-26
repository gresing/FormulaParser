package main.java;

import java.util.ArrayList;

/**
 * Created by gres on 26.06.2016.
 */
public class FormulaParser {
    private char[] numeric = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
    private char[] formulaChars;
    private ArrayList<Action> actionsList = new ArrayList<>();
    private int maxDeep = 0;

    public FormulaParser(String inputString) {
        formulaChars = inputString.toCharArray();
        Double firstN = getRightOperandFromPosition(0);
        Number prevN = new Number(firstN);

        int deepLvl = 0;
        for (int i = 0; i < formulaChars.length; i++) {
            if (String.valueOf(formulaChars[i]).equals("(")) {
                deepLvl = deepLvl + 1;
                maxDeep = deepLvl;
            }
            if (String.valueOf(formulaChars[i]).equals(")")) {
                deepLvl = deepLvl - 1;
            }

            char[] marks = {'/', '*', '+', '-'};
            for (char mark : marks) {

                if (formulaChars[i] == mark) {
                    //Нашли арифмитический знак
                    //Левый опперанд уже есть, получаем правый и делаем инстанс "действия"
                    //циферные операнды - слева prevN, справа - nextN

                    Number nextN = new Number(getRightOperandFromPosition(i));
                    if (String.valueOf(formulaChars[i]).equals("/") && nextN.getValue() == 0.0) {
                        throw new IllegalArgumentException("Деление на 0");
                    }
                    Action m = new Action(String.valueOf(formulaChars[i]), deepLvl, prevN, nextN);
                    actionsList.add(m);
                    prevN = nextN;
                }
            }
        }
        if (deepLvl != 0) {
            throw new IllegalArgumentException("Лишние скобки");
        }

    }


    public Double calculate() {
        Double allRes = 0.0;

        int deep = maxDeep;
        while (deep != -1) {
            //Перебираем все элементы на 1 глубине и сначала считаем знаки * и /, а потом + и -
            for (int iterator = 0; iterator < actionsList.size(); iterator++) {
                Action action = actionsList.get(iterator);
                if (action.getDeepLvl() == deep) {
                    if (actionsList.size() == 1 && action.getMarkInString().equals("*")) {
                        allRes = action.getN1().getValue() * action.getN2().getValue();
                        break;
                    } else if (actionsList.size() == 1 && action.getMarkInString().equals("/")) {
                        allRes = action.getN1().getValue() / action.getN2().getValue();
                        break;
                    }

                    if (action.getMarkInString().equals("*")) {
                        double tr = action.getN1().getValue() * action.getN2().getValue();
                        replaceNumbersAndRemoveAction(iterator, action, tr);
                        iterator = -1;
                    } else if (action.getMarkInString().equals("/")) {
                        double tr = action.getN1().getValue() / action.getN2().getValue();
                        replaceNumbersAndRemoveAction(iterator, action, tr);
                        iterator = -1;
                    }
                }
            }

            // операции + и -
            for (int iterator = 0; iterator < actionsList.size(); iterator++) {
                Action m = actionsList.get(iterator);
                if (m.getDeepLvl() == deep) {
                    if (actionsList.size() == 1 && m.getMarkInString().equals("+")) {
                        allRes = m.getN1().getValue() + m.getN2().getValue();
                        break;
                    } else if (actionsList.size() == 1 && m.getMarkInString().equals("-")) {
                        allRes = m.getN1().getValue() - m.getN2().getValue();
                        break;
                    }
                    if (m.getMarkInString().equals("+")) {
                        double tr = m.getN1().getValue() + m.getN2().getValue();
                        replaceNumbersAndRemoveAction(iterator, m, tr);
                        iterator = -1;
                    } else if (m.getMarkInString().equals("-")) {
                        double tr = m.getN1().getValue() - m.getN2().getValue();
                        replaceNumbersAndRemoveAction(iterator, m, tr);
                        iterator = -1;
                    }
                }
            }
            deep--;
        }
        return allRes;
    }

    private void replaceNumbersAndRemoveAction(int iterator, Action action, double tr) {
        Number n = new Number(tr);
        if (iterator - 1 > -1) {
            Action mT1 = actionsList.get(iterator - 1);
            mT1.setN2(n);
        }
        if (iterator + 1 < actionsList.size()) {
            Action mT2 = actionsList.get(iterator + 1);
            mT2.setN1(n);
        }
        actionsList.remove(action);
    }


    private Double getRightOperandFromPosition(int pos) {
        Double temp = 0.0;
        boolean isFound = false;
        for (int i = pos; i < formulaChars.length; i++) {
            for (char aNumeric : numeric) {
                if (formulaChars[i] == aNumeric) {
                    temp = readRight(i);
                    isFound = true;
                    break;
                }
            }
            if (isFound) break;
        }
        return temp;
    }

    private Double readRight(int pos) {
        String answer = "";

        boolean isStartRead = false;
        boolean isNum;
        for (int i = pos; i < formulaChars.length; i++) {
            isNum = true;
            for (char aNumeric : numeric) {
                if (formulaChars[i] == aNumeric) {
                    answer = answer + formulaChars[i];
                    isNum = false;
                    isStartRead = true;
                    break;
                }
            }
            if (isNum && isStartRead) break;
        }
        return Double.parseDouble(answer);
    }

}
