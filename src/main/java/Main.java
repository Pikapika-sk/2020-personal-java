import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.*;


public class Main {

    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String fileName = "C:\\Users\\78430\\Desktop\\2020-personal-java\\src\\trim.json";
        String tmp = readJsonFile(fileName);

        int leftCnt  = 0 ,rightCnt= 0 ;
        int leftIndex = -1 ,rightIndex = -1 ;
        int i ;
        for( i =0 ; i<tmp.length() ;i++)
        {

            if (tmp.charAt(i) == '{' ) {
                leftCnt++;
                if(leftIndex==-1)
                    leftIndex= i ;
            } else if (tmp.charAt(i) == '}') {
                rightCnt++;
                rightIndex = i ;
            }
            if(leftCnt == rightCnt && leftCnt!=0){
                String subStr = tmp.substring(leftIndex,rightIndex+1);
                JSONObject jso1=JSON.parseObject(subStr);
               System.out.println(jso1.getString("repo"));
                leftCnt = 0 ;
                rightCnt = 0;
                leftIndex = -1 ;
            }
        }

    }
}
