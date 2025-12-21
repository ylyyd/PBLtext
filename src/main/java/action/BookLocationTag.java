// 定义包路径
package action;

// 导入所需的Java类库
import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTag;

/**
 * 自定义JSP标签类，用于生成图书位置(location)的下拉选择框
 * 实现SimpleTag接口来创建自定义标签
 */
public class BookLocationTag implements SimpleTag {

	// JSP上下文对象，用于获取输出流和其他JSP相关信息
	private JspContext jspContext;

	/**
	 * 标签的主要处理方法
	 * 生成图书位置的下拉选择框HTML代码并输出到页面
	 * @throws JspException JSP异常
	 * @throws IOException IO异常
	 */
	public void doTag() throws JspException, IOException {
		try {
			// 获取JSP输出流对象
			JspWriter out = jspContext.getOut();
			// 初始化输出字符串
			String outPrint = "";
			// 定义图书位置选项数组
			String[] color = { "四层-科技图书阅览区", "四层-科技图书典藏区", "三层-社科类图书阅览区", "三层-社科类图书典藏区", "二层-杂志期刊" };

			// 开始构建select下拉框HTML代码
			outPrint += "<select name='Location' size='1' class=\"form-control\"> ";
			// 遍历位置数组，为每个位置创建option选项
			for (int i = 0; i < color.length; i++) {
				outPrint += "<option>";
				outPrint += color[i];
				outPrint += "</option>";
			}
			// 结束select标签
			outPrint += "</select>";
			// 输出构建好的HTML代码到页面
			out.print(outPrint);
		} catch (IOException e) {
			// 捕获IO异常并转换为JSP标签异常抛出
			throw new JspTagException(e.getMessage());
		}
	}

	/**
	 * 获取父标签对象
	 * @return 父标签对象，此处返回null
	 */
	public JspTag getParent() {
		return null;
	}

	/**
	 * 设置标签体内容
	 * @param arg0 JSP片段对象
	 */
	public void setJspBody(JspFragment arg0) {
		// 此标签没有标签体内容，方法体为空
	}

	/**
	 * 设置JSP上下文
	 * @param arg0 JSP上下文对象
	 */
	public void setJspContext(JspContext arg0) {
		// 保存传入的JSP上下文对象
		this.jspContext = arg0;
	}

	/**
	 * 设置父标签对象
	 * @param arg0 父标签对象
	 */
	public void setParent(JspTag arg0) {
		// 此标签没有父标签关系，方法体为空
	}
}
