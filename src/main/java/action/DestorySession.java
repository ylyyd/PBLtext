// 定义包路径
package action;

// 导入所需的Java类库
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet实现类 DestorySession
 * 用于销毁用户会话(session)，实现用户登出功能
 */
public class DestorySession extends HttpServlet {
	// 序列化版本号，用于序列化兼容性检查
	private static final long serialVersionUID = 1L;

	/**
	 * 构造函数
	 * @see HttpServlet#HttpServlet()
	 */
	public DestorySession() {
		super();
	}

	/**
	 * 处理所有HTTP请求的方法(service)
	 * 主要功能是销毁当前用户的会话并重定向到主页
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取当前用户的会话对象
		HttpSession session = request.getSession();
		// 销毁会话，清除所有存储在会话中的数据
		session.invalidate();
		// 重定向用户到主页(homepage.jsp)
		response.sendRedirect("homepage.jsp");
	}
}
