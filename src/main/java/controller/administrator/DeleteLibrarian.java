package controller.administrator;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.LibrarianDAO;

/**
 * 通过Librarian的名字与密码删除一个librarian
 *
 * @author
 *
 */
public class DeleteLibrarian extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteLibrarian() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		int librarianId = Integer.valueOf(request.getParameter("librarianId"));
		String librarianName = request.getParameter("librarianName");

		LibrarianDAO libDao = new LibrarianDAO();

		int flag = libDao.deleteLibrarianByIdName(librarianId, librarianName);

		if (flag == 1) {
			String msg = "删除管理员成功！";
			request.setAttribute("message", msg);
			request.getRequestDispatcher("adminOperateResult.jsp").forward(request, response);
		} else {
			String msg = "删除管理员失败！该管理员可能在图书馆中有某些操作或姓名与ID不正确！";
			request.setAttribute("message", msg);
			request.getRequestDispatcher("adminOperateResult.jsp").forward(request, response);
		}
	}

}
