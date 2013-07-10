package dset;

/**
 * Created by IntelliJ IDEA.
 * User: sujith.j
 * Date: 10/07/13
 * Time: 12:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class pinData {

    private int pin;
    private int val;

    private double serviceableRadius;


    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "pinData{" +
                "pin=" + pin +
                ", val=" + val +
                '}';
    }


}