package controller.reader;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BookDAO;
import entity.Book;

/**
 * Servlet实现类 ShowBookInLib
 *
 */
public class ShowBookInLib extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		BookDAO bookDAO = new BookDAO();
		HttpSession session = request.getSession();
		Collection<Book> books = new ArrayList<Book>();

		// 分页功能
		int start = 0;
		int count = 5;

		try {
			start = Integer.parseInt(request.getParameter("start"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		int next = start + count;
		int pre = start - count;

		String isbn = request.getParameter("isbn");
		if (isbn == null) {
			isbn = (String) session.getAttribute("bookInfoISBN");
		}
		Book book = bookDAO.getBookByIsbn(isbn);
		session.setAttribute("BookDetail", book);
		int total = bookDAO.getTotal(isbn);
		//
		// if(total==0) {
		// out.print("<script>alert('抱歉，这种书不可用！');window.location='readerSearchBook.jsp';</script>");
		// return;
		// }

		int last;
		if (0 == total % count)
			last = total - count;
		else
			last = total - total % count;

		pre = pre < 0 ? 0 : pre;
		next = next > last ? last : next;

		session.setAttribute("next", next);
		session.setAttribute("pre", pre);
		session.setAttribute("last", last);

		session.removeAttribute("bookList");
		session.removeAttribute("bookInfoList");
		session.setAttribute("bookInfoISBN", isbn);

		if (isbn == "" || isbn.isEmpty()) {
			out.print(
					"<script>alert('请输入完整的关键词，关键词不能为空！');window.location='readerSearchBook.jsp';</script>");
		}
		books = bookDAO.getBookListByIsbnForCart(isbn, start, count);
		if (books.isEmpty()) {
			out.print(
					"<script>alert('抱歉，图书馆现在没有这本书！请尝试新的搜索！');window.location='readerSearchBook.jsp';</script>");
		} else {
			session.setAttribute("bookInfoList", books);
		}
		request.getRequestDispatcher("readerShowBookInLib.jsp").forward(request, response);
	}

}
