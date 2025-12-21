// 定义包路径
package controller.reader;

// 导入所需的Java类库
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.*;
import dao.ReaderDAO;

/**
 * Servlet控制器类，用于处理读者登录的业务逻辑
 * Servlet实现类 Readerlogin
 */
public class ReaderLogin extends HttpServlet {
	// 序列化版本号，用于序列化兼容性检查
	private static final long serialVersionUID = 1L;

	/**
	 * 构造函数
	 * @see HttpServlet#HttpServlet()
	 */
	public ReaderLogin() {
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
	 * 实现Reader用户登陆，判断密码和ID是否相符 Liu Zhuocheng
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
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
		System.out.println(usersid);
		System.out.println(password);

		// 检查用户ID和密码是否为空
		if (request.getParameter("userID") == null || request.getParameter("password") == null
				|| request.getParameter("userID").equals("") || request.getParameter("password").equals("")) {
			// 用户ID或密码为空，提示错误并跳转回登录页面
			out.print(
					"<script language='javascript'>alert('读者ID或密码不能为空！');window.location.href='Login.jsp';</script>");
		} else {
			// 创建读者DAO实例
			ReaderDAO readerDAO = new ReaderDAO();
			// 初始化读者实体对象
			entity.Reader reader = null;
			// 验证用户ID是否为数字
			if (CharacterFilterUtil.isNumeric(usersid)) {
				// 根据用户ID获取读者实体
				reader = readerDAO.getReaderById(userid);
			}
			// 检查是否能获取到读者实体
			if (reader == null) {
				// 无法获取Reader实体，用户不存在
				out.print(
						"<script language='javascript'>alert('读者ID不存在！');window.location.href='Login.jsp';</script>");
			} else {
				// 对密码进行MD5加密
				password = SecurityUtil.md5(password);
				// 验证密码是否匹配
				if (reader.getPassword().equals(password)) {
					// 密码匹配
					if (reader.getState().equals("unlock")) {
						// 账号未锁定
						System.out.println("unlock");
						// 创建会话并设置读者实体属性
						HttpSession session = request.getSession();
						session.setAttribute("ReaderEntity", reader);
						// 重定向到读者主页
						response.sendRedirect("readerIndex.jsp");
					} else {
						// 账号被锁定，提示错误并跳转回登录页面
						out.print(
								"<script language='javascript'>alert('您的读者ID已被锁定！');window.location.href='Login.jsp';</script>");
					}
				} else {
					// 密码不正确，提示错误并跳转回登录页面
					out.print(
							"<script language='javascript'>alert('您的读者ID或密码错误！');window.location.href='Login.jsp';</script>");
				}
			}
		}
	}
}
