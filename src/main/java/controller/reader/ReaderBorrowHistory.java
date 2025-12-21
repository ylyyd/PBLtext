// 定义包路径
package controller.reader;

/**
 * Servlet控制器类，用于处理读者借阅历史的展示功能
 * 用来展示readerborrowhistory
 */
import dao.BorrowItemDAO;
import entity.BorrowItem;
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

public class ReaderBorrowHistory extends HttpServlet {
	// 构造函数
	public ReaderBorrowHistory() {
		super();
	}

	/**
	 * 处理GET请求的方法
	 * 直接委托给 doPost 方法处理
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * 处理POST请求的方法
	 * 主要功能是获取当前读者的借阅历史信息并转发到展示页面
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		// 获取当前会话对象
		HttpSession session = request.getSession();
		// 设置请求和响应的字符编码为UTF-8，支持中文字符
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 设置响应内容类型为HTML，字符集为UTF-8
		response.setContentType("text/html;charset=UTF-8");
		// 获取响应输出流
		PrintWriter out = response.getWriter();
		// 从会话中获取读者实体对象
		Reader reader = (Reader) session.getAttribute("ReaderEntity");
		// 初始化用户ID
		int userid = Integer.MAX_VALUE;
		// 如果读者实体不为空，则获取读者ID
		if (reader != null) {
			userid = reader.getId();
		}
		// 创建借阅历史列表
		List<BorrowItem> borrowItems = new ArrayList<>();
		// 创建借阅项DAO实例
		BorrowItemDAO borrowItemDAO = new BorrowItemDAO();
		// 根据读者ID获取当前借阅项列表（注：方法命名可能存在歧义，实际获取的是当前借阅而非历史）
		borrowItems = borrowItemDAO.getBorrowItemInCurrent(userid);
		// 将借阅历史列表设置为会话属性，供后续使用
		session.setAttribute("borrowHistory", borrowItems);
		// 重定向到读者借阅历史展示页面
		response.sendRedirect("readerBorrowHistory.jsp");
	}
}
