
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;


public class Facility_Information {
	static String student_no = Main.get_student_no();
	static String facility_name, date, time;
	static int professor_num, facility_id;
	static Statement stmt;
	static ResultSet rs;
	
	public static void print_facility_menu(Connection conn, Scanner keyboard) throws SQLException {
		stmt = (Statement) conn.createStatement();
		int check = 0;
		// 테이블 존재하는지 확인, 없으면 만들어 줌 
		rs = stmt.executeQuery("select 1 from information_schema.tables where TABLE_NAME like 'BookFacility';");
		if(rs.next()) check = 1;
		if(check == 0) {
			// 수강 신청 테이블 
			int result = stmt.executeUpdate("CREATE TABLE BookFacility ("
					+ "student_num int(8), "
					+ "facility_id int(20), "
					+ "date date, "
					+ "time int(2), "
					+ "booking_time datetime NOT NULL, "
					+ "PRIMARY KEY (student_num, facility_id, date, time));");
			stmt.executeQuery("COMMIT;");
			
			
			System.out.println("[ Successfully initialized BookFacility ]");
			System.out.println("\n***********************************************************\n");
			
		}
			
		
		
		System.out.println("[ Select an operation ]");
		System.out.println("0. Back");
		System.out.println("1. Search Facilities");
		System.out.println("2. Book (Before Doing This, Recommand You Choose <1. Search Facilities>)");
		System.out.println("3. Cancel (Before Doing This, Recommand You Choose <4. My Booking>)");
		System.out.println("4. My Booking");
		
		System.out.println();
		System.out.print("Input: ");
		
		int input = keyboard.nextInt();
		
		System.out.println("\n***********************************************************\n");
		
		if (input == 0) {
			Heeseok.start(conn, keyboard, student_no);
		}
		
		else if (input == 1) {
			search_facilities(conn, keyboard);
		}
		
		else if (input == 2) {
			book(conn, keyboard);	
		}
		
		else if (input == 3) {
			cancel(conn, keyboard);
		}
		
		else if (input == 4) {
			watch_my_booking(conn, keyboard);
		}	
		
	}
	

