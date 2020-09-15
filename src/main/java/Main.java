import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson.*;
import org.apache.commons.io.FileUtils;

import javax.lang.model.element.NestingKind;
import javax.print.DocFlavor;

//还未处理的：init后的Map如何存储成文件判断时再读入
// 类型3判断有错，感觉是/的原因

public class Main {

    static Map<String, Result> map1 = new HashMap<>(); //个人 4 种事件的数量。
    static Map<String, Result> map2 = new HashMap<>();//每一个项目的 4 种事件的数量。
    static Map<String, Result> map3 = new HashMap<>();//每一个人在每一个项目的 4 种事件的数量。

    private static void init(String fileName) throws IOException {
        ArrayList<String> jsonList = new ArrayList<>();
        File dir = new File(fileName);
        File[] files = dir.listFiles(file -> file.getName().endsWith(".json"));
        try {
            for (File jsonFile : files) {
                FileReader fileReader = new FileReader(jsonFile);
                BufferedReader bf = new BufferedReader(fileReader);
                String str;
                while ((str = bf.readLine()) != null) {
                    //            System.out.println("str = "+ str);
                    jsonList.add(str);
                }
                bf.close();
                fileReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < jsonList.size(); i++) {
            //  System.out.println("i = "+ i);
            String tmp = jsonList.get(i);
            //     System.out.println(i);
            //   System.out.println("tmp = "+ tmp);
            tmp = justify(tmp);
            //   System.out.println(tmp);
            JSONObject jso1 = JSON.parseObject(tmp);
            String actor = jso1.getString("actor");
            JSONObject jso2 = JSON.parseObject(actor);
            String id = jso2.getString("login");

            String repo = jso1.getString("repo");
            JSONObject jso3 = JSON.parseObject(repo);
            String repoId = jso3.getString("name");

            String idAndRepoId = id + repoId;
            //    System.out.println(idAndRepoId);

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
            //test
//            System.out.println("id = "+ id +" repoId = "+ repoId + "AndId = " + idAndRepoId );
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
        //   System.out.println(map3.keySet().size());


        String s1 = JSONObject.toJSONString(map1);;
        String s2 = JSONObject.toJSONString(map2);
        String s3 = JSONObject.toJSONString(map3);
        // 将json写入文件

    //   System.out.println(s1);
        FileUtils.writeStringToFile(new File("map1.json"), s1, "UTF-8");
        FileUtils.writeStringToFile(new File("map2.json"), s2, "UTF-8");
        FileUtils.writeStringToFile(new File("map3.json"), s3, "UTF-8");

    }

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

    public static int output(Result r, String event) {
        int ans = 0;
        System.out.println("event = " + event);
        if (event.equals("PushEvent")) {
            ans = r.PushEvent;
        } else if (event.equals("IssueCommentEvent")) {
            ans = r.IssueCommentEvent;
        } else if (event.equals("PullRequestEvent")) {
            ans = r.PullRequestEvent;
        } else if (event.equals("IssuesEvent")) {
            ans = r.IssuesEvent;
        }
        //   System.out.println("ans = "+ ans);
        return ans;
    }

    public static void countFromUser(String user, String event) throws IOException {
        String tmp = event.substring(0,1);
        tmp = tmp.toLowerCase();
        event = tmp +event.substring(1,event.length());
        String s = FileUtils.readFileToString(new File("map1.json"), "UTF-8");
    //   System.out.println(s);
   //     System.out.println("user = "+ user + "event = " + event);
        JSONObject jsonObject = JSONObject.parseObject(s);
  //      System.out.println(jsonObject);
        JSONObject object = jsonObject.getJSONObject(user);
     //   System.out.println(object);
   //     System.out.println("result = " +result);
       if(object !=null){
           System.out.println( object.getInteger(event));
       }

    }

    public static void countFromRepo(String repo, String event) throws IOException {
        String tmp = event.substring(0,1);
        tmp = tmp.toLowerCase();
        event = tmp +event.substring(1,event.length());
        String s = FileUtils.readFileToString(new File("map2.json"), "UTF-8");
        JSONObject jsonObject = JSONObject.parseObject(s);
        JSONObject object = jsonObject.getJSONObject(repo);
        System.out.println(object.getInteger(event));
    }

    public static void countFromUserAndRepo(String idAndRepoId, String event) throws IOException {
        String tmp = event.substring(0,1);
        tmp = tmp.toLowerCase();
        event = tmp +event.substring(1,event.length());
        String s = FileUtils.readFileToString(new File("map3.json"), "UTF-8");
        JSONObject jsonObject = JSONObject.parseObject(s);
        JSONObject object = jsonObject.getJSONObject(idAndRepoId);
        if (object != null) {
            System.out.println( object.getInteger(event));
        }
    }

    public static void main(String[] args) throws IOException {
        // init("C:\\Users\\78430\\Desktop\\test\\json");
      //  init("C:\\Users\\78430\\Desktop\\test\\json");
//
//        args[0] = "-u";
//        args[1] ="cdupuis";
//        args[2] ="-r";
//        args[3] ="atomist/automation-client";
//        args[4] ="-e";
//        args[5] ="PushEvent";



        ArrayList<String> jsonList = null;
        String testUser = null, testEvent = null, testRepo = null, filename = null;
//        for(String arg : args){
//            System.out.println("arg =" +arg);
//        }
        //      System.out.println(map1.size()+" "+ map2.size()+map3.size());
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
         //  countFromUser("waleko","PushEvent");
        //java -Dfile.encoding=UTF-8 -jar GHAnalysis.jar -u  cdupuis -e PushEvent
        //   System.out.println(map1.size());
        //   System.out.println(map2.size());
        //  System.out.println(map3.size());

    }


}