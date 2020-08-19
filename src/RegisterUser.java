

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String address = request.getParameter("address");
		String mobileNo = request.getParameter("mobileNo");
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		try {
			ServletContext sc = request.getServletContext();
			String driverClass = sc.getInitParameter("driverClass");
			String oracleUrl = sc.getInitParameter("oracleUrl");

			Class.forName(driverClass);
			Connection conn = DriverManager.getConnection(oracleUrl,"ibm","ibm");


			PreparedStatement pstmt = conn.prepareStatement("insert into customerDetails values(accountNumber_seq.nextval,?,?,?,?,?,?,?)");
			pstmt.setString(1, firstName);
			pstmt.setString(2, lastName);
			pstmt.setString(3, email);
			pstmt.setString(4, address);
			pstmt.setString(5, mobileNo);
			pstmt.setString(6, username);
			pstmt.setString(7, password);

			int v = pstmt.executeUpdate();
			if(v >= 0) {
				System.out.println("Value Inserted");
				RequestDispatcher rd = request.getRequestDispatcher("loginUser.html");
				rd.include(request, response);
				out.print("User Registered successfully");
			}else {
				System.out.println("Values not inserted");
				RequestDispatcher rd = request.getRequestDispatcher("index.html");
				rd.include(request, response);
				out.print("ERROR: User not Registered");
			}
			//conn.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

}
