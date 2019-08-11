import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class U {
	public static void start(Connection conn, Scanner keyboard) throws SQLException, InterruptedException{
		ResultSet rs, trs;
		System.out.println();
		Statement ststmt = (Statement) conn.createStatement();
		Statement stmt = (Statement) conn.createStatement();
		Setting.initial_setting(conn, keyboard);
		while(true) {
			
			System.out.println("* Current user: " + Main.student_no + " *\n");
			System.out.println("[ Select an operation ]");
			System.out.println("0. Quit"); //나가기
			System.out.println("1. Dorm Apply"); //입주신청
			System.out.println("2. Education Apply"); //교육신청
			System.out.println("3. Consulting Apply"); //면담신청
			System.out.println("4. My Page");
			System.out.println("5. My Point");
			System.out.println("6. Activity Register");
			System.out.println("7. Tuberculosis Provement Submit");
			System.out.println("8. How many times do i live off the dorm???");
			System.out.println("9. Out Apply");
			System.out.println("10. Log my night access");
			System.out.println("11. My activities");
			System.out.print("Input: ");
			
			int input = keyboard.nextInt();
			
			switch(input) {
				case 0 :
					System.out.println("bye!!");
					Thread.sleep(1000);
					return;
					
				case 1 :
					rs = stmt.executeQuery("select student_num from Apply where student_num=" + Integer.parseInt(Main.student_no) + " and apply_type=0;");
					if(!rs.next())
						stmt.executeUpdate("insert into Apply (student_num, apply_type) values(" + Integer.parseInt(Main.student_no) + ", 0);");
					System.out.println("completed...");
					Thread.sleep(1000);
					break;
					
				case 2 :
					trs = ststmt.executeQuery("select student_num from DormInfo where student_num = " + Main.student_no + ";");
					rs = stmt.executeQuery("select student_num from Apply where student_num=" + Integer.parseInt(Main.student_no) + " and apply_type=2;");
					if(!rs.next() && trs.next())
						stmt.executeUpdate("insert into Apply (student_num, apply_type) values(" + Integer.parseInt(Main.student_no) + ", 2);");
					System.out.println("completed...");
					Thread.sleep(1000);
					break;
					
				case 3 : 
					System.out.println("when do you want? (format : YYMMDD ex) 190714)");
					System.out.print("date : ");
					int date1 = keyboard.nextInt();
					trs = ststmt.executeQuery("select student_num from DormInfo where student_num = " + Main.student_no + ";");
					rs = stmt.executeQuery("select student_num from Apply where student_num=" + Integer.parseInt(Main.student_no) + " and apply_type=1 and date=" + date1 + ";");
					if(!rs.next() && trs.next())
						stmt.executeUpdate("insert into Apply (student_num, apply_type, date) values(" + Integer.parseInt(Main.student_no) + ", 1, "+ date1 + ");");
					System.out.println("completed...");
					Thread.sleep(1000);
					break;
					
				case 4 :
					System.out.println("#  My Page  #");
					rs = stmt.executeQuery("select dorm_type, room_num, is_educated from DormInfo where student_num=" + Integer.parseInt(Main.student_no) + ";");
					rs.next();
					System.out.println("dorm : " + rs.getString(1));
					System.out.println("room number : " + rs.getString(2));
					if(rs.getBoolean(3)) System.out.println("[ education completed ]");
					else System.out.println("[ education not completed ]");
					System.out.println();
					Thread.sleep(1000);
					break;
					
				case 5 :
					System.out.println("#  My Point  #");
					rs = stmt.executeQuery("select plus, minus from DormInfo where student_num=" + Integer.parseInt(Main.student_no) + ";");
					rs.next();
					System.out.println("[ plus : " + rs.getString(1) + " ]");
					System.out.println("[ minus : " + rs.getString(2) + " ]");
					System.out.println("[ total : " + (rs.getInt(1)+rs.getInt(2)) + " ]");
					System.out.println();
					Thread.sleep(1000);
					break;
					
				case 6 :
					trs = ststmt.executeQuery("select student_num from DormInfo where student_num = " + Main.student_no + ";");
					if(!trs.next()) {
						System.out.println("Sorry, you are not in dorm");
						Thread.sleep(1000);
						break;
					}
					System.out.println("what is your activity???");
					keyboard.nextLine();
					String text = keyboard.nextLine();
					text = '"' + text + '"';
					stmt.executeUpdate("insert into Activity (student_num, my_activity) values(" + Integer.parseInt(Main.student_no) + ", "+ text + ");");
					System.out.println("Registered...");
					Thread.sleep(1000);
					break;
					
				case 7 :
					rs = stmt.executeQuery("select id from Apply where student_num=" + Main.student_no + " and apply_type=0;");
					if(!rs.next()) {
						System.out.println("you should apply dorm first!!!");
						Thread.sleep(1000);
						break;
					}
					System.out.println("where is file in your computer?? ex)/Users/kevin/Download/a.pdf");
					System.out.print("filepath : ");
					keyboard.nextLine();
					String filepath = keyboard.nextLine();
					filepath = '"' + filepath + '"';
					rs = stmt.executeQuery("select student_num from Tuberculosis where student_num=" + Integer.parseInt(Main.student_no) + ";");
					if(!rs.next()) {
						stmt.executeUpdate("insert into Tuberculosis (student_num, filepath) values(" + Integer.parseInt(Main.student_no) + ", "+ filepath + ");");
						stmt.executeUpdate("update Apply set date=1 where apply_type=0 and student_num=" + Main.student_no + ";");
						System.out.println("done!!");
						Thread.sleep(1000);
					}
					else {
						System.out.println("you already done!!");
						Thread.sleep(1000);
					}
					break;
					
				case 8 :
					rs = stmt.executeQuery("select num_out from DormInfo where student_num=" + Integer.parseInt(Main.student_no) + ";");
					rs.next();
					System.out.println("times of out : " + rs.getString(1));
					Thread.sleep(1000);
					break;
					
				case 9 :
					System.out.println("when do you want to out? (format : YYMMDD ex) 181106)");
					System.out.print("date : ");
					int date2 = keyboard.nextInt();
					rs = stmt.executeQuery("select student_num from Apply where student_num=" + Integer.parseInt(Main.student_no) + " and apply_type=3;");
					if(!rs.next())
						stmt.executeUpdate("insert into Apply (student_num, apply_type, date) values(" + Integer.parseInt(Main.student_no) + ", 3, "+ date2 + ");");
					System.out.println("completed...");
					Thread.sleep(1000);
					break;
					
				case 10 :
					System.out.println("# My Night Access #");
					rs = stmt.executeQuery("select date, time, access_type from AccessLog where time < 400 or time > 2300 order by date, time;");
					while(rs.next()) {
						if(rs.getInt(3) == 1)	System.out.println(rs.getString(1) + " : " + rs.getString(2) + " : [In];");
						else	System.out.println(rs.getString(1) + " : " + rs.getString(2) + " : [out];");
							
					}
					break;
					
				case 11 :
					int cnt = 1;
					System.out.println("#  My RC Activity  #");
					rs = stmt.executeQuery("select my_activity from Activity where student_num=" + Integer.parseInt(Main.student_no) + " and checked=true;");
					while(rs.next()) 
						System.out.println(cnt++ + ". " + rs.getString(1));
					System.out.println("- after 5 sec, go back to menu -");
					System.out.println();
					Thread.sleep(5000);
					break;
					
				default :	
					System.out.println("error(18)");
					break;
			}
		}//while
	}
}
		