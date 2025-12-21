// 定义包路径
package controller.librarian;

// 导入所需的Java类库
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// 导入数据访问对象、实体类和工具类
import dao.LibrarianDAO;
import entity.Librarian;
import util.SecurityUtil;

/**
 * Servlet控制器类，用于处理图书管理员修改密码的业务逻辑
 * 该Servlet用于接收Librarian修改密码的请求
 */
public class ChangePassword extends HttpServlet {
	// 序列化版本号，用于序列化兼容性检查
	private static final long serialVersionUID = 1L;

	// 构造函数
	public ChangePassword() {
		super();
	}

	/**
	 * 处理GET请求的方法
	 * 当前为空实现
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 空实现
	}

	/**
	 * 处理POST请求的方法
	 * 主要功能是处理图书管理员修改密码的请求
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
		// 获取当前会话对象
		HttpSession session = request.getSession();

		// 从会话中获取图书管理员实体对象
		Librarian librarian = (Librarian) session.getAttribute("librarianEntity");
		// 创建图书管理员DAO实例
		LibrarianDAO librarianDAO = new LibrarianDAO();
		// 获取表单数据并进行MD5加密
		String oldPw = SecurityUtil.md5(request.getParameter("old"));     // 旧密码
		String newPw = SecurityUtil.md5(request.getParameter("new"));     // 新密码
		String confirm = SecurityUtil.md5(request.getParameter("confirm")); // 确认密码

		// 检查"新密码"和"确认密码"是否相同
		if (newPw.equals(confirm)) {
			// 新密码和确认密码相同，调用DAO方法修改密码
			if (librarianDAO.changePasswordByOldPassword_NewPassword(librarian.getName(), oldPw, newPw)) {
				// 密码修改成功
				System.out.println("--Librarian--, 修改密码成功");
				// 弹出成功提示并跳转到图书管理员主页
				out.print("<script language='javascript'>"
						+ "alert('恭喜！您已成功修改密码！');"
						+ "window.location.href='librarianHomepage.jsp';" + "</script>");
			} else {
				// 旧密码错误
				System.out.println("旧密码错误");
				// 弹出错误提示并跳转回密码修改页面
				out.print("<script language='javascript'>" + "alert('旧密码错误，请重新输入！');"
						+ "window.location.href='librarianModifyInfo.jsp';" + "</script>");
			}
		} else {
			// 新密码和确认密码不相同
			System.out.println("新密码和确认密码不相同");
			// 弹出错误提示并跳转回密码修改页面
			out.print("<script language='javascript'>" + "alert('确认密码与新密码不同');"
					+ "window.location.href='librarianModifyInfo.jsp';" + "</script>");
		}
	}
}
