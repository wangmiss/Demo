
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 汉字转拼音工具类
 *
 */
public abstract class PinyinUtil {

	/**
	 * 获取字符串拼音缩写或全拼
	 * @param chinese 汉字
	 * @param isUp 是否大写
	 * @param isAcronym 是否是缩写
	 * @return
	 */
	public static String spell(String chinese, boolean isUp,boolean isAcronym) {
		if (chinese == null) {
			return null;
		}
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 小写
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 不标声调
		format.setVCharType(HanyuPinyinVCharType.WITH_V);// u:的声母替换为v
		try {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < chinese.length(); i++) {
				String[] array = PinyinHelper.toHanyuPinyinStringArray(chinese.charAt(i), format);
				if (array == null || array.length == 0) {
					sb.append(chinese.charAt(i));
					continue;
				}
				String s = array[0];// 不管多音字,只取第一个
				String pinyin = null;
				if(isAcronym){
					pinyin = String.valueOf(s.charAt(0));
				}else{
					pinyin = String.valueOf(s);
				}
				if (isUp) {
					pinyin = pinyin.toUpperCase();
				}
				sb.append(pinyin);
			}
			return sb.toString();
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args){
		System.out.println(PinyinUtil.spell("张三", false,true));//缩写 zs
		System.out.println(PinyinUtil.spell("张三", false,false));//全拼小写 zhangsan
		System.out.println(PinyinUtil.spell("张三", true,false));//全拼大写 ZHANGSAN
	}
}
