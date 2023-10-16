import java.util.HashMap;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        pgSql group9db = new pgSql("postgres", "212151",
                "localhost", "Enrolled", "5432");
        // 1.单表查询 求计算机系（‘CS’）和数学系（‘MA’）的学生学号、姓名和年龄
        String query1 = "select sno, sname, sage from student where sdept='CS' or sdept='MA'"; // 单表查询
        // 2.求姓名是以”李”开头的计算机系学生 年龄按升序排序
        String query2 = "select * from student \n" +
                "where sdept='CS' and sname like '李%'" +
                "order by sage\n";
        // 3.求选修“数据库原理”课程且成绩为80分以上的学生的学号、姓名和成绩
        String query3 = "select student.sno, student.sname, sc.grade " +
                "from student, course, sc " +
                "where course.cname='数据库原理' and sc.grade > 80 " +
                "and student.sno = sc.sno and course.cno = sc.cno";
        // 4.查询每个系的学生中年龄最大的学生姓名、年龄
        String query4 = "Select sname, max(sage)\n" +
                "From student\n" +
                "Group by sdept\n";
        String query5 = "select sname, student.sage, student.sdept " +
                "from student, ( " +
                "select max(sage) as sage, sdept " +
                "From student " +
                "Group by sdept " +
                ") as maxs " +
                "where student.sage = maxs.sage and student.sdept = maxs.sdept";
        // 5.求选修课程记录中，男生里成绩最好和最差的学生的姓名，性别和成绩，
        // 以及女生里成绩最好和最差的学生的姓名，性别和成绩
        String query6 = "select student.sname, student.ssex, sc.grade\n" +
                "from student, course, sc\n" +
                "where student.ssex='M' and \n" +
                "student.sno = sc.sno and course.cno = sc.cno \n" +
                "and sc.grade in (\n" +
                "(select max(grade) \n" +
                " from student, sc \n" +
                " where student.ssex='M' and student.sno = sc.sno),\n" +
                "(select min(grade) \n" +
                " from student, sc \n" +
                " where student.ssex='M' and student.sno = sc.sno)\n" +
                ")\n" +
                "union\n" +
                "select student.sname, student.ssex, sc.grade\n" +
                "from student, course, sc\n" +
                "where student.ssex='W' and \n" +
                "student.sno = sc.sno and course.cno = sc.cno \n" +
                "and sc.grade in (\n" +
                "(select max(grade) \n" +
                " from student, sc \n" +
                " where student.ssex='W' and student.sno = sc.sno),\n" +
                "(select min(grade) \n" +
                " from student, sc \n" +
                " where student.ssex='W' and student.sno = sc.sno)\n" +
                ")\n" +
                "order by ssex, grade \n";
        // 6.插入一条学生记录
        String query7 = "insert into Student values('20150020', '苏三', 'M', 20,'CS')";
        List<HashMap<String, Object>> select = group9db.Select(query6);
        //关流
        group9db.close();
        System.out.println(select.get(0).keySet());
        for (HashMap<String, Object> c: select) {
            System.out.println(c.values());
        }
    }
}