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
import dao.ReaderDAO;
import entity.Reader;

/**
 * Servlet控制器类，用于处理图书管理员搜索读者信息的业务逻辑
 * 该类用于librarian查询reader的信息 接收从librarianSearchReader.jsp传来的参数，通过doPost方法
 */
public class LibrarianSearchReader extends HttpServlet {
	// 序列化版本号，用于序列化兼容性检查
	private static final long serialVersionUID = 1L;
	// 读者DAO实例，用于数据访问操作
	private ReaderDAO readerDAO;
	// 起始行，用于分页功能
	private int start = 0;
	// 每页显示行数，用于分页功能
	private int count = 5;
	// 查询条件标识
	private int condition = 0;

	// 构造函数
	public LibrarianSearchReader() {
		super();
		// 初始化读者DAO实例
		readerDAO = new ReaderDAO();
	}

	/**
	 * 处理GET请求的方法
	 * 主要功能是处理读者信息的分页显示
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

		try {
			// 尝试从请求参数中获取起始位置
			start = Integer.parseInt(request.getParameter("start"));
		} catch (NumberFormatException e) {
			// 当浏览器没有传参数start时，使用默认值0
		}

		// 计算下一页和上一页的位置
		int next = start + count;  // 下一页
		int pre = start - count;   // 前一页
		// 获取读者总数
		int total = readerDAO.getTotal();

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
		System.out.println("--LibrarianSearchReader--，分页展示所有读者--,Condition" + 6);

		// 获取查询条件参数
		int condition = Integer.parseInt(request.getParameter("condition"));

		// 根据条件执行相应操作
		if (condition == 3) {
			// 获取数据库中所有的reader，分页展示
			List<Reader> list = readerDAO.getAllReaders(start, count);
			request.setAttribute("readerList", list);
			request.setAttribute("condition", 3);
			RequestDispatcher dispatcher = request.getRequestDispatcher("librarianSearchReader.jsp");
			dispatcher.forward(request, response);
		} else if (condition == 1) {
			// 条件1的处理逻辑（代码未实现）
		} else if (condition == 3) {
			// 条件3的处理逻辑（代码重复，实际不会执行到这里）
		}
	}

	/**
	 * 处理POST请求的方法
	 * 主要功能是根据不同的查询条件搜索读者信息
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置请求字符编码为UTF-8，支持中文字符
		request.setCharacterEncoding("utf-8");
		// 获取参数
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String state = request.getParameter("state");
		System.out.println("id=" + id + ",name=" + name + ",state=" + state);

		// 处理参数空值情况
		if (state.equals("unknown")) {
			state = null;
		}
		if (name.equals("")) {
			name = null;
		}
		if (id.equals("")) {
			id = null;
		}

		// 3个值，每个值都有可能为空，因此共有8种条件组合
		if (id != null) {
			// id 不为空的情况下
			Reader reader = readerDAO.getReaderById(Integer.parseInt(id));
			if (reader != null) {
				// 下面4种组合为正确查询方式
				if (name != null && reader.getName().equals(name) && state == null
						|| state != null && reader.getState().equals(state) && name == null
						|| name == null && state == null || name != null && reader.getName().equals(name)
						&& state != null && reader.getState().equals(state)) {
					request.setAttribute("readerEntity", reader);
					System.out.println("--LibrarianSearchReader--,Condition" + 1);
					RequestDispatcher dispatcher = request.getRequestDispatcher("librarianSearchReader.jsp");
					dispatcher.forward(request, response);
				} else {
					System.out.println("--LibrarianSearchReader--,Condition" + 2);
					PrintWriter out = response.getWriter();
					out.print("<script language='javascript'>" + "alert('没有这样的读者！');"
							+ "window.location.href='librarianSearchReader.jsp';" + "</script>");
				}
			} else {
				PrintWriter out = response.getWriter();
				out.print("<script language='javascript'>" + "alert('没有这样的读者！');"
						+ "window.location.href='librarianSearchReader.jsp';" + "</script>");
			}
		} else {
			// id为空的情况
			if (name == null && state != null) {
				System.out.println("--LibrarianSearchReader--,Condition" + 3);
				// 根据状态查询读者
				List<Reader> list = readerDAO.getReaderByState(state);
				request.setAttribute("readerList", list);
				RequestDispatcher dispatcher = request.getRequestDispatcher("librarianSearchReader.jsp");
				dispatcher.forward(request, response);
			} else if (name != null && state == null) {
				System.out.println("--LibrarianSearchReader--,Condition" + 4);
				// 获取数据库中所有name相似的reader，分页展示
				List<Reader> list = readerDAO.getReaderByName(name, 0, count);
				request.setAttribute("readerList", list);
				request.setAttribute("condition", 1);
				RequestDispatcher dispatcher = request.getRequestDispatcher("librarianSearchReader.jsp");
				dispatcher.forward(request, response);
			} else if (name != null && state != null) {
				System.out.println("--LibrarianSearchReader--,Condition" + 5);
				// 根据姓名和状态查询读者
				List<Reader> list = readerDAO.getReaderByName_State(name, state);
				request.setAttribute("readerList", list);
				request.setAttribute("condition", 2);
				RequestDispatcher dispatcher = request.getRequestDispatcher("librarianSearchReader.jsp");
				dispatcher.forward(request, response);
			} else if (name == null && state == null) {
				System.out.println("--LibrarianSearchReader--,Condition" + 6);
				// 获取数据库中所有的reader，分页展示
				List<Reader> list = readerDAO.getAllReaders();
				request.setAttribute("condition", 3);
				request.setAttribute("readerList", list);
				RequestDispatcher dispatcher = request.getRequestDispatcher("librarianSearchReader.jsp");
				dispatcher.forward(request, response);
			}
		}
	}
}
