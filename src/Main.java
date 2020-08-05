import java.io.*;
import java.util.Scanner;

/**
 * 功能：测试类
 * @author 江政良
 */
public class Main {

    private static void cmdOfInsert(StudentDatabaseOperate sdo, Scanner sc){
        System.out.println("----------------请问您一次需要插入多少名同学：----------------");
        int num = sc.nextInt();
        System.out.println("----------------请依次输入：姓名、学号、年龄、分数----------------");
        for(int i = 0;i < num;++i){
            String name = sc.next();
            String id = sc.next();
            int age = sc.nextInt();
            int score = sc.nextInt();
            Student s = new Student(name, id, age, score);
            try {
                sdo.insertStudent(s);
            }
            catch (Exception inertE){
                System.out.println("存在输入异常的同学！！");
            }
        }
    }

    private static void cmdOfFind(StudentDatabaseOperate sdo, Scanner sc){
        System.out.println("----------------请输入需要查找的同学的学号：----------------");
        Student ret = null;
        while(true) {
            String id = sc.nextLine();
            if("".equals(id)){
                continue;
            }
            try {
                ret = sdo.findStudent(id);
                break;
            } catch (Exception findE) {
                System.out.println("查找失败，重新输入id，检查id格式！");
            }
        }
        if(null != ret){
            System.out.println(ret);
        }
        else{
            System.out.println("查找到的学生不存在！");
        }
    }

    private static void cmdOfStudentSort(StudentDatabaseOperate sdo){
        sdo.sortStudentList();
        int len = sdo.getMyStudents().size();
        for(int i = 0;i < len;++i){
            System.out.println(sdo.getMyStudents().get(i));
        }
    }

    private static void cmdOfFilterToFile(StudentDatabaseOperate sdo){
        try {
            sdo.exitDatabase(false);
            sdo.filterByScore(false);
        }
        catch (Exception fileE){
            System.out.println("文件操作有误！");
        }
    }

    private static void cmdOfRemoveStudent(StudentDatabaseOperate sdo, Scanner sc){
        System.out.println("----------------请输入您要删除的学生的学号：----------------");
        String id = "";
        while(true) {
            id = sc.next();
            if(!"".equals(id)){
                break;
            }
        }
        int len = sdo.getSize(), pos = -1;
        for(int i = 0;i < len;++i){
            if(id.equals(sdo.getMyStudents().get(i).getId())){
                pos = i;
                break;
            }
        }
        try {
            if(-1 != pos){
                sdo.getMyStudents().remove(pos);
            }
            else{
                throw new IOException("error");
            }
            exit(sdo, true);
            sdo.filterByScore(true);
        } catch (IOException e) {
            System.out.println("文件内容删除有误！");
        }
    }

    private static void exit(StudentDatabaseOperate sdo, boolean book){
        try {
            sdo.exitDatabase(book);
        }
        catch (Exception exitE) {
            System.out.println("关闭系统错误！");
        }
    }

    private static void cmdOfResumeDataFromFile(StudentDatabaseOperate sdo){
        try {
            sdo.getDataFromFileToMem();
        } catch (Exception getDataE) {
            System.out.println("从数据库恢复数据到内存失败");
            System.exit(0);
        }
    }

    public static void main(String[] args){
        System.out.println("----------------欢迎使用学生管理后台----------------");
        String src = "StudentOfFiles\\StudentInfo.txt";
        StudentDatabaseOperate sdo = new StudentDatabaseOperate(src);
        cmdOfResumeDataFromFile(sdo);
        System.out.println("----------------请根据如下提示进行操作----------------");
        System.out.println("----------------输入0：清空数据库----------------");
        System.out.println("----------------输入1：加入学生----------------");
        System.out.println("----------------输入2：查找学生----------------");
        System.out.println("----------------输入3：学生等第排序----------------");
        System.out.println("----------------输入4：学生按成绩分类到文件----------------");
        System.out.println("----------------输入5：按照学号删除某同学的记录----------------");
        System.out.println("----------------输入“结束”：结束本系统----------------");
        Scanner sc = new Scanner(System.in);
        while(true) {
            int cmd = -1;
            String cmdStr = sc.next();
            if("结束".equals(cmdStr)){
                break;
            }
            try {
                cmd = Integer.parseInt(cmdStr);
            }
            catch (Exception cmdE){
                System.out.println("亲，这边不晓得你说什么哦，请重新输入！");
                continue;
            }
            switch (cmd) {
                case 0:
                    exit(sdo, true);
                    sdo.getMyStudents().clear();
                    break;
                case 1:
                    cmdOfInsert(sdo, sc);
                    break;
                case 2:
                    cmdOfFind(sdo, sc);
                    break;
                case 3:
                    cmdOfStudentSort(sdo);
                    break;
                case 4:
                    cmdOfFilterToFile(sdo);
                    break;
                case 5:
                    cmdOfRemoveStudent(sdo, sc);
                    break;
                default:
                    System.out.println("输入失败，请重新输入");
            }
            System.out.println("----------------请根据如下提示进行操作----------------");
            System.out.println("----------------输入0：清空数据库----------------");
            System.out.println("----------------输入1：加入学生----------------");
            System.out.println("----------------输入2：查找学生----------------");
            System.out.println("----------------输入3：学生等第排序----------------");
            System.out.println("----------------输入4：学生按成绩分类到文件----------------");
            System.out.println("----------------输入5：按照学号删除某同学的记录----------------");
            System.out.println("----------------输入“结束”：结束本系统----------------");
        }
        sc.close();
    }
}