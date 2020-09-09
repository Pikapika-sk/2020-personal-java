import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class Person {
    @JSONField(name = "AGE")
    private int age;

    @JSONField(name = "FULL NAME")
    private String fullName;

    @JSONField(name = "DATE OF BIRTH")
    private Date dateOfBirth ;

    public Person(int age, String fullName, Date dateOfBirth) {
        super();
        this.age = age;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
    }

    public int getAge() {
        return age;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getFullName() {
        return fullName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
