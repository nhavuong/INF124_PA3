
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


@WebServlet(urlPatterns = "/Confirmation")
public class Confirmation extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        
        out.println("Hello from AnotherServlet");
        
        // Write the response message, in an HTML page
        try {
            String customerName ="";
            String phoneNumber = "";
            String address = "";
            String method = "";
            String item = "";
            float price = 0;
            int quantity = 0;
            float subtotal = 0;
            String shipping = "";
            float tax = 0;
            float total = 0;
            String cardNumber = "";
            String expireDate = "";
            String billingAddress = "";
            
            String URL = "jdbc:mysql://localhost:3307/jnah_shop";
            String USER = "root";
            String PASSWORD = "";
            Class.forName("com.mysql.jdbc.Driver");
            Connection dbcon = DriverManager.getConnection(URL, USER, PASSWORD);
            
            int ID = (int) request.getAttribute("ID");
            
            String query = "SELECT * FROM jnah_shop.cloth, jnah_shop.order WHERE jnah_shop.order.OrderID=? AND jnah_shop.cloth.id=jnah_shop.order.ClothID";
            PreparedStatement pre = dbcon.prepareStatement(query);
            pre.setInt(1, ID);
            ResultSet rs = pre.executeQuery();
            
            while(rs.next()){
                customerName = rs.getString("LastName") + " , " + rs.getString("FirstName");
                phoneNumber = rs.getString("PhoneNumber");
                address = rs.getString("ShippingAddress")+ ", " + rs.getString("City") + ", " + rs.getString("State") + " " + rs.getString("Zipcode");
                method = rs.getString("ShippingMethod");
                item = rs.getString("name");
                price = rs.getFloat("price");
                quantity = Integer.parseInt(rs.getString("Quantity"));
                subtotal = price * quantity;
                if (method.equals("6 Days Ground")){
                  shipping = "5";
                }
                else if (method.equals("2 Days Expedited")){
                  shipping = "10";
                }
                else {
                  shipping = "30";
                }

                tax = rs.getFloat("Tax");
                total = rs.getFloat("TotalPrice");
                cardNumber = "xxxx-xxxx-xxxx-" + rs.getString("CardLast4Digit");
                expireDate= rs.getString("CardExpDate");
                billingAddress = address;
            }
            
            dbcon.close();
           
            out.println("<!DOCTYPE HTML>\n" +
                        "<html>\n" +
                        "    <head>\n" +
                        "        <title>JNAH</title>\n" +
                        "        <link rel=\"stylesheet\" href=\"main.css\" />\n" +
                        "        <link rel=\"icon\" href=\"picture/logo-JNAH.PNG\">\n" +
                        "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "        <meta name=\"description\" content=\"Building a dynamic website using HTML, JavaScript, and CSS\">\n" +
                        "        <meta name=\"author\" content=\"Jonathan Tran, Nina Vuong, Anh Nguyen, Huy Luong\">\n" +
                        "        \n" +
                        "    </head>\n" +
                        "    <body>\n" +
                        "        <!-- NAVBAR -->\n" +
                        "        <nav id=\"main-nav\">\n" +
                        "            <div class=\"row\">\n" +
                        "                <div class=\"container\">\n" +
                        "                    <div class=\"logo\">\n" +
                        "                        <a href=\"home.html\"><img src=\"picture/logo.PNG\" alt=\"logo\"></a>\n" +
                        "                    </div>\n" +
                        "\n" +
                        "                    <ul class=\"nav-menu\" style=\"list-style-type:none;\" id=\"navbar\">\n" +
                        "                        <li><a href=\"home.html\">Home</a></li>\n" +
                        "                        <li><a href=\"home.html#products\">Products</a></li>\n" +
                        "                        <li><a href=\"form.html\">Order</a></li>\n" +
                        "                        <li><a href=\"home.html#about-us\">About Us</a></li>\n" +
                        "                        <li><a href=\"home.html#contact-us\">Contact Us</a></li>\n" +
                        "                        <li> <a href=\"javascript:void(0);\" class=\"nav-icon\" onclick=\"navbar()\">\n" +
                        "                            <i class=\"fa fa-bars\"></i> </a></li>\n" +
                        "                    </ul>\n" +
                        "                </div>\n" +
                        "            </div>\n" +
                        "            <ul id=\"toggle\" class=\"toggle-menu\" style=\"list-style-type:none;\">\n" +
                        "                <li><a href=\"home.html\">Home</a></li>\n" +
                        "                <li><a href=\"home.html#products\">Products</a></li>\n" +
                        "                <li><a href=\"form.html\">Order</a></li>\n" +
                        "                <li><a href=\"home.html#about-us\">About Us</a></li>\n" +
                        "                <li><a href=\"home.html#contact-us\">Contact Us</a></li>\n" +
                        "            </ul>\n" +
                        "        </nav>\n" +
                        "        \n" +
                        "        <!-- PRODUCTS DETAIL -->\n" +
                        "        <div class=\"container\">\n" +
                        "            <section class=\"wrapper confirmation\">                \n" +
                        "                <div class=\"row\">     \n" +
                        "                    <div class=\"col-12\">\n" +
                        "                        <h2>Thank you for your order!</h2>\n" +
                        "                        <h6> We've recieved your order, here are the details.</h6>\n" +
                        "                        <hr>\n" +
                        "                    </div>\n" +
                        "                </div> \n" +
                        "                <div class=\"row body\">\n" +
                        "                    <div class=\"col-12\">\n" +
                        "                        <h3> Delivery Details </h3> \n" +
                        "                    </div>\n" +
                        "                    <div class=\"col-6\">\n" +
                        "                        <h4> Delivery for </h4>\n" +
                        "                        <h5> Customer: <span id=\"customerName\">" + customerName + "</span></h5>\n" +
                        "                        <h5> Phone Number: <span id=\"phoneNumber\">"+ phoneNumber +"</span></h5>\n" +
                        "                        <h5> Address: <span id=\"address\">"+ address +"</span></h5>\n" +
                        "                    </div>     \n" +
                        "                    <div class=\"col-6\">\n" +
                        "                        <h4> Delivery Method </h4>\n" +
                        "                        <h5><span id=\"method\">"+ method +"</span></h5>\n" +
                        "                    </div>\n" +
                        "                    <div class=\"col-12\">\n" +
                        "                        <hr>\n" +
                        "                    </div>\n" +
                        "                </div>\n" +
                        "                <div class=\"row body\">\n" +
                        "                    <div class=\"col-12\">\n" +
                        "                        <h3> Order Summary </h3> \n" +
                        "                    </div>\n" +
                        "                    <div class=\"col-6\">\n" +
                        "                        <h4> Item Ordered </h4>\n" +
                        "                        <h5> Item: <span id=\"item\">"+ item +"</span></h5>\n" +
                        "                        <h5> Price: <span id=\"price\">"+ price +"</span></h5>\n" +
                        "                        <h5> Total Quantity: <span id=\"quantity\">"+ quantity +"</span></h5>\n" +
                        "                    </div>     \n" +
                        "                    <div class=\"col-6\">\n" +
                        "                        <h4> Receipt Information </h4>\n" +
                        "                        <h5> Subtotal: $<span id=\"subtotal\">"+ subtotal +"</span></h5>\n" +
                        "                        <h5> Shipping: $<span id=\"shipping\">" + shipping +"</span></h5>\n" +
                        "                        <h5> Tax: $<span id=\"tax\">"+ tax +"</span></h5>\n" +
                        "                        <h5> Total: $<span id=\"total\">"+ total +"</span></h5>\n" +
                        "                    </div>\n" +
                        "                    <div class=\"col-12\">\n" +
                        "                        <hr>\n" +
                        "                    </div>\n" +
                        "                </div> \n" +
                        "                <div class=\"row body\">\n" +
                        "                    <div class=\"col-12\">\n" +
                        "                        <h3> Payment Information </h3> \n" +
                        "                    </div>\n" +
                        "                    <div class=\"col-6\">\n" +
                        "                        <h4> Credit Card </h4>\n" +
                        "                        <h5> Card Number: <span id=\"cardNumber\">"+ cardNumber +"</span></h5>\n" +
                        "                        <h5> Expiration Date: <span id=\"expireDate\">"+ expireDate +"</span></h5>\n" +
                        "                    </div>     \n" +
                        "                    <div class=\"col-6\">\n" +
                        "                        <h4> Billing Address </h4>\n" +
                        "                        <h5> <span id=\"billingAddress\">"+ billingAddress +"</span> </h5>\n" +
                        "                    </div>\n" +
                        "                </div> \n" +
                        "                <div class=\"row end\">\n" +
                        "                    <div class=\"col-12\">\n" +
                        "                        <a href=\"home.html\" class=\"button\">Go to Home Page</a>\n" +
                        "                    </div>\n" +
                        "                </div>                       \n" +
                        "            </section>\n" +
                        "        </div> \n" +
                        "        \n" +
                        "        <!-- FOOTER -->\n" +
                        "        <footer id=\"contact-us\">\n" +
                        "            <div class=\"row\">\n" +
                        "                <div class=\"col-5 col-s-5\">\n" +
                        "                    <div class=\"field\">\n" +
                        "                        <h4> ABOUT US </h4>\n" +
                        "                        <p> At JNAH, we never follow the trend, because we'd like to start the trend of the world. We promise to stay ahead of fashion trends,\n" +
                        "                            market demand, and latest technology to provide extraordinary goods that fit personal needs in\n" +
                        "                            this constant changing era. <i class=\"fas fa-heart\"></i></p>\n" +
                        "                    </div>\n" +
                        "                </div>\n" +
                        "                <div class=\"col-3 col-s-3\">\n" +
                        "                    <div class=\"field\">\n" +
                        "                        <h4>QUICK LINKS</h4>\n" +
                        "                        <h5><a href=\"home.html#products\"> Products </a></h5>\n" +
                        "                        <h5><a href=\"home.html#about-us\"> About us </a></h5>\n" +
                        "                    </div>\n" +
                        "                </div>\n" +
                        "                <div class=\"col-4 col-s-4\">\n" +
                        "                    <div class=\"field\">\n" +
                        "                        <h4> CONTACT US </h4>\n" +
                        "                        <h5> <i class=\"fas fa-door-open\"></i> Open Daily: 9am-9pm</h5>\n" +
                        "                        <h5> <i class=\"fas fa-map-marked-alt\"></i> 123456 Bison, Irvine, CA 92697</h5>\n" +
                        "                        <h5> <i class=\"fas fa-phone\"></i> Phone number: 714-999-9999</h5>\n" +
                        "                        <h5> <i class=\"fas fa-envelope\"></i> Email: jnahgroup@gmail.com</h5>\n" +
                        "                    </div>\n" +
                        "                </div>\n" +
                        "            </div>\n" +
                        "            <div class=\"row\" style=\"text-align: center;\">\n" +
                        "                <p>Copyright © 2020 All rights reserved | This page is made by JNAH</p>\n" +
                        "            </div>\n" +
                        "        </footer>\n" +
                        "\n" +
                        "    </body>\n" +
                        "</html>");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Confirmation.class.getName()).log(Level.SEVERE, null, ex);
             
        } catch (SQLException ex) {
            Logger.getLogger(Confirmation.class.getName()).log(Level.SEVERE, null, ex);
           
        } finally {
           out.close();  // Always close the output writer
        }
      
    }          
}
