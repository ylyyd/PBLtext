// 定义包路径
package controller.administrator;

// 导入所需的Java类库
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// 导入数据访问对象和实体类
import dao.AdministratorDAO;
import entity.Administrator;

// 导入工具类
import util.CharacterFilterUtil;
import util.SecurityUtil;

/**
 * Servlet控制器类，用于处理管理员登录的业务逻辑
 * 该类用于处理Administrator登录的业务逻辑
 */
public class AdministratorLogin extends HttpServlet {

	/**
	 * 销毁方法，在Servlet被销毁时调用
	 */
	public void destroy() {
		super.destroy();
	}

	/**
	 * 处理GET请求的方法
	 * 当前为空实现
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 空实现
	}

	/**
	 * 处理POST请求的方法
	 * 主要功能是验证管理员登录信息并创建会话
	 * 处理Administrator登录的业务逻辑
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 设置请求和响应的字符编码为UTF-8，支持中文字符
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 设置响应内容类型为HTML，字符集为UTF-8
		response.setContentType("text/html;charset=UTF-8");
		// 获取响应输出流
		PrintWriter out = response.getWriter();

		// 从请求参数中获取用户ID
		String usersid = (String) request.getParameter("userID");
		int userid = Integer.MAX_VALUE;
		// 验证并转换用户ID为整数
		if (usersid != null && !usersid.isEmpty())
			userid = Integer.valueOf(usersid);
		// 从请求参数中获取密码
		String password = (String) request.getParameter("password");

		// 检查用户ID和密码是否为空
		if (request.getParameter("userID") == null || request.getParameter("password") == null
				|| request.getParameter("userID").equals("") || request.getParameter("password").equals("")) {
			// 如果为空，提示错误并跳转回登录页面
			out.print(
					"<script language='javascript'>alert('管理员ID或密码不能为空！');window.location.href='Login.jsp';</script>");
		} else {
			// 创建管理员DAO实例
			AdministratorDAO administratorDAO = new AdministratorDAO();
			Administrator administrator = null;
			// 对密码进行MD5加密
			password = SecurityUtil.md5(password);
			// 验证用户ID是否为数字
			if (CharacterFilterUtil.isNumeric(usersid)) {
				// 通过ID获取管理员信息
				administrator = administratorDAO.getAdministratorById(userid);
			}

			// 检查管理员是否存在
			if (administrator == null) {
				// 无法获取Administrator实体
				System.out.println("the administrator not exsit");
				// 提示管理员不存在并跳转回登录页面
				out.print(
						"<script language='javascript'>alert('管理员ID不存在！');window.location.href='Login.jsp';</script>");
			} else if (administrator.getPassword().equals(password)) {
				// 密码正确，创建会话并设置管理员实体属性
				HttpSession session = request.getSession();
				session.setAttribute("AdministratorEntity", administrator);
				// 重定向到管理员主页
				response.sendRedirect("adminHomepage.jsp");
			} else {
				// 密码错误，提示错误并跳转回登录页面
				out.print(
						"<script language='javascript'>alert('您的管理员ID或密码错误！');window.location.href='Login.jsp';</script>");
			}
		}
	}
}
