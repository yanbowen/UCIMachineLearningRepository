/**
 * Created by yanbowen on 1/10/2017.
 */
public class DataProperty {
    /*
     * 是个辅助类，用于计算数组的熵等
     */
    public double getGini(int[] data) {
        int len = data.length;
        double sum = 0;
        for (int i = 0; i < len; i++) sum += data[i];
        double pre_gini = 0;
        for (int i = 0; i < len; i++) pre_gini += (data[i] / sum) * (data[i] / sum);
        double gini = 1 - pre_gini;
        return gini;
    }

    public double getGini(int a, int b) {
        double c = a + b;
        double gini = 1 - (a / c) * (a / c) - (b / c) * (b / c);
        return gini;
    }

    public double getEntropy(int[] data) {
        int len = data.length;
        double sum = 0;
        for (int i = 0; i < len; i++) sum += data[i];  //get the summary of all data
        double pre_entro = 0;
        for (int i = 0; i < len; i++) {
            if (data[i] != 0) {
                pre_entro += (data[i] / sum) * Math.log(data[i] / sum) / Math.log(2);
            }
        }
        double entro = -pre_entro;
        return entro;
    }

    public double getEntropy(int ina, int inb) {
        double a = (double) ina;
        double b = (double) inb;
        double entro;
        if ((a * b) != 0) {
            double c = a + b;
            double a1 = (a / c) * mathLog2(a / c);
            double b1 = (b / c) * mathLog2(b / c);
            entro = -a1 - b1;
            return entro;
        } else {
            entro = 0;
            return entro;
        }
    }

    //inner methods----------------------------------------------------
    private static double mathLog(double data, double bottom) {
        return Math.log(data) / Math.log(bottom);
    }

    private static double mathLog2(double data) {
        return Math.log(data) / Math.log(2);
    }
}
