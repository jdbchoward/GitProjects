package example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JsoupTest jt = new JsoupTest();
		jt.jsoupGet("http://www.baidu.com/s", "loadrunner");
		// jt.jsoupPost("http://127.0.0.1/defect/login.php", "admin");

	}

	public Document jsoupGet(String url, String param) {
		try {
			Connection conn = Jsoup.connect(url);
			conn.data("wd", param);
			Document doc = conn.get();
			System.out.println(doc.toString());
			return doc;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public Document jsoupPost(String url, String param) {
		try {
			Connection conn = Jsoup.connect(url);
			conn.data("username", "cloud");
			conn.data("password", param);
			Document doc = conn.post();
			System.out.println(doc.toString());
			return doc;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public String postRawData(String url, String rawBody) {
		HttpURLConnection conn = null;
		PrintWriter pw = null;
		BufferedReader rd = null;
		StringBuilder sb = new StringBuilder();
		String line = null;
		String response = null;
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setReadTimeout(20000);
			conn.setConnectTimeout(20000);
			conn.setUseCaches(false);
			conn.connect();
			pw = new PrintWriter(conn.getOutputStream());
			pw.print(rawBody);
			pw.flush();
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			response = sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pw != null)
					pw.close();
				if (rd != null)
					rd.close();
				if (conn != null)
					conn.disconnect();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return response;
	}

}
