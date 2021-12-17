 /* 版权所有 ( c ) 2021。保留所有权利。
  *
  *
  * 项目：templateExcel
  * 文件名：Main
  * 描述：
  * 作者名：wanghy
  * 日期：2021/12/13
  *
  * 修改历史：
  * 【时间】     【修改者】     【修改内容】
  *
  */

 import jxl.read.biff.BiffException;
 import jxl.report.ReportEnginer;

 import java.io.IOException;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;


 /**
  * @author wanghy
  */
 public class Main {
     public static void main(String[] args) throws IOException, BiffException {

         String templateFile = Main.class.getClassLoader().getResource("template.xls").getPath();
         ReportEnginer engine = new ReportEnginer();
         Map<String, Object> context = new HashMap<String, Object>();
         //除了单个字段  还可以存入一个list
         List<Map> testList = new ArrayList<Map>();
         for(int i=0;i<5;i++){
             Map innerMap = new HashMap();
             innerMap.put("id", i);
             innerMap.put(".licensePlateNumber", "LPN"+System.currentTimeMillis());
             innerMap.put("iawConcludeDateTime", new Date());
             testList.add(innerMap);
         }
         context.put("testList", testList);
         String destFile = System.currentTimeMillis() + ".xls";;
         try {
             engine.excute(templateFile, context, destFile);
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
 }
