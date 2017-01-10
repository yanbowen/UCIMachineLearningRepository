import java.util.ArrayList;

/**
 * Created by yanbowen on 1/10/2017.
 */
public class Estimate {
    /*
     * 负责用检测集来检测决策树的性能
     */
    ArrayList<IrisData> list0;
    ArrayList<IrisData> list1;
    IrisNode examtree;
    double ErrorRatio;

    public Estimate(IrisNode rule, IrisData[] examset) {
        this.list0 = new ArrayList();
        this.list1 = new ArrayList();
        this.examtree = examTree(rule, examset);
        this.ErrorRatio = getErrorRatio(this.examtree);
    }

    private double getErrorRatio(IrisNode node) {
        if (node.datalist.length == 0) return 0;
        if (node.nodeType == -1) {
            double len1 = 0, len2 = 0;
            double ratio1 = 1, ratio2 = 1;
            if (node.leftChild == null) len1 = 0;
            else {
                len1 = node.leftChild.datalist.length;
                ratio1 = getErrorRatio(node.leftChild);
            }
            if (node.rightChild == null) len2 = 0;
            else {
                len2 = node.rightChild.datalist.length;
                ratio2 = getErrorRatio(node.rightChild);
            }
            double noderatio = (len1 * ratio1 + len2 * ratio2) / (len1 + len2);
            return noderatio;
        } else {
            if (node.nodeType == 0) {
                double len = node.datalist.length;
                double num = 0;
                for (int i = 0; i < len; i++) {
                    this.list0.add(node.datalist[i]);
                    if (node.datalist[i].tempType == 1) num++;
                }
                double noderatio = num / len;
                return noderatio;
            }
            if (node.nodeType == 1) {
                double len = node.datalist.length;
                double num = 0;
                for (int i = 0; i < len; i++) {
                    this.list1.add(node.datalist[i]);
                    if (node.datalist[i].tempType == 0) num++;
                }
                double noderatio = num / len;
                return noderatio;
            }
            return -1;
        }
    }

    private IrisNode examTree(IrisNode node, IrisData[] data) {
        node.datalist = data;
        node.formerEntropy = getIrisDataListEntropy(data);
        if (node.nodeType == -1) { //this node is not a leaf node
            IrisData[] left = IrisNode.Divide(data, node.divideType, node.valveValue, 0);
            IrisData[] right = IrisNode.Divide(data, node.divideType, node.valveValue, 1);
            if (left.length == 0) node.leftChild = null;
            else node.leftChild = examTree(node.leftChild, left);
            if (right.length == 0) node.rightChild = null;
            else node.rightChild = examTree(node.rightChild, right);
            return node;
        } else {    // this node is a leaf node
            node.leftChild = null;
            node.rightChild = null;
            return node;
        }
    }

    public double getFinalEntropy(IrisNode input) {
        double rs = -1;
        if ((input.leftChild == null) || (input.rightChild == null)) {
            rs = getIrisDataListEntropy(input.datalist);
            return rs;
        } else {
            double rs_1 = getFinalEntropy(input.leftChild);
            double len1 = input.leftChild.datalist.length;
            double rs_2 = getFinalEntropy(input.rightChild);
            double len2 = input.rightChild.datalist.length;
            rs = (rs_1 * len1 + rs_2 * len2) / (len1 + len2);
            return rs;
        }
    }

    //Private Methods
    private static double getIrisDataListEntropy(IrisData[] input) {
        DataProperty dp = new DataProperty();
        double rs_entropy = -1;
        //通过tempType的值来计算irisdata数组的熵
        //tempType只有3个值，0表示类1，1表示类2，-1表示其他类 一般用于表示异常
        int num1 = 0, num2 = 0;
        for (int i = 0; i < input.length; i++) {
            if (input[i].tempType == 0) num1++;
            if (input[i].tempType == 1) num2++;
        }
        rs_entropy = dp.getEntropy(num1, num2);
        return rs_entropy;
    }

}
