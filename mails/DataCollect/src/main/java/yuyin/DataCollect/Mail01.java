package yuyin.DataCollect;

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

/*
 * 爬取邮箱数据--会爬到一个人两个邮箱和特殊含三个邮箱的需要处理
 */
public class Mail01 {
	
	public static void main(String[] args) throws IOException {
		getMails();
	}

	//获取查询后的页面标签list
	private static void getMails() throws IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		List<String> keys=getKey(httpclient, "",18,"","");
		String line="";
		int i=0;
		for (String out : keys) {
			line=line+out+"---->";
			i++;
			if (i==3) {
				WriteFile.writeFileByLines("F:/邮箱数据/邮箱记录/allmails原始.txt", line.substring(0,line.length()-5));
				line="";
				i=0;
			}
			
		}
	}

	
	//提交查询的参数
	private static List<String> getKey(CloseableHttpClient httpclient,
			String mail, Integer pagenum, String startDate, String endDate) throws ClientProtocolException, IOException {
//		    HttpGet httpGet = new HttpGet("http://mail.nuc.edu.cn/cgi-bin/bizmail_log?sid=Vb6BWp31bJWEpzxk,2&t=biz_rf_syslog&tt=mail&action=query_mail&mailtype=all&alias=baipeikang@nuc.edu.cn&subject=-&begin=2016-10-20&end=2016-10-27&page=0&ef=pg");
		Integer numInteger=pagenum;
		List<String> list=new ArrayList<>();
		for (int i = 0; i < numInteger; i++) {
			System.out.println("爬取到第"+(i+1)+"页,总共"+numInteger+"页,进度"+new java.text.DecimalFormat("#.00").format(((double)(i+1)/numInteger)*100)+"%");
		    HttpGet httpGet = new HttpGet("http://mail.nuc.edu.cn/cgi-bin/bizmail_account?sid=wyhfbKvWOMHVkglR,2&t=biz_rf_mbr_new_list&action=get_party&partyid=5495113&listtype=all&recursive=on&nopwd=off&nobind=off&page="+i+"&pageurl=%23mbr%2Flist%2F5495113%2Ctrue%2Cfalse%2Cfalse%2C&btype=0&r=0.5563859843224468");
			httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)");
			httpGet.addHeader("Accept","image/webp,image/*,*/*;q=0.8");
			httpGet.addHeader("Accept-Language","zh-CN,zh;q=0.8");
			httpGet.addHeader("Accept-Encoding","gzip, deflate, sdch");
			httpGet.addHeader("Connection","keep-alive");
			httpGet.addHeader("Cookie","_gscu_1233436059=724560672hw3sw19; Hm_lvt_bdfb0d7298c0c5a5a2475c291ac7aca2=1477465268; pgv_pvi=1698980864; tk=-1678129173&2f01baf561bc6fdMTQ3NzQ2NTI5MQ; qm_authimgs_id=1; qm_verifyimagesession=h011b8df21d2bd4e7918280e8232de4e1d4bfb9727a9f9dcfb45dc449a841c000c342d302ec1748e51c; 0.4874544732793342; qqmail_alias=zhyj@nuc.edu.cn; tinfo=1477737847.0000*; qm_flag=0; qqmail_alias=zhyj@nuc.edu.cn; biz_username=2616838123; CCSHOW=0000; username=-1678129173&2616838123; ssl_edition=b31.exmail.qq.com; qm_ssum=-1678129173&f3bc1d1ed0ad2c6bf7e447e234ba43c3; qm_sk=-1678129173&u_rTFvOA; sid=-1678129173&b5bcad493840e90ed7039bb513311bdd; qm_sid=b5bcad493840e90ed7039bb513311bdd; qm_username=2616838123");
			CloseableHttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			String content= EntityUtils.toString(entity,"GBK");
//			System.out.println(content);
			Document doc = Jsoup.parse(content);
			Elements links = doc.select("td");
			for(Element link:links){
				
				String tmp=link.text();
				if (tmp.contains("置")) {
//					System.out.println(tmp+link.text());
//					list.add(tmp);
				} else if (tmp.length()==0||tmp.equals("")) {
					
				}else {
//					System.out.println(tmp);
					list.add(tmp);
				}
//				System.out.println(link.attr("title").toString());
			}
		}
			return list;
	}
	
	
	
}
