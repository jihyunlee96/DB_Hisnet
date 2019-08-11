import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Academic_Information_Root {
	static Connection conn;
	static Scanner keyboard;
	
	public static void print_menu(Connection in_conn, Scanner in_keyboard) {
			
		if (conn == null)
			conn = in_conn;
		if (keyboard == null)
			keyboard = in_keyboard;
			
		System.out.println("[ Academic Information ]");
		System.out.println("0. Back");
		System.out.println("1. Manage Grades");

		System.out.println();
		System.out.print("Input: ");
			
		int input = keyboard.nextInt();
			
		if (input == 0) {
			try {
				Main.print_menu(conn, keyboard, Main.student_no);
		
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if (input == 1) {
			try {
				manage_grades();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void manage_grades() throws SQLException {
		System.out.println("\n***********************************************************\n");
		System.out.println("[ Manage Grades ]\n");
		System.out.println("1. Give / change grades");
				
		System.out.println();
		System.out.print("Input: ");
		
		int input = keyboard.nextInt();
		
		if (input == 1) {
			System.out.println("\n***********************************************************\n");
			System.out.println("[ Give / Change Grades ]\n");		
			
			Statement stmt = (Statement) conn.createStatement();

			System.out.print("Type the subject code: ");
			String sub_code = keyboard.nextLine();
			
			if (sub_code.length() == 0)
				sub_code = keyboard.nextLine();
			
			ResultSet rs = stmt.executeQuery("SELECT course_id, yearAndSemester FROM CourseList "
					+ "WHERE subject_code = '" + sub_code + "';");
			
			if (rs.next() && !rs.getString("yearAndSemester").equalsIgnoreCase(Jihyun.current_yearAndSemester))
				manage_grades();
			
			int course_id = Integer.parseInt(rs.getString("course_id"));
			
			System.out.print("Type the student number: ");
			int student_no = keyboard.nextInt();
			
			System.out.print("Type the grade: ");
			String grade = keyboard.nextLine();
			
			if (grade.length() == 0)
				grade = keyboard.nextLine();
			
			double grade_point = 0;
			if (grade.equalsIgnoreCase("A+")) grade_point = 4.5;
			else if (grade.equalsIgnoreCase("A")) grade_point = 4.0;
			else if (grade.equalsIgnoreCase("B+")) grade_point = 3.5;
			else if (grade.equalsIgnoreCase("B")) grade_point = 3.0;
			else if (grade.equalsIgnoreCase("C+")) grade_point = 2.5;
			else if (grade.equalsIgnoreCase("C")) grade_point = 2.0;
			else if (grade.equalsIgnoreCase("F")) grade_point = 0;
			
			stmt.executeQuery("SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;");
			stmt.executeQuery("START TRANSACTION;");
			
			stmt.executeUpdate("UPDATE GradeList"
					+ " SET grade = '" + grade + "', grade_point = " + grade_point
					+ " WHERE student_num = " + student_no + " AND course_id = " + course_id + ";");
		
			stmt.executeQuery("COMMIT;");
			
			System.out.println("\n*** SUCCESSFULLY UPDATED GRADE *** ");
			
			System.out.println("\n* Press enter to exit *");
			
			String input2 = keyboard.nextLine();
			input2 = keyboard.nextLine();
			
			System.out.println("\n***********************************************************\n");
				Main.print_menu(conn, keyboard, Main.student_no);
						}
	}
}
