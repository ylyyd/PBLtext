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
import dao.ReaderDAO;
import entity.Book;
import entity.Librarian;
import entity.Reader;

/**
 * Servlet控制器类，用于处理图书管理员借书业务逻辑
 * 该serlvet获取librarianLendBook.jsp的请求，查询reader的状态，并将book借给reader。
 */
public class LibrarianLendBook extends HttpServlet {
	// 序列化版本号，用于序列化兼容性检查
	private static final long serialVersionUID = 1L;

	// 构造函数
	public LibrarianLendBook() {
		super();
	}

	/**
	 * 处理GET请求的方法
	 * 处理showCart.jsp发来的请求，同意将borrow_cart中的书借给读者
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取参数
		// 设置请求和响应的字符编码为UTF-8，支持中文字符
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 设置响应内容类型为HTML，字符集为UTF-8
		response.setContentType("text/html;charset=UTF-8");
		// 获取响应输出流
		PrintWriter out = response.getWriter();
		// 获取操作类型参数
		String operate = request.getParameter("operate");
		// 获取读者ID并转换为整数
		int readerId = Integer.parseInt(request.getParameter("readerId"));
		// 获取书籍ID并转换为整数
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		// 获取当前会话对象
		HttpSession session = request.getSession();
		// 从会话中获取图书管理员实体
		Librarian librarian = (Librarian) session.getAttribute("librarianEntity");
		// 创建借阅购物车DAO实例
		BorrowCartDAO bDAO = new BorrowCartDAO();

		// 根据操作类型执行相应处理
		if (operate.equals("agree")) {
			// 同意将书借出
			System.out.println(
					"readerId=" + readerId + "," + "bookId=" + bookId + "," + "librarianId" + librarian.getId());
			// 调用DAO方法同意借书
			bDAO.agreeBorrowBook(readerId, bookId, librarian.getId());
		} else if (operate.equals("disagree")) {
			// 不同意将书借出
			// 调用DAO方法拒绝借书
			bDAO.disagreeBorrowBook(readerId, bookId, librarian.getId());
		}
		// 重定向到ShowCart页面
		response.sendRedirect("ShowCart");
	}

	/**
	 * 处理POST请求的方法
	 * 接收从librarianLendBook.jsp发来的请求，将书借给读者
	 *
	 * @author zengyaoNPU
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

		// 检查是否有书籍ID参数
		if (request.getParameter("bookId") != null) {
			// 获取参数
			int bookId = Integer.parseInt(request.getParameter("bookId"));
			int readerId = Integer.parseInt(request.getParameter("readerId"));
			// 创建读者DAO实例
			ReaderDAO readerDAO = new ReaderDAO();
			// 获取reader实体
			Reader reader = readerDAO.getReaderById(readerId);
			// 获取借阅总数
			int total = readerDAO.getBorrowTotal(readerId);
			// 设置请求属性
			request.setAttribute("readerEntity", reader);
			request.setAttribute("total", String.valueOf(total));
			// 创建书籍DAO实例
			BookDAO bookDAO = new BookDAO();
			// 获取book实例
			Book book = bookDAO.searchByID(bookId);

			// 检查书籍是否存在及状态
			if (book == null) {
				// 书籍不存在
				System.out.println("--LibrarianLendBook--,book is null for id=" + bookId);
				out.print("<script language='javascript'>" + "alert('抱歉！该书不存在！');"
						+ "window.location.href='librarianLendBook.jsp';" + "</script>");
			} else if (book.getState().equals("borrowed")) {
				// 书已被借阅
				System.out.println("--LibrarianLendBook--,borrowed");
				out.print("<script language='javascript'>" + "alert('抱歉！您选择的书已被借阅！');"
						+ "window.location.href='librarianLendBook.jsp';" + "</script>");
			} else if (book.getState().equals("reserve")) {
				// 书已被预约
				System.out.println("--LibrarianLendBook--,reserve");
				out.print("<script language='javascript'>" + "alert('抱歉！您选择的书已被预约！');"
						+ "window.location.href='librarianLendBook.jsp';" + "</script>");
			} else {
				// 借书成功
				System.out.println("--LibrarianLendBook--,success branch");
				// 获取当前会话对象
				HttpSession session = request.getSession();
				// 从会话中获取图书管理员实体
				Librarian librarian = (Librarian) session.getAttribute("librarianEntity");
				// 调用DAO方法执行借书操作
				bookDAO.lendBook(bookId, readerId, librarian.getId());
				out.print("<script language='javascript'>" + "alert('恭喜！借阅成功！');"
						+ "window.location.href='librarianLendBook.jsp';" + "</script>");
			}
		} else if (request.getParameter("readerId") != null) {
			// 只有读者ID参数的情况
			int readerId = Integer.parseInt(request.getParameter("readerId"));
			// 创建读者DAO实例
			ReaderDAO readerDAO = new ReaderDAO();
			// 获取reader实例和该reader借阅的数量
			Reader reader = readerDAO.getReaderById(readerId);
			int total = readerDAO.getBorrowTotal(readerId);
			// 设置request属性
			request.setAttribute("readerEntity", reader);
			request.setAttribute("total", String.valueOf(total));
			// 转发到图书管理员借书页面
			request.getRequestDispatcher("librarianLendBook.jsp").forward(request, response);
		}
	}
}
