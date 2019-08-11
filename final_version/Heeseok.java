import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

public class Heeseok {
	// 루트 유저 
	public static void start(Connection conn, Scanner keyboard ) throws SQLException {
		System.out.println("[ Select an operation ]");
		System.out.println("0. Back");
		System.out.println("1. Manage Course Table");
		System.out.println("2. Manage Facility Table");
		
		
		System.out.println();
		System.out.print("Input: ");
		
		int input = keyboard.nextInt();
		
		System.out.println("\n***********************************************************\n");
		
		if (input == 0)
			Main.log_in(conn, keyboard);
		
		else if (input == 1) {
			Manage_Courses.print_course_menu(conn, keyboard);
		}
		
		else if (input == 2) {
			Manage_Facilities.print_facility_menu(conn, keyboard);
		}	
	}

	// 일반 유저 
	public static void start(Connection conn, Scanner keyboard, String student_no) throws SQLException {
		System.out.println("[ Select an operation ]");
		System.out.println("0. Log Out");
		System.out.println("1. Course Information");
		System.out.println("2. Facility Information");
		
		System.out.println();
		System.out.print("Input: ");
		
		int input = keyboard.nextInt();
		
		System.out.println("\n***********************************************************\n");
		
		if (input == 0)
			Main.log_in(conn, keyboard);
		
		else if (input == 1)
			Course_Information.print_course_menu(conn, keyboard);
		
		else if (input == 2)
			Facility_Information.print_facility_menu(conn, keyboard);
				
	}	
}
