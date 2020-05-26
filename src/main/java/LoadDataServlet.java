
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
@WebServlet(name = "/LoadDataServlet", urlPatterns = "/api/loaddata")
public class LoadDataServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        ArrayList<String> viewed = new ArrayList<String>();
//        request.setAttribute("viewed", viewed);
        
//        RequestDispatcher rd = request.getRequestDispatcher("/api/TrackingServlet");
//        rd.include(request, response);
//        
        
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        
        ArrayList<String> viewed = (ArrayList<String>)session.getAttribute("viewed");
        if(viewed == null) {
            viewed = new ArrayList<String>();
            session.setAttribute("viewed", viewed);
        }
        ArrayList<String> added = (ArrayList<String>)session.getAttribute("added");
        if(added == null) {
            added = new ArrayList<String>();
            session.setAttribute("added", added);
        }
        
        
        try{
            String URL = "jdbc:mysql://localhost:3307/jnah_shop";
            String USER = "root";
            String PASSWORD = "";

            Class.forName("com.mysql.jdbc.Driver");
            
            Connection dbcon = DriverManager.getConnection(URL, USER, PASSWORD);

            
            JsonArray respArray = new JsonArray();
            
            
            String query = "select * from cloth";
            Statement stmt = dbcon.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String imgHref = rs.getString("imgHref");
                String price = rs.getString("price");
                String quantity = rs.getString("quantity");

                JsonObject jObject = new JsonObject();
                jObject.addProperty("id", id);
                jObject.addProperty("name", name);
                jObject.addProperty("description", description);
                jObject.addProperty("imgHref", imgHref);
                jObject.addProperty("price", price);
                jObject.addProperty("quantity", quantity);
                jObject.addProperty("viewed", "false");

                respArray.add(jObject);
            }

//            for(int i =0; i < viewed.size(); i++){
//                JsonObject jo = new JsonObject();
//                jo.addProperty("name", viewed.get(i));
//                try{
//                    String queryVisisted = "select * from cloth where name = ?";
//                    PreparedStatement stmt1 = dbcon.prepareStatement(queryVisisted);
//                    stmt1.setString(1, viewed.get(i));
//                    ResultSet rs1 = stmt1.executeQuery();
//
//                    if(rs1.next()){
//                        Integer id = rs1.getInt("id");
//                        String name = rs1.getString("name");
//                        String description = rs1.getString("description");
//                        String imgHref = rs1.getString("imgHref");
//                        String price = rs1.getString("price");
//                        String quantity = rs1.getString("quantity");
//
//                        jo.addProperty("id", id);
//                        jo.addProperty("name", name);
//                        jo.addProperty("description", description);
//                        jo.addProperty("imgHref", imgHref);
//                        jo.addProperty("price", price);
//                        jo.addProperty("quantity", quantity);
//                        jo.addProperty("viewed", "true");
//                    }
//          
//                } catch (SQLException ex) {
//                    Logger.getLogger(TrackingServlet.class.getName()).log(Level.SEVERE, null, ex);
//                }
            //}
            
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


