import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Hyewon {
	public static void start(Connection conn, Scanner keyboard ) throws SQLException {
		Manage_BoardList.print_instructions_root(conn, keyboard);
	}

	public static void start(Connection conn, Scanner keyboard, String student_no) throws SQLException {
		// TODO Auto-generated method stub
		Manage_BoardList.print_instructions_user(conn, keyboard);
	}
}
