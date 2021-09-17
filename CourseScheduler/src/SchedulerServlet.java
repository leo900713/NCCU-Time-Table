
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SchedulerServlet
 */
@WebServlet("/")
public class SchedulerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Student student;
	String keyword;
	List<Section> searches;
	String add_follow;
	String delete_follow;
	String add_schedule;
	String delete_schedule;
	boolean conflict = false;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		
		String action = request.getServletPath();
		switch (action) {
			case "/login":
				login(request, response);
				break;
			case "/logout":
				logout(request, response);
				break;
			case "/search":
				search(request, response);
				break;
			case "/addFollowlist":
				addFollowList(request, response);
				break;
			case "/deleteFollowlist":
				deleteFollowList(request, response);
				break;
			case "/addSchedule":
				addSchedlue(request, response);
				break;
			case "/deleteSchedule":
				deleteSchedule(request, response);
				break;
			default:
				mainPage(request, response);
				break;
		}
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[Direct] /login");
		String studentID = request.getParameter("studentID");
		String password = request.getParameter("password");
		student = SQL_Query.getStudent(studentID, password);
		if (student != null) {
			System.out.println("[Login] [ID]" + studentID + " [Password]" + password);
			response.sendRedirect("main");
			return;
		} else {
			System.out.println("[Login] Failed: Invalid studentID or password");
			request.setAttribute("studentID", studentID);
			request.setAttribute("password", password);
			request.setAttribute("invalid_login", true);
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[Direct] /logout");
		request.setAttribute("studentID", student.getId());
		request.setAttribute("password", "");
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		keyword = request.getParameter("keyword");
		System.out.println("[Direct] /search " + keyword);
		searches = SQL_Query.search(keyword);
		response.sendRedirect("main");
	}

	private void addFollowList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		add_follow = request.getParameter("add_follow").substring(0, 9);
		System.out.println("[Direct] /addFollowList " + add_follow);
		student.addFollowList(add_follow);
		response.sendRedirect("main");
	}

	private void deleteFollowList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		delete_follow = request.getParameter("delete_follow").substring(0, 9);
		System.out.println("[Direct] /deleteFollowList " + delete_follow);
		student.deleteFollowList(delete_follow);
		response.sendRedirect("main");
	}

	private void addSchedlue(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		add_schedule = request.getParameter("add_schedule").substring(0, 9);
		System.out.println("[Direct] /addSchedule " + add_schedule);
		if(!student.addSchedule(add_schedule)) {
			conflict = true;
			System.out.println("conflict: " + conflict);
		} else {
			conflict = false;
			System.out.println("conflict: " + conflict);
		}
		response.sendRedirect("main");
	}

	private void deleteSchedule(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		delete_schedule = request.getParameter("delete_schedule").substring(0, 9);
		System.out.println("[Direct] /deleteSchedule " + delete_schedule);
		student.deleteSchedule(delete_schedule);
		response.sendRedirect("main");
	}
	
	private void mainPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("student", student);
		request.setAttribute("keyword", keyword);
		request.setAttribute("searches", searches);
		request.setAttribute("add_follow", add_follow);
		request.setAttribute("delete_follow", delete_follow);
		request.setAttribute("add_schedule", add_schedule);
		request.setAttribute("delete_schedule", delete_schedule);
		request.setAttribute("conflict", conflict);
		request.getRequestDispatcher("main.jsp").forward(request, response);
	}

}
