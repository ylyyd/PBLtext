package controller.administrator;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.LibrarianDAO;
import entity.Librarian;

/**
 * 先输入Librarian的ID，先判断是否存在该Librarian，若存在则跳转到AdminModifyLibrarian.jsp页面
 */
public class AdminModifyBefore extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminModifyBefore() {
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

		int id = Integer.valueOf(request.getParameter("librarianId"));
		Librarian librarian = new LibrarianDAO().getLibrarianById(id);

		if (librarian != null) {
			String name = librarian.getName();
			request.setAttribute("id", id);
			request.setAttribute("name", name);
			request.getRequestDispatcher("adminModifyLibrarian.jsp").forward(request, response);
		} else {
			String msg = "未找到该管理员，请重试！";
			request.setAttribute("message", msg);
			request.getRequestDispatcher("adminOperateResult.jsp").forward(request, response);
		}

	}

}
