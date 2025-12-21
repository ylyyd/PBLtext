package controller.librarian;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BookDAO;

public class LibrarianDeleteBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LibrarianDeleteBook() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		int bookId = Integer.parseInt(request.getParameter("book_id"));
		BookDAO bookDAO = new BookDAO();
		int tag = bookDAO.deleteBookById(bookId);
		if (tag == 1) {
			out.print("<script language='javascript'>" + "alert('删除成功！');"
					+ "window.location.href='librarianDeleteBook.jsp';" + "</script>");
		} else if (tag == 2) {
			out.print("<script language='javascript'>" + "alert('删除失败！状态不是inlib！');"
					+ "window.location.href='librarianDeleteBook.jsp';" + "</script>");
		} else if (tag == 3) {
			out.print("<script language='javascript'>" + "alert('删除失败！此书不存在！');"
					+ "window.location.href='librarianDeleteBook.jsp';" + "</script>");
		} else if (tag == 4) {
			out.print("<script language='javascript'>" + "alert('删除失败！出现异常！');"
					+ "window.location.href='librarianDeleteBook.jsp';" + "</script>");
		}

	}

}
