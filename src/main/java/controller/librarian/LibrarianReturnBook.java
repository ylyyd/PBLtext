package controller.librarian;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BookDAO;
import dao.BorrowCartDAO;
import dao.BorrowItemDAO;
import entity.Book;
import entity.Librarian;
import entity.Reader;

/**
 * librarian处理还书
 *
 *
 */
public class LibrarianReturnBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BorrowCartDAO dao = new BorrowCartDAO();

	public LibrarianReturnBook() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		int bookId = Integer.parseInt(request.getParameter("bookId"));
		HttpSession session = request.getSession();
		Librarian librarian = (Librarian) session.getAttribute("librarianEntity");
		if (dao.returnBook(bookId, librarian.getId())) {
			PrintWriter out = response.getWriter();
			out.print("<script language='javascript'>" + "alert('成功！');"
					+ "window.location.href='librarianReturnBook.jsp';" + "</script>");
		} else {
			PrintWriter out = response.getWriter();
			out.print("<script language='javascript'>" + "alert('失败！');"
					+ "window.location.href='librarianReturnBook.jsp';" + "</script>");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		HttpSession session = request.getSession();
		Librarian librarian = (Librarian) session.getAttribute("librarianEntity");
		BorrowItemDAO d = new BorrowItemDAO();

		// 获取读者信息
		Reader reader = d.getReaderInBorrowItemByBookID(bookId);
		if (reader == null) {
			out.print("<script language='javascript'>"
					+ "alert('没有读者借阅此书或读者信息丢失！');"
					+ "window.location.href='librarianReturnBook.jsp';" + "</script>");
		}
		BookDAO bDAO = new BookDAO();

		// 获取book信息
		Book book = bDAO.searchByID(bookId);
		if (book != null) {
			request.setAttribute("borrowerEntity", reader);
			request.setAttribute("bookEntity", book);
			request.getRequestDispatcher("librarianReturnBook.jsp").forward(request, response);
			// if (dao.returnBook(bookId, librarian.getId())) {
			// request.setAttribute("borrowerEntity", reader);
			// request.setAttribute("borrowerEntity", book);
			// request.getRequestDispatcher("librarianReturnBook.jsp").forward(request,
			// response);
			// } else {
			// out.print("<script language='javascript'>" + "alert('这本书未被借阅！');"
			// + "window.location.href='librarianReturnBook.jsp';" +
			// "</script>");
			// }
		} else {
			out.print("<script language='javascript'>" + "alert('错误的书籍ID！不存在ID为'" + bookId + "的书！);"
					+ "window.location.href='librarianReturnBook.jsp';" + "</script>");
		}
	}

}
