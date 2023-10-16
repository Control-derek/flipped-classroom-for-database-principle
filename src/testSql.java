import java.sql.*;

public class testSql {
    public static Connection connect() {
        Connection c = null;  // 初始化
        try {
            String url = "jdbc:postgresql://localhost:5432/Enrolled";
            String username = "postgres";
            String password = "212151";
            Class.forName("org.postgresql.Driver");  // 注册driver
            c = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        return c;
    }

    // 查询第九组成员信息
    public static void select9() {
        // 建立连接
        Connection c = connect();
        // PreparedStatement初始化
        PreparedStatement stmt = null;
        try {
            String[] sno = {"20216395", "20216428", "20215530"};
            int length = sno.length;

            String queSql = "SELECT * FROM student where sno = ?";  // 查询
            stmt = c.prepareStatement(queSql);
            for (int i=0; i<length; ++i) {
                stmt.setString(1, sno[i]);
                ResultSet rs = stmt.executeQuery();  // 执行查询
                if (rs.next()) {
                    String no = rs.getString(1);
                    String name = rs.getString(2);
                    String sex = rs.getString(3);
                    int age = rs.getInt(4);
                    String dept = rs.getString(5);
                    System.out.println(no+"　"+name+" "+sex+" "+age+" "+dept);
                }
                else {
                    System.out.println(sno[i] + "查无此人");
                }
                rs.close();
            }
            // 依次关流
            stmt.close();
            c.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // 插入第九组成员信息
    public static void insert9() {
        // 建立连接
        Connection c = connect();
        // PreparedStatement初始化
        PreparedStatement stmt = null;
        try {
            String[] sno = {"20216395", "20216428", "20215530"};
            String[] sname = {"薛佳雯", "李育同", "王培东"};
            String[] ssex = {"W", "M", "M"};
            int[] sage = {20, 20, 20};
            String[] sdept = {"CS", "CS", "CS"};
            int length = sno.length;

            String insSql = "insert into Student values(?, ?, ?, ?, ?)";
            stmt = c.prepareStatement(insSql);
            for (int i=0; i<length; ++i) {
                stmt.setString(1, sno[i]);
                stmt.setString(2, sname[i]);
                stmt.setString(3, ssex[i]);
                stmt.setInt(4, sage[i]);
                stmt.setString(5, sdept[i]);
                if(stmt.executeUpdate()!=0) {  // 返回影响的行数
                    System.out.println(sno[i]+"插入成功！");
                }
            }
            // 依次关流
            stmt.close();
            c.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // 更新第九组成员信息
    public static void update9() {
        // 建立连接
        Connection c = connect();
        // PreparedStatement初始化
        PreparedStatement stmt = null;
        try {
            String[] sno = {"20216395", "20216428", "20215530"};
            int[] sage = {120, 120, 120};  // 过了一百年
            int length = sno.length;

            String updSql = "update Student set sage = ? where sno = ?";
            stmt = c.prepareStatement(updSql);
            for (int i=0; i<length; ++i) {
                stmt.setInt(1, sage[i]);
                stmt.setString(2, sno[i]);
                if(stmt.executeUpdate()!=0) { // 返回影响的行数
                    System.out.println(sno[i]+"更新成功！");
                }
            }
            // 依次关流
            stmt.close();
            c.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // 删除第九组成员信息
    public static void delete9() {
        // 建立连接
        Connection c = connect();
        // PreparedStatement初始化
        PreparedStatement stmt = null;
        try {
            String[] sno = {"20216395", "20216428", "20215530"};
            int length = sno.length;

            String delSql = "delete from Student where sno = ?";
            stmt = c.prepareStatement(delSql);
            for (int i=0; i<length; ++i) {
                stmt.setString(1, sno[i]);
                if(stmt.executeUpdate()!=0) {
                    System.out.println(sno[i]+"删除成功！");
                }
            }
            // 依次关流
            stmt.close();
            c.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void main(String[] args) {
        /*根据程序变量执行SQL语句*/
        select9();
        insert9();
        select9();
        update9();
        select9();
        delete9();
        select9();
    }
}


