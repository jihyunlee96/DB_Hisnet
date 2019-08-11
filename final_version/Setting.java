
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Setting {
	public static void initial_setting(Connection conn, Scanner keyboard) throws SQLException {
		
		Statement stmt = (Statement) conn.createStatement();
		
		stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Apply ("
				+ "id int(11) not null auto_increment, "
				+ "student_num int(11) not null, "
				+ "apply_type int(11) not null, " //0 입주신청, 1 상담신청, 2 교육신청, 3 외박신청
				+ "date int(11) null, " //여기에 날짜말고 다른게 오면, 그건 결핵증명서 아이디
				+ "checked bool null, "
				+ "PRIMARY KEY (id));");
		
		stmt.executeUpdate("CREATE TABLE IF NOT EXISTS DormInfo ("
				+ "id int(11) not null auto_increment, "
				+ "student_num int(11) not null, "
				+ "dorm_type varchar(16) DEFAULT 'HAYOUNGJO' not null, "
				+ "room_num int(11) not null, "
				+ "plus int(11) DEFAULT 0 not null, "
				+ "minus int(11) DEFAULT 0 not null, "
				+ "is_educated bool DEFAULT false not null, "
				+ "num_out int(11) DEFAULT 0 not null, "
				+ "PRIMARY KEY (id));");
		
		stmt.executeUpdate("CREATE TABLE IF NOT EXISTS AccessLog ("
				+ "id int(11) not null auto_increment, "
				+ "student_num int(11) not null, "
				+ "date int(11) not null, "
				+ "time int(11) not null, "
				+ "access_type int(11) not null, " // 0 나가기, 1 들어오기
				+ "punished bool null, "
				+ "PRIMARY KEY (id));");
		
		stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Tuberculosis ("
				+ "id int(11) not null auto_increment, "
				+ "student_num int(11) not null, "
				+ "filepath varchar(128) not null, "
				+ "PRIMARY KEY (id));");
		
		stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Activity ("
				+ "id int(11) not null auto_increment, "
				+ "student_num int(11) not null, "
				+ "my_activity text not null, "
				+ "checked bool null, " // 승인 or not
				+ "PRIMARY KEY (id));");
		
		stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Consult ("
				+ "id int(11) not null auto_increment, "
				+ "student_num int(11) not null, "
				+ "contents text not null, " //상담내용
				+ "date int(11) not null, "
				+ "PRIMARY KEY (id));");
	}
}
