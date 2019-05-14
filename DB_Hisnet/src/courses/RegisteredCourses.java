package courses;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;


public class RegisteredCourses {
	
	public static void print_course_menu(Connection conn, Scanner keyboard) throws SQLException {
		
		System.out.println("[ Select an operation ]");
		System.out.println("1. My Course Schedule");
		System.out.println("2. Search Courses");
		System.out.println("3. Registered Courses");
		
		System.out.println();
		System.out.print("Input: ");
		
		int input = keyboard.nextInt();
		
		System.out.println("\n***********************************************************\n");
		
		if (input == 1) {
			
		}
//			Initialize_Tables.initialize_tables(conn, keyboard);
		
		else if (input == 2) {
			
		}
//			Manage_Users.print_instructions(conn, keyboard);
		
		else if (input == 3) {
			// TO DO
		}		
	}
}
