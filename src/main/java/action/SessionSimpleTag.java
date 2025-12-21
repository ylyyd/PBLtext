// 定义包路径
package action;

// 导入所需的Java类库
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 自定义JSP标签类，用于会话检查
 * 继承SimpleTagSupport类来创建自定义标签
 */
public class SessionSimpleTag extends SimpleTagSupport {

	// HTTP请求对象
	private HttpServletRequest request;
	// HTTP响应对象
	private HttpServletResponse response;

	/**
	 * 标签的主要处理方法
	 * 生成JSP转发代码，用于检查管理员会话是否存在
	 * @throws JspException JSP异常
	 * @throws IOException IO异常
	 */
	public void doTag() throws JspException, IOException {
		// 获取JSP输出流对象
		JspWriter out = this.getJspContext().getOut();
		// 输出JSP标准标签库代码，检查sessionScope中的AdministratorEntity是否为空
		// 如果为空则转发到homepage.jsp页面
		out.println(
				"<c:if test=\"${empty sessionScope.AdministratorEntity}\" > <jsp:forward page=\"homepage.jsp\"/> </c:if>");
	}
}
