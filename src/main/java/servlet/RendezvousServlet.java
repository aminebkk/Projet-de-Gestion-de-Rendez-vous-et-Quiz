package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@MultipartConfig
@WebServlet(name="rendezvous", urlPatterns={"/rendezvous"})
public class RendezvousServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        // Get form parameters
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String dateTime = request.getParameter("dateTime");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // SQL statement to insert data
            String sql = "INSERT INTO rendezvous (title, description, date_time) VALUES (?, ?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, title);
                statement.setString(2, description);
                statement.setString(3, dateTime);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Print confirmation message to the user
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Rendez-vous Information</h1>");
        out.println("<ul>");
        out.println("<li>Title: " + title + "</li>");
        out.println("<li>Description: " + description + "</li>");
        out.println("<li>Date and Time: " + dateTime + "</li>");
        out.println("</ul>");
        out.println("<p>Rendez-vous successfully added to the database!</p>");
        out.println("</body></html>");
    }
}
