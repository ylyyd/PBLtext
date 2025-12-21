// 定义包路径
package controller.reader;

// 导入所需的Java类库
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// 导入数据访问对象、实体类和工具类
import dao.ReaderDAO;
import entity.BorrowItem;
import entity.Cart;
import entity.Reader;
import util.*;

/**
 * Servlet控制器类，用于处理读者修改个人信息的业务逻辑
 * Servlet实现类 ReaderModifyInformation
 */
public class ReaderModifyInformation extends HttpServlet {
	// 序列化版本号，用于序列化兼容性检查
	private static final long serialVersionUID = 1L;

	/**
	 * 构造函数
	 * @see HttpServlet#HttpServlet()
	 */
	public ReaderModifyInformation() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 处理GET请求的方法
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 默认实现：返回服务器信息和上下文路径
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * 处理POST请求的方法
	 * 主要功能是处理读者提交的个人信息修改请求
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 初始化
		// 设置请求和响应的字符编码为UTF-8，支持中文字符
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 设置响应内容类型为HTML，字符集为UTF-8
		response.setContentType("text/html;charset=UTF-8");
		// 获取响应输出流
		PrintWriter out = response.getWriter();
		// 获取当前会话对象
		HttpSession session = request.getSession();

		// 获取读者ID
		// 从会话中获取读者实体对象
		Reader reader = (Reader) session.getAttribute("ReaderEntity");
		// 获取读者ID
		int id = reader.getId();

		// 获取新信息
		// 从请求参数中获取新的姓名、密码和邮箱
		String name = (String) request.getParameter("newName");
		String password = (String) request.getParameter("newPassword");
		String email = (String) request.getParameter("newEmail");
		// 获取读者当前状态
		String state = reader.getState();
		// 获取读者借阅历史和购物车列表
		ArrayList<BorrowItem> borrowhistory = reader.getBorrowHistory();
		ArrayList<Cart> cartList = reader.getCartList();
		// 创建读者DAO实例
		ReaderDAO readerDAO = new ReaderDAO();

		// 执行更新
		// 对新密码进行MD5加密
		String newPassword1 = SecurityUtil.md5(password);
		// 调用DAO方法更新读者信息
		readerDAO.updateReaderInformation(id, name, newPassword1, email);
		// 创建新的读者实体对象
		Reader newReader = new Reader();
		// 设置新读者实体的属性
		newReader.setId(id);
		newReader.setEmail(email);
		newReader.setName(name);
		newReader.setState(state);
		newReader.setBorrowHistory(borrowhistory);
		newReader.setCartList(cartList);
		// 将新的读者实体设置为会话属性
		session.setAttribute("ReaderEntity", newReader);
		// 弹出成功提示并跳转到读者主页
		out.print("<script>alert('更新信息成功！');window.location='readerIndex.jsp';</script>");
	}
}
