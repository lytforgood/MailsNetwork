package yuyin.chuli;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 把所有关系的邮箱输出
 * @author Administrator
 *
 */
public class DataProcessing01 {

	public static void main(String[] args) throws IOException {
		// 获取每一个邮箱文件名
		List<String> mails = getMails();
		for (String mail : mails) {
			List<String> lineStrings = WriteFile
					.readFileByLines("F:\\邮箱数据\\2016-7-30-2016-10-28\\所有记录含空\\"
							+ mail + ".txt");
			// 学校往来邮件
			Set<String> s1 = new HashSet();
			// 外部往来邮件
			Set<String> s2 = new HashSet();
			for (String line : lineStrings) {
				// System.out.println(line);
				String[] tm1 = line.split("---->");
				if (tm1.length > 1) {
					if (tm1[1].contains("nuc.edu.cn")) {
						s1.add(tm1[1]);
					} else {
						s2.add(tm1[1]);
					}
					if (tm1[2].contains("nuc.edu.cn")) {
						s1.add(tm1[2]);
					} else {
						s2.add(tm1[2]);
					}
					// 删除它本身
					s1.remove(mail);
				}
			}
			StringBuffer sb = new StringBuffer();
			for (String str : s1) {
				sb.append("-->");
				sb.append(str);
			}
			StringBuffer sb2 = new StringBuffer();
			for (String str : s2) {
				sb2.append("-->");
				sb2.append(str);
			}
			if (sb.length() < 1) {
				sb.append("-->无");
			}
			if (sb2.length() < 1) {
				sb2.append("-->无");
			}
			// 目标邮箱---->内部交流邮箱---->外部交流邮箱
			String out = mail + "---->" + sb.substring(3, sb.length())
					+ "---->" + sb2.substring(3, sb2.length());
			WriteFile.writeFileByLines(
					"F:\\邮箱数据\\2016-7-30-2016-10-28\\所有关系.txt", out);
		}

	}

	private static List<String> getMails() {
		List<String> mails = new ArrayList<>();
		String[] fileStrings = WriteFile
				.getFileName("F:\\邮箱数据\\2016-7-30-2016-10-28\\所有记录含空");
		for (String file : fileStrings) {
			// System.out.println(file.substring(0,file.length()-4));
			mails.add(file.substring(0, file.length() - 4));
		}
		return mails;
	}

}
