// 定义包路径
package controller.administrator;

// 导入所需的Java类库
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 导入数据访问对象和实体类
import dao.LibrarianDAO;
import entity.Librarian;

/**
 * Servlet实现类 AdminViewLibrarian
 * 用于处理查看图书管理员列表的请求，支持分页显示功能
 */
public class AdminViewLibrarian extends HttpServlet {
	// 序列化版本号，用于序列化兼容性检查
	private static final long serialVersionUID = 1L;

	/**
	 * 构造函数
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminViewLibrarian() {
		super();
		// TODO Auto-generated constructor stub
	}

	// 图书管理员DAO实例，用于数据访问操作
	private LibrarianDAO libDao = new LibrarianDAO();

	/**
	 * 处理GET请求的方法
	 * 主要功能是获取图书管理员列表并支持分页显示
	 * @param request HTTP请求对象
	 * @param response HTTP响应对象
	 * @throws ServletException Servlet异常
	 * @throws IOException IO异常
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 设置响应内容类型为HTML，字符集为UTF-8
		response.setContentType("text/html; charset=UTF-8");

		// 初始化分页参数
		int start = 0;  // 起始记录位置
		int count = 5;  // 每页显示记录数

		try {
			// 尝试从请求参数中获取起始位置
			start = Integer.parseInt(request.getParameter("start"));
		} catch (NumberFormatException e) {
			// 当浏览器没有传参数start时，使用默认值0
		}

		// 计算下一页和上一页的起始位置
		int next = start + count;
		int pre = start - count;

		// 获取图书管理员总记录数
		int total = libDao.getTotal();

		// 计算最后一页的起始位置
		int last;
		if (0 == total % count)
			last = total - count;
		else
			last = total - total % count;

		// 确保上一页和下一页的边界值正确
		pre = pre < 0 ? 0 : pre;
		next = next > last ? last : next;

		// 将分页参数设置为请求属性
		request.setAttribute("next", next);
		request.setAttribute("pre", pre);
		request.setAttribute("last", last);

		// 获取当前页的图书管理员列表
		ArrayList<Librarian> libs = libDao.getLibrarianList(start, count);
		// 将图书管理员列表设置为请求属性
		request.setAttribute("libs", libs);
		// 转发到管理员查看图书管理员页面
		request.getRequestDispatcher("adminViewLibrarian.jsp").forward(request, response);
	}
}
