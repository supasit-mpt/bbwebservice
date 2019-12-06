package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/getData")

public class getData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String MYSQL_DBNAME = "db1";
	public static final String MYSQL_USN = "root";
	public static final String MYSQL_PWD = "supasit1983+";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		PrintWriter out = response.getWriter();
		out.println("<h2>/getData</h2>");

		try {
			
			// connect to MySQL DB
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/" + MYSQL_DBNAME + "?characterEncoding=utf-8", MYSQL_USN, MYSQL_PWD);  

			// send query to MySQL DB
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"select * from table1"
					);  // retrieve entire dataset from Table1

			// check and show results
			if(rs!=null) {
				out.println("ID" + "&emsp;&emsp;" + "JSON Object<br>"); 
				out.println("-------------------------------<br>"); 
				while(rs.next())
					out.println(rs.getString(1) + "&emsp;&emsp;" + rs.getString(2) + "<br>");  
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