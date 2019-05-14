
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
 

public class Manage_Courses {
	static int course_id = 0;
	
	// 출력 
	public static void print_course_menu(Connection conn, Scanner keyboard) throws SQLException {
		
		System.out.println("[ Select an operation ]");
		System.out.println("0. Back");
		System.out.println("1. Initialize Course Table");
		System.out.println("2. Insert a Course");
		System.out.println("3. Delete a Course");
		
		System.out.println();
		System.out.print("Input: ");
		
		int input = keyboard.nextInt();
		
		System.out.println("\n***********************************************************\n");
		
		if (input == 0) {
			Root_User_Job.print_root_job(conn, keyboard);
		}
		
		else if (input == 1) {
			initialize_course_table(conn, keyboard);
		}

		else if (input == 2) {
			add_course(conn, keyboard);
		}

		else if (input == 3) {
			delete_courses(conn, keyboard);
		}		
	}
	
	// 코스 테이블 초기화 
	public static void initialize_course_table(Connection conn, Scanner keyboard) throws SQLException {
		
		System.out.println("[ Are you sure? (Y/N) ] ");
		
		System.out.println();
		System.out.print("Input: ");
		
		char input = keyboard.next().charAt(0);
		
		if (input == 'Y') {
			
			System.out.println("\n***********************************************************\n");
			System.out.println("[ Do you wanna drop the existing course table? (Y/N) ]");

			System.out.println();
			System.out.print("Input: ");
			input = keyboard.next().charAt(0);
			
			// drop the existing tables
			if (input == 'Y') {
				Statement stmt = (Statement) conn.createStatement();
								
				stmt.executeUpdate("DROP TABLE CourseList;");
			}
			
			// define the tables
			Statement stmt = (Statement) conn.createStatement();
			
			stmt.executeQuery("START TRANSACTION;");
			
			int result = stmt.executeUpdate("CREATE TABLE CourseList ("
					+ "course_id char(20), "
					+ "professor_num INT(20), "
					+ "subject_code char(20) NOT NULL, "
					// 전선 : MS, 전필 : ME, 교선 : LS, 교필 : LE
					+ "type char(2) CHECK (type IN ('MS', 'ME', 'LS', 'LE')), "
					+ "section INT(2) NOT NULL, "
					+ "title char(30) NOT NULL, "
					+ "credit INT(1) NOT NULL, "
					+ "times char(30) NOT NULL, "
					+ "classroom char(10) NOT NULL, "
					+ "current_personnel INT(3), "
					+ "max_personnel INT(3) NOT NULL, "
					+ "english_rate INT(3) NOT NULL, "
					// PF or A+
					+ "grade_type char(2) CHECK (grade_type IN ('PF', 'A+')), "
					+ "yearAndSemester char(10) NOT NULL, "
					+ "PRIMARY KEY (course_id, professor_num));");
			
			
			// preset
			result = stmt.executeUpdate("INSERT INTO CourseList(course_id, professor_num, subject_code, type, "
					+ "section, title, credit, times, classroom, current_personnel, max_personnel, english_rate, "
					+ "grade_type, yearAndSemester) "
					+ "VALUES (" + course_id++ + ", '" + 0 + "', '" + "ITP20003" + "', '"
					+ "MS" + "', '" + "01" + "', '" + "JAVA Programming" + "', '" + 3 + "', '" +  "Tue1,Tue2,Fri1,Fri2" + "', '"
					+ "NTH 413" + "', '" + 0 + "', '" + 37 + "', '" + 100 + "', '"
					+ "A+" + "', '" + "2019-01" + "')");

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

			
			stmt.executeQuery("COMMIT;");

			System.out.println("\n***********************************************************\n");
			System.out.println("[ Successfully initialized course table ]");
			System.out.println("\n***********************************************************\n");

			Root_User_Job.print_root_job(conn, keyboard);
		}
		
		System.out.println("\n***********************************************************\n");
		System.out.println("[ Returning to the back page ]");
		System.out.println("\n***********************************************************\n");
		
		Root_User_Job.print_root_job(conn, keyboard);
	}
	
	
	// 과목 추가 
	public static void add_course(Connection conn, Scanner keyboard) throws SQLException {
		
		System.out.println("[ Manage Courses - Add Course ]");
		System.out.println("\n***********************************************************\n");

		// add a course
		System.out.println("[ Fill out the following sections ]");
		
		// course_id will be put automatically
		
		// receive a professor number
		System.out.print("Professor Number: ");
		int professor_num = keyboard.nextInt();
			
		// receive a subject code
		System.out.print("Subject Code: ");
		String subject_code = keyboard.nextLine();	
		
		if (subject_code.length() == 0)
			subject_code = keyboard.nextLine();
		
		// receive a type
		System.out.print("Type(전선 : MS, 전필 : ME, 교선 : LS, 교필 : LE): ");
		String type = keyboard.nextLine();
		
		if (type.length() == 0)
			type = keyboard.nextLine();
		
		// receive a section
		System.out.print("Section: ");
		int section = keyboard.nextInt();
		
		// receive a title
		System.out.print("Title: ");
		String title = keyboard.nextLine();
		
		if (title.length() == 0)
			title = keyboard.nextLine();	
		
		// receive a credit
		System.out.print("Credit: ");
		int credit = keyboard.nextInt();
		
		// receive a times
		System.out.print("Times: ");
		String times = keyboard.nextLine();
		
		if (times.length() == 0)
			times = keyboard.nextLine();

		// receive a classroom
		System.out.print("Classroom: ");
		String classroom = keyboard.nextLine();
		
		if (classroom.length() == 0)
			classroom = keyboard.nextLine();

		// receive a current_personnel
		System.out.print("Current Personnel: ");
		int current_personnel = keyboard.nextInt();

		// receive a max_personnel
		System.out.print("Max Personnel: ");
		int max_personnel = keyboard.nextInt();
		
		// receive a english_rate
		System.out.print("English Rate(insert only numbers): ");
		int english_rate = keyboard.nextInt();
		
		// receive an grade_type
		System.out.print("Grade Type(PF or A+): ");
		String grade_type = keyboard.nextLine();
		
		if (grade_type.length() == 0)
			grade_type = keyboard.nextLine();
		
		// receive an yearAndSemester
		System.out.print("Year And Semester((ex)2014-01, summer : 03, winter : 04): ");
		String yearAndSemester = keyboard.nextLine();
		
		if (yearAndSemester.length() == 0)
			yearAndSemester = keyboard.nextLine();	
		
		Statement stmt = (Statement) conn.createStatement();
		
//		+ "course_id char(20), "
//		+ "professor_num INT(20), "
//		+ "subject_code char(20) NOT NULL, "
//		// 전선 : MS, 전필 : ME, 교선 : LS, 교필 : LE
//		+ "type char(2) CHECK (type IN ('MS', 'ME', 'LS', 'LE'), "
//		+ "section INT(2) NOT NULL, "
//		+ "title char(30) NOT NULL, "
//		+ "credit INT(1) NOT NULL, "
//		+ "times char(30) NOT NULL, "
//		+ "classroom char(10) NOT NULL, "
//		+ "current_personnel INT(3), "
//		+ "max_personnel INT(3) NOT NULL, "
//		+ "english_rate INT(3) NOT NULL, "
//		+ "grade_type char(3) NOT NULL, "
//		+ "yearAndSemester char(10) NOT NULL, "
//		+ "PRIMARY KEY (course_id, professor_num));");
		
		
		int result = stmt.executeUpdate("INSERT INTO CourseList(course_id, professor_num, subject_code, type, "
				+ "section, title, credit, times, classroom, current_personnel, max_personnel, english_rate, "
				+ "grade_type, yearAndSemester) "
				+ "VALUES (" + course_id++ + ", '" +  professor_num + "', '" + subject_code + "', '"
				+ type + "', '" + section + "', '" + title + "', '" + credit + "', '" +  times + "', '"
				+ classroom + "', '" + current_personnel + "', '" + max_personnel + "', '" + english_rate + "', '"
				+ grade_type + "', '" + yearAndSemester + "')");
		
		if (result == 1) {
			System.out.println("\n***********************************************************\n");
			System.out.println(title + " (" + section + ") is successfully added to CourseList");
			System.out.println("\n***********************************************************\n");
		}
	}
	
	
	// 과목 삭제  
	public static void delete_courses(Connection conn, Scanner keyboard) throws SQLException {
		
		System.out.println("[ Manage Courses - Delete Course ]");			
		System.out.println("\n***********************************************************\n");
		
		Statement stmt;
		int result = 0;
		stmt = (Statement) conn.createStatement();
		
		// 과목명 받기 
		System.out.println("[ Input the course title to delete ]\n");
		
		System.out.print("Input: ");
		String course_name = keyboard.nextLine();
		
		System.out.println();
		
		// 분반 이름 받기 
		System.out.println("[ Input the section number ]\n");
				
		System.out.print("Input: ");
		int section = keyboard.nextInt();
				
		System.out.println();
		
		// 지우기 
		result = stmt.executeUpdate("DELETE FROM CourseList WHERE title = " + course_name + " AND section = " + Integer.toString(section) + ";");

		
		if (result == 1) {
			System.out.println("\n***********************************************************\n");
			System.out.println("[ Successfully deleted course " + course_name + " " + section + "]");
			System.out.println("\n***********************************************************\n");
		}
		else {
			System.out.println("\n***********************************************************\n");
			System.out.println("[ FAILED - Cannot find course " + course_name + " " + section + "]");
			System.out.println("\n***********************************************************\n");
		}
		
		Root_User_Job.print_root_job(conn, keyboard);
	}
}
