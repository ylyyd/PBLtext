// 定义包路径
package controller.reader;

/**
 * Servlet控制器类，用于处理读者提交借阅购物车的业务逻辑
 * 用来展示readerborrowcart
 */
import dao.BookDAO;
import dao.BorrowCartDAO;
import dao.BorrowItemDAO;
import entity.Book;
import entity.BorrowItem;
import entity.Cart;
import entity.Reader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ReaderSubmitBorrowCart extends HttpServlet {

	/**
	 * 处理HTTP请求的服务方法
	 * 主要功能是处理读者提交借阅购物车中的书籍请求
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取当前会话对象
		HttpSession session = request.getSession();
		// 设置请求和响应的字符编码为UTF-8，支持中文字符
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 设置响应内容类型为HTML，字符集为UTF-8
		response.setContentType("text/html;charset=UTF-8");

		// 从请求参数中获取书籍ID
		String bookids = request.getParameter("bookid");
		int bookid = Integer.MAX_VALUE;
		if (bookids != null)
			bookid = Integer.valueOf(bookids);
		// 从会话中获取读者实体对象
		Reader reader = (Reader) session.getAttribute("ReaderEntity");
		// 初始化用户ID
		int userid = Integer.MAX_VALUE;
		if (reader != null) {
			userid = reader.getId();
		}
		// 创建借阅购物车DAO实例
		BorrowCartDAO borrowCartDAO = new BorrowCartDAO();
		// 更新借阅购物车状态
		borrowCartDAO.updateBorrowCart(bookid, userid);
		// 创建书籍DAO实例
		BookDAO bookDAO = new BookDAO();
		// 初始化书籍实体对象
		Book book = new Book();
		// 根据书籍ID搜索书籍信息
		book = bookDAO.searchByID(bookid);
		// 获取书籍状态
		String state = book.getState();
		// 检查书籍是否存在且状态是否为可借阅
		if (book != null && !state.equals("inlib")) {
			// 书籍不可借阅，删除借阅购物车记录并重定向到失败页面
			borrowCartDAO.deleteBorrowCart(bookid, reader.getId());
			response.sendRedirect("readerFailedSubmitBorrowCart.jsp");
		} else {
			// 创建借阅项DAO实例
			BorrowItemDAO borrowItemDAO = new BorrowItemDAO();
			// 初始化计数器
			int num = 0;
			// 根据读者ID获取借阅购物车列表
			List<Cart> carts = borrowCartDAO.getBorrowCartByReaderId(reader.getId());
			// 统计借阅购物车中的书籍数量
			for (Cart cart : carts) {
				num++;
			}
			// 检查借阅数量是否超过限制
			if (num >= 10) {
				// 借阅数量超限，提示错误并重定向到失败页面
				PrintWriter out = response.getWriter();
				out.print(
						"<script language='javascript'>alert('您预约的书籍太多了！');window.location.href='readerFailedSubmitBorrowCart.jsp';</script>");
			} else {
				// 更新书籍状态为预约状态
				bookDAO.updateBookStateToReserve(bookid);
				// List<Cart>carts =
				// borrowCartDAO.getBorrowCartByReaderId(userid);
				// for(Cart cart : carts){
				// if(cart.getBookId()==bookid && cart.getReaderId()==userid &&
				// )
				// }
				// 写时还没有更改状态的方法。无法进行判断
				// 已有更改状态的方法，进行更改
				// 操作成功，重定向到成功页面
				response.sendRedirect("readerSuccessSubmitBorrowCart.jsp");
			}
		}
	}
}
