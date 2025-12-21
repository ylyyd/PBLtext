// 定义包路径
package controller.librarian;

// 导入所需的Java类库
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 导入数据访问对象
import dao.ReaderDAO;

/**
 * Servlet控制器类，用于处理图书管理员删除读者的业务逻辑
 * 该类用于librarian删除reader 接收从librarianDeleteReader.jsp传来的参数
 */
public class LibrarianDeleteReader extends HttpServlet {
	// 序列化版本号，用于序列化兼容性检查
	private static final long serialVersionUID = 1L;

	// 构造函数
	public LibrarianDeleteReader() {
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
	 * 主要功能是接收图书管理员删除读者的请求，验证参数并执行删除操作
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

		try {
			// 从请求参数中获取读者ID并转换为整数
			int readerId = Integer.parseInt(request.getParameter("id"));
			// 从请求参数中获取读者姓名
			String name = request.getParameter("name");

			// 检查读者姓名是否为空
			if (name == null) {
				System.out.println("--LibrarianDeleteReader--doPost(),name为空");
				out.print("<script language='javascript'>" + "alert('失败！原因：读者姓名为空！');"
						+ "window.location.href='librarianDeleteReader.jsp';" + "</script>");
			}

			// 创建读者DAO实例
			ReaderDAO readerDAO = new ReaderDAO();
			// 调用DAO方法根据ID和姓名删除读者
			if (readerDAO.deleteReaderById(readerId, name)) {
				// 删除成功
				System.out.println("--LibrarianDeleteReader--doPost(),删除成功");
				out.print("<script language='javascript'>" + "alert('成功删除读者！');"
						+ "window.location.href='librarianDeleteReader.jsp';" + "</script>");
			} else {
				// 删除失败
				System.out.println("--LibrarianDeleteReader--doPost(),删除失败");
				out.print("<script language='javascript'>" + "alert('删除读者失败！');"
						+ "window.location.href='librarianDeleteReader.jsp';" + "</script>");
			}
		} catch (Exception e) {
			// 捕获异常，处理输入不合法的情况
			System.out.println("--LibrarianDeleteReader--doPost(),输入不合法");
			out.print("<script language='javascript'>" + "alert('输入无效！');"
					+ "window.location.href='librarianDeleteReader.jsp';" + "</script>");
		}
	}
}
