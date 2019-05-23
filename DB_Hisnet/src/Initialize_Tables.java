
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
				stmt.executeUpdate("DROP TABLE Apply;");
				stmt.executeUpdate("DROP TABLE DormInfo;");
				stmt.executeUpdate("DROP TABLE AccessLog;");
				stmt.executeUpdate("DROP TABLE Tuberculosis;");
				stmt.executeUpdate("DROP TABLE Activity;");
				stmt.executeUpdate("DROP TABLE Consult;");
				stmt.executeUpdate("DROP TABLE CourseList;");
				stmt.executeUpdate("DROP TABLE FacilityList;");
				stmt.executeUpdate("DROP TABLE TakeCourse;");
				stmt.executeUpdate("DROP TABLE BookFacility;");
				stmt.executeUpdate("DROP TABLE BoardList;");
				stmt.executeUpdate("DROP TABLE AssignmentList;");
				stmt.executeUpdate("DROP TABLE VolunteerProgramList;");
				stmt.executeUpdate("DROP TABLE VolunteerList;");
				stmt.executeUpdate("DROP TABLE GradeList;");
				stmt.executeUpdate("DROP TABLE TeamList;");
				stmt.executeUpdate("DROP TABLE TeamMemberList;");
				stmt.executeUpdate("DROP TABLE MajorChangeList;");
				stmt.executeUpdate("DROP TABLE TuitionFeeList;");
				stmt.executeUpdate("DROP TABLE PaidTuitionFeeList;");
				
			}
			
			// define the tables
			Statement stmt = (Statement) conn.createStatement();
			
			stmt.executeQuery("START TRANSACTION;");
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS StudentList ("
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
			
			// 프로페서 테이블 
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
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Apply ("
					+ "id int(11) not null auto_increment, "
					+ "student_num int(11) not null, "
					+ "apply_type int(11) not null, " //0 입주신청, 1 상담신청, 2 교육신청, 3 외박신청
					+ "date int(11) null, " //여기에 날짜말고 다른게 오면, 그건 결핵증명서 아이디
					+ "checked bool null, "
					+ "PRIMARY KEY (id));");
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS DormInfo ("
					+ "id int(11) not null auto_increment, "
					+ "student_num int(11) not null, "
					+ "dorm_type varchar(16) DEFAULT 'HAYOUNGJO' not null, "
					+ "room_num int(11) not null, "
					+ "plus int(11) DEFAULT 0 not null, "
					+ "minus int(11) DEFAULT 0 not null, "
					+ "is_educated bool DEFAULT false not null, "
					+ "num_out int(11) DEFAULT 0 not null, "
					+ "PRIMARY KEY (id));");
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS AccessLog ("
					+ "id int(11) not null auto_increment, "
					+ "student_num int(11) not null, "
					+ "date int(11) not null, "
					+ "time int(11) not null, "
					+ "access_type int(11) not null, " // 0 나가기, 1 들어오기
					+ "punished bool null, "
					+ "PRIMARY KEY (id));");
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Tuberculosis ("
					+ "id int(11) not null auto_increment, "
					+ "student_num int(11) not null, "
					+ "filepath varchar(128) not null, "
					+ "PRIMARY KEY (id));");
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Activity ("
					+ "id int(11) not null auto_increment, "
					+ "student_num int(11) not null, "
					+ "my_activity text not null, "
					+ "checked bool null, " // 승인 or not
					+ "PRIMARY KEY (id));");
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Consult ("
					+ "id int(11) not null auto_increment, "
					+ "student_num int(11) not null, "
					+ "contents text not null, " //상담내용
					+ "date int(11) not null, "
					+ "PRIMARY KEY (id));");
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS CourseList ("
					+ "course_id INT(20), "
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
					+ "ta_student_num INT(8) NOT NULL, "
					+ "PRIMARY KEY (course_id, professor_num));");
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS FacilityList ("
					+ "facility_id INT(20), "
					+ "name char(20) NOT NULL, "
					+ "PRIMARY KEY (facility_id));");
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS TakeCourse ("
					+ "student_num int(8), "
					+ "course_id int(20), "
					+ "taking_time datetime NOT NULL, "
					+ "PRIMARY KEY (student_num, course_id));");
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS BookFacility ("
					+ "student_num int(8), "
					+ "facility_id int(20), "
					+ "date date, "
					+ "time int(2), "
					+ "booking_time datetime NOT NULL, "
					+ "PRIMARY KEY (student_num, facility_id, date, time));");
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS BoardList ("
					+ "board_id int auto_increment, "
					+ "title TEXT NOT NULL, "
					+ "contents LONGTEXT NOT NULL, "
					+ "date DATE NOT NULL, "
					+ "views int NOT NULL, "
					+ "type_id int(1), "
					+ "course_id char(30), "
					+ "department char(20), "
					+ "writer char(20),"
					+ "PRIMARY KEY (board_id));");
			
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS AssignmentList ("
					+ "assignment_id int(1), "	
					+ "course_id int(11) NOT NULL, "
					+ "title char(40) NOT NULL, "
					+ "contents text NOT NULL,"
					+ "duedate DATE NOT NULL,"
					+ "submit boolean NOT NULL DEFAULT false,"
					+ "PRIMARY KEY (assignment_id));");
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS VolunteerProgramList (" + 
					"volunteer_id INT(8), " + 
					"subject_code char(20) NOT NULL, " + 
					"title char(30) NOT NULL, " + 
					"location char(30) NOT NULL, " + 
					"content char(50), " + 
					"yearAndSemester char(10) NOT NULL, " + 
					"PRIMARY KEY(volunteer_id)" + 
					");");
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS VolunteerList (" + 
					"volunteer_id INT(8), " + 
					"student_num INT(8), " + 
					"PRIMARY KEY(volunteer_id, student_num)" + 
					");");
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS GradeList (" + 
					"student_num INT(10), " + 
					"course_id INT(10), " + 
					"retake INT(10), " + 
					"grade char(10), " + 
					"grade_point FLOAT, " + 
					"PRIMARY KEY(student_num, course_id)" + 
					");");
			
			stmt.executeUpdate("Create TABLE IF NOT EXISTS TeamList (" + 
					"team_id INT(8) NOT NULL, " + 
					"professor_num INT(8) NOT NULL, " + 
					"year_and_semester INT(8) NOT NULL, " + 
					"PRIMARY KEY(team_id)" + 
					");");
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS TeamMemberList (" + 
					"student_num INT(8), " + 
					"team_id INT(8), " + 
					"PRIMARY KEY(student_num, team_id)" + 
					");");
			
			stmt.executeUpdate("Create TABLE IF NOT EXISTS MajorChangeList (" + 
					"student_num INT(10), " + 
					"date DATE NOT NULL, " + 
					"year_and_semester INT(10) NOT NULL, " + 
					"title char(40) NOT NULL, " + 
					"major1_before char(50) NOT NULL, " + 
					"major1_after char(50) NOT NULL, " + 
					"major2_before char(50) NOT NULL, " + 
					"major2_after char(50) NOT NULL, " + 
					"PRIMARY KEY(student_num, date)" + 
					");");
			
			stmt.executeUpdate("Create TABLE IF NOT EXISTS TuitionFeeList (" + 
					"department CHAR(20) NOT NULL, " + 
					"year_and_semester INT(8) NOT NULL, " + 
					"price INT(20) NOT NULL, " + 
					"PRIMARY KEY(department, year_and_semester)" + 
					");");
			
			stmt.executeUpdate("Create TABLE IF NOT EXISTS PaidTuitionFeeList (" + 
					"student_num INT(8), " + 
					"department CHAR(20), " + 
					"year_and_semester INT(8), " + 
					"refund INT(20), " + 
					"scholarship_campus INT(20), " + 
					"scholarship_off_campus INT(20), " + 
					"expected_payment INT(20), " + 
					"completed_payment INT(20)," + 
					"PRIMARY KEY(student_num, year_and_semester)" + 
					");");
		
			
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
