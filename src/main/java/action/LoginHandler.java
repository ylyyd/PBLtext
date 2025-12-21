// 定义包路径
package action;

// 导入所需的Java类库
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 导入数据访问对象(DAO)类
import dao.AdministratorDAO;
import dao.LibrarianDAO;
import dao.ReaderDAO;

/**
 * 该类用于处理登录请求，根据权限（Reader/Librarian/Administrator）分发到不同的servlet
 */
public class LoginHandler extends HttpServlet {
	// 序列化版本号，用于序列化兼容性检查
	private static final long serialVersionUID = 1L;

	// 构造函数
	public LoginHandler() {
		super();
	}

	// 处理GET请求的方法
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 目前为空实现，没有具体逻辑
	}

	// 处理POST请求的方法，主要的登录处理逻辑在这里
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置请求和响应的字符编码为UTF-8，支持中文字符
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// 检查必要参数是否为空或空字符串
		if (request.getParameter("authority") == null || request.getParameter("userID") == null
				|| request.getParameter("password") == null || request.getParameter("authority").equals("")
				|| request.getParameter("userID").equals("") || request.getParameter("password").equals("")) {
			// 如果任一参数为空，则重定向回登录页面
			request.getRequestDispatcher("Login.jsp").forward(request, response);
		} else {
			// 获取参数
			String authority = (String) request.getParameter("authority"); // 用户权限类型
			String userId = request.getParameter("userID"); // 用户ID
			int id = Integer.parseInt(userId); // 将用户ID转换为整数
			String password = request.getParameter("password"); // 用户密码
			String isRemember = request.getParameter("isRemember"); // 是否记住密码选项
			System.out.println("isRemember=" + isRemember);

			// 初始化登录状态标志位
			boolean isCorrect = true;

			// 如果用户选择了记住密码
			if (isRemember != null) {
				//判断用户ID、密码、权限是否正确

				// 根据不同权限类型验证用户身份
				if (authority.equals("reader")) {
					// 创建读者DAO实例
					ReaderDAO readerDAO = new ReaderDAO();
					// 通过ID获取读者信息并验证密码
					if(readerDAO.getReaderById(id).getPassword().equals(password)) {
						// 密码匹配
						System.out.println("reader匹配");
						isCorrect = true;
					}
				} else if (authority.equals("librarian")) {
					// 创建图书管理员DAO实例
					LibrarianDAO librarianDAO = new LibrarianDAO();
					// 通过ID获取图书管理员信息并验证密码
					if(librarianDAO.getLibrarianById(id).getPassword().equals(password)) {
						// 密码匹配
						System.out.println("librarian匹配");
						isCorrect = true;
					}
				} else if (authority.equals("administrator")) {
					// 创建管理员DAO实例
					AdministratorDAO administratorDAO = new AdministratorDAO();
					// 通过ID获取管理员信息并验证密码
					if(administratorDAO.getAdministratorById(id).getPassword().equals(password)) {
						// 密码匹配
						System.out.println("administrator匹配");
						isCorrect = true;
					}
				}

				// 只有当密码正确时才保存密码
				if (isCorrect) {
					// 调用记住密码方法，将用户信息保存到Cookie中
					rememberPassword(userId, password, authority, request, response);
				}
			} else {
				// 如果未选择记住密码，则调用忘记密码方法清除相关Cookie信息
				forgetPassword(userId, request, response);
			}

			// 根据权限类型将请求转发到相应的处理Servlet
			if (authority.equals("reader")) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("ReaderLogin");
				dispatcher.forward(request, response);
			} else if (authority.equals("librarian")) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("LibrarianLogin");
				dispatcher.forward(request, response);
			} else if (authority.equals("administrator")) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("AdministratorLogin");
				dispatcher.forward(request, response);
			}
		}
	}

	/**
	 * 记住密码功能，将用户信息保存到Cookie中
	 * @param userId 用户ID
	 * @param password 密码
	 * @param authority 权限类型
	 * @param request HTTP请求对象
	 * @param response HTTP响应对象
	 */
	private void rememberPassword(String userId, String password, String authority, HttpServletRequest request,
								  HttpServletResponse response) throws ServletException, IOException {
		// 获取所有Cookie
		Cookie[] cookies = request.getCookies();
		boolean hasAccountCookie = false; // 标记是否存在account Cookie
		Cookie accountCookie = null; // 用于存储找到的account Cookie

		// 如果cookies不为空
		if (cookies != null) {
			// 遍历cookies，查找名为"account"的Cookie
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("account")) {
					accountCookie = cookie;
					hasAccountCookie = true;
					break;
				}
			}

			// 如果找到了account Cookie（非首次保存密码）
			if (hasAccountCookie == true) {
				String cookieValue = accountCookie.getValue(); // 获取Cookie值
				System.out.println("--LoginHandler--,cookieValue=" + cookieValue);
				boolean hasStore = false; // 标记是否已保存该账号

				// 查找是否已经保存过该账号
				if (cookieValue.contains("&")) {
					// 如果包含多个账号（用&分隔）
					String[] accounts = cookieValue.split("&");

					for (int i = 0; i < accounts.length; i++) {
						System.out.println("accounts[" + i + "]=" + accounts[i]);
						// 检查当前用户ID是否已存在
						if (accounts[i].split("=")[0].equals(userId)) {
							hasStore = true;
							break;
						}
					}
				} else {
					// 只有一个账号的情况
					if (cookieValue.split("=")[0].equals(userId)) {
						hasStore = true;
					}
				}

				// 如果该账号尚未保存
				if (hasStore == false) {
					System.out.println("userId=" + userId);
					System.out.println("password=" + password);
					System.out.println("authority=" + authority);
					// 将新账号信息追加到现有Cookie值后面
					cookieValue += "&" + userId + "=" + password + "=" + authority;
					// 创建新的Cookie并设置有效期为1天
					Cookie cookie = new Cookie("account", cookieValue);
					cookie.setMaxAge(60 * 60 * 24);
					response.addCookie(cookie);
					System.out.println("--LoginHandler--,添加新的账户");
				} else {
					System.out.println("--LoginHandler--,该账户已被保存");
				}
			} else {
				// 首次保存密码，直接创建新的Cookie
				Cookie cookie = new Cookie("account", userId + "=" + password + "=" + authority);
				cookie.setMaxAge(60 * 60 * 24); // 设置有效期为1天
				response.addCookie(cookie);
			}
		} else {
			return;
		}
	}

	/**
	 * 忘记密码功能，从Cookie中移除指定用户的登录信息
	 * @param userId 用户ID
	 * @param request HTTP请求对象
	 * @param response HTTP响应对象
	 */
	private void forgetPassword(String userId, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取所有Cookie
		Cookie[] cookies = request.getCookies();
		boolean hasAccountCookie = false; // 标记是否存在account Cookie
		Cookie accountCookie = null; // 用于存储找到的account Cookie

		// 如果cookies不为空
		if (cookies != null) {
			// 遍历cookies查找名为"account"的Cookie
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("account")) {
					accountCookie = cookie;
					hasAccountCookie = true;
					break;
				}
			}

			// 如果找到了account Cookie
			if (hasAccountCookie == true) {
				String cookieValue = accountCookie.getValue(); // 获取Cookie值

				// 如果Cookie值包含多个账号
				if (cookieValue.contains("&")) {
					System.out.println("--LoginHandler--,--doGet()--,含有多个账号");
					String[] accounts = cookieValue.split("&"); // 分割各个账号
					int k = -1; // 记录要删除账号的索引位置

					// 查找要删除的账号位置
					for (int i = 0; i < accounts.length; i++) {
						if (accounts[i].split("=")[0].equals(userId)) {
							k = i;
							break;
						}
					}

					// 构建新的Cookie值，排除要删除的账号
					String newCookieValue = "";
					if (k != 0) {
						newCookieValue = accounts[0];
						for (int i = 1; i < accounts.length; i++) {
							if (k == i) {
								continue; // 跳过要删除的账号
							} else {
								newCookieValue += "&" + accounts[i];
							}
						}
					} else {
						newCookieValue = accounts[1];
						for (int i = 2; i < accounts.length; i++) {
							if (k == i) {
								continue; // 跳过要删除的账号
							} else {
								newCookieValue += "&" + accounts[i];
							}
						}
					}

					System.out.println("newCookieValue=" + newCookieValue);
					// 创建更新后的Cookie
					Cookie cookie = new Cookie("account", newCookieValue);
					response.addCookie(cookie);
				} else {
					// 仅有一个账号的情况
					System.out.println("--LoginHandler--,--forgetPassword()--,仅有一个账号");
					if (cookieValue.split("=")[0].equals(userId)) {
						// 删除该账号信息，设置空值并使其立即失效
						Cookie cookie = new Cookie("account", "");
						cookie.setMaxAge(1); // 设置为即将过期
						response.addCookie(cookie);
					}
				}
			} else {
				// 没有记录需要删除
				System.out.println("--LoginHandler--,--forgetPassword()--,没有记录");
				return;
			}
		} else {
			return;
		}
	}
}