	// 1. 시설물 검색 (시설물 이름, 날짜) (완료) 
	private static void search_facilities(Connection conn, Scanner keyboard) throws SQLException {
		stmt = (Statement) conn.createStatement();
		
		System.out.println("[ Searching Courses ]");
		System.out.println("0. Back");
		System.out.println("1. Show All Facilities Name");
		System.out.println("2. Search With Conditions");
		
		System.out.println();
		System.out.print("Input: ");
		
		int input = keyboard.nextInt();
		
		System.out.println("\n***********************************************************\n");
		
		if (input == 0) {
			print_facility_menu(conn, keyboard);
		}
		
		else if(input == 1) {
			rs = stmt.executeQuery("select * from FacilityList order by name;");
			
			ResultSetMetaData metaInfo = rs.getMetaData();
			int count = metaInfo.getColumnCount();
			
			// 출력 결과 헤더 출력
			System.out.println("--------------------------------------------------------------------------------------------------------------------");
			// 컬럼명
			for (int i = 0; i < count; i++) {
				System.out.printf("%s\t ", metaInfo.getColumnName(i + 1));
			}
			System.out.println();
			
			// 결과값 출력
			while (rs.next()) {
				for (int i = 0; i < count; i++) {
					System.out.printf("|%s|\t", rs.getString(i + 1));
				}
				System.out.println();
			}
			System.out.println("--------------------------------------------------------------------------------------------------------------------");	
		}
		
		else if(input == 2) {
			System.out.println("[ Put this 3 searching conditions(You can put nothing) ]\n");
			
			// receive a facility name
			System.out.print("Facility Name: ");
			facility_name = keyboard.nextLine();	
			if (facility_name.length() == 0)
			facility_name = keyboard.nextLine();
			
			// receive a date
			System.out.print("Date(ex) 2019-05-20): ");
			date = keyboard.nextLine();	
			if (date.length() == 0)
				date = keyboard.nextLine();
			
			
			
			// 쿼리 시작 
			// 전부 x
			if(facility_name.isEmpty() && date.isEmpty()) {
				rs = stmt.executeQuery("select FacilityList.name, BookFacility.date, BookFacility.time "
						+ "from BookFacility natural join FacilityList "
						+ "order by FacilityList.name;");
			}
			
			// 시설 이름 o
			else if(!facility_name.isEmpty() && date.isEmpty()) {
				rs = stmt.executeQuery("select FacilityList.name, BookFacility.date, BookFacility.time "
						+ "from BookFacility natural join FacilityList where FacilityList.name = '" + facility_name + "' "
						+ "order by FacilityList.name;");
			}

			// 시설 이름, 날짜 o 
			else if(!facility_name.isEmpty() && !date.isEmpty()) {
				rs = stmt.executeQuery("select FacilityList.name, BookFacility.date, BookFacility.time "
						+ "from BookFacility natural join FacilityList where FacilityList.name = '" + facility_name + "' and "
						+ "BookFacility.date = '" + date + "' order by FacilityList.name;");
			}
			ResultSetMetaData metaInfo = rs.getMetaData();
			int count = metaInfo.getColumnCount();
			
			// 출력 결과 헤더 출력
			System.out.println("--------------------------------------------------------------------------------------------------------------------");
			// 컬럼명
			for (int i = 0; i < count; i++) {
				System.out.printf("%s\t ", metaInfo.getColumnName(i + 1));
			}
			System.out.println();

			// 결과값 출력
			while (rs.next()) {
				for (int i = 0; i < count; i++) {
					System.out.printf("|%s|\t", rs.getString(i + 1));
				}
				System.out.println();
			}
			System.out.println("--------------------------------------------------------------------------------------------------------------------");
		}
		
		print_facility_menu(conn, keyboard);
	}
	
	
	// 2. 시설 예약 (완료)
	public static void book(Connection conn, Scanner keyboard) throws SQLException {
		stmt = (Statement) conn.createStatement();
		
		System.out.println("[ Book : You should put all blanks ]\n");
	
		// get facility_name
		do {
			System.out.print("Facility Name(Essential, ex)SSLab 1): ");
			facility_name = keyboard.nextLine();	
						
			if (facility_name.length() == 0)
				facility_name = keyboard.nextLine();
		} while(facility_name.isEmpty());
		// 시설명으로 시설 인덱스 가져오기 
		rs = stmt.executeQuery("select facility_id from FacilityList where name = '" + facility_name + "';");
		if(rs.next()) {
			facility_id = rs.getInt(1);
		}
		
		// get date
		do {
			System.out.print("Date(Essential, ex)2019-05-20): ");
			date = keyboard.nextLine();	
						
			if (date.length() == 0)
				date = keyboard.nextLine();
		} while(date.isEmpty());
		
		// receive a time
		do {
			System.out.print("Time(Essential, < Range : 0 ~ 23 >, You Can Book 1 hour at One Time): ");
			time = keyboard.nextLine();	
			if (time.length() == 0)
				time = keyboard.nextLine();
		} while(0 > Integer.parseInt(time) || 23 < Integer.parseInt(time));
		
		System.out.println("\n***********************************************************\n");
		
		// 날짜 시간 계산 
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String datetime = sdf.format(cal.getTime());
		
			
		// 수강 신청 확인 
		System.out.println("\n[ Are you sure? (Y/N) ] ");
		System.out.println();
		System.out.print("Input: ");
		
		int input = keyboard.next().charAt(0);
		
		if (input == 'Y') {
	
			
			// 쿼리실행 
			// CourseList에 현인원 추가 
			// 비교 후 삽입 
			int result = stmt.executeUpdate("insert into BookFacility select '" + student_no + "', " + facility_id + ", '" + date + "', '" + time + "', '" + datetime 
					+ "' from dual where not exists "
					+ "(select * from BookFacility where facility_id = " + facility_id 
					+ " and BookFacility.date = '" + date + "' and BookFacility.time = '" + time +"');");
			stmt.executeQuery("COMMIT;");
			
			if (result == 1) {
				System.out.println("\n***********************************************************\n");
				System.out.println("Successful Booking < " + facility_name + ", " + date + ", " + time + " >");
				System.out.println("\n***********************************************************\n");
			}
		} 

		
		// 다시 돌아감 
		System.out.println();
		print_facility_menu(conn, keyboard);
	}
	
