// 定义包路径
package controller.librarian;

// 导入所需的Java类库
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 导入数据访问对象和实体类
import dao.BorrowCartDAO;
import dao.BorrowItemDAO;
import entity.BorrowItem;
import entity.Cart;

/**
 * Servlet控制器类，用于展示读者信息供图书管理员查看
 * 该类用于展示reader信息，供librarian查看 接收从librarianSearchReader.jsp传来的参数，通过doGet方法
 */
public class ShowReaderInfo extends HttpServlet {
	// 序列化版本号，用于序列化兼容性检查
	private static final long serialVersionUID = 1L;

	// 构造函数
	public ShowReaderInfo() {
		super();
	}

	/**
	 * 处理GET请求的方法
	 * 主要功能是根据不同的参数展示读者的相关信息（购物车、借阅历史、当前借阅等）
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
		// 从请求参数中获取操作参数
		String param = request.getParameter("param");

		// 根据不同的参数执行相应的操作
		// 查看购物车
		if (param.equals("cart")) {
			// 创建借阅购物车DAO实例
			BorrowCartDAO borrowCartDAO = new BorrowCartDAO();
			// 根据读者ID获取借阅购物车列表
			List<Cart> list = borrowCartDAO.getBorrowCartByReaderId(readerId);
			// 检查列表是否为空
			if (list != null && !list.isEmpty()) {
				// 设置请求属性供JSP页面使用
				request.setAttribute("borrowCart", list);
			}
			// 创建请求转发器并转发到显示读者信息页面
			RequestDispatcher dispatcher = request.getRequestDispatcher("showReaderInfo.jsp");
			dispatcher.forward(request, response);

		} else if (param.equals("history")) {
			// 查询借阅历史
			// 创建借阅项DAO实例
			BorrowItemDAO borrowItemDAO = new BorrowItemDAO();
			// 获取读者的借阅历史列表
			List<BorrowItem> list = borrowItemDAO.getBorrowItemInHistory(readerId);
			// 检查列表是否为空
			if (list != null && !list.isEmpty()) {
				// 设置请求属性供JSP页面使用
				request.setAttribute("historyList", list);
			}
			// 创建请求转发器并转发到显示读者信息页面
			RequestDispatcher dispatcher = request.getRequestDispatcher("showReaderInfo.jsp");
			dispatcher.forward(request, response);

		} else if (param.equals("current")) {
			// 查看当前借阅
			// 创建借阅项DAO实例
			BorrowItemDAO borrowItemDAO = new BorrowItemDAO();
			// 获取读者当前借阅列表
			List<BorrowItem> list = borrowItemDAO.getBorrowItemInCurrent(readerId);
			// 检查列表是否为空
			if (list != null && !list.isEmpty()) {
				// 设置请求属性供JSP页面使用
				request.setAttribute("currentList", list);
			}
			// 创建请求转发器并转发到显示读者信息页面
			RequestDispatcher dispatcher = request.getRequestDispatcher("showReaderInfo.jsp");
			dispatcher.forward(request, response);

		} else {
			// 参数不匹配，重定向到图书管理员搜索读者页面
			response.sendRedirect("librarianSearchReader.jsp");
		}
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
