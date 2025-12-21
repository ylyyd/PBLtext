// 定义包路径
package controller.librarian;

// 导入所需的Java类库
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// 导入阿里巴巴FastJSON库
import com.alibaba.fastjson.JSON;

/**
 * Servlet控制器类，用于验证ISBN的正确性，并从网上获取图书信息
 * 该servlet用于验证ISBN的正确性，并从网上获取图书信息
 */
public class ValidateISBN extends HttpServlet {
	// 序列化版本号，用于序列化兼容性检查
	private static final long serialVersionUID = 1L;

	// 构造函数
	public ValidateISBN() {
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
	 * 主要功能是验证ISBN的有效性，并通过豆瓣API获取图书详细信息
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置请求和响应的字符编码为UTF-8，支持中文字符
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 设置响应内容类型为HTML，字符集为UTF-8
		response.setContentType("text/html;charset=UTF-8");

		// 从请求参数中获取ISBN
		String isbn = request.getParameter("ISBN");
		// 构建豆瓣图书API的URL
		String uri = "https://api.douban.com/v2/book/isbn/" + isbn;
		// 存储JSON数据的字符串
		String jsonData = "";
		// 获取当前会话对象
		HttpSession session = request.getSession();
		// 获取响应输出流
		PrintWriter out = response.getWriter();

		try {
			// 调用自定义函数获取JSON数据
			jsonData = getJsonData(uri, "");
		} catch (Exception e) {
			// 捕获异常并打印堆栈跟踪
			e.printStackTrace();
		}

		// 检查是否获取到JSON数据
		if (jsonData != "") {
			System.out.println(jsonData);
			// 使用FastJSON解析JSON数据
			Map maps = (Map) JSON.parse(jsonData);
			// 处理作者信息，去除特殊字符
			String author = maps.get("author").toString();
			author = author.replace("[", "");
			author = author.replace("]", "");
			author = author.replace("\"", "");

			// 打印图书信息到控制台
			System.out.println("定价: " + maps.get("price"));
			System.out.println("封面: " + maps.get("image"));
			System.out.println("作者：  " + author);
			System.out.println("出版日期: " + maps.get("pubdate"));
			System.out.println("出版社: " + maps.get("publisher"));
			System.out.println("书名: " + maps.get("title"));
			System.out.println("摘要: " + maps.get("summary"));

			// 将图书信息保存到会话中
			session.setAttribute("ISBN__", isbn);
			session.setAttribute("BookName__", maps.get("title"));
			session.setAttribute("BookDes__", maps.get("summary"));
			session.setAttribute("PubTime__", maps.get("pubdate"));
			session.setAttribute("Price__", maps.get("price"));
			session.setAttribute("Author__", author);
			session.setAttribute("PubName__", maps.get("publisher"));

			// 弹出成功提示并跳转到添加书籍页面
			out.print(
					"<script language='javascript'>alert('ISBN有效！');window.location.href='librarianAddBook.jsp';</script>");

		} else {
			// 弹出失败提示并跳转到添加书籍页面
			out.print(
					"<script language='javascript'>alert('无效的ISBN！');window.location.href='librarianAddBook.jsp';</script>");
		}
	}

	/**
	 * 通过GET请求获取指定URL的JSON数据
	 * @param url 请求的URL地址
	 * @param param 请求参数
	 * @return 返回获取到的JSON数据字符串
	 * @throws Exception 抛出异常
	 */
	private String getJsonData(String url, String param) throws Exception {
		String result = "";
		BufferedReader in = null;
		try {
			// 构建完整的URL字符串
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String line;
			// 逐行读取响应内容
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			// 处理GET请求异常
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 通过POST请求发送数据到指定URL
	 * @param url 请求的URL地址
	 * @param param 请求参数
	 * @return 返回响应结果字符串
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			// 逐行读取响应内容
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			// 处理POST请求异常
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
}
