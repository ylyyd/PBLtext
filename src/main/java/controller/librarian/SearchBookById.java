// 定义包路径
package controller.librarian;

// 导入所需的Java类库
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 导入数据访问对象和实体类
import dao.BookDAO;
import entity.Book;

/**
 * Servlet控制器类，用于通过书籍ID搜索书籍信息
 * 该servlet用于通过book_id搜索一本书的信息
 */
public class SearchBookById extends HttpServlet {
	// 序列化版本号，用于序列化兼容性检查
	private static final long serialVersionUID = 1L;

	// 构造函数
	public SearchBookById() {
		super();
	}

	/**
	 * 处理GET请求的方法
	 * 当前为空实现
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 空实现
	}

	/**
	 * 处理POST请求的方法
	 * 主要功能是根据书籍ID搜索书籍信息并转发到删除书籍页面
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
		int bookId = Integer.parseInt(request.getParameter("book_id"));
		// 创建书籍DAO实例
		BookDAO bookDAO = new BookDAO();
		// 根据书籍ID搜索书籍信息
		Book book = bookDAO.searchByID(bookId);
		// 设置请求属性供JSP页面使用
		request.setAttribute("bookEntity", book);
		// 转发到图书管理员删除书籍页面
		request.getRequestDispatcher("librarianDeleteBook.jsp").forward(request, response);
	}
}
