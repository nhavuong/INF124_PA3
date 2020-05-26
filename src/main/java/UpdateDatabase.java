
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

@WebServlet(name = "UpdateDatabase", urlPatterns = "/UpdateDatabase")
public class UpdateDatabase extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {  
//        try {
//            String URL = "jdbc:mysql://localhost:3307/jnah_shop";
//            String USER = "root";
//            String PASSWORD = "";
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection dbcon = DriverManager.getConnection(URL, USER, PASSWORD);
//            Statement stmt = dbcon.createStatement();
//            stmt.executeUpdate("insert into order" + "values (1,1,1,1,1,1,1,1,11,1,1,1)");
//            dbcon.close();
            PrintWriter out = response.getWriter();
            RequestDispatcher rd = request.getRequestDispatcher("Confirmation");
            rd.forward(request, response);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(UpdateDatabase.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(UpdateDatabase.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}


