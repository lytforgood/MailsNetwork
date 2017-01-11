package yuyin.DataCollect;

import java.io.IOException;



public class Test {
	public static void main(String[] args) throws IOException {
		String path="中北大学/党支部";
		String mail="b@xx.cc";
		String newpath="F:/邮箱数据/分类/"+path+"/";
		WriteFile.createFolder(newpath);
		WriteFile.writeFileByLines(newpath+mail+".txt", "");
	}

}
