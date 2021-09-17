import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Department {
	
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost/choose_class";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "12345678";

	private static final String MAJOR_COURSES = "SELECT major_courses.Department_Name, major_courses.Course_Name "
			+ "FROM choose_class.major_courses WHERE major_courses.Department_Name = ?;";
	private static final String MINOR_COURSES = "SELECT minor_courses.Department_Name, minor_courses.Course_Name "
			+ "FROM choose_class.minor_courses WHERE minor_courses.Department_Name = ?;";

	private String name;
	private boolean major;
	private List<String> courses;
	
	public Department(String name, boolean major) {
		this.name = name;
		this.major = major;
		if(major) {
			courses = this.getMajorCourses();
		} else {
			courses = this.getMinorCourses();
		}
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean isMajor() {
		return this.major;
	}
	
	public List<String> getCourses() {
		return this.courses;
	}
	
	private static Connection getConnection() {
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
	
	public List<String> getMajorCourses() {
		Connection conn = getConnection();
		PreparedStatement ps;
		ArrayList<String> courses = new ArrayList<String>();
		try {
			ps = conn.prepareStatement(MAJOR_COURSES);
			ps.setString(1, this.name);
			
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
	
	public List<String> getMinorCourses() {
		Connection conn = getConnection();
		PreparedStatement ps;
		ArrayList<String> courses = new ArrayList<String>();
		try {
			ps = conn.prepareStatement(MINOR_COURSES);
			ps.setString(1, this.name);
			
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
	
}
