

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;


public class Root_User_Job {
	
	public static void print_root_job(Connection conn, Scanner keyboard) throws SQLException {
		
		System.out.println("[ Select an operation ]");
		System.out.println("0. Back");
		System.out.println("1. Manage Courses");
		System.out.println("2. Manage Facilities");
		System.out.println("3. ");
		
		System.out.println();
		System.out.print("Input: ");
		
		int input = keyboard.nextInt();
		
		System.out.println("\n***********************************************************\n");
		
		if (input == 0) {
			Main.print_menu(conn, keyboard);
		}
		
		else if (input == 1) {
			Manage_Courses.print_course_menu(conn, keyboard);
		}
		
		else if (input == 2) {
			
		}
		
		else if (input == 3) {
			// TO DO
		}		
	}
	
}
