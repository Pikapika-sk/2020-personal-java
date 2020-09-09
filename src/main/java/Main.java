import com.alibaba.fastjson.JSON;
import  com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.JSONToken;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Main {




    public static void main(String[] args) {
        List<Person> listPersons = new ArrayList<>();
        listPersons.add(new Person(15, "John Doe", new Date()));
        listPersons.add(new Person(25, "xqq", new Date()));
        String s = JSON.toJSONString(listPersons);
        System.out.println(s);
    }
}

