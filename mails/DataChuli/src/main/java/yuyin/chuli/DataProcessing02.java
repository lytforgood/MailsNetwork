package yuyin.chuli;

import java.io.IOException;
import java.util.List;

/**
 * 过滤所有和内部无联系的
 * @author Administrator
 *
 */
public class DataProcessing02 {
	
	public static void main(String[] args) throws IOException {
		List<String> lineStrings = WriteFile
				.readFileByLines("F:\\邮箱数据\\邮箱记录\\allmails原始.txt");
		for (String line : lineStrings) {
			String[] tm1 = line.split("---->");
			if (tm1[0].contains("(1)")) {
				WriteFile.writeFileByLines("F:\\邮箱数据\\邮箱记录\\多邮箱2.txt", line);
			}
		}
	}

}
