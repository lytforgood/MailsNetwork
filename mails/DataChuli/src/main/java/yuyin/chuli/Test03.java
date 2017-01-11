package yuyin.chuli;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据部门分类
 * @author Administrator
 *
 */
public class Test03 {
	public static void main(String[] args) throws IOException {
		//获取所有邮箱
		Map<String, String> nodes=getmailstxt();
		//获取所有邮箱
		List<String> edges=getmailstxt2();
		for (String mail : edges) {
			String[] dataStrings = mail.split(",");
			String mailx=dataStrings[0];
			String maily=dataStrings[1];
			String out=nodes.get(mailx)+","+nodes.get(maily);
//			System.out.println(out);
			WriteFile.writeFileByLines("F:/邮箱数据/2016-7-30-2016-10-28/邮箱边脱敏2.csv",out);
		}
//			WriteFile.writeFileByLines("F:/邮箱数据/twox.txt", dataStrings[0]+"---->"+onemail+"---->"+dataStrings[2]);

	}
	private static Map<String, String> getmailstxt() throws IOException {
		File file = new File("F:/邮箱数据/2016-7-30-2016-10-28/邮箱节点.csv");
		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader(file));
		String tempString = null;
		int line = 1;
		Map<String, String> list=new HashMap<String, String>();
		// 一次读入一行，直到读入null为文件结束
		while ((tempString = reader.readLine()) != null) {
				try {
					String[] dataStrings = tempString.split(",");
					String mail=dataStrings[0];
					String Id=dataStrings[1];
					list.put(mail, Id);
				} catch (Exception e) {
				}
			line++;
		}
		reader.close();		
		return list;
	}
	private static List getmailstxt2() throws IOException {
		File file = new File("F:/邮箱数据/2016-7-30-2016-10-28/去异常边.csv");
		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader(file));
		String tempString = null;
		int line = 1;
		List<String> list=new ArrayList<>();
		// 一次读入一行，直到读入null为文件结束
		while ((tempString = reader.readLine()) != null) {
				try {
//					String[] dataStrings = tempString.split(",");
//					String mail=dataStrings[0];
//					String Id=dataStrings[1];
//					list.put(mail, Id);
					list.add(tempString);
				} catch (Exception e) {
				}
			line++;
		}
		reader.close();		
		return list;
	}
}
