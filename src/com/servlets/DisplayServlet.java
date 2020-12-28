package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.main.Products;


@WebServlet("/DisplayServlet")
public class DisplayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		int productid = Integer.parseInt(request.getParameter("pid"));


		try {
			Class.forName("org.h2.Driver");
			Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
			String sql = "select * from Products";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			boolean productFound = false;
			Products pd = new Products();
			while (rs.next()) {
				if (productid == rs.getInt(1)) {
					pd.setPid(productid);
					pd.setPname(rs.getString(2));
					pd.setPcategory(rs.getString(3));
					pd.setPquantity(rs.getString(4));
					productFound = true;
					
					break;
				}
			}
			
			
			if (productFound) {
				// response.getWriter().println(". Product:");
				PrintWriter out = response.getWriter();
				out.println("<h2 align='center'>Product List</h2>");
				out.print("<table border='1' width='100%'");
				out.print("<tr><th>ProductId</th><th>Product Name</th><th>Product Category</th><th>Product Quantity</th></tr>");
				out.print("<tr><td>"+pd.getPid()+"</td><td>"+pd.getPname()+"</td><td>"+pd.getPcategory()+"</td><td>"+pd.getPquantity()+"</td> </tr>");
				out.print("<a href='index.html'>Go Back to Main Page</a>");
				out.print("<br/>");
			} else {
				response.getWriter().println("\nProduct not found in Database");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
