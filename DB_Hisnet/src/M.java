import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class M {
	static int room_num_cache = 0;
	public static void start(Connection conn, Scanner keyboard) throws SQLException, InterruptedException{
		ResultSet rs, rs2, rs3;
		System.out.println();
		
		Statement stmt = (Statement) conn.createStatement();
		Setting.initial_setting(conn, keyboard);
		while(true) {
			System.out.println("* Current user: " + Main.student_no + " *\n");
			System.out.println("[ Select an operation ]");
			System.out.println("0. Quit"); 
			System.out.println("1. Evaluate Dorm Apply and Register students"); 
			System.out.println("2. Check Education Apply"); 
			System.out.println("3. Accept Consulting Apply"); 
			System.out.println("4. Authorize Students Activity");
			System.out.println("5. See the Exorcists"); //기숙사 추방자 조회
			System.out.println("6. See the Best one trusty"); //상점 높은 애들
			System.out.println("7. Make random Access log(cuz we can't not get the data)");
			System.out.println("8. Update minus from Access logs");
			System.out.println("9. give the one plus point");
			System.out.println("10. Randomly selected student applied to dorm");
			System.out.println("11. Reset Database(you need to 'special password' for this command)");
			System.out.print("Input: ");
			
			int input = keyboard.nextInt();
			
			switch(input) {
				case 0 :
					System.out.println("bye...");
					Thread.sleep(1000);
					return;
					
				case 1 :
					rs = stmt.executeQuery("select student_num from DormInfo;");
					if(!rs.next()) {
						rs = stmt.executeQuery("select student_num from Apply where date=1 and apply_type=0;");
						while(rs.next()) {
							Statement tstmt = (Statement) conn.createStatement();
							tstmt.executeUpdate("update Apply set checked=true where apply_type=0 and student_num=" + rs.getString(1) + ";");
						}
						System.out.println("how many people do you want to pick me pick me pick me up!");
						int number = keyboard.nextInt();
						rs = stmt.executeQuery("select student_num from Apply where date=1 and apply_type=0 order by student_num desc limit " + number + ";");
						System.out.println("how many people do you want to live on the each floor");
						int offset = keyboard.nextInt();
						while(rs.next()) {
							Statement tstmt = (Statement) conn.createStatement();
							tstmt.executeUpdate("insert into DormInfo (student_num, room_num) values(" + rs.getString(1) + ", " + room_generator(offset) + ");");
						}
							
					}
					else {
						System.out.println("This semester is done if you want plz select reset command!!!");
						Thread.sleep(1000);
						break;
					}
					System.out.println("completed...");
					Thread.sleep(1000);
					break;
					
				case 2 :
					keyboard.nextLine();
					rs = stmt.executeQuery("select student_num from Apply where checked is NULL and apply_type = 2 limit 5;");
					if(rs.next()) {
						do {
							System.out.println("are you sure to accept " + rs.getString(1) + "'s education??? (y/n)");
							String ans = keyboard.nextLine();
							if(ans.compareToIgnoreCase("y") == 0) {
								Statement tstmt = (Statement) conn.createStatement();
								tstmt.executeUpdate("update Apply set checked=true where apply_type=2 and student_num=" + rs.getString(1) + ";");	
								tstmt.executeUpdate("update DormInfo set is_educated=true where student_num=" + rs.getString(1) + ";");
							}
							else {
								Statement tstmt = (Statement) conn.createStatement();
								tstmt.executeUpdate("delete from Apply where apply_type=2 and student_num=" + rs.getString(1) + ";");		
							}
						}while(rs.next());
						System.out.println("completed...");
						Thread.sleep(1000);
					}
					else {
						System.out.println("there is no apply");
						Thread.sleep(1000);
					}
					break;
					
				case 3 : 
					keyboard.nextLine();
					rs = stmt.executeQuery("select id, student_num, date from Apply where checked is NULL and apply_type = 1 limit 5;");
					if(rs.next()) {
						do {
							System.out.println("are you finish consulting with " + rs.getString(2) + "? (y/n)");
							String ans = keyboard.nextLine();
							if(ans.compareToIgnoreCase("y") == 0) {
								Statement tstmt = (Statement) conn.createStatement();
								tstmt.executeUpdate("update Apply set checked=true where id = " + rs.getString(1) + ";");	
								System.out.println("what are contents of consult with " + rs.getString(2) + "???");
								String text = keyboard.nextLine();
								text = '"' + text + '"';
								tstmt.executeUpdate("insert into Consult (student_num, contents, date) values(" + rs.getString(2) + ", " + text + ", " + rs.getString(3) + ");");
							}
							else {
								Statement tstmt = (Statement) conn.createStatement();
								tstmt.executeUpdate("delete from Apply where id = " + rs.getString(1) + ";");		
							}
						}while(rs.next());
						System.out.println("completed...");
						Thread.sleep(1000);
					}
					else {
						System.out.println("there is no apply");
						Thread.sleep(1000);
					}
					break;
					
				case 4 :
					keyboard.nextLine();
					rs = stmt.executeQuery("select id, student_num, my_activity from Activity where checked is NULL limit 10;");
					if(rs.next()) {
						do {
							System.out.println("[ " + rs.getString(2) + " ]");
							System.out.println(rs.getString(3));
							System.out.println("are you sure? (y/n)");
							String ans = keyboard.nextLine();
							if(ans.compareToIgnoreCase("y") == 0) {
								Statement tstmt = (Statement) conn.createStatement();
								tstmt.executeUpdate("update Activity set checked=true where id = " + rs.getString(1) + ";");
							}
							else {
								Statement tstmt = (Statement) conn.createStatement();
								tstmt.executeUpdate("delete from Activity where id = " + rs.getString(1) + ";");
							}
						}while(rs.next());
					}
					else {
						System.out.println("there is no register");
						Thread.sleep(1000);
					}
					break;
					
				case 5 :
					System.out.println("what is bound?? ex) -2");
					int bound = keyboard.nextInt();
					keyboard.nextLine();
					System.out.println("# Exorcists List #");
					int cnt = 0;
					rs = stmt.executeQuery("select student_num, minus from DormInfo where minus < " + bound + " order by minus;");
					while(rs.next()) 
						System.out.println(++cnt + ". " + rs.getString(1) + " : " + rs.getString(2));
					System.out.println("After 5 sec, back to the menu\n");
					Thread.sleep(5000);
					break;
					
				case 6 : 
					System.out.println("# Best Student With The Highest Plus Point (egnore whatever your minus is...) #");
					rs = stmt.executeQuery("select student_num, plus from DormInfo order by plus desc limit 1;");
					if(!rs.next()) {
						System.out.println("there is no student");
						Thread.sleep(1000);
						break;
					}
					System.out.println(rs.getString(1) + " : " + rs.getString(2));
					System.out.println("After 5 sec, back to the menu\n");
					Thread.sleep(5000);
					break;
					
				case 7 :
					System.out.println("what date do you want to generate? (format : YYMMDD ex. 190519)");
					int date = keyboard.nextInt();
					keyboard.nextLine();
					
					System.out.println("how many logs do you want to generate?");
					int offset = keyboard.nextInt();
					keyboard.nextLine();
					
					while(offset-- != 0) {
						int h1 = ((int)(Math.random()*24))*100;
						int h2 = ((int)(Math.random()*24))*100;
						int m1 = (int)(Math.random()*60);
						int m2 = (int)(Math.random()*60);
						int outtime = Math.min(h1+m1, h2+m2);
						int intime = Math.max(h1+m1, h2+m2);
						Statement tstmt = (Statement) conn.createStatement();
						rs = stmt.executeQuery("select student_num from DormInfo order by rand() limit 1;");
						if(!rs.next()) {
							System.out.println("there is no student...");
							Thread.sleep(1000);
							break;
						}
						tstmt.executeUpdate("insert into AccessLog (student_num, date, time, access_type) values(" + rs.getString(1) + ", " + date + ", " + outtime + ", 0);");
						tstmt.executeUpdate("insert into AccessLog (student_num, date, time, access_type) values(" + rs.getString(1) + ", " + date + ", " + intime + ", 1);");
					}
					
					System.out.println("generated...");
					Thread.sleep(1000);
					break;
				
				case 8 :
					rs = stmt.executeQuery("select id, student_num, date, access_type from AccessLog where time < 400 or time > 2300 and punished is null");
					Statement tstmt5 = (Statement) conn.createStatement();
					while(rs.next()) {
						rs2 = tstmt5.executeQuery("select id, student_num, date from Apply where student_num = " + rs.getString(2) + " and apply_type = 3 and date = " + rs.getString(3) + ";");
						if(rs2.next() && rs.getInt(4) == 1) {
							Statement tstmt2 = (Statement) conn.createStatement();
							tstmt2.executeUpdate("update Apply set checked=true where id = " + rs2.getString(1) + ";");
							tstmt2.executeUpdate("update AccessLog set punished=false where id = " + rs.getString(1) + ";");
							tstmt2.executeUpdate("update DormInfo set num_out=num_out+1 where student_num = " + rs.getString(2) + ";");
						}
						else {
							Statement tstmt2 = (Statement) conn.createStatement();
							tstmt2.executeUpdate("update AccessLog set punished=true where id = " + rs.getString(1) + ";");
							tstmt2.executeUpdate("update DormInfo set minus=minus-1 where student_num = " + rs.getString(2) + ";");
						}	
					}
					System.out.println("completed...");
					Thread.sleep(1000);
					break;
					
				case 9 :
					keyboard.nextLine();
					System.out.println("who is the one whe get the one point plz type student number!!");
					int sn = keyboard.nextInt();
					rs = stmt.executeQuery("select id from DormInfo where student_num = " + sn + ";");
					if(!rs.next()) {
						System.out.println("there is no that student!!!");
						Thread.sleep(1000);
						break;
					}
					Statement tstmt3 = (Statement) conn.createStatement();
					tstmt3.executeUpdate("update Dorminfo set plus=plus+1 where student_num = " + sn + ";");
					System.out.println("completed...");
					Thread.sleep(1000);
					break;
				
				case 10 :
					keyboard.nextLine();
					System.out.println("how many people??");
					int num = keyboard.nextInt();
					rs = stmt.executeQuery("select student_num from studentlist order by rand() limit " + num + ";");
					while(rs.next()) {
						Statement tstmt4 = (Statement) conn.createStatement();
						rs3 = tstmt4.executeQuery("select student_num from Apply where student_num=" + rs.getString(1) + " and apply_type=0;");
						if(!rs3.next())
							tstmt4.executeUpdate("insert into Apply (student_num, apply_type) values(" + rs.getString(1) + ", 0);");
					}
					System.out.println("completed...");
					Thread.sleep(1000);
					break;
					
				case 11 :
					while(true) {
						System.out.print("password: "); // 1
						if(keyboard.nextInt() != 1) {
							System.out.println("program is ended!!");
							break;
						}
						keyboard.nextLine();
						System.out.println("are you really sure? (y/n)");
						String ans = keyboard.next();
						if(ans.compareToIgnoreCase("y") == 0) {
							stmt.executeUpdate("delete from DormInfo;");
							stmt.executeUpdate("delete from Apply;");
							stmt.executeUpdate("delete from AccessLog;");
							stmt.executeUpdate("delete from Tuberculosis;");
							System.out.println("clear...");
							Thread.sleep(1000);
							break;
						}
						else if(ans.compareToIgnoreCase("n") == 0) {
							System.out.println("bye!!");
							Thread.sleep(1000);
							break;
						}
						else
							break;
					}
					return;
				default :
					System.out.println("error(18)");
					break;
			}
		}
	}
	
	public static int room_generator(int offset) {
		int room_num = 101 + (room_num_cache++ / offset) * 100;
		return room_num + (room_num_cache % offset);
	}
}
		