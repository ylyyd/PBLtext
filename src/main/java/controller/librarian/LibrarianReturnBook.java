// 定义包路径
package controller.librarian;

// 导入所需的Java类库
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// 导入数据访问对象和实体类
import dao.BookDAO;
import dao.BorrowCartDAO;
import dao.BorrowItemDAO;
import entity.Book;
import entity.Librarian;
import entity.Reader;

/**
 * Servlet控制器类，用于处理图书管理员还书业务逻辑
 * librarian处理还书
 */
public class LibrarianReturnBook extends HttpServlet {
	// 序列化版本号，用于序列化兼容性检查
	private static final long serialVersionUID = 1L;
	// 借阅购物车DAO实例，用于处理还书操作
	private BorrowCartDAO dao = new BorrowCartDAO();

	// 构造函数
	public LibrarianReturnBook() {
		super();
	}

	/**
	 * 处理GET请求的方法
	 * 主要功能是执行实际的还书操作
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置请求和响应的字符编码为UTF-8，支持中文字符
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 设置响应内容类型为HTML，字符集为UTF-8
		response.setContentType("text/html;charset=UTF-8");

		// 从请求参数中获取书籍ID并转换为整数
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		// 获取当前会话对象
		HttpSession session = request.getSession();
		// 从会话中获取图书管理员实体
		Librarian librarian = (Librarian) session.getAttribute("librarianEntity");
		// 调用DAO方法执行还书操作
		if (dao.returnBook(bookId, librarian.getId())) {
			// 还书成功，显示成功提示并跳转回还书页面
			PrintWriter out = response.getWriter();
			out.print("<script language='javascript'>" + "alert('成功！');"
					+ "window.location.href='librarianReturnBook.jsp';" + "</script>");
		} else {
			// 还书失败，显示失败提示并跳转回还书页面
			PrintWriter out = response.getWriter();
			out.print("<script language='javascript'>" + "alert('失败！');"
					+ "window.location.href='librarianReturnBook.jsp';" + "</script>");
		}
	}

	/**
	 * 处理POST请求的方法
	 * 主要功能是验证书籍和读者信息，并显示还书确认页面
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
		// 从请求参数中获取书籍ID并转换为整数
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		// 获取当前会话对象
		HttpSession session = request.getSession();
		// 从会话中获取图书管理员实体
		Librarian librarian = (Librarian) session.getAttribute("librarianEntity");
		// 创建借阅项DAO实例
		BorrowItemDAO d = new BorrowItemDAO();

		// 获取读者信息
		Reader reader = d.getReaderInBorrowItemByBookID(bookId);
		// 检查读者信息是否存在
		if (reader == null) {
			// 读者信息不存在，显示错误提示并跳转回还书页面
			out.print("<script language='javascript'>"
					+ "alert('没有读者借阅此书或读者信息丢失！');"
					+ "window.location.href='librarianReturnBook.jsp';" + "</script>");
		}
		// 创建书籍DAO实例
		BookDAO bDAO = new BookDAO();

		// 获取book信息
		Book book = bDAO.searchByID(bookId);
		// 检查书籍是否存在
		if (book != null) {
			// 书籍存在，设置请求属性并转发到还书页面
			request.setAttribute("borrowerEntity", reader);
			request.setAttribute("bookEntity", book);
			request.getRequestDispatcher("librarianReturnBook.jsp").forward(request, response);
			// 注释掉的代码是另一种还书处理方式
		} else {
			// 书籍不存在，显示错误提示并跳转回还书页面
			out.print("<script language='javascript'>" + "alert('错误的书籍ID！不存在ID为'" + bookId + "的书！);"
					+ "window.location.href='librarianReturnBook.jsp';" + "</script>");
		}
	}
}
