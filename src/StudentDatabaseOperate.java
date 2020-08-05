import java.io.*;
import java.util.*;

/**
 * 功能：文本数据库操作类
 * @author jiangzl
 */
class StudentDatabaseOperate {
    private List<Student> myStudents;
    private File file;

    public StudentDatabaseOperate(String filename){
        this.file = new File(filename);
        this.myStudents = new ArrayList<>();
    }

    public void sortStudentList(){
        Collections.sort(myStudents, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                if(s1.getScore() != s2.getScore()){
                    return s2.getScore() - s1.getScore();
                }
                else{
                    return s1.getName().compareTo(s2.getName());
                }
            }
        });
    }

    public Student findStudent(String id) throws Exception {
        if("".equals(id)){
            throw new Exception("id is null");
        }
        int len = myStudents.size();
        for(int i = 0;i < len;++i){
            Student s = myStudents.get(i);
            if(id.equals(s.getId())) {
                return s;
            }
        }
        return null;
    }

    public int getSize(){
        return myStudents.size();
    }

    public List<Student> getMyStudents(){
        return myStudents;
    }

    public void insertStudent(Student s) throws IOException {
        myStudents.add(s);
        BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(file, true),
                        "UTF-8"
                )
        );
        String strLine = new String(s.getName() + "\t" + s.getId() + "\t" + s.getAge() + "\t" + s.getScore());
        bw.write(strLine + "\t\n");
        bw.flush();
        bw.close();
    }

    private void addIntoLevelFiles(char level, Student s) throws IOException {
        BufferedWriter bw = null;
        switch (level)
        {
            case 'A':
                bw = new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(new File("StudentOfFiles\\Score-perfect.txt"), true),
                                "UTF-8"
                        )
                );
                break;
            case 'B':
                bw = new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(new File("StudentOfFiles\\Score-pass.txt"), true),
                                "UTF-8"
                        )
                );
                break;
            default:
                bw = new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(new File("StudentOfFiles\\Score-bad.txt"), true),
                                "UTF-8"
                        )
                );
        }
        String strLine = new String(s.getName() + "\t" + s.getId() + "\t" + s.getAge() + "\t" + s.getScore());
        bw.write(strLine + "\t\n");
        bw.flush();
        bw.close();
    }

    public void filterByScore(boolean book) throws IOException {
        int len = myStudents.size();
        for(int i = 0;i < len;++i){
            Student s = myStudents.get(i);
            if(null == s){
                continue;
            }
            if(book){
                insertStudent(s);
            }
            switch (s.getScore() / 30)
            {
                case 3:
                    addIntoLevelFiles('A', s);
                    break;
                case 2:
                    addIntoLevelFiles('B', s);
                    break;
                default:
                    addIntoLevelFiles('C', s);
            }
        }
    }

    public void exitDatabase(boolean book) throws IOException {
        BufferedWriter bw;
        if(book) {
            bw = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(new File("StudentOfFiles\\StudentInfo.txt")),
                            "UTF-8"
                    )
            );
            bw.write("");
            bw.flush();
        }
        bw = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(new File("StudentOfFiles\\Score-perfect.txt")),
                        "UTF-8"
                )
        );
        bw.write("");
        bw.flush();
        bw = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(new File("StudentOfFiles\\Score-pass.txt")),
                        "UTF-8"
                )
        );
        bw.write("");
        bw.flush();
        bw = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(new File("StudentOfFiles\\Score-bad.txt")),
                        "UTF-8"
                )
        );
        bw.write("");
        bw.flush();
        bw.close();
    }

    public void getDataFromFileToMem() throws Exception {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(new File("StudentOfFiles\\StudentInfo.txt")),
                        "UTF-8"
                )
        );
        String strLine = null;
        while((strLine = br.readLine()) != null){
            strLine = strLine.replaceAll(" {2,}", " ");
            String[] splitArray = strLine.split("\\ |\\t");
            if(splitArray.length != 4){
                throw new Exception("读取信息有误！");
            }
            else{
                myStudents.add(new Student(splitArray[0], splitArray[1],
                        Integer.parseInt(splitArray[2]), Integer.parseInt(splitArray[3])));
            }
        }
        br.close();
    }
}
