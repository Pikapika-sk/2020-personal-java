import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.*;


public class Main {

    private static ArrayList<String> readJsonFile(String fileName) {
        ArrayList<String> stringArrayList = new ArrayList<>();
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            BufferedReader bf = new BufferedReader(fileReader);
            String str;
            while ((str = bf.readLine()) != null) {
                stringArrayList.add(str);
            }
            bf.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (stringArrayList.size() == 0) return null;
        return stringArrayList;

    }

    public static void main(String[] args) {

        String fileName = "C:\\Users\\78430\\Desktop\\2020-personal-java\\src\\trim.json";
        ArrayList<String> jsonList = readJsonFile(fileName);
        Map<String, Result> map1 = new HashMap<>(); //个人 4 种事件的数量。
        Map<String, Result> map2 = new HashMap<>();//每一个项目的 4 种事件的数量。
        Map<String, Result> map3 = new HashMap<>();//每一个人在每一个项目的 4 种事件的数量。


        for (int i = 0; i < jsonList.size(); i++) {
            String tmp = jsonList.get(i);
            //String subStr = tmp.substring(leftIndex,rightIndex+1);
            JSONObject jso1 = JSON.parseObject(tmp);
            String actor = jso1.getString("actor");
            JSONObject jos2 = JSON.parseObject(actor);
            String id = jos2.getString("id");
            if(!map1.containsKey(id))
            {
                map1.put(id,new Result());
            }
            Result res = map1.get(id);

            String event = jso1.getString("type");

            if(event.equals("PushEvent" ) ) {
                res.PushEvent ++ ;
            }else if(event.equals("IssueCommentEvent") ){
                res.IssuesEvent ++ ;
            }else if(event.equals("PullRequestEvent"))
                res.PullRequestEvent ++ ;
            else if(event.equals("IssuesEvent")){
                res.IssuesEvent++;
            }
            map1.put(id,res);
        }
        for(Map.Entry<String, Result> entry : map1.entrySet()){
            System.out.println("id = " +entry.getKey() + " value = "+ entry.getValue());
        }

    }


}
