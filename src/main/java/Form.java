import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author monke
 */
@WebServlet(urlPatterns = "/Form")
public class Form extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        // Write the response message, in an HTML page
      try {
         out.println("<!DOCTYPE HTML>\n" +
                            "<html>\n" +
                            "    <head>\n" +
                            "        <title>JNAH</title>\n" +
                            "        <link rel=\"stylesheet\" href=\"form.css\" />\n" +
                            "        <link rel=\"stylesheet\" href=\"main.css\" />\n" +
                            "        <link rel=\"icon\" href=\"picture/logo-JNAH.PNG\">\n" +
                            "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                            "        <meta name=\"description\" content=\"Building a dynamic website using HTML, JavaScript, and CSS\">\n" +
                            "        <meta name=\"author\" content=\"Jonathan Tran, Nina Vuong, Anh Nguyen, Huy Luong\">\n" +
                            "    </head>\n" +
                            "    <body style=\"text-align: left;\">\n" +
                            "         <nav id=\"main-nav\">\n" +
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
                            "            <div id=\"toggle\" class=\"toggle-menu\" style=\"list-style-type:none;\">\n" +
                            "                <ul>\n" +
                            "                <li><a href=\"home.html\">Home</a></li>\n" +
                            "                <li><a href=\"home.html#products\">Products</a></li>\n" +
                            "                <li><a href=\"form.html\">Order</a></li>\n" +
                            "                <li><a href=\"home.html#about-us\">About Us</a></li>\n" +
                            "                <li><a href=\"home.html#contact-us\">Contact Us</a></li>\n" +
                            "                </ul>\n" +
                            "            </div>\n" +
                            "        </nav>\n" +
                            "\n" +
                            "        <!-- ORDER FORM -->\n" +
                            "        <div class=\"container wrapper\" style=\"text-align: left;\">\n" +
                            "            <div class=\"row\">\n" +
                            "                    <div class=\"card col-12\" id=\"sumary\">\n" +
                            "                        <div class=\"card-title\">\n" +
                            "                            <h2>Summary</h2>\n" +
                            "                            <img id=\"productImage\"></img>\n" +
                            "                            <p><strong>Items added: </strong><span id=\"itemName\"></span></p>\n" +
                            "                            <p><strong>Quantity: </strong><span id=\"itemQuantity\"></span></p>\n" +
                            "                            <p><strong>Price: </strong><span id=\"itemPrice\"></span></p>\n" +
                            "                            <p><strong>Shipping: </strong><span id=\"shippingMethod\"></span></p>\n" +
                            " <p><strong>Tax: </strong><span id=\"tax\"></span></p>" +
                            "                            <p><strong>Total: </strong><span id=\"total\"></span></p>\n" +
                            "                        </div>\n" +
                            "                    </div>\n" +
                            "            </div>\n" +
                            "        </div>\n" +
                            "\n" +
                            "\n" +
                            "        <div class=\"container\">\n" +
                            "        <div class=\"row form-container\">\n" +
                            "            <div class=\"col-10 title\">\n" +
                            "                <h2>Order Form</h2>\n" +
                            "            \n" +
                            "           <form class=\"form\" id=\"form\" method=\"get\" action=\"UpdateDatabase\" onsubmit=\"return valiData();\">\n" +
                            "                    <div class=\"row\">\n" +
                            "                        <div class=\"col-12 form-control\">\n" +
                            "                            <label for=\"firstName\">First Name</label>\n" +
                            "                            <input id=\"firstName\" type=\"text\" placeholder=\"Your first name\" name=\"firstName\">\n" +
                            "                            <i class=\"fas fa-check-circle\"></i>\n" +
                            "                            <i class=\"fas fa-exclamation-circle\"></i>\n" +
                            "                            <small>Error message</small>\n" +
                            "                        </div>\n" +
                            "                        <div class=\"col-12 form-control\">\n" +
                            "                            <label for=\"lastName\">Last Name</label>\n" +
                            "                            <input id=\"lastName\" type=\"text\" placeholder=\"Your last name\" name=\"lastName\">\n" +
                            "                            <i class=\"fas fa-check-circle\"></i>\n" +
                            "                            <i class=\"fas fa-exclamation-circle\"></i>\n" +
                            "                            <small>Error message</small>\n" +
                            "                        </div>  \n" +
                            "                        <div class=\"col-12 form-control\">\n" +
                            "                            <label for=\"phone\">Phone Number</label>\n" +
                            "                            <input id=\"phone\" type=\"number\" placeholder=\"xxx-xxx-xxxx\" name=\"phone\">\n" +
                            "                            <i class=\"fas fa-check-circle\"></i>\n" +
                            "                            <i class=\"fas fa-exclamation-circle\"></i>\n" +
                            "                            <small>Error message</small>\n" +
                            "                        </div>\n" +
                            "                        <div class=\"col-12 form-control\">\n" +
                            "                            <label for=\"email\">Email</label>\n" +
                            "                            <input id=\"email\" type=\"text\" placeholder=\"abc@abc.com\" name=\"email\">\n" +
                            "                            <i class=\"fas fa-check-circle\"></i>\n" +
                            "                            <i class=\"fas fa-exclamation-circle\"></i>\n" +
                            "                            <small>Error message</small>\n" +
                            "                        </div>\n" +
                            "                        <div class=\"col-12 form-control\">\n" +
                            "                            <label for=\"address\">Shipping Address</label>\n" +
                            "                            <input id=\"address\" type=\"text\" placeholder=\"Ex: 123 Bison Ave\" name=\"address\">\n" +
                            "                            <i class=\"fas fa-check-circle\"></i>\n" +
                            "                            <i class=\"fas fa-exclamation-circle\"></i>\n" +
                            "                            <small>Error message</small>\n" +
                            "                        </div>\n" +
                            "                        <div class=\"col-6 form-control\">\n" +
                            "                            <label for=\"city\">City</label>\n" +
                            "                            <input id=\"city\" type=\"text\" placeholder=\"Type in City\" name=\"city\">\n" +
                            "                            <i class=\"fas fa-check-circle\"></i>\n" +
                            "                            <i class=\"fas fa-exclamation-circle\"></i>\n" +
                            "                            <small>Error message</small>\n" +
                            "                        </div>\n" +
                            "                        <div class=\"col-6 form-control\">\n" +
                            "                            <label for=\"state\">State</label>\n" +
                            "                            <input id=\"state\" type=\"text\" placeholder=\"Type in State\" name=\"state\">\n" +
                            "                            <i class=\"fas fa-check-circle\"></i>\n" +
                            "                            <i class=\"fas fa-exclamation-circle\"></i>\n" +
                            "                            <small>Error message</small>\n" +
                            "                        </div>\n" +
                            "                        <div class=\"col-6 form-control\">\n" +
                            "                            <label for=\"zipcode\">Zip Code</label>\n" +
                            "                            <input id=\"zipcode\" type=\"number\" placeholder=\"Ex: 92626\" name=\"zipcode\"> \n" +
                            "                            <i class=\"fas fa-check-circle\"></i>\n" +
                            "                            <i class=\"fas fa-exclamation-circle\"></i>\n" +
                            "                            <small >Error message</small>\n" +
                            "                            <div id='zipcode-error'></div>\n" +
                            "                        </div>                        \n" +
                            " <div class=\"col-6 form-control\" id=\"zipcode-button\" onmouseover=\"getInfoFromZipcode()\">Get Tax from zip code</div>" +
                            "                        <div class=\"col-12 form-control\">\n" +
                            "                            <label>Shipping Method</label>\n" +
                            "                            <div class=\"option\">\n" +
                            "                                <select id=\"option\" class=\"shipping-option\" onchange=\"getShippingPrice()\">\n" +
                            "                                    <option value=\"5\">6 Days Ground - $5</option>\n" +
                            "                                    <option value=\"10\">2 Days Expedited - $10</option>\n" +
                            "                                    <option value=\"30\">Overnight - $30</option>\n" +
                            "                                </select>\n" +
                            "                            </div>\n" +
                            "                        </div>\n" +
                            "                    </div>\n" +
                            "                    <br><br><hr><br><br>\n" +
                            "                    <div class=\"card-info\">\n" +
                            "                        <h2>Card Infomation</h2>\n" +
                            "                        <div class=\"row\">\n" +
                            "                            <div class=\"col-12 form-control\">\n" +
                            "                                <label for=\"nameOnCard\">Name On Card</label>\n" +
                            "                                <input id=\"nameOnCard\" type=\"text\" placeholder=\"Full Name Displayed on the Card\" name=\"nameOnCard\">\n" +
                            "                                <i class=\"fas fa-check-circle\"></i>\n" +
                            "                                <i class=\"fas fa-exclamation-circle\"></i>\n" +
                            "                                <small>Error message</small>\n" +
                            "                            </div>\n" +
                            "                            <div class=\"col-12 form-control\">\n" +
                            "                                <label for=\"cardNo\">Card Number</label>\n" +
                            "                                <input id=\"cardNo\" type=\"number\" placeholder=\"Ex: 9999 9999 9999 9999\" name=\"cardNo\">\n" +
                            "                                <i class=\"fas fa-check-circle\"></i>\n" +
                            "                                <i class=\"fas fa-exclamation-circle\"></i>\n" +
                            "                                <small>Error message</small>\n" +
                            "                            </div>\n" +
                            "                            <div class=\"col-4 form-control\">\n" +
                            "                                <label for=\"exp\">Expiration Date</label>\n" +
                            "                                <input id=\"exp\" type=\"text\" placeholder=\"mm/yy\" pattern=\"((0[6-9]|1[0-2])/20)|((0[1-9]|1[0-2])/(2[1-9]|3[0-9]))\" name=\"exp\">\n" +
                            "                                <i class=\"fas fa-check-circle\"></i>\n" +
                            "                                <i class=\"fas fa-exclamation-circle\"></i>\n" +
                            "                                <small>Error message</small>\n" +
                            "                            </div>\n" +
                            "                            <div class=\"col-3 form-control\">\n" +
                            "                                <label for=\"cvv\">CVV</label>\n" +
                            "                                <input id=\"cvv\" type=\"number\" placeholder=\"Ex: 123\" name=\"cvv\">\n" +
                            "                                <i class=\"fas fa-check-circle\"></i>\n" +
                            "                                <i class=\"fas fa-exclamation-circle\"></i>\n" +
                            "                                <small>Error message</small>\n" +
                            "                            </div>\n" +
                            "                        </div>\n" +
                            "                    </div>\n" +
                            "                    <div class=\"form-button col-12\">\n" +
                            "                        <button id=\"submit-button\" type=\"submit\" >Submit</button>\n" +
                            "                    </div>\n" +
                            "                </form>\n" +
                            "            </div>\n" +
                            "        </div>  \n" +
                            "        </div>\n" +
                            "\n" +
                            "        \n" +
                            "\n" +
                            "        <!-- JavaScript -->\n" +
                            "        <script src=\"https://kit.fontawesome.com/3a7f96f49f.js\" crossorigin=\"anonymous\"></script>\n" +
                            "        <script src=\"js/form.js\"></script>\n" +
                            "        <script src=\"js/main.js\"></script>\n" +
                            "\n" +
                            "    </body>\n" +
                            "</html>\n" +
                            "\n");

        } finally {
           out.close();  // Always close the output writer
        }
      
    }          
}