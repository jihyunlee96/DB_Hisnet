
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
	
	static String student_no;
	
	public static void main(String args[]) {
		// open the keyboard scanner
		Scanner keyboard = new Scanner(System.in);
		
		// open the connection to mysql
		try {
    			Class.forName("com.mysql.cj.jdbc.Driver");
    			
    			/* change the user name and password */
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db?serverTimezone=UTC&&useSSL=false&&allowPublicKeyRetrieval=true", "root", "1");

			log_in(conn, keyboard);			
		} 
		
		catch(SQLException ex) {
			System.out.println("SQLException" + ex);
		} 
		
		catch(Exception ex) {
			System.out.println("Exception:" + ex);
		}
	}
	
	
	public static void log_in(Connection conn, Scanner keyboard) throws SQLException {
		
		System.out.println("********************* Hisnet Database *********************");
		System.out.println("[ Log-In ]");
		
		System.out.print("ID: ");		
		String id = keyboard.nextLine();
		
		if (id.length() == 0)
			id = keyboard.nextLine();
		
		Statement stmt = (Statement) conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from StudentList where user_id ='" + id + "';");
		
		if(!rs.next() && id.compareTo("root") != 0) {
			System.out.println("\n***********************************************************\n");
			System.out.println("[ ID doesn't exist! ]\n");
			
			Main.log_in(conn, keyboard);
		}
		
		System.out.print("Password: ");		
		String password = keyboard.nextLine();
		
		if (password.length() == 0)
			password = keyboard.nextLine();
		
		if (id.compareTo("root") == 0 && password.compareTo("1") == 0) {
			student_no = "root";
			
			System.out.println("\n***********************************************************\n");
			System.out.println("[ Successfully logged in as " + student_no + " ]");
			System.out.println("\n***********************************************************");
		}
		else if (rs.getString("password").compareTo(password) != 0) {
			System.out.println("\n***********************************************************\n");
			System.out.println("[ Incorrect password! ]\n");
			
			Main.log_in(conn, keyboard);
		}
		else {
			student_no = rs.getString("student_num");
		
			System.out.println("\n***********************************************************\n");
			System.out.println("[ Successfully logged in as " + student_no + " ]");
			System.out.println("\n***********************************************************");
		}
		
		print_menu(conn, keyboard);			

	}
	
	public static void print_menu(Connection conn, Scanner keyboard) throws SQLException {
		 
		// for root user
		if (student_no.compareTo("root") == 0 ) {
			System.out.println("* Current user: " + student_no + " *\n");
			System.out.println("[ Select an operation ]");
			System.out.println("0. Log Out");
			System.out.println("1. Initialize Tables");
			System.out.println("2. Manage Users (Add / Modify / Delete)");
			System.out.println("3. Search Users");
			
			System.out.println();
			System.out.print("Input: ");
			
			int input = keyboard.nextInt();
			
			System.out.println("\n***********************************************************\n");
			
			if (input == 0)
				log_in(conn, keyboard);
			
			else if (input == 1)
				Initialize_Tables.initialize_tables(conn, keyboard);
			
			else if (input == 2)
				Manage_Users.print_instructions(conn, keyboard);
			
			else if (input == 3) {
				// TO DO
			}		
		}
		// for normal user
		else {
			System.out.println("* Current user: " + student_no + " *\n");
			System.out.println("[ Select an operation ]");
			System.out.println("0. Log Out");
			System.out.println("1. Board");
			System.out.println("2. Course Information");
			System.out.println("3. Academic Information");
			System.out.println("4. Facility Reservation");
			System.out.println("5. Dormitory");
			
			System.out.println();
			System.out.print("Input: ");
			
			int input = keyboard.nextInt();
			
			System.out.println("\n***********************************************************\n");
			
			if (input == 0) {
				log_in(conn, keyboard);
			}
			else if (input == 1) {
				
			}
			
			else if (input == 2) {
				
			}
			
			else if (input == 3) {
				// TO DO
			}	
			
			else if (input == 4) {
				
			}
			
			else if (input == 5) {
				
			}
		}
	}
}

