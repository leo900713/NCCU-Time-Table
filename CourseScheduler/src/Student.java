import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Student {
	
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost/choose_class";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "12345678";
	
	private static final String STUDENT_MAJOR = "SELECT major.Student_ID, major.Department_Name "
			+ "FROM choose_class.major WHERE major.Student_ID = ?;";
	private static final String STUDENT_DOUBLE_MAJOR = "SELECT double_major.Student_ID, double_major.Department_Name "
			+ "FROM choose_class.double_major WHERE double_major.Student_ID = ?;";
	private static final String STUDENT_MINOR = "SELECT minor.Student_ID, minor.Department_Name "
			+ "FROM choose_class.minor WHERE minor.Student_ID = ?;";
	
	private static final String TAKEN_COURSES = "SELECT taken_courses.Student_ID, taken_courses.Course_Name "
			+ "FROM choose_class.taken_courses WHERE taken_courses.Student_ID = ?;";
	private static final String FOLLOW_LIST = "SELECT follow_list.Student_ID, follow_list.Section_ID "
			+ "FROM choose_class.follow_list WHERE follow_list.Student_ID = ?;";
	private static final String SCHEDULE = "SELECT schedule.Student_ID, schedule.Section_ID "
			+ "FROM choose_class.schedule WHERE schedule.Student_ID = ?;";
	
	private static final String ADD_FOLLOWLIST = "INSERT INTO choose_class.follow_list (follow_list.Student_ID, follow_list.section_ID) VALUES (?, ?);";
	private static final String DELETE_FOLLOWLIST = "DELETE FROM choose_class.follow_list WHERE follow_list.Student_ID = ? AND follow_list.Section_ID = ?;";
	private static final String ADD_SCHEDULE = "INSERT INTO choose_class.schedule (schedule.Student_ID, schedule.section_ID) VALUES (?, ?);";
	private static final String DELETE_SCHEDULE = "DELETE FROM choose_class.schedule WHERE schedule.Student_ID = ? AND schedule.Section_ID = ?;";
	
	private String id;
	private String name;
	private String password;
	private int grade;
	private Department major;
	private Department double_major;
	private Department minor;
	
	public Student(String id, String name, String password,int grade) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.grade = grade;
		this.major = this.getStudentMajor();
		this.double_major = this.getStudentDoubleMajor();
		this.minor = this.getStudentMinor();
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public int getGrade() {
		return this.grade;
	}
	
	public Department getMajor() {
		return this.major;
	}
	
	public Department getDouble_major() {
		return this.double_major;
	}
	
	public Department getMinor() {
		return this.minor;
	}
			
	private Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public Department getStudentMajor() {
		Connection conn = getConnection();
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(STUDENT_MAJOR);
			ps.setString(1, this.id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				String department_name = rs.getString("Department_Name");
				Department department = new Department(department_name, true);
				return department;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Department getStudentDoubleMajor() {
		Connection conn = getConnection();
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(STUDENT_DOUBLE_MAJOR);
			ps.setString(1, this.id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				String department_name = rs.getString("Department_Name");
				Department department = new Department(department_name, true);
				return department;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Department getStudentMinor() {
		Connection conn = getConnection();
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(STUDENT_MINOR);
			ps.setString(1, this.id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				String department_name = rs.getString("Department_Name");
				Department department = new Department(department_name, false);
				return department;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<String> getTakenCourses() {
		Connection conn = getConnection();
		PreparedStatement ps;
		ArrayList<String> courses = new ArrayList<String>();
		try {
			ps = conn.prepareStatement(TAKEN_COURSES);
			ps.setString(1, this.id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String course_name = rs.getString("Course_Name");
				courses.add(course_name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courses;
	}
	
	public List<String> getUntakenMajor() {
		List<String> major_courses = this.major.getCourses();
		ArrayList<String> taken_courses = (ArrayList<String>) this.getTakenCourses();
		for(String t: taken_courses) {
			for(String m: major_courses) {
				if(t.equals(m)) {
					major_courses.remove(m);
					break;
				}
			}
		}
		return major_courses;
	}
	
	public List<String> getUntakenDoubleMajor() {
		List<String> double_major_courses = this.double_major.getCourses();
		ArrayList<String> taken_courses = (ArrayList<String>) this.getTakenCourses();
		for(String t: taken_courses) {
			for(String m: double_major_courses) {
				if(t.equals(m)) {
					double_major_courses.remove(m);
					break;
				}
			}
		}
		return double_major_courses;
	}
	
	public List<String> getUntakenMinor() {
		List<String> minor_courses = this.minor.getCourses();
		ArrayList<String> taken_courses = (ArrayList<String>) this.getTakenCourses();
		for(String t: taken_courses) {
			for(String m: minor_courses) {
				if(t.equals(m)) {
					minor_courses.remove(m);
					break;
				}
			}
		}
		return minor_courses;
	}
		
	public List<Section> getFollow_List() {
		Connection conn = getConnection();
		PreparedStatement ps;
		ArrayList<Section> sections = new ArrayList<Section>();
		try {
			ps = conn.prepareStatement(FOLLOW_LIST);
			ps.setString(1, this.id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String section_ID = rs.getString("Section_ID");
				Section section = SQL_Query.getSection(section_ID);
				sections.add(section);
			}
		} catch (SQLException e) {
			System.out.println("[FollowList] Failed: sql error");
		}
		return sections;
	}
	
	public List<Section> getSchedule() {
		Connection conn = getConnection();
		PreparedStatement ps;
		ArrayList<Section> sections = new ArrayList<Section>();
		try {
			ps = conn.prepareStatement(SCHEDULE);
			ps.setString(1, this.id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String section_ID = rs.getString("Section_ID");
				Section section = SQL_Query.getSection(section_ID);
				sections.add(section);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sections;
	}
	
	public Section[][] getScheduleTable() {
		Section[][] scheduleTable = new Section[15][6];
		for(int h = 0; h < scheduleTable.length; h++) {
			for(int d = 0; d < scheduleTable[h].length; d++) {
				if(scheduleTable[h][d] == null) {
					scheduleTable[h][d] = new Section("000", "", "", "", "", 0, 0);
					scheduleTable[h][d].setHour(h+1);
					scheduleTable[h][d].setDay(d);
				}
			}
		}
		System.out.print("[Schedule]");
		for(Section s: getSchedule()) {
			System.out.print(" " + s.getId());
			int[][] st = s.schedule_time();
			for(int i = 0; i < st.length; i++) {
				scheduleTable[st[i][0]-1][st[i][1]] = s;
			}
		}
		System.out.println();
		return scheduleTable;
	}
	
	public boolean addFollowList(String section_ID) {
		Connection conn = getConnection();
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(ADD_FOLLOWLIST);
			ps.setString(1, this.id);
			ps.setString(2, section_ID);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("[AddFollowList] Failed: sql error");
			return false;
		}
		System.out.println("[AddFollowList] Success");
		return true;
	}
	
	public boolean deleteFollowList(String section_ID) {
		Connection conn = getConnection();
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(DELETE_FOLLOWLIST);
			ps.setString(1, this.id);
			ps.setString(2, section_ID);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("[DeleteFollowList] Failed");
			return false;
		}
		System.out.println("[DeleteFollowList] Success");
		return true;
	}
	
	public boolean addSchedule(String section_ID) {
		Section s = SQL_Query.getSection(section_ID);
		for(Section section: this.getSchedule()) {
			if(section.getId().equals(section_ID)) {
				System.out.println("[AddSchedule] Failed: Section exists");
				return false;
			}
			if(section.getCourse_name().equals(s.getCourse_name())) {
				System.out.println("[AddSchedule] Failed: Course exists");
				return false;
			}
			for(int[] section_st: section.schedule_time()) {
				for(int[] s_st: s.schedule_time()) {
					if(section_st[0] == s_st[0] && section_st[1] == s_st[1]) {
						System.out.println("[AddSchedule] Failed: Time conflict");
						return false;
					}
				}
			}
		}
		
		Connection conn = getConnection();
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(ADD_SCHEDULE);
			ps.setString(1, this.id);
			ps.setString(2, section_ID);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[AddSchedule] Failed: sql error");
			return false;
		}
		System.out.println("[AddSchedule] Success");
		return true;
	}
	
	public boolean deleteSchedule(String section_ID) {
		boolean section_in_schedule = false;
		for(Section section: this.getSchedule()) {
			if(section.getId().equals(section_ID)) {
				section_in_schedule = true;
				break;
			}
		}
		if(!section_in_schedule) {
			System.out.println("[DeleteSchedule] Failed: Section not found");
			return section_in_schedule;
		}
		
		Connection conn = getConnection();
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(DELETE_SCHEDULE);
			ps.setString(1, this.id);
			ps.setString(2, section_ID);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("[DeleteSchedule] Failed: sql error");
			return false;
		}
		System.out.println("[DeleteSchedule] Success");
		return true;
	}
	
}
