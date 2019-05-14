package courses;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;


public class My_Course_Schedule {
	
	public static void print_my_courses(Connection conn, Scanner keyboard) throws SQLException {
		
		// 현재 학기 시간표 출력 
		for(int i = 0; i < 10; i++) {
			System.out.println("[ Select an operation ]");
		}
		
		// 다시 돌아감 
		Course_Information.print_course_menu(conn, keyboard);
	}
}
