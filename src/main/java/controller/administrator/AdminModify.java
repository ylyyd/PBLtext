// 定义包路径
package controller.administrator;

// 导入所需的Java类库
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 导入数据访问对象和实体类
import dao.LibrarianDAO;
import entity.Librarian;

/**
 * Servlet控制器类，用于处理管理员信息修改的业务逻辑
 * 在把指定的Librarian的所有信息显示出来后将修改Librarian的信息
 */
public class AdminModify extends HttpServlet {
	// 序列化版本号，用于序列化兼容性检查
	private static final long serialVersionUID = 1L;

	/**
	 * 构造函数
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminModify() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 处理POST请求的方法
	 * 主要功能是接收并处理图书管理员信息的修改请求
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
		int id = Integer.valueOf(request.getParameter("librarianId"));
		// 从请求参数中获取图书管理员姓名
		String name = request.getParameter("librarianName");
		// 从请求参数中获取密码
		String password = request.getParameter("password");
		// 从请求参数中获取账号状态
		String state = request.getParameter("state");

		// 通过ID获取原始图书管理员信息
		Librarian librarian = new LibrarianDAO().getLibrarianById(id);
		// 判断密码是否被更改
		if (password == null || password.equals("")) {
			// 密码未更改，使用原有密码
			password = librarian.getPassword();
		} else {
			// 密码已更改，对新密码进行MD5加密
			password = util.SecurityUtil.md5(password);
		}

		// 调用DAO方法更新图书管理员信息
		int flag = new LibrarianDAO().updateLibrarian(id, name, password, state);

		// 根据更新结果进行相应处理
		if (flag == 1) {
			// 更新成功，设置成功消息并转发到操作结果页面
			String msg = "修改管理员成功！";
			request.setAttribute("message", msg);
			request.getRequestDispatcher("adminOperateResult.jsp").forward(request, response);
		} else {
			// 更新失败，设置失败消息并转发到操作结果页面
			String msg = "修改管理员失败，请重试！";
			request.setAttribute("message", msg);
			request.getRequestDispatcher("adminOperateResult.jsp").forward(request, response);
		}
	}
}
