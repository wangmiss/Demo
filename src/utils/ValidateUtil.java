
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

/**
 * 校验工具类
 *
 */
public abstract class ValidateUtil {
	
	/** 用户名正则 */
	private static final String REG_USERNAME = "^[a-zA-Z]\\w{3,31}";
	/** Email 正则 */
	private static final String REG_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
	
	/**
	 * 验证必填项,如果不通过,返回List【错误描述信息】
	 * 
	 * @param dataObj 校验对象
	 * @param propNames 校验属性【数组】
	 * @param displayNames 属性对应的【中文名称】
	 * @return errorList 错误集合,每条数据表示一个属性的错误描述信息
	 */
	public static List<String> validateMust(Object dataObj, String[] propNames,
			String[] displayNames) {
		Assert.isTrue(propNames.length == displayNames.length, "参数名与显示名的个数不一致");
		List<String> errorList = new ArrayList<String>();
		for (int i = 0; i < propNames.length; i++) {
			String propName = propNames[i];
			String displayName = displayNames[i];
			Object value = ReflectUtil.getProperty(dataObj, propName);
			if (null == value) {
				errorList.add(displayName + "(" + propName + ")是必填项");
			} else {
				if (value instanceof String) {
					// 处理全角空格
					String valueStr = value.toString().replace("　", "").trim();
					if (valueStr.length() == 0) {
						errorList.add(displayName + "(" + propName + ")是必填项");
					}
				}
			}
		}
		return errorList;
	}
	
	/**
	 * 
	 * 校验邮箱
	 * 
	 * @param email
	 * @return boolean
	 */
	public static boolean validateEmail(String email) {
		if (null == email || email.trim().length() == 0) {
			return false;
		}
		return email.matches(REG_EMAIL);
	}

	/**
	 * 
	 * 校验用户名
	 * 
	 * @param
	 * @return boolean
	 */
	public static boolean validateUserName(String userName) {
		return validateByRegex(userName, REG_USERNAME);
	}

	/**
	 * 按指定正则表达式验证
	 * @param input 输入字符
	 * @param regex 正则
	 * @return 成立与否
	 */
	private static boolean validateByRegex(String input, String regex) {
		if (null == input || input.trim().length() == 0) {
			return false;
		}
		return input.matches(regex);
	}

}
