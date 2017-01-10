/**
 * Created by yanbowen on 1/10/2017.
 */
public class IrisNode {
    /*
    负责用训练集生成决策树
     */
    //Elements for Node itself
    public int deep;               // the deep of the IrisNode tree
    public double formerEntropy;   // the entropy of the list belong to the node
    public IrisData[] datalist;    // the data list belong to the node
    public String tag;             // in order to research the node tree
    public int nodeType = -1;            //nodeType=-1 means it's not leaf node =0 means it belongs to class0(tempType) =1 belongs to class1

    public int divideType = -1;         // the attritube selected to divide the IrisData list
    public double valveValue = -1;       // the corresponding value of the attribute to divide

    //Elements for the child for the node
    public IrisNode leftChild = null;        // the left child of the node
    public IrisNode rightChild = null;   // the right child of the node
    public double laterEntropy = -1; // the total entropy of the two node after division
    public double deltaEntropy = 0;  // the change of entropy between the ahead and after division

    //Methods in Node class
    public IrisNode(IrisData[] input, int deep, String tag) {  //Construction Method

        this.tag = tag;
        this.deep = deep;
        this.datalist = input;
        this.formerEntropy = getIrisDataListEntropy(input);
        this.nodeType = -1;

        if ((this.deep > 5) || (this.datalist.length < 2)) {
            this.leftChild = this.rightChild = null;
            int temp = decideType(this.datalist);
            if ((temp == 0) || (temp == 1)) this.nodeType = temp;
            else System.out.println("ERROR:函数decideType输出值不合法");
        } else {
            Hunt hunt = new Hunt(input);
            this.divideType = hunt.type;
            this.valveValue = hunt.value_value;
            this.laterEntropy = hunt.min_entropy;
            this.deltaEntropy = this.formerEntropy - this.laterEntropy;

            if ((this.formerEntropy - this.laterEntropy) < 0.05) {
                this.leftChild = this.rightChild = null; //if deltaEntropy<0.05 or deep>5 no longer continue
                int temp = decideType(this.datalist);
                if ((temp == 0) || (temp == 1)) this.nodeType = temp;
                else System.out.println("ERROR:函数decideType输出值不合法");
            } else {
                //System.out.println("tag1") ;              //used for debug
                IrisData[] leftList = Divide(input, this.divideType, this.valveValue, 0);
                IrisData[] rightList = Divide(input, this.divideType, this.valveValue, 1);

                if ((leftList.length == 0) || (rightList.length == 0)) {
                    this.leftChild = this.rightChild = null;
                    int temp = decideType(this.datalist);
                    if ((temp == 0) || (temp == 1)) this.nodeType = temp;
                    else System.out.println("ERROR:函数decideType输出值不合法");
                } else {
                    this.leftChild = new IrisNode(leftList, deep + 1, tag + '0');
                    this.rightChild = new IrisNode(rightList, deep + 1, tag + '1');
                }
            }
        }
    }

    public static IrisData[] Divide(IrisData[] input, int attribute, double valve, int methodtype) {
        IrisData[] rs = null;
        //通过attribute value type来将input分成两部分
        if (methodtype == 0) { //此处为methodtype=1时的情况，也就是attr value<valve的情况
            int num = 0;
            for (int i = 0; i < input.length; i++) {
                double tempvalue = -1;    //tempvalue初始值为=-1  在复用时要注意一下
                switch (attribute) {
                    case 0: {
                        tempvalue = input[i].SL;
                        break;
                    }
                    case 1: {
                        tempvalue = input[i].SW;
                        break;
                    }
                    case 2: {
                        tempvalue = input[i].PL;
                        break;
                    }
                    case 3: {
                        tempvalue = input[i].PW;
                        break;
                    }
                    default:
                        System.out.println("ERROR:The value of attribute value illegal");
                }
                if (tempvalue <= valve) num++;
            }
            rs = new IrisData[num];
            int index = 0;
            for (int i = 0; i < input.length; i++) {
                double tempvalue = -1;
                switch (attribute) {
                    case 0: {
                        tempvalue = input[i].SL;
                        break;
                    }
                    case 1: {
                        tempvalue = input[i].SW;
                        break;
                    }
                    case 2: {
                        tempvalue = input[i].PL;
                        break;
                    }
                    case 3: {
                        tempvalue = input[i].PW;
                        break;
                    }
                }
                if (tempvalue <= valve) rs[index++] = input[i];
            }
            return rs;
        } else if (methodtype == 1) {
            int num = 0;
            for (int i = 0; i < input.length; i++) {
                double tempvalue = -1;    //tempvalue初始值为=-1  在复用时要注意一下
                switch (attribute) {
                    case 0: {
                        tempvalue = input[i].SL;
                        break;
                    }
                    case 1: {
                        tempvalue = input[i].SW;
                        break;
                    }
                    case 2: {
                        tempvalue = input[i].PL;
                        break;
                    }
                    case 3: {
                        tempvalue = input[i].PW;
                        break;
                    }
                    default:
                        System.out.println("ERROR:The value of attribute value illegal");
                }
                if (tempvalue > valve) num++;
            }
            rs = new IrisData[num];
            int index = 0;
            for (int i = 0; i < input.length; i++) {
                double tempvalue = -1;
                switch (attribute) {
                    case 0: {
                        tempvalue = input[i].SL;
                        break;
                    }
                    case 1: {
                        tempvalue = input[i].SW;
                        break;
                    }
                    case 2: {
                        tempvalue = input[i].PL;
                        break;
                    }
                    case 3: {
                        tempvalue = input[i].PW;
                        break;
                    }
                }
                if (tempvalue > valve) {
                    rs[index++] = input[i];
                }
            }
            return rs;
        } else System.out.println("ERROR:methodtype value illegal");
        return rs;
    }


    //------Private Method-----------------------
    private static int decideType(IrisData[] input) {  //decide which class this node belongs to
        int rs = -1;
        int num0 = 0, num1 = 0;
        for (int i = 0; i < input.length; i++) {
            if (input[i].tempType == 0) num0++;
            if (input[i].tempType == 1) num1++;
        }
        if (num0 < num1) rs = 1; //有条件的话可以吧num0=num1时node的归属用随机数来实现
        else rs = 0;

        return rs;
    }

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
