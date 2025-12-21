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
import dao.BookDAO;
import dao.BorrowCartDAO;
import entity.Book;
import entity.Reader;

/**
 * Servlet控制器类，用于处理读者将书籍添加到借阅购物车的业务逻辑
 * Servlet实现类 AddBookToCart
 */
public class AddBookToCart extends HttpServlet {
	// 序列化版本号，用于序列化兼容性检查
	private static final long serialVersionUID = 1L;

	/**
	 * 构造函数
	 * @see HttpServlet#HttpServlet()
	 */
	public AddBookToCart() {
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
	 * 主要功能是将指定书籍添加到读者的借阅购物车中
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
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
		// 创建借阅购物车DAO实例
		BorrowCartDAO borrowcartdao = new BorrowCartDAO();
		// 获取当前会话对象
		HttpSession session = request.getSession();
		// 从会话中获取读者实体对象
		Reader reader = (Reader) session.getAttribute("ReaderEntity");
		// 获取读者ID
		int reader_id = reader.getId();
		// 创建书籍集合
		Collection<Book> books = new ArrayList<Book>();

		// 从请求参数中获取书籍ID并转换为整数
		int id = Integer.valueOf(request.getParameter("id"));
		// 调用DAO方法将书籍添加到借阅购物车
		borrowcartdao.addBorrowCart(id, reader_id);
		// 弹出成功提示并返回上一页
		out.print("<script>alert('添加成功');window.history.back();</script>");
	}
}
