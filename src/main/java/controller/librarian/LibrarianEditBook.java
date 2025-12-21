// 定义包路径
package controller.librarian;

// 导入所需的Java类库
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 导入数据访问对象和实体类
import dao.AuthorDAO;
import dao.BookDAO;
import entity.Book;

/**
 * Servlet控制器类，用于处理图书管理员编辑书籍信息的业务逻辑
 */
public class LibrarianEditBook extends HttpServlet {
	// 序列化版本号，用于序列化兼容性检查
	private static final long serialVersionUID = 1L;

	// 书籍DAO实例，用于数据访问操作
	private BookDAO bookDAO = new BookDAO();
	// 作者DAO实例，用于作者相关操作
	private AuthorDAO authorDAO = new AuthorDAO();

	// 构造函数
	public LibrarianEditBook() {
		super();
	}

	/**
	 * 处理GET请求的方法
	 * 主要功能是获取指定书籍的详细信息并转发到编辑页面
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

		// 从请求参数中获取书籍ID并转换为整数
		int bookId = Integer.parseInt(request.getParameter("book_id"));
		// 通过书籍ID查询书籍信息
		Book book = bookDAO.searchByID(bookId);
		// 通过ISBN获取同ISBN的所有书籍列表
		List<Book> list = (List<Book>) bookDAO.getBookListByIsbn(book.getISBN());

		// 设置请求属性供JSP页面使用
		request.setAttribute("bookEditList", list);
		request.setAttribute("bookEdit", book);
		request.setAttribute("bookEditId", bookId);
		// 转发到图书管理员编辑书籍页面
		request.getRequestDispatcher("librarianEditBook.jsp").forward(request, response);
	}

	/**
	 * 处理POST请求的方法
	 * 主要功能是接收并处理图书管理员提交的书籍信息修改请求
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
		int bookId = Integer.parseInt(request.getParameter("Book ID"));
		// 从请求参数中获取位置信息
		String location = request.getParameter("Location");
		// 从请求参数中获取状态信息
		String state = request.getParameter("State");

		// 调用DAO方法更新书籍信息
		boolean tag = bookDAO.updateBookInfoById(bookId, location, state);

		// 根据更新结果进行相应处理
		if (tag) {
			// 更新成功
			out.print("<script language='javascript'>" + "alert('恭喜！您已成功修改书籍！');"
					+ "window.location.href='librarianSearchBook.jsp';" + "</script>");
		} else {
			// 没有改变
			out.print("<script language='javascript'>" + "alert('没有任何改变！');"
					+ "window.location.href='librarianSearchBook.jsp';" + "</script>");
		}
	}
}
