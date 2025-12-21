// 定义包路径
package controller.reader;

// 导入所需的Java类库
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// 导入数据访问对象和实体类
import dao.*;
import entity.*;

/**
 * Servlet控制器类，用于处理读者搜索书籍的业务逻辑
 * Servlet实现类 SearchBookForReader
 */
public class SearchBookForReader extends HttpServlet {
	// 序列化版本号，用于序列化兼容性检查
	private static final long serialVersionUID = 1L;

	/**
	 * 构造函数
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchBookForReader() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 处理GET请求的方法
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 默认实现：返回服务器信息和上下文路径
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * 处理POST请求的方法
	 * 主要功能是根据不同的搜索条件（书名、作者、出版社、ISBN）搜索书籍
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
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

		// 从请求参数中获取搜索方式和搜索关键词
		String style = request.getParameter("searchBy");
		String name = request.getParameter("name");
		// 去除关键词首尾空格
		name = name.trim();
		// name=name.toLowerCase();//删除该行，否则英文无法查询

		// 检查关键词是否为空
		if (name == "" || name.isEmpty()) {
			// 关键词为空，提示错误并跳转回搜索页面
			out.print(
					"<script>alert('请输入完整的关键词，关键词不能为空！');window.location='readerSearchBook.jsp';</script>");
		}

		// 根据不同的搜索方式执行相应搜索
		if (style.equals("Book Name")) {
			// 按书名搜索
			books = bookDAO.getBookByAlikeTitle(name);
			System.out.println("--SearchBookForReader--,books.size()=" + books.size());

			if (books.isEmpty()) {
				// 搜索结果为空，提示错误并跳转回搜索页面
				out.print(
						"<script>alert('没有相关书籍！请尝试新的搜索！');window.location='readerSearchBook.jsp';</script>");
			} else {
				// 搜索成功，将结果保存到会话中并转发到搜索结果页面
				session.setAttribute("bookList", books);
				session.removeAttribute("bookEntity");
				request.getRequestDispatcher("readerSearchBook.jsp").forward(request, response);
			}
		} else if (style.equals("Author")) {
			// 按作者搜索
			books = bookDAO.getBookByAuthor(name);
			System.out.println("--SearchBookForReader--,books.size()=" + books.size());

			if (books.isEmpty()) {
				// 搜索结果为空，提示错误并跳转回搜索页面
				out.print(
						"<script>alert('没有相关书籍！请尝试新的搜索！');window.location='readerSearchBook.jsp';</script>");
			} else {
				// 搜索成功，将结果保存到会话中并转发到搜索结果页面
				session.setAttribute("bookList", books);
				session.removeAttribute("bookEntity");
				request.getRequestDispatcher("readerSearchBook.jsp").forward(request, response);
			}
		} else if (style.equals("Publisher")) {
			// 按出版社搜索
			books = bookDAO.getBookByPublisher(name);
			System.out.println("--SearchBookForReader--,books.size()=" + books.size());

			if (books.isEmpty()) {
				// 搜索结果为空，提示错误并跳转回搜索页面
				out.print(
						"<script>alert('没有相关书籍！请尝试新的搜索！');window.location='readerSearchBook.jsp';</script>");
			} else {
				// 搜索成功，将结果保存到会话中并转发到搜索结果页面
				session.setAttribute("bookList", books);
				session.removeAttribute("bookEntity");
				request.getRequestDispatcher("readerSearchBook.jsp").forward(request, response);
			}
		} else if (style.equals("ISBN")) {
			// 按ISBN搜索
			Book book = bookDAO.getBookByIsbn(name);
			System.out.println(book.toString());
			if (book == null) {
				// 搜索结果为空，提示错误并跳转回搜索页面
				out.print(
						"<script>alert('没有相关书籍！请尝试新的搜索！');window.location='readerSearchBook.jsp';</script>");
			}
			// 搜索成功，将结果保存到会话中并转发到搜索结果页面
			session.setAttribute("bookEntity", book);
			session.removeAttribute("bookList");
			request.getRequestDispatcher("readerSearchBook.jsp").forward(request, response);

		} else {
			// 搜索方式未选择，提示错误并跳转回搜索页面
			out.print("<script>alert('请选择搜索类型！');window.location='readerSearchBook.jsp';</script>");
		}
	}
}
