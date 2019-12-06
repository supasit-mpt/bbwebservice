package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/saveData")

public class saveData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String MYSQL_DBNAME = "db1";
	public static final String MYSQL_USN = "root";
	public static final String MYSQL_PWD = "supasit1983+";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public saveData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		PrintWriter out = response.getWriter();
		out.println("<h2>/saveData</h2>");
		out.println("key : " + request.getAttribute("key") + "<br>");
		out.println("value : " + request.getAttribute("value") + "<br>");
		out.println("sum = " + request.getAttribute("sum") + "<br><br>");
		
		try {
			
			// connect to MySQL DB
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/" + MYSQL_DBNAME + "?characterEncoding=utf-8", MYSQL_USN, MYSQL_PWD);  
			
			// send query to MySQL DB
			Statement stmt = con.createStatement();
			String jsonObj = "{\"key\":\"" + request.getAttribute("key") + "\",\"sum\":\"" + request.getAttribute("sum") + "\"}";
			int result = stmt.executeUpdate(
					"INSERT INTO Table1 VALUE(NULL,'" + jsonObj + "')"
					); // store JSON Object to Table1
			
			// check and show result
			if(result==1) {
				out.println("JSON object " + jsonObj.toString() + " has been stored."); 
			} else {
				out.println("Something went wrong.<br>"); 
			}
			
			con.close();
			
		} catch(Exception e) {
			System.out.println("Error occured.");  
		}  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}