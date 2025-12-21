// 定义包路径
package controller.librarian;

// 导入所需的Java类库
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 导入数据访问对象和实体类
import dao.BorrowCartDAO;
import entity.Cart;

/**
 * Servlet控制器类，用于处理显示借阅购物车的业务逻辑
 * 该servlet用于接收从showCart.jsp传来的get请求，从数据库中获取borrow_cart，并分页展示给librarian
 */
public class ShowCart extends HttpServlet {
	// 序列化版本号，用于序列化兼容性检查
	private static final long serialVersionUID = 1L;

	// 构造函数
	public ShowCart() {
		super();
	}

	/**
	 * 处理GET请求的方法
	 * 主要功能是从数据库获取借阅购物车信息并分页展示给图书管理员
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

		// 初始化分页参数
		int start = 0;    // 起始行
		int count = 5;    // 每页显示行数

		try {
			// 尝试从请求参数中获取起始位置
			start = Integer.parseInt(request.getParameter("start"));
		} catch (NumberFormatException e) {
			// 当浏览器没有传参数start时，使用默认值0
		}

		// 计算下一页和上一页的位置
		int next = start + count;  // 下一页
		int pre = start - count;   // 前一页

		// 创建借阅购物车DAO实例
		BorrowCartDAO bDAO = new BorrowCartDAO();
		// 获取borrow_cart中的总数
		int total = bDAO.getTotal();

		// 计算最后一页的起始行
		int last;
		if (0 == total % count) {
			// 每一页都能展示最大行数
			last = total - count;
		} else {
			last = total - total % count;
		}

		// 确保上一页和下一页的边界值正确
		pre = pre < 0 ? 0 : pre;
		next = next > last ? last : next;

		// 设置request属性供JSP页面使用
		request.setAttribute("next", next);
		request.setAttribute("pre", pre);
		request.setAttribute("last", last);
		request.setAttribute("current", start);

		// 获取数据库中所有的borrow_cart，按时间排序并分页展示
		List<Cart> list = bDAO.getAllBorrowCartOrderByTime(start, count);
		// 设置request属性
		request.setAttribute("cartList", list);
		// 转发到showCart.jsp页面
		request.getRequestDispatcher("showCart.jsp").forward(request, response);
	}

	/**
	 * 处理POST请求的方法
	 * 当前为空实现
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 空实现
	}
}
