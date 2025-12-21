// 定义包路径
package controller.librarian;

// 导入所需的Java类库
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// 导入数据访问对象、实体类和工具类
import dao.LibrarianDAO;
import entity.Librarian;
import util.CharacterFilterUtil;
import util.SecurityUtil;

/**
 * Servlet控制器类，用于处理图书管理员登录的业务逻辑
 * 该类用于处理Librarian登录的业务逻辑
 */
public class LibrarianLogin extends HttpServlet {

	/**
	 * 处理GET请求的方法
	 * 当前为空实现
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 空实现
	}

	/**
	 * 销毁方法，在Servlet被销毁时调用
	 */
	public void destroy() {
		super.destroy();
	}

	/**
	 * 处理Librarian登录的业务逻辑
	 */

	/**
	 * 更改了判断后的提示信息LiuZhuocheng
	 * 处理POST请求的方法，主要功能是验证图书管理员登录信息
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 测试用：NPU，710072
		// 设置请求和响应的字符编码为UTF-8，支持中文字符
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 设置响应内容类型为HTML，字符集为UTF-8
		response.setContentType("text/html;charset=UTF-8");
		// 获取响应输出流
		PrintWriter out = response.getWriter();

		// 获取用户名参数
		String userName = (String) request.getParameter("userID");
		int userid = Integer.MAX_VALUE;
		// 验证并转换用户ID为整数
		if (userName != null && !userName.isEmpty())
			userid = Integer.valueOf(userName);
		// 获取密码参数
		String password = (String) request.getParameter("password");
		System.out.println(userName);
		System.out.println(password);

		// 检查用户名和密码是否为空
		if (request.getParameter("userID") == null || request.getParameter("password") == null
				|| request.getParameter("userID").equals("") || request.getParameter("password").equals("")) {
			out.print(
					"<script language='javascript'>alert('管理员ID或密码不能为空！');window.location.href='Login.jsp';</script>");
		} else {
			// 创建图书管理员DAO实例
			LibrarianDAO librarianDAO = new LibrarianDAO();
			Librarian librarian = null;
			// 验证用户名是否为数字
			if (CharacterFilterUtil.isNumeric(userName)) {
				// 根据用户ID获取一个librarian实体
				librarian = librarianDAO.getLibrarianById(userid);
			}

			// 检查是否能获取到librarian实体
			if (librarian == null) {
				// 无法获取librarian实体，用户不存在
				System.out.println("用户不存在");
				out.print(
						"<script language='javascript'>alert('管理员ID不存在！');window.location.href='Login.jsp';</script>");
			} else {
				// 对密码进行MD5加密
				password = SecurityUtil.md5(password);
				// 检查密码是否匹配
				if (librarian.getPassword().equals(password)) {
					// 密码匹配
					if (librarian.getState().equals("unlock")) {
						// 账号未锁定
						System.out.println("登录成功");
						// 创建会话并设置图书管理员实体属性
						HttpSession session = request.getSession();
						session.setAttribute("librarianEntity", librarian);
						// 重定向到图书管理员主页
						response.sendRedirect("librarian/librarianHomepage.jsp");
					} else {
						// 账号被锁定
						System.out.println("账号被锁定");
						out.print(
								"<script language='javascript'>alert('您的管理员ID已被锁定！');window.location.href='Login.jsp';</script>");
					}
				} else {
					// 密码不正确
					System.out.println("密码不正确");
					out.print(
							"<script language='javascript'>alert('您的管理员ID或密码错误！');window.location.href='Login.jsp';</script>");
				}
			}
		}
	}
}
