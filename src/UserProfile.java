

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class UserProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();

			request.getRequestDispatcher("userProfile.html").include(request, response);

			HttpSession session = request.getSession(false);

			if(session!=null) {
				String name = (String) session.getAttribute("name");
				if(!name.equals("")||name!=null) {
					out.println("<b>Welcome to profile, "+ name + "</b><br>");
					out.println("Name: " + (String)session.getAttribute("name") + "<br>");
					out.println("Address: " + (String)session.getAttribute("address") + "<br>");
					out.println("Email: " + (String)session.getAttribute("email") + "<br>");
					out.println("Mobile Number: " + (String)session.getAttribute("mobile") + "<br>");
				}
			}
			else {
				out.print("Please Login");
				request.getRequestDispatcher("login.html").include(request, response);
			}
			out.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

}
