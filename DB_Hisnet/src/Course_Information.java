
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;


public class Course_Information {
	static String student_no = Main.get_student_no();
	static String yearAndSemester, course_title, professor_name, section;
	static int professor_num, course_id;
	
	public static void print_course_menu(Connection conn, Scanner keyboard) throws SQLException {
		
		System.out.println("[ Select an operation ]");
		System.out.println("0. Back");
		System.out.println("1. Search Courses");
		System.out.println("2. Take Courses(Before Do This, Recommand You Choose <2. Search Courses>)");
		System.out.println("3. Drop Courses(Before Do This, Recommand You Choose <4. My Courses>");
		System.out.println("4. My Courses");
		System.out.println("5. Check Attendance");
		
		System.out.println();
		System.out.print("Input: ");
		
		int input = keyboard.nextInt();
		
		System.out.println("\n***********************************************************\n");
		
		if (input == 0) {
			Main.print_menu(conn, keyboard, student_no);
		}
		
		else if (input == 1) {
			search_courses(conn, keyboard);
			
		}
		
		else if (input == 2) {
			take_courses(conn, keyboard);
			
		}
		
		else if (input == 3) {
			delete_my_courses(conn, keyboard);
		}
		
		else if (input == 4) {
			watch_my_courses(conn, keyboard);
		}	
		
		else if (input == 5) {
			check_attendance(conn, keyboard);
		}
	}
	

