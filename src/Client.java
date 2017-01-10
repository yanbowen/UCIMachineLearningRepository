/**
 * Created by yanbowen on 1/10/2017.
 */
public class Client {
    /*
     * function Hunt Method is used to get the best attribute and the best value to divide a node into two parts
     */
    public static void main(String[] args) {


        IrisData[] dataset = new IrisInfoGet().dataset;

        ExcelPrint ep = new ExcelPrint();

        ep.PrintIrisDataArray(dataset, "test", "从数据库搬来的原始数据");

        System.out.println("首先将0与1，2分开来");
        for (int i = 0; i < dataset.length; i++) {
            if (dataset[i].Type == 0) dataset[i].tempType = 0;
            else dataset[i].tempType = 1;
        }
        ep.PrintIrisDataArray(dataset, "test", "加tempType以后的的dataset");

        //此处完成训练集和检验集的完成。训练集的比例可能比较低，
        //这个不是问题，以后可以随意更改的。
        IrisData[] trainset = new IrisData[75];
        IrisData[] examset = new IrisData[75];
        int index = 0;
        for (int i = 0; i < 75; i++) {
            trainset[index] = dataset[index * 2];
            examset[index] = dataset[index * 2 + 1];
            index++;
        }
        ep.PrintIrisDataArray(trainset, "test", "训练集trainset");
        ep.PrintIrisDataArray(examset, "test", "检验集examset");

        IrisNode root = new IrisNode(trainset, 0, "0");
        IrisNode node1 = root;
        Estimate es = new Estimate(node1, examset);
        System.out.println("得到的决策树使用检验集检验的错误率是： " + String.valueOf(es.ErrorRatio));

        System.out.println("然后将1与2分开来");
        IrisData[] ts2 = root.rightChild.datalist;
        for (int i = 0; i < ts2.length; i++) {
            if (ts2[i].Type == 1) ts2[i].tempType = 0;
            if (ts2[i].Type == 2) ts2[i].tempType = 1;
        }
        int es2len = 0;
        for (int i = 0; i < examset.length; i++) {
            if ((examset[i].Type == 1) || (examset[i].Type == 2)) es2len++;
        }
        IrisData[] es2 = new IrisData[es2len];
        int es2index = 0;
        for (int i = 0; i < examset.length; i++) {
            if (examset[i].Type == 1) {
                es2[es2index] = examset[i];
                es2[es2index++].tempType = 0;
            }
            if (examset[i].Type == 2) {
                es2[es2index] = examset[i];
                es2[es2index++].tempType = 1;
            }
        }
        IrisNode r2 = new IrisNode(ts2, 0, "0");
        Estimate estimate2 = new Estimate(r2, es2);
        System.out.println("得到的决策树使用检验集检验的错误率是： " + String.valueOf(estimate2.ErrorRatio));

        IrisData[] es2class0 = new IrisData[estimate2.list0.size()];
        for (int i = 0; i < estimate2.list0.size(); i++) es2class0[i] = estimate2.list0.get(i);
        ep.PrintIrisDataArray(es2class0, "test", "判定为typd1的标本");
        IrisData[] es2class1 = new IrisData[estimate2.list1.size()];
        for (int i = 0; i < estimate2.list1.size(); i++) es2class1[i] = estimate2.list1.get(i);
        ep.PrintIrisDataArray(es2class1, "test", "判定为typd2的标本");

    }
}
