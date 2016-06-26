package main.java;

/**
 * Created by gres on 26.06.2016.
 */
public class Action {
    int mark;
    String markInString;
    int deepLvl;
    Number n1;
    Number n2;

    public void setN1(Number n1) {
        this.n1 = n1;
    }

    public void setN2(Number n2) {
        this.n2 = n2;
    }


    public Number getN2() {
        return n2;
    }

    public Number getN1() {
        return n1;
    }

    public int getDeepLvl() {
        return deepLvl;
    }


    public String getMarkInString() {
        return markInString;
    }

    public Action(String markInString, int deepLvl, Number n1, Number n2) {
        this.markInString = markInString;
         this.deepLvl = deepLvl;
        this.n1 = n1;
        this.n2 = n2;
    }

    @Override
    public String toString() {
        return n1.getValue() + " " + this.getMarkInString() + n2.getValue() + " deep lvl = " + this.getDeepLvl();
    }
}
