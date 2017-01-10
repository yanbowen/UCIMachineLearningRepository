/**
 * Created by yanbowen on 1/10/2017.
 */
public class IrisData {
    /*
    数据的基本结构
     */
    public double SL, SW, PL, PW; //Sepal Length/Width, Petal Length/Width
    public int Type;  //Iris-setosa:0 Iris-versicolor:1 Iris-virginica:2
    public int tempType;
    public int SetNum;  //IrisSet的编号

    public IrisData() {
        this.SL = -1;    //Sepal Length
        this.SW = -1;    //Sepal Width
        this.PL = -1;    //Petal Length
        this.PW = -1;    //Petal Width
        this.Type = -1;  //The type of the flower
        this.tempType = 0;
        this.SetNum = -1;
    }

    public IrisData(double SL, double SW, double PL, double PW, int Type, int SetNum) {
        this.SL = SL;
        this.SW = SW;
        this.PL = PL;
        this.PW = PW;
        this.Type = Type;
        this.tempType = -1; //tempType=-1 means undefined
        this.SetNum = SetNum;
    }
}
