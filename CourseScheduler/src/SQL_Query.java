import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQL_Query {

	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost/choose_class";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "12345678";
	
	private static final String STUDENT = "SELECT Student_ID, Student_Name, Student_Grade, Student_Password "
			+ "FROM choose_class.student WHERE Student_ID = ? AND Student_Password = ?;";
	private static final String SECTION_ID = "SELECT course.Course_Name, section.Section_ID, section.Teacher_Name, section.Classroom, section.Time, section.Semester ,course.Credits "
			+ "FROM choose_class.section, choose_class.course WHERE course.Course_Name = section.Course_Name AND section.Section_ID = ?;";
	private static final String SECTION_NAME = "SELECT course.Course_Name, section.Section_ID, section.Teacher_Name, section.Classroom, section.Time, section.Semester ,course.Credits "
			+ "FROM choose_class.section, choose_class.course WHERE course.Course_Name = section.Course_Name AND course.Course_Name = ?;";

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
	
	public static Student getStudent(String student_ID, String student_password) {
		Connection conn = getConnection();
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(STUDENT);
			ps.setString(1, student_ID);
			ps.setString(2, student_password);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				String id = rs.getString("Student_ID");
				String name = rs.getString("Student_Name");
				String password = rs.getString("Student_Password");
				int grade = rs.getInt("Student_Grade");
				Student student = new Student(id, name, password, grade);
				return student;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Section getSection(String section_ID) {
		Connection conn = getConnection();
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(SECTION_ID);
			ps.setString(1, section_ID);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				String name = rs.getString("Course_Name");
				String id = rs.getString("Section_ID");
				String teacher_name = rs.getString("Teacher_Name");
				String classroom = rs.getString("Classroom");
				String time = rs.getString("Time");
				int semester = rs.getInt("Semester");
				int credits = rs.getInt("Credits");
				return new Section(name, id, teacher_name, classroom, time, semester, credits);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Section not found: " + section_ID);
		return null;
	}
	
	public static List<Section> search(String course_name) {
		Connection conn = getConnection();
		PreparedStatement ps;
		ArrayList<Section> sections = new ArrayList<Section>();
		try {
			ps = conn.prepareStatement(SECTION_NAME);
			ps.setString(1, course_name);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String name = rs.getString("Course_Name");
				String id = rs.getString("Section_ID");
				String teacher_name = rs.getString("Teacher_Name");
				String classroom = rs.getString("Classroom");
				String time = rs.getString("Time");
				int semester = rs.getInt("Semester");
				int credits = rs.getInt("Credits");
				Section section = new Section(name, id, teacher_name, classroom, time, semester, credits);
				sections.add(section);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sections;
	}

}
