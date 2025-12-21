// 定义包路径
package controller.administrator;

// 导入所需的Java类库
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 导入数据访问对象
import dao.LibrarianDAO;

/**
 * Servlet控制器类，用于处理删除图书管理员的业务逻辑
 * 通过Librarian的名字与密码删除一个librarian
 */
public class DeleteLibrarian extends HttpServlet {
	// 序列化版本号，用于序列化兼容性检查
	private static final long serialVersionUID = 1L;

	/**
	 * 构造函数
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteLibrarian() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 处理POST请求的方法
	 * 主要功能是根据图书管理员ID和姓名删除指定的图书管理员
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

		// 从请求参数中获取图书管理员ID并转换为整数
		int librarianId = Integer.valueOf(request.getParameter("librarianId"));
		// 从请求参数中获取图书管理员姓名
		String librarianName = request.getParameter("librarianName");

		// 创建图书管理员DAO实例
		LibrarianDAO libDao = new LibrarianDAO();

		// 调用DAO方法根据ID和姓名删除图书管理员
		int flag = libDao.deleteLibrarianByIdName(librarianId, librarianName);

		// 根据删除结果进行相应处理
		if (flag == 1) {
			// 删除成功，设置成功消息并转发到操作结果页面
			String msg = "删除管理员成功！";
			request.setAttribute("message", msg);
			request.getRequestDispatcher("adminOperateResult.jsp").forward(request, response);
		} else {
			// 删除失败，设置失败消息并转发到操作结果页面
			// 失败原因可能是管理员在图书馆中有某些操作或姓名与ID不正确
			String msg = "删除管理员失败！该管理员可能在图书馆中有某些操作或姓名与ID不正确！";
			request.setAttribute("message", msg);
			request.getRequestDispatcher("adminOperateResult.jsp").forward(request, response);
		}
	}
}
