import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Initialize_Tables {
	
	public static void initialize_tables(Connection conn, Scanner keyboard) throws SQLException {
		
		System.out.println("[ Are you sure? (Y/F) ] ");
		
		System.out.println();
		System.out.print("Input: ");
		
		char input = keyboard.next().charAt(0);
		
		if (input == 'Y') {
			
			System.out.println("\n***********************************************************\n");
			System.out.println("[ Do you have to drop the existing tables? (Y/F) ]");

			System.out.println();
			System.out.print("Input: ");
			input = keyboard.next().charAt(0);
			
			// drop the existing tables
			if (input == 'Y') {
				Statement stmt = (Statement) conn.createStatement();
				
				stmt.executeQuery("START TRANSACTION;");
				
				stmt.executeUpdate("DROP TABLE StudentList;");
				stmt.executeUpdate("DROP TABLE ProfessorList;");
			}
			
			// define the tables
			Statement stmt = (Statement) conn.createStatement();
			
			stmt.executeQuery("START TRANSACTION;");
			
			int result = stmt.executeUpdate("CREATE TABLE StudentList ("
					+ "student_num INT(8), "
					+ "user_id char(20) NOT NULL, "
					+ "password char(20) NOT NULL, "
					+ "name char(12) NOT NULL, "
					+ "phone_num char(16), "
					+ "gender CHAR(1) CHECK (gender IN ('F', 'M')),"
					+ "high_school char(20), "
					+ "entrance_date DATE NOT NULL, "
					+ "graduate_date DATE, "
					+ "department char(20) NOT NULL, "
					+ "first_major char(20) NOT NULL, "
					+ "second_major char(20), "
					+ "minor char(20), "
					+ "rc char(20) CHECK (rc IN ('BETHEL', 'GRACE', 'KUYPER', 'ROTHEM', 'TORREY', 'CARMICHAEL')),"
					+ "e_mail char(20), "
					+ "address char(30), "
					+ "PRIMARY KEY (student_num));");
			
			result = stmt.executeUpdate("CREATE TABLE ProfessorList ("
					+ "professor_num INT(8), "
					+ "user_id char(20) NOT NULL, "
					+ "password char(20) NOT NULL, "
					+ "phone_num char(16) NOT NULL, "
					+ "name char(20) NOT NULL, "
					+ "rc char(20) CHECK (rc IN ('BETHEL', 'GRACE', 'KUYPER', 'ROTHEM', 'TORREY', 'CARMICHAEL')), "
					+ "e_mail char(20), "
					+ "address char(30), "
					+ "employment_date DATE NOT NULL, "
					+ "retirement_date DATE, "
					+ "PRIMARY KEY (professor_num));");
			
			stmt.executeQuery("COMMIT;");

			System.out.println("\n***********************************************************\n");
			System.out.println("[ Successfully initialized the tables ]");
			System.out.println("\n***********************************************************\n");

			Main.print_menu(conn, keyboard);
		}
		
		System.out.println("\n***********************************************************\n");
		System.out.println("[ Returning to the main menu ]");
		System.out.println("\n***********************************************************\n");
		
		Main.print_menu(conn, keyboard);
	}
}
