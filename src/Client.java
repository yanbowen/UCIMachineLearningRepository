import java.util.ArrayList;

/**
 * @author yanbowen
 *
 * @version Created on: Jan 10, 2017 10:32:48 PM
 *
 * @E-mail: 1743803588@qq.com
 */
public class Client {
	/*
     * function Hunt Method is used to get the best attribute and the best value to divide a node into two parts
     */
	
	
    public static void main(String[] args) {

    	int setlength = 150;
        
        
        

        //此处完成训练集和检验集的完成。训练集的比例可能比较低，
        //这个不是问题，以后可以随意更改的。
        IrisData[] trainset = new IrisData[135];
        IrisData[] examset = new IrisData[15];
        int index = 0;
        IrisData[] tempset = new IrisData[150];
        IrisData tempsingle = new IrisData();
        
        //
        
//        for (int i = 0; i < 75; i++) {
//            trainset[index] = dataset[index * 2];
//            examset[index] = dataset[index * 2 + 1];
//            index++;
//        }
//        

        double timeerrors = 0;
        //十次
		for(int time=0;time<10;time++)
		{
			double folderrors = 0;
			IrisData[] dataset = new IrisInfoGet().dataset;
			//十折
			for(int fold=0;fold<10;fold++)
        	{
				ExcelPrint ep = new ExcelPrint();
		        ep.PrintIrisDataArray(dataset, "test"+time+fold, "从数据库搬来的原始数据");
		        ep.PrintIrisDataArray(dataset, "test"+time+fold, "加tempType以后的的dataset");
				System.out.println("首先将0与1，2分开来");
		        for (int i = 0; i < dataset.length; i++) {
		            if (dataset[i].Type == 0) dataset[i].tempType = 0;
		            else dataset[i].tempType = 1;
		        }
			
				for(int i = 0;i<150;i++)
		        {
		        	tempset[i] = dataset[i];
		        }
		        
				//将原始数据打乱
		        for(int i=0;i<150;i++)
		        {
		        	int j = (int) (Math.random()*setlength);
		        	tempsingle = tempset[i];
		        	tempset[i] = tempset[j];
		        	tempset[j] = tempsingle;
		        }
		        IrisData[][] irisdata = new IrisData[10][15];
		        for(int i=0;i<10;i++)
		        {
		        	for(int j=0;j<15;j++)
		        	{
		        		irisdata[i][j] = tempset[i*10+j];
		        	}
		        }
		        //训练集
		        int k=0;
		        for(int i=0;i<10;i++)
		        {
		        	if(i!=fold)
		        	{
		        		for(int j=0;j<15;j++)
		        		{
		        			trainset[k] = irisdata[i][j];
		        			k++;
		        		}
		        	}
		        }
		        //测试集
		        for(int i=0;i<15;i++)
		        {
		        	examset[i] = irisdata[fold][i];
		        }
		        
		        ep.PrintIrisDataArray(trainset, "test"+time+fold, "训练集trainset");
		        ep.PrintIrisDataArray(examset, "test"+time+fold, "检验集examset");

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
		         ep.PrintIrisDataArray(es2class0, "test"+time+fold, "判定为typd1的标本");
		         IrisData[] es2class1 = new IrisData[estimate2.list1.size()];
		         for (int i = 0; i < estimate2.list1.size(); i++) es2class1[i] = estimate2.list1.get(i);
		         ep.PrintIrisDataArray(es2class1, "test"+time+fold, "判定为typd2的标本");    
		        
		         folderrors += estimate2.ErrorRatio;
        	}
			timeerrors += folderrors;
		}
		System.out.println("******错误率:"+timeerrors/(double)100);
       
		
    }

}
