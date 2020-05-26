
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/api/TrackingServlet")
public class TrackingServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
      
        String product = request.getParameter("product");
        
        HttpSession session = request.getSession(false);
        ArrayList<String> viewed = (ArrayList<String>)session.getAttribute("viewed"); 
//        response.getWriter().write(viewed.toString());
        if(viewed.size() == 5){
            viewed.remove(0);
            viewed.add(product);
        }
        else{
            viewed.add(product);
        }
        session.setAttribute("viewed", viewed);
        JsonArray products = new JsonArray();
        
        String URL = "jdbc:mysql://localhost:3307/jnah_shop";
        String USER = "root";
        String PASSWORD = "";
        
        for(int i = 0; i < viewed.size(); i++){
            JsonObject jo = new JsonObject();
            jo.addProperty("name", viewed.get(i));
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection dbcon = DriverManager.getConnection(URL, USER, PASSWORD);
                String query = "select * from cloth where name = ?";
                PreparedStatement stmt = dbcon.prepareStatement(query);
                stmt.setString(1, viewed.get(i));
                ResultSet rs = stmt.executeQuery();

                if(rs.next()){
                    Integer id = rs.getInt("id");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    String imgHref = rs.getString("imgHref");
                    String price = rs.getString("price");
                    String quantity = rs.getString("quantity");
                    
                    jo.addProperty("id", id);
                    jo.addProperty("name", name);
                    jo.addProperty("description", description);
                    jo.addProperty("imgHref", imgHref);
                    jo.addProperty("price", price);
                    jo.addProperty("quantity", quantity);
                    
                }
            
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TrackingServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(TrackingServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
           
            products.add(jo);
        }
        
        out.write(products.toString());
        out.close();
        response.setStatus(200);
    }
            
            
}
    



