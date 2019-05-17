
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Manage_Users {
	
	static Connection conn;
	static Scanner keyboard;
	
	public static void print_instructions(Connection in_conn, Scanner in_keyboard) {
		
		if (conn == null)
			conn = in_conn;
		
		if (keyboard == null)
			keyboard = in_keyboard;
		
		System.out.println("[ Manage Users ]");
		System.out.println("1. Add a user");
		System.out.println("2. Modify a user information");
		System.out.println("3. Delete a user");
		
		System.out.println();
		System.out.print("Input: ");
		
		int input = keyboard.nextInt();
		
		System.out.println("\n***********************************************************\n");
		
		if (input == 1) {
			try {
				add_user();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if (input == 2) {
			try {
				modify_user();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		else if (input == 3) {
			try {
				delete_user();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		try {
			Main.print_menu(conn, keyboard, "root");
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void add_user() throws SQLException {
		
		System.out.println("[ Manage Users - Add User ]");
		System.out.println("Input the type of user to add (1: Student, 2: Professor)\n");
		System.out.print("Input: ");
		
		int input = keyboard.nextInt();
		
		System.out.println("\n***********************************************************\n");

		// add a student
		if (input == 1) {
			System.out.println("[ Fill out the following sections ]");
			
			// receive a student number
			System.out.print("Student number: ");
			int student_num = keyboard.nextInt();
			
			// receive an user id
			System.out.print("User id: ");
			String user_id = keyboard.nextLine();

			if (user_id.length() == 0)
				user_id = keyboard.nextLine();
						
			// receive a password
			System.out.print("Password: ");
			String password = keyboard.nextLine();	
			
			if (password.length() == 0)
				password = keyboard.nextLine();
			
			// receive a name
			System.out.print("Name: ");
			String name = keyboard.nextLine();
			
			if (name.length() == 0)
				name = keyboard.nextLine();
			
			// receive a phone number
			System.out.print("Phone #: ");
			String phone_num = keyboard.nextLine();
			
			if (phone_num.length() == 0)
				phone_num = keyboard.nextLine();
			
			// receive a gender
			System.out.print("Gender (M/F): ");
			String gender = keyboard.nextLine();
			
			if (gender.length() == 0)
				gender = keyboard.nextLine();	
			
			// receive a high school
			System.out.print("High school: ");
			String high_school = keyboard.nextLine();
			
			if (high_school.length() == 0)
				high_school = keyboard.nextLine();
			
			// receive an entrance_date
			System.out.print("Entrance date (ex. 2017-03-01): ");
			String entrance_date = keyboard.nextLine();
			
			if (entrance_date.length() == 0)
				entrance_date = keyboard.nextLine();

			// receive a graduate_date
			System.out.print("Graduate date (ex. 2017-03-01): ");
			String graduate_date = keyboard.nextLine();
			
			if (graduate_date.length() == 0)
				graduate_date = keyboard.nextLine();

			// receive a department
			System.out.print("Department: ");
			String department = keyboard.nextLine();
			
			if (department.length() == 0)
				department = keyboard.nextLine();	

			// receive a first major
			System.out.print("First major: ");
			String first_major = keyboard.nextLine();
			
			if (first_major.length() == 0)
				first_major = keyboard.nextLine();	
			
			// receive a second major
			System.out.print("Second major: ");
			String second_major = keyboard.nextLine();
			
			if (second_major.length() == 0)
				second_major = keyboard.nextLine();	
			
			// receive a minor
			System.out.print("Minor: ");
			String minor = keyboard.nextLine();
			
			if (minor.length() == 0)
				minor = keyboard.nextLine();	
			
			// receive a rc
			System.out.print("RC: ");
			String rc = keyboard.nextLine();
			
			if (rc.length() == 0)
				rc = keyboard.nextLine();	
			
			// receive an e-mail address
			System.out.print("E-mail address: ");
			String e_mail = keyboard.nextLine();
			
			if (e_mail.length() == 0)
				e_mail = keyboard.nextLine();
			
			// receive an address
			System.out.print("Address: ");
			String address = keyboard.nextLine();
			
			if (address.length() == 0)
				address = keyboard.nextLine();	
			
			Statement stmt = (Statement) conn.createStatement();
			
			int result = stmt.executeUpdate("INSERT INTO StudentList(student_num, user_id, password, name, "
					+ "phone_num, gender, high_school, entrance_date, graduate_date, department, first_major, second_major, "
					+ "minor, rc, e_mail, address) "
					+ "VALUES (" + Integer.toString(student_num) + ", '" +  user_id + "', '" + password + "', '"
					+ name + "', '" + phone_num + "', '" + gender + "','" + high_school + "', DATE '" 
					+ entrance_date + "', DATE '" + graduate_date + "', '" + department + "', '" + first_major + "', '"
					+ second_major + "', '" + minor + "', '" + rc + "', '"+ e_mail + "', '" + address + "')");
			
			if (result == 1) {
				System.out.println("\n***********************************************************\n");
				System.out.println(name + " (" + student_num + ") is successfully added to StudentList");
				System.out.println("\n***********************************************************\n");
			}
			
		}
		
		// add a professor
		else {
			System.out.println("[ Fill out the following sections ]");
			
			// receive a student number
			System.out.print("Professor number: ");
			int prof_num = keyboard.nextInt();
			
			// receive an user id
			System.out.print("User id: ");
			String user_id = keyboard.nextLine();

			if (user_id.length() == 0)
				user_id = keyboard.nextLine();
			
			// receive a password
			System.out.print("Password: ");
			String password = keyboard.nextLine();

			if (password.length() == 0)
				password = keyboard.nextLine();
			
			// receive a phone number
			System.out.print("Phone #: ");
			String phone_num = keyboard.nextLine();
			
			if (phone_num.length() == 0)
				phone_num = keyboard.nextLine();

			// receive a name
			System.out.print("Name: ");
			String name = keyboard.nextLine();

			if (name.length() == 0)
				name = keyboard.nextLine();
			
			// receive a rc
			System.out.print("RC: ");
			String rc = keyboard.nextLine();

			if (rc.length() == 0)
				rc = keyboard.nextLine();
			
			// receive an e-mail
			System.out.print("E-mail address: ");
			String e_mail = keyboard.nextLine();

			if (e_mail.length() == 0)
				e_mail = keyboard.nextLine();
			
			// receive an address
			System.out.print("Address: ");
			String address = keyboard.nextLine();

			if (address.length() == 0)
				address = keyboard.nextLine();
								
			// receive an employment_date
			System.out.print("Employment date (ex. 2017-03-01): ");
			String employment_date = keyboard.nextLine();
			
			if (employment_date.length() == 0)
				employment_date = keyboard.nextLine();

			// receive a retirement_date
			System.out.print("Graduate date (ex. 2017-03-01): ");
			String retirement_date = keyboard.nextLine();
			
			if (retirement_date.length() == 0)
				retirement_date = keyboard.nextLine();
			
			
			Statement stmt = (Statement) conn.createStatement();
			
			int result = stmt.executeUpdate("INSERT INTO ProfessorList(professor_num, user_id, password, "
					+ "phone_num, name, rc, e_mail, address, employment_date, retirement_date) "
					+ "VALUES (" + prof_num + ", '" + user_id + "', '" + password + "', " + phone_num
					+ ", '" + name + "', '" + rc + "', '" + e_mail + "', '" + address + "', DATE '" + employment_date
					+ "', DATE '" + retirement_date + "');");
			
			if (result == 1) {
				System.out.println("\n***********************************************************\n");
				System.out.println(name + " (" + prof_num + ") is successfully added to ProfessorList");
				System.out.println("\n***********************************************************\n");
			}
		}
		
	}
	
	public static void modify_user() throws SQLException {
		System.out.println("[ Manage Users - Modify User Information ]");
		System.out.println("Input the type of user to modify (1: Student, 2: Professor)\n");
		System.out.print("Input: ");
		
		int input = keyboard.nextInt();
				
		Statement stmt;
		int result = 0;
		stmt = (Statement) conn.createStatement();
		
		if (input == 1) {
			System.out.println("\n***********************************************************\n");
			System.out.println("[ Student Number? ]\n");
			System.out.print("Input: ");
			
			int st_num = keyboard.nextInt();
			
			ResultSet rs = stmt.executeQuery("select * from StudentList where student_num ='" + st_num + "';");
			
			if (!rs.next()) {
				System.out.println("\n***********************************************************\n");
				System.out.println("[ Non-existing student number ]");
				System.out.println("\n***********************************************************\n");

				Main.print_menu(conn, keyboard, "root");
			}
			
			System.out.println("\n***********************************************************\n");
			System.out.println("[ Select an attribute to modify ]");
			System.out.println("1. password");
			System.out.println("2. name");
			System.out.println("3. phone_num");
			System.out.println("4. graduate date");
			System.out.println("5. minor");
			System.out.println("6. rc");
			System.out.println("7. e_mail");
			System.out.println("8. address");
			System.out.println();
			
			System.out.print("Input: ");
			input = keyboard.nextInt();
			
			String attribute;
			if (input == 1)
				attribute = "password";
			else if (input == 2)
				attribute = "name";
			else if (input == 3)
				attribute = "phone_num";			
			else if (input == 4)
				attribute = "graduate_date";
			else if (input == 5)
				attribute = "minor";
			else if (input == 6)
				attribute = "rc";
			else if (input == 7)
				attribute = "e_mail";
			else 
				attribute = "address";
			
			System.out.println("\n***********************************************************\n");
			System.out.println("[ Student ("+ Integer.toString(st_num) + ")s current ("
					+ attribute + ") value ]: " + rs.getString(attribute) + "\n");
			
			System.out.print("Type the new value: ");
			
			String input2 = keyboard.nextLine();
			if (input2.length() == 0)
				input2 = keyboard.nextLine();
			
			result = stmt.executeUpdate("UPDATE StudentList set " + attribute + " = '" + input2 + "';");
			
			System.out.println();
			
			System.out.println("\n***********************************************************\n");
			System.out.println("[ Successfully modified student " + Integer.toString(st_num) + " ]");
			System.out.println("\n***********************************************************\n");	
		}
		else {
			System.out.println("\n***********************************************************\n");
			System.out.println("[ Professor Number? ]\n");
			System.out.print("Input: ");
			
			int pf_num = keyboard.nextInt();
			
			ResultSet rs = stmt.executeQuery("select * from ProfessorList where professor_num ='" + pf_num + "';");
			
			if (!rs.next()) {
				System.out.println("\n***********************************************************\n");
				System.out.println("[ Non-existing professor number ]");
				System.out.println("\n***********************************************************\n");

				Main.print_menu(conn, keyboard, "root");
			}
			
			System.out.println("\n***********************************************************\n");
			System.out.println("[ Select an attribute to modify ]");
			System.out.println("1. password");
			System.out.println("2. name");
			System.out.println("3. phone_num");
			System.out.println("4. retirement_date");
			System.out.println("5. rc");
			System.out.println("6. e_mail");
			System.out.println("7. address");
			System.out.println();
			
			System.out.print("Input: ");
			input = keyboard.nextInt();
			
			String attribute;
			if (input == 1)
				attribute = "password";
			else if (input == 2)
				attribute = "name";
			else if (input == 3)
				attribute = "phone_num";			
			else if (input == 4)
				attribute = "retirement_date";
			else if (input == 5)
				attribute = "rc";
			else if (input == 6)
				attribute = "e_mail";
			else 
				attribute = "address";

			
			System.out.println("\n***********************************************************\n");
			System.out.println("[ Professor ("+ Integer.toString(pf_num) + ")s current ("
					+ attribute + ") value ]: " + rs.getString(attribute) + "\n");
			
			System.out.print("Type the new value: ");
			
			String input2 = keyboard.nextLine();
			if (input2.length() == 0)
				input2 = keyboard.nextLine();
			
			if (input != 3)
				result = stmt.executeUpdate("UPDATE ProfessorList set " + attribute + " = '" + input2 + "';");
			else
				result = stmt.executeUpdate("UPDATE ProfessorList set " + attribute + " = " + input2 + ";");
			
			System.out.println();
			
			System.out.println("\n***********************************************************\n");
			System.out.println("[ Successfully modified professor " + Integer.toString(pf_num) + " ]");
			System.out.println("\n***********************************************************\n");
			
		}

		Main.print_menu(conn, keyboard, "root");
	}

	
	public static void delete_user() throws SQLException {
		System.out.println("[ Manage Users - Delete User ]");
		System.out.println("Input the type of user to delete (1: Student, 2: Professor)\n");
		System.out.print("Input: ");
		
		int input = keyboard.nextInt();
		
		System.out.println("\n***********************************************************\n");
		
		Statement stmt;
		int result = 0;
		stmt = (Statement) conn.createStatement();
		
		if (input == 1) {
			System.out.println("[ Input the student number of the user to delete ]\n");
			
			System.out.print("Input: ");
			input = keyboard.nextInt();
			
			System.out.println();
			
			result = stmt.executeUpdate("DELETE FROM StudentList where student_num =" + Integer.toString(input) + ";");

			
			if (result == 1) {
				System.out.println("\n***********************************************************\n");
				System.out.println("[ Successfully deleted student " + Integer.toString(input) + " ]");
				System.out.println("\n***********************************************************\n");
			}
			else {
				System.out.println("\n***********************************************************\n");
				System.out.println("[ FAILED - Cannot find student " + Integer.toString(input) + " ]");
				System.out.println("\n***********************************************************\n");
			}
		}
		else {
			System.out.println("[ Input the professor number of the user to delete ]\n");
			
			System.out.print("Input: ");
			input = keyboard.nextInt();
			
			System.out.println();
			
			result = stmt.executeUpdate("DELETE FROM ProfessorList where professor_num =" + Integer.toString(input) + ";");

			
			if (result == 1) {
				System.out.println("\n***********************************************************\n");
				System.out.println("[ Successfully deleted professor " + Integer.toString(input) + " ]");
				System.out.println("\n***********************************************************\n");
			}
			else {
				System.out.println("\n***********************************************************\n");
				System.out.println("[ FAILED - Cannot find professor " + Integer.toString(input) + " ]");
				System.out.println("\n***********************************************************\n");
			}
		}
		
		Main.print_menu(conn, keyboard, "root");

	}
	
}
