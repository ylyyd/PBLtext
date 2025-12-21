// 定义包路径
package controller.librarian;

// 导入所需的Java类库
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// 导入数据访问对象和实体类
import dao.BookDAO;
import entity.Book;

/**
 * Servlet控制器类，用于处理图书管理员搜索书籍的业务逻辑
 * 该servlet用于获取librarianSearchBook.jsp的请求，返回图书的信息
 */
public class LibrarianSearchBook extends HttpServlet {
	// 序列化版本号，用于序列化兼容性检查
	private static final long serialVersionUID = 1L;
	// 书籍DAO实例，用于数据访问操作
	private BookDAO bookDAO = new BookDAO();

	// 构造函数
	public LibrarianSearchBook() {
		super();
	}

	/**
	 * 处理GET请求的方法
	 * 主要功能是处理书籍详情查看和分页显示功能
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置请求和响应的字符编码为UTF-8，支持中文字符
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 设置响应内容类型为HTML，字符集为UTF-8
		response.setContentType("text/html;charset=UTF-8");
		// 获取响应输出流
		PrintWriter out = response.getWriter();

		// 创建书籍DAO实例
		BookDAO bookDAO = new BookDAO();
		// 获取当前会话对象
		HttpSession session = request.getSession();
		// 创建书籍集合
		Collection<Book> books = new ArrayList<Book>();

		// 分页功能
		int start = 0;  // 起始记录位置
		int count = 5;  // 每页显示记录数

		try {
			// 尝试从请求参数中获取起始位置
			start = Integer.parseInt(request.getParameter("start"));
		} catch (NumberFormatException e) {
			// 处理数字格式异常
			e.printStackTrace();
		}

		// 计算下一页和上一页的起始位置
		int next = start + count;
		int pre = start - count;
		// 获取ISBN参数
		String isbn = request.getParameter("isbn");
		if (isbn == null) {
			// isbn为空，说明是从details.jsp发来的请求
			isbn = request.getParameter("bookInfoISBN"); // 从url中获取参数
		}
		// 获取指定ISBN书籍的总数
		int total = bookDAO.getTotal(isbn);

		// 检查是否有相关书籍
		if (total == 0) {
			out.print(
					"<script>alert('这种书不可用！');window.location='librarianSearchBook.jsp';</script>");
			return;
		}

		// 计算最后一页的起始位置
		int last;
		if (0 == total % count)
			last = total - count;
		else
			last = total - total % count;

		// 确保上一页和下一页的边界值正确
		pre = pre < 0 ? 0 : pre;
		next = next > last ? last : next;

		// 设置分页参数为请求属性
		request.setAttribute("next", next);
		request.setAttribute("pre", pre);
		request.setAttribute("last", last);

		// 转发到页面，展示图书详细信息
		// 通过ISBN获取书籍信息列表，区分ID
		List<Book> list = (List<Book>) bookDAO.getBookListByIsbn(isbn, start, count);
		// 获取书本实例，不区分id
		Book book = bookDAO.getBookByIsbn(isbn);

		// 设置请求属性
		request.setAttribute("information", book);
		request.setAttribute("library", list);
		// 转发到详情页面
		request.getRequestDispatcher("details.jsp").forward(request, response);
	}

	/**
	 * 处理POST请求的方法
	 * 主要功能是根据不同的搜索条件搜索书籍
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置请求和响应的字符编码为UTF-8，支持中文字符
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 设置响应内容类型为HTML，字符集为UTF-8
		response.setContentType("text/html;charset=UTF-8");
		// 获取响应输出流
		PrintWriter out = response.getWriter();

		// 获取搜索方式和关键词参数
		String searchBy = request.getParameter("searchBy");
		String keyword = request.getParameter("keyword");
		System.out.println("关键词keyword=" + keyword);
		System.out.println(request.getCharacterEncoding());

		// 根据不同的搜索方式执行相应搜索
		if (searchBy.equals("Book Name")) {
			System.out.println("By Book Name");
			// 按书名搜索
			List<Book> list = (List<Book>) bookDAO.getBookByAlikeTitle(keyword);
			if (list == null || list.isEmpty()) {
				// 搜索结果为空
				out.print("<script language='javascript'>" + "alert('图书馆中没有这样的书！');"
						+ "window.location.href='librarianSearchBook.jsp';" + "</script>");
			} else {
				// 搜索成功，将结果保存到会话中并重定向
				HttpSession session = request.getSession();
				session.setAttribute("bookList", list);
				session.removeAttribute("bookEntity");
				response.sendRedirect("librarianSearchBook.jsp");
			}
		} else if (searchBy.equals("ISBN")) {
			System.out.println("By ISBN");
			// 按ISBN搜索
			Book book = bookDAO.getBookByIsbn(keyword);
			if (book == null) {
				// 搜索结果为空
				out.print("<script language='javascript'>" + "alert('图书馆中没有这样的书！');"
						+ "window.location.href='librarianSearchBook.jsp';" + "</script>");
			} else {
				// 搜索成功，将结果保存到会话中并重定向
				HttpSession session = request.getSession();
				session.setAttribute("bookEntity", book);
				session.removeAttribute("bookList");
				response.sendRedirect("librarianSearchBook.jsp");
			}
		} else if (searchBy.equals("Author")) {
			System.out.println("By Author");
			// 按作者搜索
			List<Book> list = (List<Book>) bookDAO.getBookByAuthor(keyword);
			if (list == null || list.isEmpty()) {
				// 搜索结果为空
				out.print("<script language='javascript'>" + "alert('图书馆中没有这样的书！');"
						+ "window.location.href='librarianSearchBook.jsp';" + "</script>");
			} else {
				// 搜索成功，将结果保存到会话中并重定向
				HttpSession session = request.getSession();
				session.setAttribute("bookList", list);
				session.removeAttribute("bookEntity");
				response.sendRedirect("librarianSearchBook.jsp");
			}
		} else if (searchBy.equals("Publisher")) {
			// 按出版社搜索
			List<Book> list = (List<Book>) bookDAO.getBookByPublisher(keyword);
			if (list == null || list.isEmpty()) {
				// 搜索结果为空
				out.print("<script language='javascript'>" + "alert('图书馆中没有这样的书！');"
						+ "window.location.href='librarianSearchBook.jsp';" + "</script>");
			} else {
				// 搜索成功，将结果保存到会话中并重定向
				HttpSession session = request.getSession();
				session.setAttribute("bookList", list);
				session.removeAttribute("bookEntity");
				response.sendRedirect("librarianSearchBook.jsp");
			}
		}
	}
}
