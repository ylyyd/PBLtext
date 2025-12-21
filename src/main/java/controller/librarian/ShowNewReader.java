// 定义包路径
package controller.librarian;

// 导入所需的Java类库
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 导入数据访问对象和实体类
import dao.ReaderDAO;
import entity.Reader;

/**
 * Servlet控制器类，用于显示新添加的读者信息
 * Servlet实现类 ShowNewReader
 */
public class ShowNewReader extends HttpServlet {
	// 序列化版本号，用于序列化兼容性检查
	private static final long serialVersionUID = 1L;

	/**
	 * 构造函数
	 * @see HttpServlet#HttpServlet()
	 */
	public ShowNewReader() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 处理GET请求的方法
	 * 主要功能是根据读者ID获取新添加的读者信息并转发到显示页面
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
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
		// 从请求参数中获取读者ID字符串
		String s = request.getParameter("reader_id");
		// 将字符串转换为整数类型的读者ID
		int readerId = Integer.parseInt(s);
		// 创建读者DAO实例
		ReaderDAO readerDAO = new ReaderDAO();
		// 根据读者ID获取读者实体对象
		Reader reader = readerDAO.getReaderById(readerId);
		// 将读者实体设置为请求属性，供JSP页面使用
		request.setAttribute("newReaderEntity", reader);
		// 转发请求到显示新读者信息的JSP页面
		request.getRequestDispatcher("showNewReader.jsp").forward(request, response);
	}

	/**
	 * 处理POST请求的方法
	 * 直接调用doGet方法处理请求
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// POST请求委托给GET方法处理
		doGet(request, response);
	}
}
