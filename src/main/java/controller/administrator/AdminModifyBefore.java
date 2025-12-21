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
 * Servlet控制器类，用于处理管理员修改前的准备工作
 * 先输入Librarian的ID，先判断是否存在该Librarian，若存在则跳转到AdminModifyLibrarian.jsp页面
 */
public class AdminModifyBefore extends HttpServlet {
	// 序列化版本号，用于序列化兼容性检查
	private static final long serialVersionUID = 1L;

	/**
	 * 构造函数
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminModifyBefore() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 处理POST请求的方法
	 * 主要功能是验证图书管理员是否存在，如果存在则跳转到修改页面
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
		// 通过ID获取图书管理员信息
		Librarian librarian = new LibrarianDAO().getLibrarianById(id);

		// 判断图书管理员是否存在
		if (librarian != null) {
			// 图书管理员存在，获取姓名并设置为请求属性
			String name = librarian.getName();
			request.setAttribute("id", id);
			request.setAttribute("name", name);
			// 转发到管理员修改页面
			request.getRequestDispatcher("adminModifyLibrarian.jsp").forward(request, response);
		} else {
			// 图书管理员不存在，设置错误消息并转发到操作结果页面
			String msg = "未找到该管理员，请重试！";
			request.setAttribute("message", msg);
			request.getRequestDispatcher("adminOperateResult.jsp").forward(request, response);
		}
	}
}
