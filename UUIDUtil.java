

import java.util.UUID;

/**
 * UUID生成工具类
 *
 */
public abstract class UUIDUtil {
	/**
	 * 获取32位UUID
	 */
	public static String UUID32() {
		return UUID64().replace("-", "");
	}
	/**
	 * 获取UUID:默认64为位
	 */
	public static String UUID64() {
		return UUID.randomUUID().toString();
	}

}
