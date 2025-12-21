// 定义包路径
package action;

// 导入所需的Java类库
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet实现类 Initialization
 * 用于系统初始化，处理Cookie中的账户信息并在登录页面显示
 */
public class Initialization extends HttpServlet {
	// 序列化版本号，用于序列化兼容性检查
	private static final long serialVersionUID = 1L;

	/**
	 * 构造函数
	 * @see HttpServlet#HttpServlet()
	 */
	public Initialization() {
		super();
	}

	/**
	 * 处理GET请求的方法
	 * 主要功能是从Cookie中读取已保存的账户信息，并将其传递给登录页面
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取当前会话对象
		HttpSession session = request.getSession();
		// 获取所有Cookie
		Cookie[] cookies = request.getCookies();
		// 标记是否存在account Cookie
		boolean hasAccountCookie = false;
		// 用于存储找到的account Cookie
		Cookie accountCookie = null;

		// 检查cookies是否为空
		if (cookies != null) {
			System.out.println("--Initialization--,cookie not null");
			// 遍历所有Cookie，查找名为"account"的Cookie
			for (Cookie cookie : cookies) {
				if ("account".equals(cookie.getName())) {
					hasAccountCookie = true;
					accountCookie = cookie;
					break;
				}
			}

			// 如果找到了account Cookie
			if (hasAccountCookie) {
				// 创建用户ID列表，用于存储从Cookie中解析出的所有用户ID
				List<String> idList = new ArrayList<>();
				// 获取Cookie的值
				String cookieValue = accountCookie.getValue();
				System.out.println("--Initialization--,cookieValue=" + cookieValue);

				// 字符串处理：解析Cookie中的账户信息
				if (cookieValue.contains("&")) {
					// 如果包含多个账户（用&分隔）
					String[] accounts = cookieValue.split("&");
					// 遍历每个账户信息
					for (int i = 0; i < accounts.length; i++) {
						// 解析每个账户的ID、密码和权限信息（格式：id=password=authority）
						String id = accounts[i].split("=")[0];
						String pw = accounts[i].split("=")[1];
						String authority = accounts[i].split("=")[2];
						// 将用户ID添加到列表中
						idList.add(id);
					}
				} else {
					// 只有一个账户的情况
					String id = cookieValue.split("=")[0];
					String pw = cookieValue.split("=")[1];
					String authority = cookieValue.split("=")[2];
					// 将用户ID添加到列表中
					idList.add(id);
				}

				// 将用户ID列表设置为session属性，供JSP页面使用
				session.setAttribute("idList", idList);
				// 转发请求到登录页面
				request.getRequestDispatcher("Login.jsp").forward(request, response);
			} else {
				// 没有找到account Cookie，直接转发到登录页面
				request.getRequestDispatcher("Login.jsp").forward(request, response);
			}
		} else {
			// cookies为空，直接转发到登录页面
			request.getRequestDispatcher("Login.jsp").forward(request, response);
		}
	}

	/**
	 * 处理POST请求的方法
	 * 当前为空实现
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 当前没有实现具体的POST处理逻辑
	}
}