	// 1. 개설 시간표 조회 (수업 이름, 교수님 이름, 분반, 년&학기, 영어비율, 타입 으로 조회)
		private static void search_courses(Connection conn, Scanner keyboard) throws SQLException {
			Statement stmt = (Statement) conn.createStatement();
			ResultSet rs;
			
			// get professor_num
			rs = stmt.executeQuery("select professor_num from ProfessorList where name = '" + professor_name + "';");
			if(rs.next()) {
				professor_num = rs.getInt(1);
			}
			
			// 수업 이름 없으면, 모든 과목을 과목코드로 정렬해서 불러옴 
			if(course_title.isEmpty()) {
				System.out.println("test1");
				rs = stmt.executeQuery("select * from CourseList order by subject_code;");
			}
			
			// 수업 이름만 있을 때 
			else if(professor_name.isEmpty() && section.isEmpty()) {
				System.out.println("test2");
				rs = stmt.executeQuery("select * from CourseList where title = '" + course_title + "';");
			} 
			
			// 수업 이름 + 분반 
			else if(professor_name.isEmpty()) {
				System.out.println("test3");
				rs = stmt.executeQuery("select * from CourseList where title = '" + course_title + 
						"' and section = " + section + ";");
			} 
			
			// 수업 이름 + 교수님 이름 
			else if(section.isEmpty()) {
				System.out.println("test4");
				rs = stmt.executeQuery("select * from CourseList where title = '" + course_title + 
						"' and professor_num = " + professor_num + ";");
			}
			
			// 다 있을 때 
			else {
				rs = stmt.executeQuery("select * from CourseList where title = '" + course_title + 
						"' and professor_num = " + professor_num + " and section = " + section + ";");
			}
			
			ResultSetMetaData metaInfo = rs.getMetaData();
			int count = metaInfo.getColumnCount();
			
			// 출력 결과 헤더 출력
			System.out.println("--------------------------------------------------------------------------------------------------------------------");
			// 컬럼명
			for (int i = 0; i < count; i++) {
				System.out.printf("%s\t ", metaInfo.getColumnName(i + 1));
			}
			System.out.println("--------------------------------------------------------------------------------------------------------------------");

			// 결과값 출력
			while (rs.next()) {
				for (int i = 0; i < count; i++) {
					System.out.printf("%s\t", rs.getString(i + 1));
				}
				System.out.println();
			}
			
		}
	
	
	// 2. 수강 신청 
	public static void take_courses(Connection conn, Scanner keyboard) throws SQLException {
		Statement stmt = (Statement) conn.createStatement();
		ResultSet rs;
		
		System.out.println("[ Taking Courses ]");
		System.out.println("0. Back");
		System.out.println("1. For This Semester");
		System.out.println("2. For Other Semester");
		
		System.out.println();
		System.out.print("Input: ");
		
		int input = keyboard.nextInt();
		
		System.out.println("\n***********************************************************\n");
		
		if (input == 0) {
			print_course_menu(conn, keyboard);
		}
		
		else {
			yearAndSemester = "2019-01";
			
			System.out.println("[ Course Information - Take Courses ]");
			System.out.println("\n***********************************************************\n");

			// add a course
			System.out.println("[ You should put all blanks ]");
			
			// get yearAndSemester
			if(input == 2) {
				do {
					System.out.print("YearAndSemester(Essential, ex)2018-02): ");
					yearAndSemester = keyboard.nextLine();	
								
					if (yearAndSemester.length() == 0)
						yearAndSemester = keyboard.nextLine();
				} while(yearAndSemester.isEmpty());
			}
			
			// receive a course title
			do {
				System.out.print("Course Name(Essential): ");
				course_title = keyboard.nextLine();	
							
				if (course_title.length() == 0)
				course_title = keyboard.nextLine();
			} while(course_title.isEmpty());
			
			// receive a subject code
			do {
				System.out.print("Professor Name(Essential): ");
				professor_name = keyboard.nextLine();	
							
				if (professor_name.length() == 0)
				professor_name = keyboard.nextLine();
			} while(professor_name.isEmpty());
			
			// get professor_num
			rs = stmt.executeQuery("select professor_num from ProfessorList where name = '" + professor_name + "';");
			if(rs.next()) {
				professor_num = rs.getInt(1);
			}
			
			
			// receive a section
			do {
				System.out.print("Section(Essential): ");
				section = keyboard.nextLine();
				
				if (section.length() == 0)
					section = keyboard.nextLine();
			} while(section.isEmpty());
			
			
			// 해당 수업 id 가져옴
			rs = stmt.executeQuery("select course_id from CourseList where title = '" + course_title + 
					"' and professor_num = " + professor_num + " and section = '" + section + "';");
			if(rs.next()) {
				course_id = rs.getInt(1);
			}
			
			System.out.println("courseID : " + course_id);
			
			// 수강 신청 확인 
			System.out.println("\n[ Are you sure? (Y/N) ] ");
			System.out.println();
			System.out.print("Input: ");
			
			input = keyboard.next().charAt(0);
			
			int check = 0;
			if (input == 'Y') {
				// 테이블 존재하는지 확인, 없으면 만들어 줌 
				rs = stmt.executeQuery("select 1 from information_schema.tables where TABLE_NAME like 'TakeCourse';");
				if(rs.next()) check = 1;
				if(check == 0) {
					// 수강 신청 테이블 
					int result = stmt.executeUpdate("CREATE TABLE TakeCourse ("
							+ "student_num int(8), "
							+ "course_id int(20), "
							+ "taking_time datetime NOT NULL, "
							+ "PRIMARY KEY (student_num, course_id));");
					stmt.executeQuery("COMMIT;");
					
					System.out.println("\n***********************************************************\n");
					System.out.println("[ Successfully initialized TakeCourse ]");
					System.out.println("\n***********************************************************\n");
				}
				
				// 날짜 시간 계산 
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String datetime = sdf.format(cal.getTime());
				System.out.println("datetime : " + datetime);
				
				// 쿼리실행 
				int result = stmt.executeUpdate("insert into TakeCourse values"
						+ "('" + student_no + "', "
						+ course_id + ", '"
						+ datetime + "');");
				stmt.executeQuery("COMMIT;");
				
				if (result == 1) {
					System.out.println("\n***********************************************************\n");
					System.out.println("Now, You are in the course < " + course_title + " >");
					System.out.println("\n***********************************************************\n");
				}
			} 
		}
		
		// 다시 돌아감 
		System.out.println();
		print_course_menu(conn, keyboard);
	}
	
