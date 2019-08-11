
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
 

public class Manage_Facilities {
	static int facility_id = 0;
	
	// 출력 
	public static void print_facility_menu(Connection conn, Scanner keyboard) throws SQLException {
		
		System.out.println("[ Select an operation ]");
		System.out.println("0. Back");
		System.out.println("1. Initialize Facility Table");
		System.out.println("2. Insert a Facility");
		System.out.println("3. Delete a Facility");

		System.out.println();
		System.out.print("Input: ");
		
		int input = keyboard.nextInt();
		
		System.out.println("\n***********************************************************\n");
		
		if (input == 0) {
			Heeseok.start(conn, keyboard);
		}
		
		else if (input == 1) {
			initialize_facility_table(conn, keyboard);
		}

		else if (input == 2) {
			add_facility(conn, keyboard);
		}

		else if (input == 3) {
			delete_facility(conn, keyboard);
		}		
	}
	
	// 코스 테이블 초기화 
	public static void initialize_facility_table(Connection conn, Scanner keyboard) throws SQLException {
		
		System.out.println("[ Are you sure? (Y/N) ] ");
		
		System.out.println();
		System.out.print("Input: ");
		
		char input = keyboard.next().charAt(0);
		
		if (input == 'Y') {
			
			System.out.println("\n***********************************************************\n");
			System.out.println("[ Do you wanna drop the existing facility table? (Y/N) ]");

			System.out.println();
			System.out.print("Input: ");
			input = keyboard.next().charAt(0);
			
			// drop the existing tables
			if (input == 'Y') {
				Statement stmt = (Statement) conn.createStatement();
								
				stmt.executeUpdate("DROP TABLE FacilityList;");
			}
			
			// define the tables
			Statement stmt = (Statement) conn.createStatement();
			
			stmt.executeQuery("START TRANSACTION;");
			
			int result = stmt.executeUpdate("CREATE TABLE FacilityList ("
					+ "facility_id INT(20), "
					+ "name char(20) NOT NULL, "
					+ "PRIMARY KEY (facility_id));");
			
			
//			// preset
//			result = stmt.executeUpdate("INSERT INTO CourseList(course_id, professor_num, subject_code, type, "
//					+ "section, title, credit, times, classroom, current_personnel, max_personnel, english_rate, "
//					+ "grade_type, yearAndSemester) "
//					+ "VALUES (" + course_id++ + ", '" + 0 + "', '" + "ITP20003" + "', '"
//					+ "MS" + "', '" + "01" + "', '" + "JAVA Programming" + "', '" + 3 + "', '" +  "Tue1,Tue2,Fri1,Fri2" + "', '"
//					+ "NTH 413" + "', '" + 0 + "', '" + 37 + "', '" + 100 + "', '"
//					+ "A+" + "', '" + "2019-01" + "')");

			
			stmt.executeQuery("COMMIT;");

			System.out.println("\n***********************************************************\n");
			System.out.println("[ Successfully initialized facility table ]");
			System.out.println("\n***********************************************************\n");

			Heeseok.start(conn, keyboard);
		}
		
		System.out.println("\n***********************************************************\n");
		System.out.println("[ Returning to the back page ]");
		System.out.println("\n***********************************************************\n");
		
		Heeseok.start(conn, keyboard);
	}
	
	
	// 과목 추가 
	public static void add_facility(Connection conn, Scanner keyboard) throws SQLException {
		
		System.out.println("[ Manage Facilities - Add Facility ]");
		System.out.println("\n***********************************************************\n");

		// add a course
		System.out.println("[ Fill out the following sections ]");
		
		// get the last facility_id value 
		Statement stmt = (Statement) conn.createStatement();
		ResultSet rs = stmt.executeQuery("select facility_id from FacilityList where facility_id = (select max(facility_id) from FacilityList);");
		
		if(rs.next()) {
			facility_id = rs.getInt(1);
			facility_id++;
		}
		
		
		// receive a name
		System.out.print("Facility Name: ");
		String facility_name = keyboard.nextLine();	
		
		if (facility_name.length() == 0)
			facility_name = keyboard.nextLine();
		
		
		stmt = (Statement) conn.createStatement();
		
		int result = stmt.executeUpdate("INSERT INTO FacilityList(facility_id, name) "
				+ "VALUES (" + facility_id++ + ", '" + facility_name + "')");
		
		if (result == 1) {
			System.out.println("\n***********************************************************\n");
			System.out.println(facility_name + " is successfully added to FacilityList");
			System.out.println("\n***********************************************************\n");
		}
		
		System.out.println("\n***********************************************************\n");
		System.out.println("[ Returning to the back page ]");
		System.out.println("\n***********************************************************\n");
		Heeseok.start(conn, keyboard);
	}
	
	
	// 과목 삭제  
	public static void delete_facility(Connection conn, Scanner keyboard) throws SQLException {
		
		System.out.println("[ Manage Facilities - Delete Facility ]");			
		System.out.println("\n***********************************************************\n");
		
		Statement stmt;
		int result = 0;
		stmt = (Statement) conn.createStatement();
		
		// 과목명 받기 
		System.out.println("[ Input the facility name to delete ]\n");
		
		System.out.print("Facility Name: ");
		String facility_name = keyboard.nextLine();
		
		if (facility_name.length() == 0)
			facility_name = keyboard.nextLine();
		
		System.out.println();
		
		// 지우기 
		result = stmt.executeUpdate("DELETE FROM FacilityList WHERE name = '" + facility_name + "';");

		
		if (result == 1) {
			System.out.println("\n***********************************************************\n");
			System.out.println("[ Successfully deleted facility " + facility_name + " ]");
			System.out.println("\n***********************************************************\n");
		}
		else {
			System.out.println("\n***********************************************************\n");
			System.out.println("[ FAILED - Cannot find facility " + facility_name + " ]");
			System.out.println("\n***********************************************************\n");
		}
		
		System.out.println("\n***********************************************************\n");
		System.out.println("[ Returning to the back page ]");
		System.out.println("\n***********************************************************\n");
		Heeseok.start(conn, keyboard);
	}
}
