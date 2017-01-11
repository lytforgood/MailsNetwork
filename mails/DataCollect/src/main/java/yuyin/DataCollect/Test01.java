package yuyin.DataCollect;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * 将两个邮箱的拆分成2行数据带1个邮箱
 * @author Administrator
 *
 */
public class Test01 {
	public static void main(String[] args) throws IOException {
		List<String> mailsTxt=getmailstxt();
		for (String mailTxt : mailsTxt) {
			String[] dataStrings = mailTxt.split("---->");
			String mail=dataStrings[1];
			String path=dataStrings[2];
			String[] twomail=mail.split(" ");
			for (String onemail : twomail) {
				System.out.println(dataStrings[0]+"---->"+onemail+"---->"+dataStrings[2]);
				WriteFile.writeFileByLines("F:/邮箱数据/twox.txt", dataStrings[0]+"---->"+onemail+"---->"+dataStrings[2]);
			}
		}

	}
	private static List<String> getmailstxt() throws IOException {
//		File file = new File("F:/邮箱数据/邮箱记录/allmails.txt");
		File file = new File("F:/邮箱数据/邮箱记录/twomail.txt");
		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader(file));
		String tempString = null;
		int line = 1;
		List<String> list=new ArrayList<>();
		// 一次读入一行，直到读入null为文件结束
		while ((tempString = reader.readLine()) != null) {
				try {
					list.add(tempString);
				} catch (Exception e) {
				}
			line++;
		}
		reader.close();		
		return list;
	}
}
