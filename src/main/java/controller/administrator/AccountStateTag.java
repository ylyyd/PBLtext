// 定义包路径
package controller.administrator;

// 导入所需的Java类库
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 自定义JSP标签类，用于生成账号状态的下拉选择框
 * 继承TagSupport类来创建自定义标签
 */
public class AccountStateTag extends TagSupport {

	/**
	 * 将librarian或reader的账号状态做成Tag标签
	 * 生成账号状态选择下拉框，包含"unlock"(解锁)和"blockade"(封锁)两个选项
	 * @return SKIP_BODY 表示标签没有主体内容
	 * @throws JspException JSP异常
	 */
	public int doStartTag() throws JspException {

		try {
			// 获取JSP页面输出流
			JspWriter out = pageContext.getOut();
			// 初始化输出字符串
			String outPrint = "";
			// 定义账号状态选项数组
			String[] states = { "unlock", "blockade" };

			// 构建select下拉框HTML代码
			outPrint += "<select id='State' name='state' size='1'>";
			// 遍历状态数组，为每个状态创建option选项
			for (String state : states) {
				outPrint += "<option>";
				outPrint += state;
				outPrint += "</option>";
			}
			// 结束select标签
			outPrint += "</select>";
			// 输出构建好的HTML代码到页面
			out.print(outPrint);
		} catch (java.io.IOException e) {
			// 捕获IO异常并转换为JSP标签异常抛出
			throw new JspTagException(e.getMessage());
		}
		// 返回SKIP_BODY表示标签没有主体内容，不需要处理标签体
		return SKIP_BODY;
	}
}
