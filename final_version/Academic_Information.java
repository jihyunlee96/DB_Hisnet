import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Academic_Information {
	static Connection conn;
	static Scanner keyboard;
	
	public static void print_menu(Connection in_conn, Scanner in_keyboard) {
		
		if (conn == null)
			conn = in_conn;
		if (keyboard == null)
			keyboard = in_keyboard;
		
		System.out.println("[ Academic Information ]");
		System.out.println("0. Back");
		System.out.println("1. Retrieve My Academic Information");
		System.out.println("2. Current Grades / Rank");
		System.out.println("3. Retrieve Scholarship Information");
		System.out.println("4. Retrieve Social Services Information");
		System.out.println("5. Graduate Evaluation");
		System.out.println("6. Tuition Payment History");
		System.out.println("7. Team Information");
		
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
		
		else if (input == 1)
			retrieve_info();
		
		else if (input == 3) {
			try {
				retrieve_scholarship_info();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (input == 2) {
			try {
				retrieve_grades();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (input == 4) {
			try {
				retrieve_social_service_info();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (input == 5) {
			try {
				graduate_evaluation();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (input == 6) {
			try {
				show_tuition_payment();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if (input == 7) {
			try {
				show_team_information();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void retrieve_info() {

		System.out.println("\n***********************************************************\n");
		System.out.println("[ Retrieve My Academic Information ]\n");
		System.out.println("0. Back");
		System.out.println("1. Current Academic Status");
		System.out.println("2. Change of Major History");

		
		System.out.println();
		System.out.print("Input: ");
		
		int input = keyboard.nextInt();
		
		if (input == 0) {
			try {
				Main.print_menu(conn, keyboard, Main.student_no);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if (input == 1) {
			try {
				current_academic_status();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if (input == 2) {
			try {
				show_major_history();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	public static void current_academic_status() throws SQLException {
		
		Statement stmt = (Statement) conn.createStatement();
						
		ResultSet rs = stmt.executeQuery("SELECT * FROM StudentList WHERE student_num = " + Main.student_no + ";");
		
		if (rs.next()) {
			System.out.println("\n***********************************************************\n");
			System.out.println("[ Current Academic Status ]");
			System.out.println("- Student Number: " + rs.getString("student_num"));
			System.out.println("- Name: " + rs.getString("name"));
			System.out.println("- ID: " + rs.getString("user_id"));
			System.out.println("- Phone #: " + rs.getString("phone_num"));
			System.out.println("- High School: " + rs.getString("high_school"));
			System.out.println("- Entrance Date: " + rs.getString("entrance_date"));
			System.out.println("- (Expected) Graduate Date: " + rs.getString("graduate_date"));
			System.out.println("- Department: " + rs.getString("department"));
			System.out.println("- First Major: " + rs.getString("first_major"));
			System.out.println("- Second Major: " + rs.getString("second_major"));
			System.out.println("- Minor: " + rs.getString("minor"));
			System.out.println("- RC: " + rs.getString("rc"));
			System.out.println("- E-mail: " + rs.getString("e_mail"));
			System.out.println("- Address: " + rs.getString("address"));
		}
		
		System.out.println("\n* Press enter to exit *");
		
		String input = keyboard.nextLine();
		input = keyboard.nextLine();
		
		System.out.println("\n***********************************************************\n");
		Main.print_menu(conn, keyboard, Main.student_no);
	}
	
	public static void show_major_history() throws SQLException {
		Statement stmt = (Statement) conn.createStatement();
		
		ResultSet rs = stmt.executeQuery("SELECT * FROM MajorChangeList "
						+ "WHERE student_num = " + Main.student_no + " ORDER BY date ASC;");
		
		int count = 1;
		
		System.out.println("\n***********************************************************\n");
		System.out.println("[ Change of Major History ]");
		
		while (rs.next()) {
			
			System.out.print(Integer.toString(count) + ". ");
			System.out.print(rs.getString("title") + " | " + rs.getString("date")
			+ " | Before: " + rs.getString("major1_before") + ", " + rs.getString("major2_before")
			+ " | After: " + rs.getString("major1_after") + ", " + rs.getString("major2_after"));
			System.out.println(" |");
			
			count ++;
		}
	
		System.out.println("\n* Press enter to exit *");
		
		String input = keyboard.nextLine();
		input = keyboard.nextLine();
		
		System.out.println("\n***********************************************************\n");
		Main.print_menu(conn, keyboard, Main.student_no);
	}
	
	public static void show_tuition_payment() throws SQLException {
		Statement stmt = (Statement) conn.createStatement();
		
		ResultSet rs = stmt.executeQuery("SELECT * FROM PaidTuitionFeeList "
						+ "WHERE student_num = " + Main.student_no + 
						"  ORDER BY year_and_semester ASC;");
		
		int count = 1;
		
		System.out.println("\n***********************************************************\n");
		System.out.println("[ Tuition Payment History ]");
		
		while (rs.next()) {
			
			System.out.print(Integer.toString(count) + ". ");
			System.out.print(rs.getString("year_and_semester") + " | Expected Payment: " + rs.getString("expected_payment")
			+ " | Completed Payment: " + rs.getString("completed_payment") + " | Campus Scholarship: " + rs.getString("scholarship_campus")
			+ " | Off-Campus Scholarship: " + rs.getString("scholarship_off_campus") + " | Refund: " + rs.getString("refund"));
			System.out.println(" |");
			
			count ++;
		}

		System.out.println("\n* Press enter to exit *");
		
		String input = keyboard.nextLine();
		input = keyboard.nextLine();
		
		System.out.println("\n***********************************************************\n");
		Main.print_menu(conn, keyboard, Main.student_no);	
	}
	
	public static void show_team_information() throws SQLException {
		System.out.println("\n***********************************************************\n");
		System.out.println("[ Team Information ]\n");
		System.out.println("1. Current Team Members");
		System.out.println("2. Team History");
		
		System.out.println();
		System.out.print("Input: ");
		
		int input = keyboard.nextInt();
		
		if (input == 1) {
			System.out.println("\n***********************************************************\n");
			System.out.println("[ Current Team Members ]\n");		
			
			Statement stmt = (Statement) conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT team_id FROM (TeamMemberList NATURAL LEFT OUTER JOIN TeamList) "
							+ "WHERE (TeamMemberList.student_num = " + Main.student_no + " "
							+ "AND TeamList.year_and_semester = "+ Jihyun.current_year_and_semester +");");
			rs.next();
			int my_team_id = Integer.parseInt(rs.getString("team_id"));
			
			int count = 1;
			
			rs = stmt.executeQuery("SELECT name, student_num FROM (TeamMemberList NATURAL LEFT OUTER JOIN StudentList) "
					+ "WHERE team_id = " + my_team_id + ";");
			
			while(rs.next()) {
				System.out.println(count + ". " + rs.getString("name") + "(" + rs.getString("student_num") + ")");
				count ++;
			}
			
		}
		else if (input == 2) {
			
			Statement stmt = (Statement) conn.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT year_and_semester, name "
						+ "FROM ((TeamMemberList NATURAL LEFT OUTER JOIN TeamList) NATURAL LEFT OUTER JOIN ProfessorList) "
						+ "WHERE student_num = " + Main.student_no + " ORDER BY TeamList.year_and_semester ASC;");
			
			int count = 1;
						
			while(rs.next()) {
				System.out.println(count + ". " + rs.getString("year_and_semester") + " | " + rs.getString("name") + "'s Team");
				count ++;
			}
		}
		
		System.out.println("\n* Press enter to exit *");
		
		String input2 = keyboard.nextLine();
		input2 = keyboard.nextLine();
		
		System.out.println("\n***********************************************************\n");
		Main.print_menu(conn, keyboard, Main.student_no);	
	}
	
	public static void retrieve_scholarship_info() throws SQLException {
		
		System.out.println("\n***********************************************************\n");
		System.out.println("[ Retrieve Scholarship Information ]\n");	
		System.out.println("1. Received Scholarship In Certain Year & Semester");
		System.out.println("2. Received Scholarship History");
		
		System.out.println();
		System.out.print("Input: ");
		
		int input = keyboard.nextInt();
		
		Statement stmt = (Statement) conn.createStatement();
			
		if (input == 1) {
			System.out.println("\n***********************************************************\n");
			System.out.println("[ Received Scholarship In Certain Year & Semester ]\n");	
			System.out.println("Type Year and Semester (e.g. 201801)");
			
			input = keyboard.nextInt();
			
			ResultSet rs = stmt.executeQuery("SELECT scholarship_campus, scholarship_off_campus FROM PaidTuitionFeeList "
								+ "WHERE student_num = " + Main.student_no + " AND year_and_semester = "+ input +";");

			int count = 1;
			
			System.out.println();

			while(rs.next()) {
				System.out.println(count + ". Year & Semester: " + input + " | Campus Scholarship: " + rs.getString("scholarship_campus") + 
						" | Off-Campus Scholarship: " + rs.getString("scholarship_off_campus"));
				count ++;
			}
		}
		else if (input == 2) {
			System.out.println("\n***********************************************************\n");
			System.out.println("[ Received Scholarship History ]\n");	

			ResultSet rs = stmt.executeQuery("SELECT year_and_semester, scholarship_campus, scholarship_off_campus "
								+ "FROM PaidTuitionFeeList "
								+ "WHERE student_num = " + Main.student_no + " ORDER BY year_and_semester ASC;");
				
			int count = 1;
			
			System.out.println();

			while(rs.next()) {
				System.out.println(count + ". Year & Semester: " + rs.getString("year_and_semester") + " | Campus Scholarship: " + rs.getString("scholarship_campus") + 
						" | Off-Campus Scholarship: " + rs.getString("scholarship_off_campus"));
				count ++;
			}	
		}
			
		System.out.println("\n* Press enter to exit *");
		
		String input2 = keyboard.nextLine();
		input2 = keyboard.nextLine();
		
		System.out.println("\n***********************************************************\n");
		Main.print_menu(conn, keyboard, Main.student_no);	
		
	}
	
	public static void retrieve_social_service_info() throws SQLException {
		
		System.out.println("\n***********************************************************\n");
		System.out.println("[ Retrieve Social Service Information ]\n");	
		System.out.println("1. Social Service History");
		
		System.out.println();
		System.out.print("Input: ");
		
		int input = keyboard.nextInt();
		
		Statement stmt = (Statement) conn.createStatement();
			
		if (input == 1) {
			System.out.println("\n***********************************************************\n");
			System.out.println("[ Social Service History ]\n");	
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM (VolunteerList NATURAL LEFT OUTER JOIN VolunteerProgramList) "
								+ "WHERE student_num = " + Main.student_no + " ORDER BY yearAndSemester ASC;");

			int count = 1;
			
			System.out.println();

			while(rs.next()) {
				System.out.println(count + ". Year & Semester: " + rs.getString("yearAndSemester") + " | Subject Code: " + rs.getString("subject_code") + 
						" | Title: " + rs.getString("title") + " | Location: " + rs.getString("location"));
				System.out.println("- " + rs.getString("content"));
				count ++;
			}
		}
		else if (input == 2) {
			System.out.println("\n***********************************************************\n");
			System.out.println("[ Received Scholarship History ]\n");	

			ResultSet rs = stmt.executeQuery("SELECT year_and_semester, scholarship_campus, scholarship_off_campus "
								+ "FROM PaidTuitionFeeList "
								+ "WHERE student_num = " + Main.student_no + " ORDER BY year_and_semester ASC;");
				
			int count = 1;
			
			System.out.println();

			while(rs.next()) {
				System.out.println(count + ". Year & Semester: " + rs.getString("year_and_semester") + " | Campus Scholarship: " + rs.getString("scholarship_campus") + 
						" | Off-Campus Scholarship: " + rs.getString("scholarship_off_campus"));
				count ++;
			}	
		}
			
		System.out.println("\n* Press enter to exit *");
		
		String input2 = keyboard.nextLine();
		input2 = keyboard.nextLine();
		
		System.out.println("\n***********************************************************\n");
		Main.print_menu(conn, keyboard, Main.student_no);			
	}
	
	public static void retrieve_grades() throws SQLException {
		
		System.out.println("\n***********************************************************\n");
		System.out.println("[ Retrieve Current Grades / Ranks ]\n");	
		System.out.println("1. Current Semester's Grades");
		
		System.out.println();
		System.out.print("Input: ");
		
		int input = keyboard.nextInt();
		
		Statement stmt = (Statement) conn.createStatement();
			
		if (input == 1) {
			System.out.println("\n***********************************************************\n");
			System.out.println("[ Current Semester's Grades ]\n");	
			
			ResultSet rs = stmt.executeQuery("SELECT GradeList.grade, ProfessorList.name, CourseList.title, "
							+ "CourseList.section, CourseList.subject_code, CourseList.credit"
							+ " FROM ((GradeList NATURAL LEFT OUTER JOIN CourseList) NATURAL LEFT OUTER JOIN ProfessorList)"
							+ "WHERE student_num = " + Main.student_no + " "
							+ "AND CourseList.yearAndSemester = '" + Jihyun.current_yearAndSemester + "';");
			
			int count = 1;
			
			System.out.println();

			while(rs.next()) {
				System.out.println(count + ". Subject Code: " + rs.getString("subject_code") + " | Course Title: " + rs.getString("CourseList.title") + 
						 " | Section: " + rs.getString("CourseList.section") + " | Professor: " + rs.getString("ProfessorList.name") + " | Credit: " + rs.getString("CourseList.credit")
						 +  " | Grade: " + rs.getString("GradeList.grade"));
				count ++;
			}
			
			// 총 취득학점, 평점 평균 계산
			rs = stmt.executeQuery("SELECT avg(grade_point), sum(credit) FROM (GradeList NATURAL LEFT OUTER JOIN CourseList)"
							+ "WHERE student_num = " + Jihyun.student_no
							+ " AND CourseList.yearAndSemester = '" + Jihyun.current_yearAndSemester + "';");
			rs.next();
			System.out.println("Total credit Earned In This Semester: " + rs.getString("sum(credit)"));
			System.out.println("Average GPA: " + rs.getString("avg(grade_point)"));
		}
		else if (input == 2) {
			// TO BE DONE
		}
			
		System.out.println("\n* Press enter to exit *");
		
		String input2 = keyboard.nextLine();
		input2 = keyboard.nextLine();
		
		System.out.println("\n***********************************************************\n");
		Main.print_menu(conn, keyboard, Main.student_no);		
	}
	
	public static void graduate_evaluation() throws SQLException {
		
		System.out.println("\n***********************************************************\n");
		System.out.println("[ Graduate Evaluation ]\n");	
		
		Statement stmt = (Statement) conn.createStatement();

		
		ResultSet rs = stmt.executeQuery("SELECT sum(CourseList.credit) FROM (GradeList NATURAL LEFT OUTER JOIN CourseList) "
						+ "WHERE GradeList.student_num = " + Jihyun.student_no + ";");
		rs.next();
		
		int sum_credit = Integer.parseInt(rs.getString("sum(CourseList.credit)"));
		
		System.out.println("The sum of credits you have earned: " + sum_credit );
		
		if (sum_credit >= 130)
				System.out.println("*** YOU ARE ALLOWED TO GRADUATE *** ");
		else
			System.out.println("*** YOU ARE NOT ALLOWED TO GRADUATE *** ");
			
		System.out.println("\n* Press enter to exit *");
		
		String input2 = keyboard.nextLine();
		input2 = keyboard.nextLine();
		
		System.out.println("\n***********************************************************\n");
		Main.print_menu(conn, keyboard, Main.student_no);		
	}

}

