import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
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
                //            System.out.println("str = "+ str);
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

    private static String justify(String s) {
        int cnt = 0;
        int index = -1;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '\"') {
                cnt++;
            }
            if (cnt == 36) {
                index = i;
                break;
            }
        }
        String ans = s.substring(0, index);
        ans = ans + "\"}}";

        return ans;
    }


    public static void main(String[] args) {

        String fileName = ".\\src\\test.json";

        ArrayList<String> jsonList = readJsonFile(fileName);
        Map<String, Result> map1 = new HashMap<>(); //个人 4 种事件的数量。
        Map<String, Result> map2 = new HashMap<>();//每一个项目的 4 种事件的数量。
        Map<String, Result> map3 = new HashMap<>();//每一个人在每一个项目的 4 种事件的数量。

        //     System.out.println(jsonList.size());
        System.out.println(jsonList.size());
        for (int i = 0; i < jsonList.size(); i++) {
            String tmp = jsonList.get(i);
       //     System.out.println(i);
            //   System.out.println("tmp = "+ tmp);
            tmp = justify(tmp);
            //    System.out.println(tmp);
            JSONObject jso1 = JSON.parseObject(tmp);
            String actor = jso1.getString("actor");
            JSONObject jso2 = JSON.parseObject(actor);
            String id = jso2.getString("id");

            String repo = jso1.getString("repo");
            JSONObject jso3 = JSON.parseObject(repo);
            String repoId = jso3.getString("id");

            String idAndRepoId = "id:" + id + " repoId:" + repoId;

            //  System.out.println(repoId);
            if (!map1.containsKey(id)) {
                map1.put(id, new Result());
            }
            if (!map2.containsKey(repoId)) {
                map2.put(repoId, new Result());
            }
            if (!map3.containsKey(idAndRepoId)) {
                map3.put(idAndRepoId, new Result());
            }
            Result res = map1.get(id);
            Result repoRes = map2.get(repoId);
            Result iAndRepoRes = map3.get(idAndRepoId);

            String event = jso1.getString("type");

            if (event.equals("PushEvent")) {
                res.PushEvent++;
                repoRes.PushEvent++;
                iAndRepoRes.PushEvent++;
            } else if (event.equals("IssueCommentEvent")) {
                res.IssuesEvent++;
                repoRes.IssuesEvent++;
                iAndRepoRes.IssuesEvent++;
            } else if (event.equals("PullRequestEvent")) {
                res.PullRequestEvent++;
                repoRes.PullRequestEvent++;
                iAndRepoRes.PullRequestEvent++;
            } else if (event.equals("IssuesEvent")) {
                res.IssuesEvent++;
                repoRes.IssuesEvent++;
                iAndRepoRes.IssuesEvent++;
            } else {
                //      System.out.println("i = " + i );
                continue;
            }
            map1.put(id, res);
            map2.put(repoId, repoRes);
            map3.put(idAndRepoId, iAndRepoRes);

        }
            for (Map.Entry<String, Result> entry : map1.entrySet()) {
                System.out.println("id = " + entry.getKey() + entry.getValue());
        }
        for (Map.Entry<String, Result> entry : map2.entrySet()) {
            System.out.println("repoId = " + entry.getKey() + entry.getValue());
        }

        for (Map.Entry<String, Result> entry : map3.entrySet()) {
            System.out.println(entry.getKey() + entry.getValue());
        }

    }
}
