// 定义包路径
package controller.reader;

// 导入所需的Java类库
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// 导入数据访问对象和实体类
import dao.BookDAO;
import entity.Book;

/**
 * Servlet控制器类，用于处理显示图书馆中书籍详情的业务逻辑
 * Servlet实现类 ShowBookInLib
 */
public class ShowBookInLib extends HttpServlet {

	/**
	 * 处理HTTP请求的服务方法
	 * 主要功能是根据ISBN获取图书馆中特定书籍的详细信息，并支持分页显示
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置响应字符编码为UTF-8
		response.setCharacterEncoding("UTF-8");
		// 设置请求字符编码为UTF-8
		request.setCharacterEncoding("UTF-8");
		// 设置响应内容类型为HTML，字符集为UTF-8
		response.setContentType("text/html; charset=UTF-8");
		// 获取响应输出流
		PrintWriter out = response.getWriter();
		// 创建书籍DAO实例
		BookDAO bookDAO = new BookDAO();
		// 获取当前会话对象
		HttpSession session = request.getSession();
		// 创建书籍集合
		Collection<Book> books = new ArrayList<Book>();

		// 分页功能
		int start = 0;  // 起始记录位置
		int count = 5;  // 每页显示记录数

		try {
			// 尝试从请求参数中获取起始位置
			String startParam = request.getParameter("start");
			if (startParam != null && !startParam.isEmpty()) {
				start = Integer.parseInt(startParam);
			}		} catch (NumberFormatException e) {
			// 处理数字格式异常并打印堆栈跟踪
			e.printStackTrace();
		}

		// 计算下一页和上一页的位置
		int next = start + count;
		int pre = start - count;

		// 从请求参数中获取ISBN
		String isbn = request.getParameter("isbn");
		if (isbn == null) {
			// 如果请求参数中没有ISBN，则从会话中获取
			isbn = (String) session.getAttribute("bookInfoISBN");
		}
		// 根据ISBN获取书籍信息
		Book book = bookDAO.getBookByIsbn(isbn);
		// 将书籍详情信息保存到会话中
		session.setAttribute("BookDetail", book);
		// 获取指定ISBN书籍的总数
		int total = bookDAO.getTotal(isbn);
		//
		// if(total==0) {
		// out.print("<script>alert('抱歉，这种书不可用！');window.location='readerSearchBook.jsp';</script>");
		// return;
		// }

		// 计算最后一页的位置
		int last;
		if (0 == total % count)
			last = total - count;
		else
			last = total - total % count;

		// 确保上一页和下一页的边界值正确
		pre = pre < 0 ? 0 : pre;
		next = next > last ? last : next;

		// 将分页参数保存到会话中
		session.setAttribute("next", next);
		session.setAttribute("pre", pre);
		session.setAttribute("last", last);

		// 移除会话中的旧数据
		session.removeAttribute("bookList");
		session.removeAttribute("bookInfoList");
		// 将ISBN保存到会话中
		session.setAttribute("bookInfoISBN", isbn);

		// 检查ISBN是否为空
		if (isbn == "" || isbn.isEmpty()) {
			// ISBN为空，提示错误并跳转回搜索页面
			out.print(
					"<script>alert('请输入完整的关键词，关键词不能为空！');window.location='readerSearchBook.jsp';</script>");
		}
		// 根据ISBN获取书籍列表用于购物车展示，支持分页
		books = bookDAO.getBookListByIsbnForCart(isbn, start, count);
		if (books.isEmpty()) {
			// 书籍列表为空，提示错误并跳转回搜索页面
			out.print(
					"<script>alert('抱歉，图书馆现在没有这本书！请尝试新的搜索！');window.location='readerSearchBook.jsp';</script>");
			return;
		} else {
			// 将书籍信息列表保存到会话中
			session.setAttribute("bookInfoList", books);
		}
		// 转发到读者查看图书馆书籍详情页面
		request.getRequestDispatcher("readerShowBookInLib.jsp").forward(request, response);
	}
}
