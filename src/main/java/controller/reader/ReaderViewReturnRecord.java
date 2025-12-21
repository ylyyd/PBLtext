// 定义包路径
package controller.reader;

// 导入所需的Java类库
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// 导入数据访问对象和实体类
import dao.BorrowItemDAO;
import dao.LibrarianDAO;
import entity.BorrowItem;
import entity.Librarian;
import entity.Reader;

/**
 * Servlet控制器类，用于处理读者查看还书记录的业务逻辑
 * Servlet实现类 AdminViewLibrarian
 */
public class ReaderViewReturnRecord extends HttpServlet {
	// 序列化版本号，用于序列化兼容性检查
	private static final long serialVersionUID = 1L;

	/**
	 * 构造函数
	 * @see HttpServlet#HttpServlet()
	 */
	public ReaderViewReturnRecord() {
		super();
		// TODO Auto-generated constructor stub
	}

	// 借阅项DAO实例，用于数据访问操作
	private BorrowItemDAO borrowItemDAO = new BorrowItemDAO();

	/**
	 * 处理GET请求的方法
	 * 主要功能是获取读者的还书记录并支持分页显示
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 设置响应内容类型为HTML，字符集为UTF-8
		response.setContentType("text/html; charset=UTF-8");
		// 设置响应字符编码为UTF-8
		response.setCharacterEncoding("UTF-8");

		// 初始化分页参数
		int start = 0;  // 起始记录位置
		int count = 5;  // 每页显示记录数
		int num = 0;    // 计数器

		// 获取当前会话对象
		HttpSession session = request.getSession();
		try {
			// 尝试从请求参数中获取起始位置
			start = Integer.parseInt(request.getParameter("start"));
			num = num + start;
			// 将计数器值保存到会话中
			session.setAttribute("nums", num);
		} catch (NumberFormatException e) {
			// 当浏览器没有传参数start时，使用默认值
		}

		// 计算下一页和上一页的起始位置
		int next = start + count;
		int pre = start - count;

		// 检查会话是否存在
		if (session != null) {
			// 从会话中获取读者实体对象
			Reader reader = (Reader) session.getAttribute("ReaderEntity");
			// 检查读者实体是否存在
			if (reader != null) {
				// 获取读者ID
				int readerid = reader.getId();

				// 获取读者的所有借阅历史记录
				List<BorrowItem> borrowitems = borrowItemDAO.getBorrowItemInHistory(readerid);
				// 计算总记录数
				int total = 0;
				for (BorrowItem borrowItem : borrowitems) {
					total++;
				}

				// 计算最后一页的起始位置
				int last;
				if (0 == total % count)
					last = total - count;
				else
					last = total - total % count;

				// 确保上一页和下一页的边界值正确
				pre = pre < 0 ? 0 : pre;
				next = next > last ? last : next;

				// 设置分页参数为请求属性
				request.setAttribute("next", next);
				request.setAttribute("pre", pre);
				request.setAttribute("last", last);

				// 获取当前页的借阅历史记录列表
				List<BorrowItem> borrowItems = borrowItemDAO.getBorrowItemInHistory(start, count, readerid);
				// 设置借阅历史记录列表为请求属性
				request.setAttribute("borrowitems", borrowItems);
				// 转发到读者还书历史页面
				request.getRequestDispatcher("readerReturnHistory.jsp").forward(request, response);
			} else {
				// 读者实体不存在，转发到主页
				request.getRequestDispatcher("homepage.jsp").forward(request, response);
			}
		} else {
			// 会话不存在，转发到主页
			request.getRequestDispatcher("homepage.jsp").forward(request, response);
		}
	}
}
