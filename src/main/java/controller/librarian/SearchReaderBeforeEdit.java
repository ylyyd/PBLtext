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

// 导入数据访问对象和实体类
import dao.ReaderDAO;
import entity.Reader;

/**
 * Servlet控制器类，用于在编辑读者信息前搜索读者
 * Servlet实现类 SearchReaderBeforeEdit
 */
public class SearchReaderBeforeEdit extends HttpServlet {
	// 序列化版本号，用于序列化兼容性检查
	private static final long serialVersionUID = 1L;

	// 构造函数
	public SearchReaderBeforeEdit() {
		super();
	}

	/**
	 * 处理GET请求的方法
	 * 主要功能是根据读者ID获取读者信息并转发到编辑前查看页面
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

		// 从请求参数中获取读者ID并转换为整数
		int readerId = Integer.parseInt(request.getParameter("reader_id"));
		// 创建读者DAO实例
		ReaderDAO readerDAO = new ReaderDAO();
		// 根据ID获取读者实体
		Reader reader = readerDAO.getReaderById(readerId);
		// 设置请求属性供JSP页面使用
		request.setAttribute("readerEntity", reader);
		// 创建请求转发器并转发到搜索读者编辑前页面
		RequestDispatcher dispatcher = request.getRequestDispatcher("searchReaderBeforeEdit.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * 处理POST请求的方法
	 * 主要功能是接收读者账号参数，获取读者信息并转发到编辑前查看页面
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置请求和响应的字符编码为UTF-8，支持中文字符
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// 获取响应输出流
		PrintWriter out = response.getWriter();
		try {
			// 从请求参数中获取读者账号并转换为整数
			int readerId = Integer.parseInt(request.getParameter("account"));
			// 创建读者DAO实例
			ReaderDAO readerDAO = new ReaderDAO();
			// 根据ID获取读者实体
			Reader reader = readerDAO.getReaderById(readerId);
			// 设置请求属性供JSP页面使用
			request.setAttribute("readerEntity", reader);
			// 创建请求转发器并转发到搜索读者编辑前页面
			RequestDispatcher dispatcher = request.getRequestDispatcher("searchReaderBeforeEdit.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			// 捕获异常，处理传参错误情况
			System.out.println("--SearchReaderBeforeEdit--doPost(),传参错误：很有可能是reader账号没有写对");
			// 弹出错误提示并跳转回搜索页面
			out.print("<script language='javascript'>" + "alert('读者ID可能无效，请重新输入！');"
					+ "window.location.href='searchReaderBeforeEdit.jsp';" + "</script>");
		}
	}
}
