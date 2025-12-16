package controller.librarian;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.LibrarianDAO;
import entity.Librarian;
import util.SecurityUtil;

/**
 * 该Servlet用于接收Librarian修改密码的请求
 *
 *
 */
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ChangePassword() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		Librarian librarian = (Librarian) session.getAttribute("librarianEntity");
		LibrarianDAO librarianDAO = new LibrarianDAO();
		// 获取表单数据
		String oldPw = SecurityUtil.md5(request.getParameter("old"));
		String newPw = SecurityUtil.md5(request.getParameter("new"));
		String confirm = SecurityUtil.md5(request.getParameter("confirm"));
		// 检查"新密码"和"确认密码"是否相同
		if (newPw.equals(confirm)) {
			if (librarianDAO.changePasswordByOldPassword_NewPassword(librarian.getName(), oldPw, newPw)) {
				System.out.println("--Librarian--, 修改密码成功");
				out.print("<script language='javascript'>"
						+ "alert('恭喜！您已成功修改密码！');"
						+ "window.location.href='librarianHomepage.jsp';" + "</script>");
			} else {
				System.out.println("旧密码错误");
				out.print("<script language='javascript'>" + "alert('旧密码错误，请重新输入！');"
						+ "window.location.href='librarianModifyInfo.jsp';" + "</script>");

			}
		} else {
			System.out.println("新密码和确认密码不相同");
			out.print("<script language='javascript'>" + "alert('确认密码与新密码不同');"
					+ "window.location.href='librarianModifyInfo.jsp';" + "</script>");
		}

	}

}
