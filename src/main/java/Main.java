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
      //  String tmp  = "{\"id\":\"2489653575\",\"type\":\"ReleaseEvent\",\"actor\":{\"id\":9699715,\"login\":\"D0nBilb0\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/D0nBilb0\",\"avatar_url\":\"https://avatars.githubusercontent.com/u/9699715?\"},\"repo\":{\"id\":26546687,\"name\":\"D0nBilb0/AAL\",\"url\":\"https://api.github.com/repos/D0nBilb0/AAL\"},\"payload\":{\"action\":\"published\",\"release\":{\"url\":\"https://api.github.com/repos/D0nBilb0/AAL/releases/818686\",\"assets_url\":\"https://api.github.com/repos/D0nBilb0/AAL/releases/818686/assets\",\"upload_url\":\"https://uploads.github.com/repos/D0nBilb0/AAL/releases/818686/assets{?name}\",\"html_url\":\"https://github.com/D0nBilb0/AAL/releases/tag/v0.2\",\"id\":818686,\"tag_name\":\"v0.2\",\"target_commitish\":\"master\",\"name\":\"Version 0.2\",\"draft\":false,\"author\":{\"login\":\"D0nBilb0\",\"id\":9699715,\"avatar_url\":\"https://avatars.githubusercontent.com/u/9699715?v=3\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/D0nBilb0\",\"html_url\":\"https://github.com/D0nBilb0\",\"followers_url\":\"https://api.github.com/users/D0nBilb0/followers\",\"following_url\":\"https://api.github.com/users/D0nBilb0/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/D0nBilb0/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/D0nBilb0/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/D0nBilb0/subscriptions\",\"organizations_url\":\"https://api.github.com/users/D0nBilb0/orgs\",\"repos_url\":\"https://api.github.com/users/D0nBilb0/repos\",\"events_url\":\"https://api.github.com/users/D0nBilb0/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/D0nBilb0/received_events\",\"type\":\"User\",\"site_admin\":false},\"prerelease\":true,\"created_at\":\"2015-01-01T15:04:30Z\",\"published_at\":\"2015-01-01T15:05:26Z\",\"assets\":[],\"tarball_url\":\"https://api.github.com/repos/D0nBilb0/AAL/tarball/v0.2\",\"zipball_url\":\"https://api.github.com/repos/D0nBilb0/AAL/zipball/v0.2\",\"body\":\"with bath tub and radio\"}},\"public\":true,\"created_at\":\"2015-01-01T15:05:26Z\"}";
       //  JSONObject obj=JSON.parseObject(tmp);//将json字符串转换为json对象
       // System.out.println(obj);
//        List<Student> list=new ArrayList<>();
//        Student student=new Student("bob",24);
//        Student student12=new Student("lily", 23);
//        list.add(student);
//        list.add(student12);
//        System.out.println("*******javaBean  to jsonString*******");
 //         String str1=JSON.toJSONString(student);
//           System.out.println(str1);
//          System.out.println(JSON.toJSONString(list));
//        System.out.println();

     //   System.out.println("*******jsonString to jsonObject*****");
    //    JSONObject jso1=JSON.parseObject(tmp);
      //  System.out.println(jso1);
      //  System.out.println(jso1.getString("id"));

        System.out.println(tmp);
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
                String subStr = tmp.substring(leftIndex,rightIndex);
                System.out.println(subStr);
                leftCnt = 0 ;
                rightCnt = 0;
                leftIndex = -1 ;
            }
        }
     //   System.out.println("i = " + i );
     //  System.out.println(tmp.charAt(893));
       // System.out.println(leftCnt +" " +rightCnt);
        System.out.println();

    }
}