	// 3. 내 예약 삭제 
	public static void cancel(Connection conn, Scanner keyboard) throws SQLException {
		stmt = (Statement) conn.createStatement();
		
		System.out.println("[ Cancel : You should put all blanks ]\n");
		
		// get facility_name
		do {
			System.out.print("Facility Name(Essential, ex)SSLab 1): ");
			facility_name = keyboard.nextLine();	
						
			if (facility_name.length() == 0)
				facility_name = keyboard.nextLine();
		} while(facility_name.isEmpty());
		// 시설명으로 시설 인덱스 가져오기 
		rs = stmt.executeQuery("select facility_id from FacilityList where name = '" + facility_name + "';");
		if(rs.next()) {
			facility_id = rs.getInt(1);
		}
		
		// get date
		do {
			System.out.print("Date(Essential, ex)2019-05-20): ");
			date = keyboard.nextLine();	
						
			if (date.length() == 0)
				date = keyboard.nextLine();
		} while(date.isEmpty());
		
		// receive a time
		do {
			System.out.print("Time(Essential, < Range : 0 ~ 23 >, You Can Book 1 hour at One Time): ");
			time = keyboard.nextLine();	
			if (time.length() == 0)
				time = keyboard.nextLine();
		} while(0 > Integer.parseInt(time) || 23 < Integer.parseInt(time));
		
		
		System.out.println("\n***********************************************************\n");
			
			
		// 수강 삭제 확인 
		System.out.println("\n[ Are you sure? (Y/N) ] ");
		System.out.println();
		System.out.print("Input: ");
		
		int input = keyboard.next().charAt(0);
		
		int check = 0;
		if (input == 'Y') {
								
			// 쿼리실행 	
			// BookFacility에 튜플 삭제  
			int result = stmt.executeUpdate("delete from BookFacility " + 
					"where facility_id = '" + facility_id + "' and date = '" + date + "' and time = " + time + ";");
			stmt.executeQuery("COMMIT;");
			
			if (result == 1) {
				System.out.println("\n***********************************************************\n");
				System.out.println("You Cancel The Booking < " + facility_name + ", " + date + ", " + time + " >");
				System.out.println("\n***********************************************************\n");
			}
		} 
		
		// 다시 돌아감 
		System.out.println();
		print_facility_menu(conn, keyboard);
	}
	
	// 4. 내 예약 내역 조회 
	public static void watch_my_booking(Connection conn, Scanner keyboard) throws SQLException {
		stmt = (Statement) conn.createStatement();
		
		System.out.println("[ Watch My Booking ]");
		System.out.println("\n***********************************************************\n");
			
		rs = stmt.executeQuery("select FacilityList.name, BookFacility.date, "
				+ "BookFacility.time from FacilityList natural join BookFacility"
				+ " where BookFacility.student_num = '" + student_no + "';");

		// 시간표 출력 
		ResultSetMetaData metaInfo = rs.getMetaData();
		int count = metaInfo.getColumnCount();
		
		// 출력 결과 헤더 출력
		System.out.println("--------------------------------------------------------------------------------------------------------------------");
		// 컬럼명
		for (int i = 0; i < count; i++) {
			System.out.printf("%s\t ", metaInfo.getColumnName(i + 1));
		}
		System.out.println();

		// 결과값 출력
		while (rs.next()) {
			for (int i = 0; i < count; i++) {
				System.out.printf("|%s|\t", rs.getString(i + 1));
			}
			System.out.println();
		}
		System.out.println("--------------------------------------------------------------------------------------------------------------------");
		
		// 다시 돌아감 
		print_facility_menu(conn, keyboard);
	}
}
