import java.io.File;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
/**
 * Created by yanbowen on 1/10/2017.
 */
public class ExcelPrint {
    /*
     * 将IrisData数组输出到excel文件里面
     */
    public ExcelPrint() {

    }

    public void PrintIrisDataArray(IrisData[] input, String filename) {
        try {

            String rootname = "C:\\yanbowen\\Iris\\";
            String path = rootname + filename + ".xls";
            File file = new File(path);
            WritableSheet sheet;
            WritableWorkbook book;
            if (file.exists()) {
                Workbook wb = Workbook.getWorkbook(file);
                book = Workbook.createWorkbook(file, wb);
                int sheetnum = book.getNumberOfSheets();
                sheet = book.createSheet("第" + sheetnum + "页", sheetnum);
                System.out.println("正在第" + sheetnum + "页打印IrisData数组");
            } else {
                book = Workbook.createWorkbook(new File(path));
                sheet = book.createSheet("第0页", 0);
                System.out.println("正在第0页打印IrisData数组");
            }
            //System.out.println("已获取到需要的表单");

            String[] name = {"SetNum", "Sepal Length", "Sepan Width", "Petal Length", "Petal Width", "Type", "tempType"};
            for (int i = 0; i < 7; i++) {
                Label temp = new Label(i, 0, name[i]);
                sheet.addCell(temp);
            }

            int len = input.length;
            int row = 1;
            for (int i = 0; i < len; i++) {
                int col = 0;
                Label cell1 = new Label(col++, row, String.valueOf(input[i].SetNum));
                Label cell2 = new Label(col++, row, String.valueOf(input[i].SL));
                Label cell3 = new Label(col++, row, String.valueOf(input[i].SW));
                Label cell4 = new Label(col++, row, String.valueOf(input[i].PL));
                Label cell5 = new Label(col++, row, String.valueOf(input[i].PW));
                Label cell6 = new Label(col++, row, String.valueOf(input[i].Type));
                Label cell7 = new Label(col++, row, String.valueOf(input[i].tempType));
                sheet.addCell(cell1);
                sheet.addCell(cell2);
                sheet.addCell(cell3);
                sheet.addCell(cell4);
                sheet.addCell(cell5);
                sheet.addCell(cell6);
                sheet.addCell(cell7);

                row++;
            }
            book.write();
            book.close();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("ERROR:ExcelPrint");
        }

    }

    public void PrintIrisDataArray(IrisData[] input, String filename, String description) {
        try {

            String rootname = "C:\\yanbowen\\Iris\\";
            String path = rootname + filename + ".xls";
            File file = new File(path);
            WritableSheet sheet;
            WritableWorkbook book;
            if (file.exists()) {
                Workbook wb = Workbook.getWorkbook(file);
                book = Workbook.createWorkbook(file, wb);
                int sheetnum = book.getNumberOfSheets();
                sheet = book.createSheet("第" + sheetnum + "页", sheetnum);
                System.out.println("正在第" + sheetnum + "页打印IrisData数组");
            } else {
                book = Workbook.createWorkbook(new File(path));
                sheet = book.createSheet("第0页", 0);
                System.out.println("正在第0页打印IrisData数组");
            }
            //System.out.println("已获取到需要的表单");

            Label descrip = new Label(0, 0, description);
            sheet.addCell(descrip);
            String[] name = {"SetNum", "Sepal Length", "Sepan Width", "Petal Length", "Petal Width", "Type", "tempType"};
            for (int i = 0; i < 7; i++) {
                Label temp = new Label(i, 1, name[i]);
                sheet.addCell(temp);
            }

            int len = input.length;
            int row = 2;
            for (int i = 0; i < len; i++) {
                int col = 0;
                Label cell1 = new Label(col++, row, String.valueOf(input[i].SetNum));
                Label cell2 = new Label(col++, row, String.valueOf(input[i].SL));
                Label cell3 = new Label(col++, row, String.valueOf(input[i].SW));
                Label cell4 = new Label(col++, row, String.valueOf(input[i].PL));
                Label cell5 = new Label(col++, row, String.valueOf(input[i].PW));
                Label cell6 = new Label(col++, row, String.valueOf(input[i].Type));
                Label cell7 = new Label(col++, row, String.valueOf(input[i].tempType));
                sheet.addCell(cell1);
                sheet.addCell(cell2);
                sheet.addCell(cell3);
                sheet.addCell(cell4);
                sheet.addCell(cell5);
                sheet.addCell(cell6);
                sheet.addCell(cell7);

                row++;
            }
            book.write();
            book.close();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("ERROR:ExcelPrint");
        }

    }

    public void PrintIrisData(IrisData[] input, String filename, int sheetnum) { //要注意 input sheet num>current sheet num+1的情况可能出现的BUG
        //待定
    }

    private static boolean CreateExcel(String filename) {
        try {
            WritableWorkbook book = Workbook.createWorkbook(new File(filename));   //打开文件
            WritableSheet sheet = book.createSheet("FirstPage", 0);  //生成名为“FirstPage”的工作表，参数0表示这是第一页
            Label label = new Label(0, 0, "");
            sheet.addCell(label);
            book.write();
            book.close();
            return true;
        } catch (Exception e) {
            System.out.println("ERROR:CreateExcel");
            return false;
        }
    }

    private static boolean CreateExcel(String filename, String sheetname) {
        try {
            WritableWorkbook book = Workbook.createWorkbook(new File(filename));   //打开文件
            WritableSheet sheet = book.createSheet(sheetname, 0);  //生成名为sheetname的工作表，参数0表示这是第一页
            Label label = new Label(0, 0, "");
            sheet.addCell(label);
            book.write();
            book.close();
            return true;
        } catch (Exception e) {
            System.out.println("ERROR:CreateExcel");
            return false;
        }
    }

    private static void WriteExcel_Cell(String filename, String value, int cow, int row) {
        try {
            // Excel获得文件
            System.out.println("0");
            File file = new File(filename);
            Workbook wb = Workbook.getWorkbook(file);
            // 打开一个文件的副本，并且指定数据写回到原文件
            WritableWorkbook book = Workbook.createWorkbook(file, wb);
            System.out.println("1");
            // 添加一个工作表
            WritableSheet sheet = book.getSheet(0);  //book.createSheet("第二页", 1);
            sheet.addCell(new Label(cow, row, value));
            book.write();
            book.close();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("ERROR:写入失败");
        }
    }
}
