import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.*;
import org.apache.commons.io.FileUtils;


public class Main {

    static Map<String, Result> map1 = new HashMap<>(); //个人 4 种事件的数量。
    static Map<String, Result> map2 = new HashMap<>();//每一个项目的 4 种事件的数量。
    static Map<String, Result> map3 = new HashMap<>();//每一个人在每一个项目的 4 种事件的数量。

    //初始化
    private static void init(String fileName) throws IOException {
        ArrayList<String> jsonList = new ArrayList<>();
        File dir = new File(fileName);
        //读入文件列表转存JSON于jsonList中
        File[] files = dir.listFiles(file -> file.getName().endsWith(".json"));
        try {
            for (File jsonFile : files) {
                FileReader fileReader = new FileReader(jsonFile);
                BufferedReader bf = new BufferedReader(fileReader);
                String str;
                while ((str = bf.readLine()) != null) {
                    jsonList.add(str);
                }
                bf.close();
                fileReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //遍历jsonList，进行统计
        for (int i = 0; i < jsonList.size(); i++) {
            //"裁剪“
            String tmp = jsonList.get(i);
            tmp = justify(tmp);
            //获取所需信息
            JSONObject jso1 = JSON.parseObject(tmp);
            String actor = jso1.getString("actor");
            JSONObject jso2 = JSON.parseObject(actor);
            String id = jso2.getString("login");

            String repo = jso1.getString("repo");
            JSONObject jso3 = JSON.parseObject(repo);
            String repoId = jso3.getString("name");
            //创建第三种查询的ID
            String idAndRepoId = id + repoId;
            //创建3种Result并插入对应的Map
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
                continue;
            }
            map1.put(id, res);
            map2.put(repoId, repoRes);
            map3.put(idAndRepoId, iAndRepoRes);

        }

        //map转换为JSON String
        String s1 = JSONObject.toJSONString(map1);
        String s2 = JSONObject.toJSONString(map2);
        String s3 = JSONObject.toJSONString(map3);
        // 将json写入文件
        FileUtils.writeStringToFile(new File("map1.json"), s1, "UTF-8");
        FileUtils.writeStringToFile(new File("map2.json"), s2, "UTF-8");
        FileUtils.writeStringToFile(new File("map3.json"), s3, "UTF-8");
    }

    //”裁剪“读入的JSON
    private static String justify(String s) {
        int cnt = 0;
        int index = -1;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '\"') {
                cnt++;
            }
            if (cnt == 40) {
                index = i;
                break;
            }
        }
        String ans = s.substring(0, index);
        ans = ans + "\"}}";

        return ans;
    }
    //查询个人 4 种事件的数量
    public static void countFromUser(String user, String event) throws IOException {
        String tmp = event.substring(0, 1);
        tmp = tmp.toLowerCase();
        event = tmp + event.substring(1, event.length());
        String s = FileUtils.readFileToString(new File("map1.json"), "UTF-8");
        JSONObject jsonObject = JSONObject.parseObject(s);
        JSONObject object = jsonObject.getJSONObject(user);
        if (object != null) {
            System.out.println(object.getInteger(event));
        }
    }
    //查询每一个项目的 4 种事件的数量
    public static void countFromRepo(String repo, String event) throws IOException {
        String tmp = event.substring(0, 1);
        tmp = tmp.toLowerCase();
        event = tmp + event.substring(1, event.length());
        String s = FileUtils.readFileToString(new File("map2.json"), "UTF-8");
        JSONObject jsonObject = JSONObject.parseObject(s);
        JSONObject object = jsonObject.getJSONObject(repo);
        System.out.println(object.getInteger(event));
    }
    //每一个人在每一个项目的 4 种事件的数量
    public static void countFromUserAndRepo(String idAndRepoId, String event) throws IOException {
        String tmp = event.substring(0, 1);
        tmp = tmp.toLowerCase();
        event = tmp + event.substring(1, event.length());
        String s = FileUtils.readFileToString(new File("map3.json"), "UTF-8");
        JSONObject jsonObject = JSONObject.parseObject(s);
        JSONObject object = jsonObject.getJSONObject(idAndRepoId);
        if (object != null) {
            System.out.println(object.getInteger(event));
        }
    }

    public static void main(String[] args) throws IOException {
        String testUser = null, testEvent = null, testRepo = null, filename = null;
        //命令行读入并判断
        if (args.length != 0) {
            if (args[0].equals("--init") || args[0].equals("-i ")) {
                filename = args[1];
                init(filename);
            } else if (args[0].equals("-u") || args[0].equals("--user")) {
                if (args.length > 2) {
                    testUser = args[1];
                    if (args[2].equals("-e") || args[2].equals("--event")) {
                        testEvent = args[3];
                        countFromUser(testUser, testEvent);
                    } else if (args[2].equals("-r") || args[2].equals("repo")) {
                        testRepo = args[3];
                        testEvent = args[5];
                        countFromUserAndRepo(testUser + testRepo, testEvent);
                    }
                }
            } else if (args[0].equals("-r") || args[0].equals("--repo")) {
                testRepo = args[1];
                testEvent = args[3];
                countFromRepo(testRepo, testEvent);
            }
        }
    }
}