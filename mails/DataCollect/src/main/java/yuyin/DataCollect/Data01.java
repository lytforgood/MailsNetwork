package yuyin.DataCollect;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 爬取收发日志
 * @author Administrator
 *
 */
public class Data01 {
	
	public static void main(String[] args) throws IOException {
		List<String> mailsTxt=getmailstxt();
		for (String mailTxt : mailsTxt) {
			String[] dataStrings = mailTxt.split("---->");
			String mail=dataStrings[1];
			String path=dataStrings[2];
			System.out.println(mail);
			gettitles(mail,path);
			WriteFile.writeFileByLines("F:/邮箱数据/爬取记录.txt", mail);
		}
	}

	//获取查询后的页面标签list
	private static void gettitles(String mail2, String path) throws IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String mail=mail2;
		String startDate="2016-7-29";
		String endDate="2016-10-27";
		String pagenum=getpagenum(httpclient, mail,0,startDate,endDate);
		List<String> keys=getKey(httpclient, mail,pagenum,startDate,endDate);
		if (keys.size()==0) {
			WriteFile.writeFileByLines("F:/邮箱数据/邮箱目录/"+mail+".txt", "");
		}
		String line="";
		int i=0;
		for (String out : keys) {
			line=line+out+"---->";
			i++;
			if (i==5) {
				WriteFile.writeFileByLines("F:/邮箱数据/邮箱目录/"+mail+".txt", line.substring(0,line.length()-5));
				line="";
				i=0;
			}
			
		}
	}

	
	//提交查询的参数
	private static List<String> getKey(CloseableHttpClient httpclient,
			String mail, String pagenum, String startDate, String endDate) throws ClientProtocolException, IOException {
//		    HttpGet httpGet = new HttpGet("http://mail.nuc.edu.cn/cgi-bin/bizmail_log?sid=Vb6BWp31bJWEpzxk,2&t=biz_rf_syslog&tt=mail&action=query_mail&mailtype=all&alias=baipeikang@nuc.edu.cn&subject=-&begin=2016-10-20&end=2016-10-27&page=0&ef=pg");
		Integer numInteger=Integer.parseInt(pagenum);
		List<String> list=new ArrayList<>();
		for (int i = 0; i < numInteger; i++) {
			System.out.println("爬取到第"+(i+1)+"页,总共"+numInteger+"页,进度"+new java.text.DecimalFormat("#.00").format(((double)(i+1)/numInteger)*100)+"%");
		    HttpGet httpGet = new HttpGet("http://mail.nuc.edu.cn/cgi-bin/bizmail_log?sid=H-TkBsFszjcmviV9,2&t=biz_rf_syslog&tt=mail&action=query_mail&mailtype=all&alias="+mail+"&subject=-&begin="+startDate+"&end="+endDate+"&page="+i+"&ef=pg");
			httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)");
			httpGet.addHeader("Accept","image/webp,image/*,*/*;q=0.8");
			httpGet.addHeader("Accept-Language","zh-CN,zh;q=0.8");
			httpGet.addHeader("Accept-Encoding","gzip, deflate, sdch");
			httpGet.addHeader("Connection","keep-alive");
			httpGet.addHeader("Cookie","_gscu_1233436059=724560672hw3sw19; Hm_lvt_bdfb0d7298c0c5a5a2475c291ac7aca2=1477465268; pgv_pvi=1698980864; tk=-1678129173&2f01baf561bc6fdMTQ3NzQ2NTI5MQ; 0.28385402754014377; biz_referrer=mail.nuc.edu.cn; qm_authimgs_id=1; qm_verifyimagesession=h0181e4f87d2e072aeb54b431ac176b7ae45a556d0b69f64dc64284f469a78dd43d9ef86018d6e33b54; qqmail_alias=zhyj@nuc.edu.cn; tinfo=1477553423.0000*; qm_flag=0; qqmail_alias=zhyj@nuc.edu.cn; biz_username=2616838123; CCSHOW=0000; username=-1678129173&2616838123; ssl_edition=b31.exmail.qq.com; qm_ssum=-1678129173&a0e134f42021b3f11b072eddd3ca303f; qm_sk=-1678129173&u_rTFvOA; sid=-1678129173&6d366903dec6116a1ad1e991a88a4bb4; qm_sid=6d366903dec6116a1ad1e991a88a4bb4; qm_username=2616838123");
			CloseableHttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			String content= EntityUtils.toString(entity,"GBK");
//			System.out.println(content);
			Document doc = Jsoup.parse(content);
			Elements links = doc.select("td[title]");
			for(Element link:links){
//				System.out.println(link.text());
				String tmp=link.attr("title");
				if (tmp.contains("日")) {
//					System.out.println(tmp+link.text());
					list.add(tmp+link.text());
				} else {
//					System.out.println(tmp);
					list.add(tmp);
				}
//				System.out.println(link.attr("title").toString());
			}
		}
			return list;
	}
	
	
	private static String getpagenum(CloseableHttpClient httpclient,
			String mail, Integer pagenum, String startDate, String endDate) throws ClientProtocolException, IOException {
//		    HttpGet httpGet = new HttpGet("http://mail.nuc.edu.cn/cgi-bin/bizmail_log?sid=Vb6BWp31bJWEpzxk,2&t=biz_rf_syslog&tt=mail&action=query_mail&mailtype=all&alias=baipeikang@nuc.edu.cn&subject=-&begin=2016-10-20&end=2016-10-27&page=0&ef=pg");
		    HttpGet httpGet = new HttpGet("http://mail.nuc.edu.cn/cgi-bin/bizmail_log?sid=H-TkBsFszjcmviV9,2&t=biz_rf_syslog&tt=mail&action=query_mail&mailtype=all&alias="+mail+"&subject=-&begin="+startDate+"&end="+endDate+"&page="+pagenum+"&ef=pg");
			httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)");
			httpGet.addHeader("Accept","image/webp,image/*,*/*;q=0.8");
			httpGet.addHeader("Accept-Language","zh-CN,zh;q=0.8");
			httpGet.addHeader("Accept-Encoding","gzip, deflate, sdch");
			httpGet.addHeader("Connection","keep-alive");
			httpGet.addHeader("Cookie","_gscu_1233436059=724560672hw3sw19; Hm_lvt_bdfb0d7298c0c5a5a2475c291ac7aca2=1477465268; pgv_pvi=1698980864; tk=-1678129173&2f01baf561bc6fdMTQ3NzQ2NTI5MQ; 0.28385402754014377; biz_referrer=mail.nuc.edu.cn; qm_authimgs_id=1; qm_verifyimagesession=h0181e4f87d2e072aeb54b431ac176b7ae45a556d0b69f64dc64284f469a78dd43d9ef86018d6e33b54; qqmail_alias=zhyj@nuc.edu.cn; tinfo=1477553423.0000*; qm_flag=0; qqmail_alias=zhyj@nuc.edu.cn; biz_username=2616838123; CCSHOW=0000; username=-1678129173&2616838123; ssl_edition=b31.exmail.qq.com; qm_ssum=-1678129173&a0e134f42021b3f11b072eddd3ca303f; qm_sk=-1678129173&u_rTFvOA; sid=-1678129173&6d366903dec6116a1ad1e991a88a4bb4; qm_sid=6d366903dec6116a1ad1e991a88a4bb4; qm_username=2616838123");
			CloseableHttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			String content= EntityUtils.toString(entity,"GBK");
//			System.out.println(content);
			Document doc = Jsoup.parse(content);
			Elements wu = doc.select("td[height]");
			String num="";
			if (wu.size()>0) {
				if (wu.get(0).text().contains("没有")) {
					num="0";
				}
			} else {
				try {
					Elements links = doc.select("div[page]");
					num=links.get(0).attr("page").split(",")[0];
				} catch (Exception e) {
					Elements links = doc.select("td[title]");
					for(Element link:links){
						String tmp=link.attr("title");
						if (tmp.contains("日")) {
							num="0";
						} 
					}
				}
			}
//			System.out.println(links.get(0).attr("page").split(",")[0]);
			return num;
	}
	
	private static List<String> getmailstxt() throws IOException {
//		File file = new File("F:/邮箱数据/邮箱记录/allmails.txt");
		File file = new File("F:/邮箱数据/邮箱记录/test1.txt");
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
