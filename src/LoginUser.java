

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		
		try {
			ServletContext sc = request.getServletContext();
			String driverClass = sc.getInitParameter("driverClass");
			String oracleUrl = sc.getInitParameter("oracleUrl");
			
			Class.forName(driverClass);
			Connection conn = DriverManager.getConnection(oracleUrl,"ibm","ibm");
			
			
			PreparedStatement ps=conn.prepareStatement("select * from customerDetails where Username=? and Password=?");
			ps.setString(1, username);
			ps.setString(2, password);
			
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{     
				HttpSession session = request.getSession();
				
				session.setAttribute("username", username);
				session.setAttribute("password", password);
				session.setAttribute("accountNumber", rs.getInt("accountNumber"));
				session.setAttribute("firstName",rs.getString("FirstName"));
				session.setAttribute("lastName",rs.getString("LastName"));
				session.setAttribute("address",rs.getString("Address"));
				session.setAttribute("email",rs.getString("Email"));
				session.setAttribute("mobile",rs.getString("PhoneNumber"));
				
				RequestDispatcher rd= request.getRequestDispatcher("home.html");
				rd.include(request,response);
				
				pw.println("Login Success \n");
				session.setAttribute("name", rs.getString("FirstName") + " "+ rs.getString("LastName"));
				pw.println("welcome "+rs.getString("FirstName") + " " + rs.getString("LastName"));
			}
			else {
				System.out.println("login failed");
				pw.print("login fail");
				request.getRequestDispatcher("loginUser.html").include(request, response);
			}

		}
		catch (Exception e) 
		{
			System.out.println(e);
		}
	}

}
