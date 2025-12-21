// 定义包路径
package controller.administrator;

// 导入所需的Java类库
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 导入数据访问对象和实体类
import dao.LibrarianDAO;
import entity.Librarian;

/**
 * Servlet实现类 SearchLibrarian
 * 用于处理图书管理员搜索请求的Servlet控制器
 */
public class SearchLibrarian extends HttpServlet {
	// 序列化版本号，用于序列化兼容性检查
	private static final long serialVersionUID = 1L;

	/**
	 * 构造函数
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchLibrarian() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 处理POST请求的方法
	 * 主要功能是根据不同的搜索条件（ID或姓名）搜索图书管理员
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

		// 从请求参数中获取搜索方式选项
		String option = request.getParameter("style");

		// 如果搜索方式是按图书管理员ID搜索
		if (option.equals("librarianId")) {
			// 获取并转换图书管理员ID
			int id = Integer.valueOf(request.getParameter("name"));
			// 调用DAO方法根据ID获取图书管理员列表
			ArrayList<Librarian> libs = new LibrarianDAO().getLibrarianListById(id);
			// 检查搜索结果是否存在
			if (libs != null && libs.size() != 0) {
				// 存在结果，将结果设置为请求属性并转发到搜索结果显示页面
				request.setAttribute("libs", libs);
				request.getRequestDispatcher("adminSearchLibrarian.jsp").forward(request, response);
			} else {
				// 不存在结果，设置错误消息并转发到操作结果页面
				String msg = "管理员ID不存在，请尝试其他管理员ID！";
				request.setAttribute("message", msg);
				request.getRequestDispatcher("adminOperateResult.jsp").forward(request, response);
			}
		}

		// 如果搜索方式是按图书管理员姓名搜索
		if (option.equals("librarianName")) {
			// 获取图书管理员姓名
			String name = request.getParameter("name");
			// 调用DAO方法根据姓名获取图书管理员列表
			ArrayList<Librarian> libs = new LibrarianDAO().getLibrarianListByName(name);
			// 检查搜索结果是否存在
			if (libs != null && libs.size() != 0) {
				// 存在结果，将结果设置为请求属性并转发到搜索结果显示页面
				request.setAttribute("libs", libs);
				request.getRequestDispatcher("adminSearchLibrarian.jsp").forward(request, response);
			} else {
				// 不存在结果，设置错误消息并转发到操作结果页面
				String msg = "管理员姓名不存在，请尝试其他管理员姓名！";
				request.setAttribute("message", msg);
				request.getRequestDispatcher("adminOperateResult.jsp").forward(request, response);
			}
		}
	}
}
