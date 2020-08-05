import java.util.Comparator;

/**
 * 功能：操作对象：学生类
 * @author jiangzl
 */
class Student {
    private String name, id;
    private int age, score;

    public Student(String name, String id, int age, int score){
        this.name = name;
        this.id = id;
        this.age = age;
        this.score = score;
    }

    public String getName(){
        return name;
    }

    public int getScore(){
        return score;
    }

    public String getId(){
        return id;
    }

    public int getAge(){
        return age;
    }

    @Override
    public String toString(){
        return new String("姓名：" + name + " 年龄：" + age + " 分数：" + score);
    }
}
