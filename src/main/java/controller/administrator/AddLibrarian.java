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
 * Servlet控制器类，用于处理添加图书管理员的请求
 * 增加一个librarian，根据name跟password，添加成功后，则返回数据库自动生成的ID
 */
public class AddLibrarian extends HttpServlet {
	// 序列化版本号，用于序列化兼容性检查
	private static final long serialVersionUID = 1L;

	/**
	 * 构造函数
	 * @see HttpServlet#HttpServlet()
	 */
	public AddLibrarian() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 处理POST请求的方法
	 * 主要功能是接收管理员添加图书管理员的表单数据，将新图书管理员信息保存到数据库
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置请求编码格式为UTF-8，支持中文字符
		request.setCharacterEncoding("UTF-8");
		// 设置响应编码格式为UTF-8，支持中文字符
		response.setCharacterEncoding("UTF-8");
		// 设置响应内容类型为HTML，字符集为UTF-8
		response.setContentType("text/html;charset=UTF-8");
		// 获取响应输出流
		PrintWriter out = response.getWriter();

		// 从请求参数中获取图书管理员姓名
		String name = request.getParameter("librarianName");
		// 从请求参数中获取密码，并使用MD5加密
		String password = util.SecurityUtil.md5(request.getParameter("password"));
		// 创建LibrarianDAO实例
		LibrarianDAO lib = new LibrarianDAO();
		// 调用DAO方法添加图书管理员，初始状态设置为"unlock"(解锁)
		int libId = lib.addLibrarian(name, password, "unlock");

		// 判断添加操作是否成功
		if (libId != -1) {
			// 添加成功，生成成功消息并转发到操作结果页面
			String msg = "添加管理员成功！管理员ID为：" + libId;
			request.setAttribute("message", msg);
			request.getRequestDispatcher("adminOperateResult.jsp").forward(request, response);
		} else {
			// 添加失败，生成失败消息并转发到操作结果页面
			String msg = "添加管理员失败，请重试！";
			request.setAttribute("message", msg);
			request.getRequestDispatcher("adminOperateResult.jsp").forward(request, response);
		}
	}
}
