package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/transformKeyValue")

public class transformKeyValue extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String KEY_API = "https://asia-east2-candidateplayground.cloudfunctions.net/key";
	private static final String VALUE_API = "https://asia-east2-candidateplayground.cloudfunctions.net/value";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public transformKeyValue() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		PrintWriter out = response.getWriter();
		out.println("<h2>/transformKeyValue</h2>");
		
		// 1. Retrieve 'key' from KEY_API
		String key = getKey();
		out.println("key : " + key + "<br>");
		
		// 2. Query 'value' from VALUE_API with authorization header of 'key'
		String value = getValue(key);
		out.println("value : " + value + "<br>");
		
		// 3. Calculate sum of 'value'
		String sum = sumValue(value);
		out.println("sum = " + sum + "<br>");
		
		// 4. Store sum of 'value' to DB via /saveData
		request.setAttribute("key", key);
		request.setAttribute("value", value);
		request.setAttribute("sum", sum);
		RequestDispatcher rd = request.getRequestDispatcher("saveData");
		rd.forward(request, response);
		
	}

	/**
	 * @return
	 * @throws IOException
	 */
	private String getKey() throws IOException {
		// TODO Auto-generated method stub

		StringBuffer result = new StringBuffer();
		
		// disable certificate validation
		HostnameVerifier hv = new HostnameVerifier() {
			@Override
			public boolean verify(String urlHostName, SSLSession session) {
				return true;
			}
		};
		HttpsURLConnection.setDefaultHostnameVerifier(hv);
		
		// send GET request to KEY_API
		URL url = new URL(KEY_API);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		con.setRequestMethod("GET");	
	    int responseCode = con.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			System.out.println("GET request success.");
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;			
			while ((inputLine = in.readLine()) != null)
				result.append(inputLine);
			in.close();
		} else {
			System.out.println("GET request failed.");
		}
		con.disconnect();
		
		// extract 'key'
		JSONObject json = new JSONObject(result.toString());
		return json.getString("key");
		
	}

	/**
	 * @param key
	 * @return
	 * @throws IOException
	 */
	private String getValue(String key) throws IOException {
		// TODO Auto-generated method stub

		StringBuffer result = new StringBuffer();

		// disable certificate validation
		HostnameVerifier hv = new HostnameVerifier() {
			@Override
			public boolean verify(String urlHostName, SSLSession session) {
				return true;
			}
		};
		HttpsURLConnection.setDefaultHostnameVerifier(hv);
		
		// send GET request to VALUE_API
		URL url = new URL(VALUE_API);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Authorization", key); // set authorization header with 'key'
	    int responseCode = con.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			System.out.println("GET request success.");
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				result.append(inputLine);
			in.close();
		} else {
			System.out.println("GET request failed.");
		}
		con.disconnect();

		// extract 'value'
		JSONObject json = new JSONObject(result.toString());
		return json.get("value").toString();
		
	}
	
	/**
	 * @param value
	 * @return
	 */
	private String sumValue(String value) {
		// TODO Auto-generated method stub
		
		int sum = 0;
	
		String [] valueArray = value.substring(1, value.length()-1).split(",");
		for(int i=0; i<valueArray.length; i++) {
			sum += Integer.parseInt(valueArray[i]);
		}
		
		return sum + "";
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}