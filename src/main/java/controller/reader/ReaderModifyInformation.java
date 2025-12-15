package controller.reader;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ReaderDAO;
import entity.BorrowItem;
import entity.Cart;
import entity.Reader;
import util.*;

/**
 * Servlet实现类 ReaderModifyInformation
 */
public class ReaderModifyInformation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReaderModifyInformation() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 初始化
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();

		// 获取读者ID
		Reader reader = (Reader) session.getAttribute("ReaderEntity");
		int id = reader.getId();

		// 获取新信息
		String name = (String) request.getParameter("newName");
		String password = (String) request.getParameter("newPassword");
		String email = (String) request.getParameter("newEmail");
		String state = reader.getState();
		ArrayList<BorrowItem> borrowhistory = reader.getBorrowHistory();
		ArrayList<Cart> cartList = reader.getCartList();
		ReaderDAO readerDAO = new ReaderDAO();

		// 执行更新
		String newPassword1 = SecurityUtil.md5(password);
		readerDAO.updateReaderInformation(id, name, newPassword1, email);
		Reader newReader = new Reader();
		newReader.setId(id);
		newReader.setEmail(email);
		newReader.setName(name);
		newReader.setState(state);
		newReader.setBorrowHistory(borrowhistory);
		newReader.setCartList(cartList);
		session.setAttribute("ReaderEntity", newReader);
		out.print("<script>alert('更新信息成功！');window.location='readerIndex.jsp';</script>");

	}
}
