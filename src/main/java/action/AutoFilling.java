// 定义包路径
package action;

// 导入所需的Java类库
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet实现类 AutoFilling
 * 用于自动填充登录表单功能，从Cookie中读取保存的用户信息并填充到登录页面
 */
public class AutoFilling extends HttpServlet {
	// 序列化版本号，用于序列化兼容性检查
	private static final long serialVersionUID = 1L;

	/**
	 * 构造函数
	 * @see HttpServlet#HttpServlet()
	 */
	public AutoFilling() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 处理GET请求的方法
	 * 主要功能是从Cookie中查找指定用户的信息，并将其设置为请求属性供登录页面使用
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取当前会话对象
		HttpSession session = request.getSession();
		// 从请求参数中获取用户ID
		String userId = request.getParameter("param");
		System.out.println(userId);
		// 获取所有Cookie
		Cookie[] cookies = request.getCookies();
		// 用于存储找到的account Cookie
		Cookie accountCookie = null;
		// 标记是否存在account Cookie
		boolean has = false;

		// 检查cookies是否为空
		if (cookies != null) {
			// 含有cookie
			System.out.println("--AutoFilling--,--doGet()--,cookie存在");
			// 遍历所有Cookie查找名为"account"的Cookie
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("account")) {
					System.out.println("--AutoFilling--,--doGet()--,含有保存账号的cookie");
					has = true;
					accountCookie = cookie;
					break;
				}
			}

			// 如果找到了account Cookie
			if (has) {
				// 含有所需cookie
				// 获取Cookie的值
				String cookieValue = accountCookie.getValue();

				// 如果Cookie值包含多个账户
				if (cookieValue.contains("&")) {
					// 有多个account
					System.out.println("--AutoFilling--,--doGet()--,含有多个账号");
					String[] accounts = cookieValue.split("&");
					// 遍历所有账户查找匹配的用户ID
					for (int i = 0; i < accounts.length; i++) {
						if (accounts[i].split("=")[0].equals(userId)) {
							// 在cookie中找到选中的userID
							// 提取密码和权限信息
							String password = accounts[i].split("=")[1];
							String authority = accounts[i].split("=")[2];
							System.out.println("--AutoFilling--,--doGet()--,userId=" + userId);
							System.out.println("--AutoFilling--,--doGet()--,password=" + password);
							System.out.println("--AutoFilling--,--doGet()--,authority=" + authority);
							// 将用户信息设置为请求属性，供JSP页面使用
							request.setAttribute("userId", userId);
							request.setAttribute("password", password);
							request.setAttribute("authority", authority);
							break;
						}
					}
					// 转发请求到登录页面
					request.getRequestDispatcher("Login.jsp").forward(request, response);
				} else {
					// 仅有一个account
					System.out.println("--AutoFilling--,--doGet()--,仅有一个账号");
					if (cookieValue.split("=")[0].equals(userId)) {
						// 在cookie中找到选中的userID
						// 提取密码和权限信息
						String password = cookieValue.split("=")[1];
						String authority = cookieValue.split("=")[2];
						System.out.println("--AutoFilling--,--doGet()--,userId=" + userId);
						System.out.println("--AutoFilling--,--doGet()--,password=" + password);
						System.out.println("--AutoFilling--,--doGet()--,authority=" + authority);
						// 将用户信息设置为请求属性，供JSP页面使用
						request.setAttribute("userId", userId);
						request.setAttribute("password", password);
						request.setAttribute("authority", authority);
					}
					// 转发请求到登录页面
					request.getRequestDispatcher("Login.jsp").forward(request, response);
				}
			} else {
				// 不含有所需cookie，直接转发到登录页面
				request.getRequestDispatcher("Login.jsp").forward(request, response);
			}
		} else {
			// 无cookie
			System.out.println("--AutoFilling--,--doGet()--,无cookie");
			// 直接转发到登录页面
			request.getRequestDispatcher("Login.jsp").forward(request, response);
		}
	}

	/**
	 * 处理POST请求的方法
	 * 直接调用doGet方法处理
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// POST请求直接委托给GET方法处理
		doGet(request, response);
	}
}
