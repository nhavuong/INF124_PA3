
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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;


//, urlPatterns = "/api/loaddata"
@WebServlet(name = "TaxData", urlPatterns = "/TaxData")
public class TaxData extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PrintWriter out = response.getWriter();
               
        try{
            String URL = "jdbc:mysql://localhost:3307/jnah_shop";
            String USER = "root";
            String PASSWORD = "";

            Class.forName("com.mysql.jdbc.Driver");
            
            Connection dbcon = DriverManager.getConnection(URL, USER, PASSWORD);

            
            JsonArray respArray = new JsonArray();
            
            int zipcode =  Integer.parseInt(request.getParameter("zipcode"));
            String query = "SELECT * FROM tax_rates WHERE ZipCode = ?";
            PreparedStatement pre = dbcon.prepareStatement(query);
            pre.setInt(1, zipcode);
            ResultSet rs = pre.executeQuery();
            
            while(rs.next()){
                double taxRate = rs.getDouble("CombinedRate");

                JsonObject jObject = new JsonObject();
                jObject.addProperty("CombinedRate", taxRate);

                respArray.add(jObject);
            }            
            dbcon.close();
            out.write(respArray.toString());
            response.setStatus(200);
        } catch (SQLException ex) {
            Logger.getLogger(LoadDataServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.setStatus(500);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoadDataServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.setStatus(500);
        }
            
        out.close();
    }
     
}


