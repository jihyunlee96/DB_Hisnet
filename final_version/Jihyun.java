import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Jihyun {
	
	static int current_year_and_semester = 201901;
	static String current_yearAndSemester = "2019-01";
	static int student_no;
	
	public static void start(Connection conn, Scanner keyboard) {
		Academic_Information_Root.print_menu(conn, keyboard);
		
	}
	
	public static void start(Connection conn, Scanner keyboard, String in_student_no ) {
		student_no = Integer.parseInt(in_student_no);
		Academic_Information.print_menu(conn, keyboard);
	}
}
