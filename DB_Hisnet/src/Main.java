
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
	
	public static void main(String args[]) {
		
		// open the keyboard scanner
		Scanner keyboard = new Scanner(System.in);
		
		// open the connection to mysql
		try {
    			Class.forName("com.mysql.cj.jdbc.Driver");
    			
    			/* change the user name and password */
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db?serverTimezone=UTC&&useSSL=false", "hisnet", "1");
			
			System.out.println("********************* Hisnet Database *********************");
			
			print_menu(conn, keyboard);			
		} 
		
		catch(SQLException ex) {
			System.out.println("SQLException" + ex);
		} 
		
		catch(Exception ex) {
			System.out.println("Exception:" + ex);
		}
	}
	
	public static void print_menu(Connection conn, Scanner keyboard) throws SQLException {
		
		System.out.println("[ Select an operation ]");
		System.out.println("0. Initialize Tables");
		System.out.println("1. Manage Users (Add / Modify / Delete)");
		System.out.println("2. Search Users");
		
		System.out.println();
		System.out.print("Input: ");
		
		int input = keyboard.nextInt();
		
		System.out.println("\n***********************************************************\n");
		
		if (input == 0)
			Initialize_Tables.initialize_tables(conn, keyboard);
		
		else if (input == 1)
			Manage_Users.print_instructions(conn, keyboard);
		
		else if (input == 2) {
			// TO DO
		}
			
	}
}

