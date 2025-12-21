// 定义包路径
package controller.librarian;

// 导入所需的Java类库
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 导入数据访问对象和工具类
import dao.ReaderDAO;
import util.SecurityUtil;

/**
 * Servlet控制器类，用于处理图书管理员添加读者的业务逻辑
 * librarian添加reader
 */
public class LibrarianAddReader extends HttpServlet {
	// 序列化版本号，用于序列化兼容性检查
	private static final long serialVersionUID = 1L;

	// 构造函数
	public LibrarianAddReader() {
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
	 * 主要功能是接收图书管理员添加新读者的表单数据，并将读者信息保存到数据库
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

		// 获取参数
		String name = request.getParameter("readerName");        // 用户名
		String password = request.getParameter("password");      // 密码
		String state = request.getParameter("state");            // 状态
		password = SecurityUtil.md5(password);                  // 将reader的密码转换成密文

		// 数据库操作
		ReaderDAO readerDAO = new ReaderDAO();
		// 调用DAO方法添加读者，返回新读者的ID
		int readerId = readerDAO.addReaderByName_Passowrd_State(name, password, state);

		// 根据添加结果进行相应处理
		if (readerId == -1) {
			// 添加失败
			System.out.println("添加Reader失败");
			out.print("<script language='javascript'>" + "alert('抱歉！添加读者失败！');"
					+ "window.location.href='librarianAddReader.jsp';" + "</script>");
		} else {
			// 添加成功，显示新读者ID
			System.out.println("添加Reader成功，新的reader id=" + readerId);
			out.print("<script language='javascript'>" + "alert('成功添加读者！');"
					+ "window.location.href='ShowNewReader?reader_id=" + readerId + "';" + "</script>");
		}
	}
}
