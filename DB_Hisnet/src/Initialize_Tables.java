
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
 

public class Initialize_Tables {
	
	public static void initialize_tables(Connection conn, Scanner keyboard) throws SQLException {
		
		System.out.println("[ Are you sure? (Y/N) ] ");
		
		System.out.println();
		System.out.print("Input: ");
		
		char input = keyboard.next().charAt(0);
		
		if (input == 'Y') {
			
			System.out.println("\n***********************************************************\n");
			System.out.println("[ Do you have to drop the existing tables? (Y/N) ]");

			System.out.println();
			System.out.print("Input: ");
			input = keyboard.next().charAt(0);
			
			// drop the existing tables
			if (input == 'Y') {
				Statement stmt = (Statement) conn.createStatement();
								
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
					+ "first_major char(40) NOT NULL, "
					+ "second_major char(40), "
					+ "minor char(20), "
					+ "rc char(20) CHECK (rc IN ('BETHEL', 'GRACE', 'KUYPER', 'ROTHEM', 'TORREY', 'CARMICHAEL')),"
					+ "e_mail char(20), "
					+ "address char(30), "
					+ "PRIMARY KEY (student_num));");
			
//			result = stmt.executeUpdate("INSERT INTO StudentList(student_num, user_id, password, name, "
//					+ "phone_num, gender, high_school, entrance_date, graduate_date, department, first_major, second_major, "
//					+ "minor, rc, e_mail, address) "
//					+ "VALUES (" + Integer.toString(21700581) + ", '" +  "jlee" + "', '" + "123" + "', '"
//					+ "Jihyun Lee" + "', '" + "01074547621" + "', '" + "F" + "','" + "Lincoln School" + "', DATE '" 
//					+ "2017-03-01" + "', DATE '" + "2021-02-28" + "', '" + "CSEE" + "', '" + "Computer Science" + "', '"
//					+ "NULL" + "', '" + "NULL" + "', '" + "GRACE" + "', '"+ "21700581@handong.edu" + "', '" + "Busan" + "')");
//
//			result = stmt.executeUpdate("INSERT INTO StudentList(student_num, user_id, password, name, "
//					+ "phone_num, gender, high_school, entrance_date, graduate_date, department, first_major, second_major, "
//					+ "minor, rc, e_mail, address) "
//					+ "VALUES (" + Integer.toString(21700001) + ", '" +  "yjung" + "', '" + "321" + "', '"
//					+ "Yujin Jung" + "', '" + "01012347321" + "', '" + "F" + "','" + "Moonhyun High School" + "', DATE '" 
//					+ "2017-03-01" + "', DATE '" + "2021-02-28" + "', '" + "SESE" + "', '" + "Architecture" + "', '"
//					+ "NULL" + "', '" + "NULL" + "', '" + "KUYPER" + "', '"+ "21700001@handong.edu" + "', '" + "Suwon" + "')");
//			
//			result = stmt.executeUpdate("INSERT INTO StudentList(student_num, user_id, password, name, "
//					+ "phone_num, gender, high_school, entrance_date, graduate_date, department, first_major, second_major, "
//					+ "minor, rc, e_mail, address) "
//					+ "VALUES (" + Integer.toString(21400001) + ", '" +  "mkim" + "', '" + "12345" + "', '"
//					+ "Moonsu Kim" + "', '" + "01015227321" + "', '" + "M" + "','" + "Daeyeon High School" + "', DATE '" 
//					+ "2014-03-01" + "', DATE '" + "2020-02-28" + "', '" + "ISLL" + "', '" + "International Studies" + "', '"
//					+ "NULL" + "', '" + "NULL" + "', '" + "BETHEL" + "', '"+ "21400001@handong.edu" + "', '" + "Kimcheon" + "')");
//
//			result = stmt.executeUpdate("INSERT INTO StudentList(student_num, user_id, password, name, "
//					+ "phone_num, gender, high_school, entrance_date, graduate_date, department, first_major, second_major, "
//					+ "minor, rc, e_mail, address) "
//					+ "VALUES (" + Integer.toString(21400145) + ", '" +  "jjung" + "', '" + "1125" + "', '"
//					+ "Jusung Jung" + "', '" + "01015897321" + "', '" + "M" + "','" + "Jungang High School" + "', DATE '" 
//					+ "2014-03-01" + "', DATE '" + "2020-02-28" + "', '" + "CPSW" + "', '" + "Counselling Psychology" + "', '"
//					+ "NULL" + "', '" + "NULL" + "', '" + "BETHEL" + "', '"+ "21400145@handong.edu" + "', '" + "Busan" + "')");
			
			// 프로페서 테이블 
			result = stmt.executeUpdate("CREATE TABLE ProfessorList ("
					+ "professor_num INT(8), "
					+ "user_id char(20) NOT NULL, "
					+ "password char(20) NOT NULL, "
					+ "phone_num char(16) NOT NULL, "
					+ "name char(20) NOT NULL, "
					+ "rc char(20) CHECK (rc IN ('BETHEL', 'GRACE', 'KUYPER', 'ROTHEM', 'TORREY', 'CARMICHAEL')), "
					+ "e_mail char(20), "
					+ "address char(30), "
					+ "department char(20), "
					+ "employment_date DATE NOT NULL, "
					+ "retirement_date DATE, "
					+ "PRIMARY KEY (professor_num));");
			
			stmt.executeQuery("COMMIT;");

			System.out.println("\n***********************************************************\n");
			System.out.println("[ Successfully initialized the tables ]");
			System.out.println("\n***********************************************************\n");

			Main.print_menu(conn, keyboard, "root");
		}
		
		System.out.println("\n***********************************************************\n");
		System.out.println("[ Returning to the main menu ]");
		System.out.println("\n***********************************************************\n");
		
		Main.print_menu(conn, keyboard, "root");
	}
}
