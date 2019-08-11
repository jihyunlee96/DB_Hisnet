
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

	static String id;	//For root
	static String student_no;	//For user
	static String user_id;	//For student, professor id
	static boolean is_professor = false;
	static boolean is_TA = false;

	public static void main(String args[]) {
		// open the keyboard scanner
		Scanner keyboard = new Scanner(System.in);




		// open the connection to mysql
		try {
    			Class.forName("com.mysql.cj.jdbc.Driver");

    			/* change the user name and password */
//			Connection conn = DriverManager.getConnection("jdbc:mysql://172.17.217.136:3306/db?serverTimezone=UTC&&useSSL=false&&allowPublicKeyRetrieval=true", "4InQueue", "dbdb1234!");
    			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db?serverTimezone=UTC&&useSSL=false&&allowPublicKeyRetrieval=true", "root", "cho7611278");

    			Statement stmt = (Statement) conn.createStatement();
    			stmt.executeUpdate("create table IF NOT EXISTS StudentList(student_num int(8), user_id char(20) NOT NULL,"
    					+ " password char(20) NOT NULL, name char(12) NOT NULL, phone_num char(16), "
    					+ "gender char(1) CHECK (gender IN ('F', 'M')), high_school char(20), enterance_date DATE NOT NULL, "
    					+ "graduate_date DATE, department char(20) NOT NULL, first_major char(20) NOT NULL, "
    					+ "second_major char(40), minor char(20), rc char(20) CHECK (rc IN ('BETHEL', 'GRACE', 'KUYPER', 'ROTHEM', 'TORREY', 'CARMICHAEL')),"
    					+ " e_mail char(20), address char(30), PRIMARY KEY(student_num))");
    			stmt.executeQuery("COMMIT;");

    			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ProfessorList ("
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
		ResultSet rs = stmt.executeQuery("select StudentList.student_num, StudentList.user_id, StudentList.password "+
				"from StudentList where user_id= '"+ id +"' UNION select ProfessorList.professor_num, ProfessorList.user_id,"+
						" ProfessorList.password from ProfessorList"+" where user_id='"+ id + "';");

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
			System.out.println("student_num : " + student_no);
			user_id = rs.getString("user_id");

			Statement stmt3 = (Statement)conn.createStatement();
			ResultSet r = stmt3.executeQuery("select * from ProfessorList where user_id='" +user_id+ "';");
			while(r.next()) {
				String pf_id = r.getString("user_id");

				if(pf_id.equals(Main.user_id)) {
					is_professor = true;
				}
			}

			r = stmt3.executeQuery("select * from CourseList where ta_student_num=" +Main.student_no+ ";");

			while(r.next()) {
				String ta_num = r.getString("ta_student_num");
				System.out.println("ta_student_num: "+ ta_num);

				if(ta_num.equals(Main.student_no)) {
					is_TA = true;
				}
			}


			System.out.println("\n***********************************************************\n");
			System.out.println("[ Successfully logged in as " + student_no + " ]");
//			System.out.println("[ Successfully logged in as " + user_id + " ]");
			System.out.println("\n***********************************************************");
		}

		print_menu(conn, keyboard, student_no);

	}

	public static void print_menu(Connection conn, Scanner keyboard, String student_no) throws SQLException {
		set_student_no(student_no);

		// for root user
		if (student_no.compareTo("root") == 0 ) {
			System.out.println("* Current user: " + student_no + " *\n");
			System.out.println("[ Select an operation ]");
			System.out.println("0. Log Out");
			System.out.println("1. Course and Facility System");
			System.out.println("2. Dormitory System");
			System.out.println("3. Academic Information System");
			System.out.println("4. Board System");
			System.out.println("5. Initialize All Tables");


			System.out.println();
			System.out.print("Input: ");

			int input = keyboard.nextInt();

			System.out.println("\n***********************************************************\n");

			if (input == 0)
				log_in(conn, keyboard);

			else if (input == 1)
				Heeseok.start(conn, keyboard);

			else if (input == 2)
				try {
					M.start(conn, keyboard);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			else if (input == 3) {
				Jihyun.start(conn, keyboard);
			}

			else if (input == 4) {
				Hyewon. start(conn, keyboard);
			}

			else if (input == 5) {
				Initialize_Tables.initialize_tables(conn, keyboard);
			}
		}

		// for normal user
		else {
			System.out.println("* Current user: " + student_no + " *\n");
			System.out.println("[ Select an operation ]");
			System.out.println("0. Log Out");
			System.out.println("1. Course and Facility System");
			System.out.println("2. Dormitory System");
			System.out.println("3. Academic Information System");
			System.out.println("4. Board System");
			System.out.println("5. Initialize All Tables");

			System.out.println();
			System.out.print("Input: ");

			int input = keyboard.nextInt();

			System.out.println("\n***********************************************************\n");


			if (input == 0)
				log_in(conn, keyboard);

			else if (input == 1)
				Heeseok.start(conn, keyboard, student_no);

			else if (input == 2)
				try {
					U.start(conn, keyboard);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			else if (input == 3) {
				Jihyun.start(conn, keyboard, student_no);
			}

			else if (input == 4) {
				Hyewon.start(conn, keyboard, student_no);
			}
		}
	}

	// setter of student_id
		static public void set_student_no(String student_num) {
			student_no = student_num;
		}

	// getter of student_id
	static public String get_student_no() {
		return student_no;
	}
}
