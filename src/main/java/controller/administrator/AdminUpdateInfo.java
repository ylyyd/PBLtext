// 定义包路径
package controller.administrator;

// 导入所需的Java类库
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// 导入数据访问对象和实体类
import dao.AdministratorDAO;
import entity.Administrator;

/**
 * Servlet控制器类，用于处理管理员信息更新的业务逻辑
 * 更新administrator的名字/密码
 */
public class AdminUpdateInfo extends HttpServlet {
	// 序列化版本号，用于序列化兼容性检查
	private static final long serialVersionUID = 1L;

	/**
	 * 构造函数
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminUpdateInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 处理POST请求的方法
	 * 主要功能是更新管理员的个人信息（姓名和密码）
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

		// 创建管理员DAO实例
		AdministratorDAO adminDao = new AdministratorDAO();
		// 从请求参数中获取管理员ID并转换为整数
		int adminId = Integer.valueOf(request.getParameter("AdminID"));
		// 从请求参数中获取管理员姓名
		String name = request.getParameter("AdminName");
		// 从请求参数中获取密码
		String password = request.getParameter("password");

		// 判断密码是否为空或空字符串
		if (password == null || password.equals("")) {
			// 密码未更改，从session中获取原有的密码
			HttpSession session = request.getSession();
			Administrator ad = (Administrator) session.getAttribute("AdministratorEntity");
			password = ad.getPassword();
		} else {
			// 密码已更改，对新密码进行MD5加密
			password = util.SecurityUtil.md5(password);
		}

		// 调用DAO方法更新管理员信息
		int flag = adminDao.updateAdmin(adminId, name, password);

		// 根据更新结果进行相应处理
		if (flag == 1) {
			// 更新成功后，更新session中的数据
			Administrator admin = new Administrator();
			admin.setId(adminId);
			admin.setName(name);
			admin.setPassword(password);
			HttpSession session = request.getSession();
			session.setAttribute("AdministratorEntity", admin);

			// 设置成功消息并转发到操作结果页面
			String msg = "更新信息成功！";
			request.setAttribute("message", msg);
			request.getRequestDispatcher("adminOperateResult.jsp").forward(request, response);
		} else {
			// 更新失败，设置失败消息并转发到操作结果页面
			String msg = "更新信息失败，请重试！";
			request.setAttribute("message", msg);
			request.getRequestDispatcher("adminOperateResult.jsp").forward(request, response);
		}
	}
}