	// 3. 내 수강 삭
	public static void delete_my_courses(Connection conn, Scanner keyboard) throws SQLException {
		Statement stmt = (Statement) conn.createStatement();
		ResultSet rs;
		
		System.out.println("[ Deleting Courses ]");
		System.out.println("0. Back");
		System.out.println("1. For This Semester");
		System.out.println("2. For Other Semester");
		
		System.out.println();
		System.out.print("Input: ");
		
		int input = keyboard.nextInt();
		
		System.out.println("\n***********************************************************\n");
		
		if (input == 0) {
			print_course_menu(conn, keyboard);
		}
		
		else {
			yearAndSemester = "2019-01";
			
			System.out.println("[ Course Information - Delete My Courses ]");
			System.out.println("\n***********************************************************\n");

			// add a course
			System.out.println("[ You should put all blanks ]");
			
			// get yearAndSemester
			if(input == 2) {
				do {
					System.out.print("YearAndSemester(Essential, ex)2018-02): ");
					yearAndSemester = keyboard.nextLine();	
								
					if (yearAndSemester.length() == 0)
						yearAndSemester = keyboard.nextLine();
				} while(yearAndSemester.isEmpty());
			}
			
			// receive a course title
			do {
				System.out.print("Course Name(Essential): ");
				course_title = keyboard.nextLine();	
							
				if (course_title.length() == 0)
				course_title = keyboard.nextLine();
			} while(course_title.isEmpty());
			
			// receive a professor name
			do {
				System.out.print("Professor Name(Essential): ");
				professor_name = keyboard.nextLine();	
							
				if (professor_name.length() == 0)
				professor_name = keyboard.nextLine();
			} while(professor_name.isEmpty());
			
			// get professor_num
			rs = stmt.executeQuery("select professor_num from ProfessorList where name = '" + professor_name + "';");
			if(rs.next()) {
				professor_num = rs.getInt(1);
			}
			
			
			// receive a section
			do {
				System.out.print("Section(Essential): ");
				section = keyboard.nextLine();
				
				if (section.length() == 0)
					section = keyboard.nextLine();
			} while(section.isEmpty());
			
			
			// 해당 수업 id 가져옴
			rs = stmt.executeQuery("select course_id from CourseList where title = '" + course_title + 
					"' and professor_num = " + professor_num + " and section = '" + section + "';");
			if(rs.next()) {
				course_id = rs.getInt(1);
			}
			
			System.out.println("courseID : " + course_id);
			
			// 수강 신청 확인 
			System.out.println("\n[ Are you sure? (Y/N) ] ");
			System.out.println();
			System.out.print("Input: ");
			
			input = keyboard.next().charAt(0);
			
			int check = 0;
			if (input == 'Y') {
				// 테이블 존재하는지 확인, 없으면 만들어 줌 
				rs = stmt.executeQuery("select 1 from information_schema.tables where TABLE_NAME like 'TakeCourse';");
				if(rs.next()) check = 1;
				if(check == 0) {
					// 수강 신청 테이블 
					int result = stmt.executeUpdate("CREATE TABLE TakeCourse ("
							+ "student_num int(8), "
							+ "course_id int(20), "
							+ "taking_time datetime NOT NULL, "
							+ "PRIMARY KEY (student_num, course_id));");
					stmt.executeQuery("COMMIT;");
					
					System.out.println("\n***********************************************************\n");
					System.out.println("[ Successfully initialized TakeCourse ]");
					System.out.println("\n***********************************************************\n");
				}
				
				// 날짜 시간 계산 
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String datetime = sdf.format(cal.getTime());
				System.out.println("datetime : " + datetime);
				
				// 쿼리실행 
				int result = stmt.executeUpdate("insert into TakeCourse values"
						+ "('" + student_no + "', "
						+ course_id + ", '"
						+ datetime + "');");
				stmt.executeQuery("COMMIT;");
				
				if (result == 1) {
					System.out.println("\n***********************************************************\n");
					System.out.println("Now, You are in the course < " + course_title + " >");
					System.out.println("\n***********************************************************\n");
				}
			} 
			
			
		}
		
		// 다시 돌아감 
		System.out.println();
		print_course_menu(conn, keyboard);
	}
	
	// 4. 내 수강내역들 조회 
	public static void watch_my_courses(Connection conn, Scanner keyboard) throws SQLException {
		System.out.println("[ Watch My Courses ]");
		System.out.println("0. Back");
		System.out.println("1. For This Semester");
		System.out.println("2. For Other Semester");
		
		System.out.println();
		System.out.print("Input: ");
		
		int input = keyboard.nextInt();
		
		System.out.println("\n***********************************************************\n");
		
		if (input == 0) {
			print_course_menu(conn, keyboard);
		}
		
		else if (input == 1) {
			
			
		}
		
		else if (input == 2) {
			
		}
		
		// 현재 학기 시간표 출력 
		for(int i = 0; i < 10; i++) {
			System.out.println("[ Select an operation ]");
		}
		
		// 다시 돌아감 
		print_course_menu(conn, keyboard);
	}
	
	// 출석 현황 
	public static void check_attendance(Connection conn, Scanner keyboard) throws SQLException {
		
		System.out.println("[ Select an operation ]");
		System.out.println("0. Back");
		System.out.println("1. My Course Schedule");
		System.out.println("2. Search Courses");
		System.out.println("3. Registered Courses");
		
		System.out.println();
		System.out.print("Input: ");
		
		int input = keyboard.nextInt();
		
		System.out.println("\n***********************************************************\n");
		
		if (input == 0) {
			print_course_menu(conn, keyboard);
		}
		
		else if (input == 1) {
			
		}
		
		else if (input == 2) {
			
		}
		
		else if (input == 3) {
			// TO DO
		}		
	}
}
