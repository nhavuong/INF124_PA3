
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
        try {
            String URL = "jdbc:mysql://localhost:3307/jnah_shop";
            String USER = "root";
            String PASSWORD = "";
            Class.forName("com.mysql.jdbc.Driver");
            Connection dbcon = DriverManager.getConnection(URL, USER, PASSWORD);
//            Statement stmt = dbcon.createStatement();
//            stmt.executeUpdate("insert into order" + "values (1,1,1,1,1,1,1,1,11,1,1,1)");
//            dbcon.close();
            PreparedStatement stmt = dbcon.prepareStatement("INSERT INTO jnah_shop.order(OrderID, ClothID, FirstName, LastName, PhoneNumber, Email, ShippingAddress, City, State, Zipcode, ShippingMethod, Tax, Quantity, TotalPrice, CardLast4Digit, CardExpDate ) values (0,'3',?,?,?,?,?,?,?,?,?,'0','10','20.20',?,?)");
            
            stmt.setString(1, request.getParameter("firstName")); 
            stmt.setString(2, request.getParameter("lastName")); 
            stmt.setString(3, request.getParameter("phone")); 
            stmt.setString(4, request.getParameter("email")); 
            stmt.setString(5, request.getParameter("address")); 
            stmt.setString(6, request.getParameter("city")); 
            stmt.setString(7, request.getParameter("state")); 
            stmt.setString(8, request.getParameter("zipcode")); 
            String shippingValue = request.getParameter("shippingvalue");
            if(shippingValue.equals("5")){
                stmt.setString(9, "6 Days Ground" ); 
            }
            else if(shippingValue.equals("10")){
                stmt.setString(9, "2 Days Expedited" ); 
            }
            else{
                stmt.setString(9, "Overnight" ); 
            }          
            
            stmt.setInt(10, Integer.valueOf(request.getParameter("cardNo").substring(12))); 
            stmt.setString(11, request.getParameter("exp")); 
            
            stmt.executeUpdate();
            
            //Get id of last inserted record
            ResultSet rs = null;
            int autoIncKeyFromFunc = -1;
            rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");

            if (rs.next()) {
                autoIncKeyFromFunc = rs.getInt(1);
            } else {
                // throw an exception from here
            }
            
            stmt.close();
            dbcon.close();
                        
            PrintWriter out = response.getWriter();
            RequestDispatcher rd = request.getRequestDispatcher("Confirmation");
            request.setAttribute("ID", autoIncKeyFromFunc);
            rd.forward(request, response);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UpdateDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UpdateDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


