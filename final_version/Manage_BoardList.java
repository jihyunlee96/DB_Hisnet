import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Manage_BoardList {
	static Connection conn;
	static Scanner keyboard;
	static boolean authority = false;
	
	public static void print_instructions_root(Connection in_conn, Scanner in_keyboard) throws SQLException {
		if (conn == null)
			conn = in_conn;
		
		if (keyboard == null)
			keyboard = in_keyboard;

		System.out.println("[ Manage BoardList ]");
		System.out.println("0. Back <- ");
		System.out.println("1. View Board");
		System.out.println("2. Search Board");
		System.out.println("3. Write Board");
		System.out.println("4. Delete Board");

		System.out.println();
		System.out.print("Input: ");
		
		int input = keyboard.nextInt();
		
		System.out.println("\n***********************************************************\n");
		
		if (input == 0) {
			try {
				Main.print_menu(conn, keyboard, Main.student_no);
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if (input == 1) {	//view
			select_boardType(conn, keyboard, 1);
		}
		else if (input == 2) {	//search
			select_boardType(conn, keyboard, 2);	
		}
		else if (input == 3) {	//write
			select_boardType(conn, keyboard, 3);	
		}
		else if(input == 4) {	//delete
			select_boardType(conn, keyboard, 4);
		}
		else 
			System.out.println("Enter the correct number!");
		
	}
	public static void print_instructions_user(Connection in_conn, Scanner in_keyboard) throws SQLException {
		if (conn == null)
			conn = in_conn;
		
		if (keyboard == null)
			keyboard = in_keyboard;

		System.out.println("[ BoardList ]");
		System.out.println("0. Back <- ");
		System.out.println("1. View Board");
		System.out.println("2. Search Board");
		System.out.println("3. Write Board");
		
		if(!Main.is_professor)
			System.out.println("4. Submit Assignment");
		else
			System.out.println("4. Write Assignment");

		System.out.println();
		System.out.print("Input: ");
		
		int input = keyboard.nextInt();
		
		System.out.println("\n***********************************************************\n");
		
		if (input == 0) {
			try {
				Main.print_menu(conn, keyboard, Main.student_no);
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else if (input == 1) {	//view
			select_boardType(conn, keyboard, 1);
		}
		else if (input == 2) {	//search
			search_board(conn, keyboard);
		}
		else if (input == 3) {	//write
			select_boardType(conn, keyboard, 3);
		}
		else if(input == 4) {	//submit
			if(Main.is_professor)
//				writeAssign(conn,keyboard);
				submitAssign(conn, keyboard);
//			else
				submitAssign(conn,keyboard);
		}
		else {
			System.out.println("Enter the correct number!");
		}
	}
	public static void submitAssign(Connection in_conn, Scanner in_keyboard) throws SQLException {
		Statement stmt = (Statement) conn.createStatement();	
		
		stmt.executeQuery("START TRANSACTION;");
		ResultSet rs = stmt.executeQuery("select * from AssignmentList" + ";");
		
		System.out.println("idx   title         duedate	  submit");
		System.out.println("-----------------------------");	
		while (rs.next()) {
			String assignment_id = rs.getString("assignment_id");
			String title = rs.getString("title");
			Date duedate = rs.getDate("dudate");
			boolean submit = rs.getBoolean("submit");
			
			System.out.println(assignment_id + " | " + title + "  |  " + duedate+ "  |  " +submit);
			}
		
		//submit
		System.out.println("Select the assignment_id: ");
		int assignment_id = keyboard.nextInt();
		System.out.println("Wirte the contents of assignment");
		String contents = keyboard.next();
		
		int result = stmt.executeUpdate("UPDATE AssignmentList set " + "contents" + " = '" + contents 
			+"', submit= '1'"+ " where assignment_id ='" + assignment_id + "';");
		stmt.executeQuery("COMMIT;");
		
		if(result == 1) {
			System.out.println("\n***********************************************************\n");
			System.out.println("[ Successfully submit assignment " + " ]");
			System.out.println("\n***********************************************************\n");	
		}
	}
//	public static void writeAssign(Connection in_conn, Scanner in_keyboard) throws SQLException {
//		System.out.println("[ BoardList - Write Assignment ]");
//		System.out.println("\n***********************************************************\n");
//		System.out.println("[ Fill out the following sections ]");
//		
//			String assignment_id = "1";
//			
//			System.out.print("course_id: ");
//			int course_id = keyboard.nextInt();
//			
//			// receive title
//			System.out.print("title: ");
//			String title = keyboard.nextLine();
//			if (title.length() == 0)
//				title = keyboard.nextLine();
//			
//			// receive contents
//			System.out.print("Enter the contents: ");
//			String contents = keyboard.nextLine();
//			if (contents.length() == 0)
//				contents = keyboard.nextLine();
//						
//			// set Today's date
//			System.out.print("Enter the duedate ((ex)2014-01");
//			String duedate = keyboard.nextLine();
//
//			int submit = 0;
//			
//			
//			Statement stmt = (Statement) conn.createStatement();	
////				int result = stmt2.executeUpdate("INSERT INTO AssignmentList(assignment_id, course_id, title, contents, dudate, submit, "
////						+ ") "
////						+ "VALUES ('" + course_id + "', '"+title+ "', + "'  contents + "', DATE '" + strToday + "', '"
////						+ views + "', '" + type_id + "', '" + course_id + "','" + department + "','" +writer+ "')");
//				int result = stmt.executeUpdate("INSERT INTO AssignmentList(assignment_id, course_id, title, contents, "
//						+ "dudate, submit) "
//						+ "VALUES ('" + assignment_id + "', '" +  course_id + "', DATE '" + title + "', '"
//						+ contents + "', '" + duedate + "', '" + submit + "')");
//				
//				if (result == 1) {
//					System.out.println("\n***********************************************************\n");
//					System.out.println("["+title + "] is successfully added to AssignmentList");
//					System.out.println("\n***********************************************************\n");
//				}
//				else
//					System.out.println("Write Failed!");
//				}
//			
//
////			Main.print_menu(conn, keyboard, Main.student_no);
//	}
	public static void printMyCourse(Connection in_conn, Scanner in_keyboard) throws SQLException {
		Statement stmt = (Statement) conn.createStatement();
		String yearAndSemester = "2019-01";
		
		ResultSet rs = stmt.executeQuery("select CourseList.course_id, CourseList.subject_code, CourseList.title, "
				+ "CourseList.credit, CourseList.section, CourseList.times, CourseList.yearAndSemester "
				+ "from TakeCourse natural join CourseList where TakeCourse.student_num = '" + Main.student_no + "' "
				+ "and CourseList.yearAndSemester = '" + yearAndSemester + "';");

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
		
	}
	
	public static void select_boardType(Connection in_conn, Scanner in_keyboard, int mode) throws SQLException {
		if (conn == null)
			conn = in_conn;
		
		if (keyboard == null)
			keyboard = in_keyboard;
		
		System.out.println("[ Select Board Type]");
		System.out.println("0. Back <- ");
		System.out.println("1. One Click Complaints");
		System.out.println("2. General Notice");
		System.out.println("3. Main Notice");
		System.out.println("4. Scholarship Notice");
		System.out.println("5. Career Notice");
		System.out.println("6. Department Notice");
		System.out.println("7. All Course's Notice");
		System.out.println("8. My Course's Notice");
		
		System.out.println();
		System.out.print("Input: ");
		
		int input = keyboard.nextInt();
		int type_id = 1; //default general notice
		
		System.out.println("\n***********************************************************\n");
		
		if (input == 0) {
			print_instructions_user(conn, keyboard);
		}
		else {
			type_id = input;
		}
		
		if(mode == 1) {
			select_viewType(in_conn, in_keyboard, type_id);
		}
		else if(mode == 3) {
			try {
				write_board(conn, keyboard, type_id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(mode == 4) {
			try {
				delete_board(conn, keyboard, type_id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}
	public static void select_viewType(Connection in_conn, Scanner in_keyboard, int board_type) throws SQLException {
		if (conn == null)
			conn = in_conn;
		
		if (keyboard == null)
			keyboard = in_keyboard;
		
		System.out.println("[ Select View Type]");
		System.out.println("0. Back <- ");
		System.out.println("1.General mode");
		System.out.println("2.View by Writer");
		System.out.println("3.View by date");		
		System.out.println("4.View as Popular");
		
		System.out.println();
		System.out.print("Input: ");
		
		int input = keyboard.nextInt();
		System.out.println("\n***********************************************************\n");
		
		if (input == 0) {
			select_boardType(conn, keyboard, 1);
		}
		else if(0<input && input<5) {
			view_board(conn, keyboard, board_type, input);	
		}
		else{
			System.out.println("Enter the correct number!");
		}
	}
	
	public static void view_board(Connection in_conn, Scanner in_keyboard, int board_type, int view_type) throws SQLException {
			
		Statement stmt = (Statement) conn.createStatement();
		Statement stmt2 = (Statement) conn.createStatement();
		ResultSet result;

		System.out.println("[ BoardList - View Board ]");
		System.out.println("\n***********************************************************\n");
		System.out.println("idx   title         date");
		System.out.println("-----------------------------");	
		
		if(view_type == 1) {
			result = stmt.executeQuery("select * from BoardList where type_id=" + board_type + ";");
			if(board_type == 8) {
				printMyCourse(conn, keyboard);
				
				System.out.println("Select the course_id: ");
				int course_id = keyboard.nextInt();
				result = stmt.executeQuery("select * from BoardList where course_id=" + course_id + ";");
			}
		}
		else if(view_type == 2) {
			System.out.println("Enter the writer's id");
			String writer = keyboard.next();
			result = stmt.executeQuery("select * from BoardList where type_id=" + board_type+
					" AND writer='" +writer+ "';");
			
			//For myCourse Board
			if(board_type == 8) {
				printMyCourse(conn, keyboard);
				
				System.out.println("Select the course_id: ");
				int course_id = keyboard.nextInt();
				result = stmt.executeQuery("select * from BoardList where course_id=" + course_id + ";");
			}
		}
		else if(view_type == 3) {
			result = stmt.executeQuery("select * from BoardList where type_id=" + board_type+
					" ORDER BY date DESC" +";");
			if(board_type == 8) {
				printMyCourse(conn, keyboard);
				
				System.out.println("Select the course_id: ");
				int course_id = keyboard.nextInt();
				result = stmt.executeQuery("select * from BoardList where course_id=" + course_id + ";");
			}
		}
		else if(view_type == 4) {
			result = stmt.executeQuery("select * from BoardList where type_id=" + board_type+
					" ORDER BY views DESC" +";");
			if(board_type == 8) {
				printMyCourse(conn, keyboard);
				
				System.out.println("Select the course_id: ");
				int course_id = keyboard.nextInt();
				result = stmt.executeQuery("select * from BoardList where course_id=" + course_id + ";");
			}
		}
		else	{	//default general
			result = stmt.executeQuery("select * from BoardList where type_id=" + board_type + ";");
			if(board_type == 8) {
				printMyCourse(conn, keyboard);
				
				System.out.println("Select the course_id: ");
				int course_id = keyboard.nextInt();
				result = stmt.executeQuery("select * from BoardList where course_id=" + course_id + ";");
			}
		}
		
		while (result.next()) {
			String board_id = result.getString("board_id");
			String title = result.getString("title");
			String date = result.getString("date");
			
			System.out.println(board_id + "| " + title + " | " + date);
			}
		
		System.out.print("Enter the number of the notice you want to view (0: <-back): ");
		int idx = in_keyboard.nextInt();
		if(idx == 0) //back
			select_viewType(in_conn, in_keyboard, board_type);
		
		stmt.executeQuery("START TRANSACTION;");
		result = stmt.executeQuery("select * from BoardList where board_id=" + idx + ";");

		while (result.next()) {
			String title = result.getString("title");
			String date = result.getString("date");
			String contents = result.getString("contents");

			int views = result.getInt("views");
			views++;
			
			stmt2.executeUpdate("UPDATE BoardList set views = '" + Integer.toString(views) +
					"' where board_id ='" + idx + "';");
			
			
			System.out.println("[title: "+title+"]");
			System.out.println("[date: "+date+ " views: "+views+"]");
			System.out.println(contents);
			}
		stmt.executeQuery("COMMIT;");
		
		Main.print_menu(conn, keyboard, Main.student_no);
	}
	public static void write_board(Connection in_conn, Scanner in_keyboard, int type_id) throws SQLException{
		
		//Restrict writing authority
		if(Main.user_id == "root")
			authority = true;
		else if(Main.is_professor || Main.is_TA) {
			authority = true;
			if(type_id != 8 && Main.is_TA)
				authority = false;
		} 
			
		if(!Main.is_professor){
			System.out.println("student mode");
			if(type_id == 1)
				authority = true;
			else {
			System.out.println("Permission Denied Writing Board");
			authority = false;
			}
		}
		
		if(authority) {
			
		System.out.println("[ BoardList - Write Board ]");
		System.out.println("\n***********************************************************\n");
		System.out.println("[ Fill out the following sections ]");
			
			// receive tile
			System.out.print("Board title: ");
			String title = keyboard.nextLine();
			if (title.length() == 0)
				title = keyboard.nextLine();
			
			// receive contents
			System.out.print("Enter the contents: ");
			String contents = keyboard.nextLine();
			if (contents.length() == 0)
				contents = keyboard.nextLine();
						
			// set Today's date
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			String strToday = formatter.format(c.getTime());
	
			//  number of views (initialize 0)
			int views = 0;
			
			//course_id
			String course_id;
			if(type_id == 8)
				course_id = "1"; //내 수업정보 중 select하기 
			else
				course_id = "NULL";
			
			//if department type, receive
			String department;
			if(type_id == 6) {
				System.out.print("Select the department: ");
				department = keyboard.nextLine();
			}
			else
				department = "NULL";
			
			//writer: user_id가져오기 
			String writer;
			Statement stmt = (Statement) conn.createStatement();
			Statement stmt2 = (Statement) conn.createStatement();	
			
			//For root
			if(Main.id.compareTo("root") == 0) {
				writer = "root";
				int result = stmt2.executeUpdate("INSERT INTO BoardList(title, contents, date, views, "
						+ "type_id, course_id, department, writer) "
						+ "VALUES ('" + title + "', '" +  contents + "', DATE '" + strToday + "', '"
						+ views + "', '" + type_id + "', '" + course_id + "','" + department + "','" +writer+ "')");
				
				if (result == 1) {
					System.out.println("\n***********************************************************\n");
					System.out.println("["+title + "] is successfully added to BoardList");
					System.out.println("\n***********************************************************\n");
				}
				else
					System.out.println("Write Failed!");
				}
			
			//For user	
			else {
			ResultSet rs = stmt.executeQuery("select * from StudentList where student_num=" 
			+ Main.student_no + ";");
			while (rs.next()) {
				writer = rs.getString("user_id");
				
				int result = stmt2.executeUpdate("INSERT INTO BoardList(title, contents, date, views, "
						+ "type_id, course_id, department, writer) "
						+ "VALUES ('" + title + "', '" +  contents + "', DATE '" + strToday + "', '"
						+ views + "', '" + type_id + "', '" + course_id + "','" + department + "','" +writer+ "')");
				
				if (result == 1) {
					System.out.println("\n***********************************************************\n");
					System.out.println("["+title + "] is successfully added to BoardList");
					System.out.println("\n***********************************************************\n");
				}
				else
					System.out.println("Write Failed!");
				}
			}
		}//authority
			Main.print_menu(conn, keyboard, Main.student_no);
	}
	public static void delete_board(Connection in_conn, Scanner in_keyboard, int type_id) throws SQLException {
		
		System.out.println("[ BoardList - Delete Board ]");
		System.out.println("\n***********************************************************\n");
		
		Statement stmt = (Statement) conn.createStatement();
		Statement stmt2 = (Statement) conn.createStatement();
		
		ResultSet result = stmt.executeQuery("select * from BoardList where type_id=" + type_id + ";");
		System.out.println("idx   title         date");
		System.out.println("-----------------------------");
		while (result.next()) {
			String board_id = result.getString("board_id");
			String title = result.getString("title");
			String date = result.getString("date");
			
			System.out.println(board_id + "| " + title + " | " + date);
			}
		
		System.out.print("Enter the number of the notice you want to delete (0: <-back): ");
		int idx = in_keyboard.nextInt();
		if(idx == 0) //back
			select_boardType(in_conn, in_keyboard, type_id);
		
		int rs = stmt.executeUpdate("delete from BoardList where board_id=" + idx + ";");
		if(rs == 1) {
			System.out.println("\n***********************************************************\n");
			System.out.println("[ Successfully deleted board " + idx + " ]");
			System.out.println("\n***********************************************************\n");
		}
		else {
			System.out.println("\n***********************************************************\n");
			System.out.println("[ FAILED - Cannot find board " + idx + " ]");
			System.out.println("\n***********************************************************\n");
		}
		Main.print_menu(conn, keyboard, Main.student_no);
	}
	public static void search_board(Connection in_conn, Scanner in_keyboard) throws SQLException {
		
		System.out.println("[ BoardList - Search Board ]");
		System.out.println("\n***********************************************************\n");
		
		Statement stmt = (Statement) conn.createStatement();
		Statement stmt2 = (Statement) conn.createStatement();
		
		System.out.println("Enter the title you looking for: ");
		String search = in_keyboard.next();
		ResultSet result = stmt.executeQuery("select * from BoardList where title " +
		"LIKE '%"+ search + "%';");
		
		System.out.println("idx   title         date");
		System.out.println("-----------------------------");
		while (result.next()) {
			String board_id = result.getString("board_id");
			String title = result.getString("title");
			String date = result.getString("date");
			
			System.out.println(board_id + "| " + title + " | " + date);
			}
		
		System.out.print("Enter the number of the notice you want to view (0: <-back): ");
		int idx = in_keyboard.nextInt();
		if(idx == 0) //back
			select_boardType(in_conn, in_keyboard, 1);
		
		result = stmt.executeQuery("select * from BoardList where board_id=" + idx + ";");

		while (result.next()) {
			String title = result.getString("title");
			String date = result.getString("date");
			String contents = result.getString("contents");

			int views = result.getInt("views");
			views++;
			
			stmt2.executeUpdate("UPDATE BoardList set views = '" + Integer.toString(views) +
					"' where board_id ='" + idx + "';");
			
			System.out.println("[title: "+title+"]");
			System.out.println("[date: "+date+ " views: "+views+"]");
			System.out.println(contents);
			}
		
		Main.print_menu(conn, keyboard, Main.student_no);
	}
	
}