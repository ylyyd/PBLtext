// 定义包路径
package controller.librarian;

// 导入所需的Java类库
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 导入数据访问对象
import dao.BookDAO;

/**
 * Servlet控制器类，用于处理图书管理员删除书籍的业务逻辑
 */
public class LibrarianDeleteBook extends HttpServlet {
	// 序列化版本号，用于序列化兼容性检查
	private static final long serialVersionUID = 1L;

	// 构造函数
	public LibrarianDeleteBook() {
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
	 * 主要功能是接收图书管理员删除书籍的请求，并执行相应的删除操作
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

		// 从请求参数中获取要删除的书籍ID并转换为整数
		int bookId = Integer.parseInt(request.getParameter("book_id"));
		// 创建书籍DAO实例
		BookDAO bookDAO = new BookDAO();
		// 调用DAO方法删除书籍，返回操作结果标识
		int tag = bookDAO.deleteBookById(bookId);

		// 根据删除操作的返回结果进行相应处理
		if (tag == 1) {
			// 删除成功
			out.print("<script language='javascript'>" + "alert('删除成功！');"
					+ "window.location.href='librarianDeleteBook.jsp';" + "</script>");
		} else if (tag == 2) {
			// 删除失败，书籍状态不是"inlib"
			out.print("<script language='javascript'>" + "alert('删除失败！状态不是inlib！');"
					+ "window.location.href='librarianDeleteBook.jsp';" + "</script>");
		} else if (tag == 3) {
			// 删除失败，书籍不存在
			out.print("<script language='javascript'>" + "alert('删除失败！此书不存在！');"
					+ "window.location.href='librarianDeleteBook.jsp';" + "</script>");
		} else if (tag == 4) {
			// 删除失败，出现异常
			out.print("<script language='javascript'>" + "alert('删除失败！出现异常！');"
					+ "window.location.href='librarianDeleteBook.jsp';" + "</script>");
		}
	}
}
