package yuyin.chuli;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 根据部门分类
 * @author Administrator
 *
 */
public class Test02 {
	public static void main(String[] args) throws IOException {
		//获取所有邮箱
		Map<String, String> mails=getmailstxt();
//		for (String mail : mails.keySet()) {
//			System.out.println(mail+"地址："+mails.get(mail));
//			String oldpath="F:/邮箱数据/2016-7-30-2016-10-28/所有记录含空/"+mail+".txt";
//			String newpath="F:/邮箱数据/2016-7-30-2016-10-28分类/"+mails.get(mail);
////			WriteFile.createFolder(newpath);
//			WriteFile.copyFile(oldpath, newpath+"/"+mail);
//		}
		
//			WriteFile.writeFileByLines("F:/邮箱数据/twox.txt", dataStrings[0]+"---->"+onemail+"---->"+dataStrings[2]);

	}
	private static Map<String, String> getmailstxt() throws IOException {
		File file = new File("F:\\邮箱数据\\2016-7-30-2016-10-28\\内部有联系的邮箱关系.csv");
		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader(file));
		String tempString = null;
		int line = 1;
		Map<String, String> list=new HashMap<String, String>();
		// 一次读入一行，直到读入null为文件结束
		while ((tempString = reader.readLine()) != null) {
				try {
					String[] dataStrings = tempString.split(",");
					String t1=dataStrings[0];
					String[] t2=dataStrings[1].split("-->");
					String t3="";
					for (String str : t2) {
//						System.out.println(t1+","+str);
						WriteFile.writeFileByLines("F:/邮箱数据/2016-7-30-2016-10-28/邮箱边.csv", t1+","+str);
						
					}
//					System.out.println(t1+","+t3.substring(1,t3.length()));
//					list.put(mail, path);
//					WriteFile.writeFileByLines("F:/邮箱数据/邮箱记录/allmails.csv", dataStrings[0]+","+dataStrings[1]+","+dataStrings[2]);
				} catch (Exception e) {
				}
			line++;
		}
		reader.close();		
		return list;
	}
}
