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

// 导入数据访问对象、实体类和工具类
import dao.ReaderDAO;
import entity.Reader;
import util.SecurityUtil;

/**
 * Servlet控制器类，用于处理图书管理员修改读者信息的业务逻辑
 * 该Servlet的doGet方法将请求转发到Reader信息的修改界面
 * 该Servlet的doPost方法处理Reader修改后的信息，并加入数据库
 */
public class LibrarianModifyReader extends HttpServlet {
	// 序列化版本号，用于序列化兼容性检查
	private static final long serialVersionUID = 1L;

	// 构造函数
	public LibrarianModifyReader() {
		super();
	}

	/**
	 * 处理GET请求的方法
	 * 主要功能是获取指定读者的信息并转发到修改界面
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置请求和响应的字符编码为UTF-8，支持中文字符
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 设置响应内容类型为HTML，字符集为UTF-8
		response.setContentType("text/html;charset=UTF-8");
		// 获取响应输出流
		PrintWriter out = response.getWriter();
		System.out.println("--LibrarianModifyReader--doGet()");
		// 从请求参数中获取读者ID并转换为整数
		int readerId = Integer.parseInt(request.getParameter("reader_id"));
		// 创建读者DAO实例
		ReaderDAO readerDAO = new ReaderDAO();
		// 根据ID获取读者实体
		Reader reader = readerDAO.getReaderById(readerId);
		// 设置请求属性供JSP页面使用
		request.setAttribute("pwToString", SecurityUtil.md5(reader.getPassword())); // 显示字符串密码
		request.setAttribute("readerEntity", reader); // 新的request，request属性与之前的request对象相同不会影响
		// 创建请求转发器并转发到图书管理员修改读者页面
		RequestDispatcher dispatcher = request.getRequestDispatcher("librarianModifyReader.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * 处理POST请求的方法
	 * 主要功能是接收并处理图书管理员提交的读者信息修改请求
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("--LibrarianModifyReader--doPost()");
		// 设置请求和响应的字符编码为UTF-8，支持中文字符
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 设置响应内容类型为HTML，字符集为UTF-8
		response.setContentType("text/html;charset=UTF-8");
		// 获取响应输出流
		PrintWriter out = response.getWriter();
		// 获取参数
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String state = request.getParameter("state");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		// 创建读者DAO实例
		ReaderDAO readerDAO = new ReaderDAO();
		// 调用DAO方法更新读者数据
		if (readerDAO.updateData(id, name, password, email, state)) {
			// 更新成功，重定向到搜索读者编辑前页面
			response.sendRedirect("SearchReaderBeforeEdit?reader_id=" + id);
		} else {
			// 更新失败，弹出错误提示并跳转回修改页面
			out.print("<script language='javascript'>" + "alert('修改读者失败！');"
					+ "window.location.href='librarianModifyReader.jsp';" + "</script>");
		}
	}
}
