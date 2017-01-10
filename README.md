# UCIMachineLearningRepository
Using decision tree classification algorithm to deal with Iris flower data set  
   

* test 程序的入口   
* IrisData 数据的基本结构   
* IrisInfoGet 负责从SQL中提取数据并转化成IrisData数组的形式。   
* IrisNode 负责用训练集生成决策树。   
* Hunt 负责寻找当前决策树节点最适合分类的属性，是决策树算法的关键组成部分。   
* Estimate 负责用检测集来检测决策树的性能。   
* ExcelPrint 将IrisData数组输出到excel文件里面。   
* DataProperty 是个辅助类，用于计算数组的熵等。  
