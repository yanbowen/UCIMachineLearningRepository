import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by yanbowen on 1/10/2017.
 */

public class IrisInfoGet {
    /*
    负责从SQL中提取数据并转化成IrisData数组的形式
     */
    public IrisData[] dataset;

    public IrisInfoGet() {
        ResultSet rs = getResultSet();
        this.dataset = ResultDeal(rs);
    }

    public static void main(String[] args) throws SQLException {
        ResultSet rs = getResultSet();
        IrisData[] data = ResultDeal(rs);
    }

    private static ResultSet getResultSet() {
        String JDriver = "com.mysql.jdbc.Driver";//SQL数据库引擎
        String connectDB = "jdbc:mysql://10.10.4.151:3306/irisdata";//数据源

        try {
            Class.forName(JDriver);//加载数据库引擎，返回给定字符串名的类
            System.out.println("数据库驱动成功");
        } catch (ClassNotFoundException e) { //e.printStackTrace();
            System.out.println("加载数据库引擎失败");
            System.out.println(e);
        }

        ResultSet rs;
        try {
            String user = "root";
            String password = "123456";
            Connection con = DriverManager.getConnection(connectDB, user, password);
            System.out.println("数据库连接成功");
            Statement stmt = con.createStatement();
            //String query = "select ROW_NUMBER()over(order by class)as id,* from dbo.[iris]";
            String query = "select * from iris";
            rs = stmt.executeQuery(query);
            return rs;
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("数据库内容读取失败");
            return null;
        }
    }

    public static IrisData[] ResultDeal(ResultSet rs) {
        IrisData[] dataset = new IrisData[150];
        int num = 0;
        try {
            while ((num < 150) && (rs.next())) {
                double SL = Double.parseDouble(rs.getString("SepalLength"));
                double SW = Double.parseDouble(rs.getString("SepalWidth"));
                double PL = Double.parseDouble(rs.getString("PetalLength"));
                double PW = Double.parseDouble(rs.getString("PetalWidth"));
                int setnum = Integer.parseInt(rs.getString("id"));

                String name = rs.getString("Classification");
                int type;
                if (name.equals("Iris-setosa")) type = 0;
                else if (name.equals("Iris-versicolor")) type = 1;
                else if (name.equals("Iris-virginica")) type = 2;
                else type = -1;

                dataset[num++] = new IrisData(SL, SW, PL, PW, type, setnum);
                //System.out.println(setnum+"       "+SL+"      "+SW+"      "+PL+"      "+PW+"      "+type) ;
            }
            System.out.println("ResultSet 解析完毕");
            return dataset;
        } catch (SQLException e) {
            System.out.println("ResultSet 解析出错");
            System.out.println(e);
            return null;
        }
    }
}
