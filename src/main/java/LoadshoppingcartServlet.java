
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/api/loadshoppingcart"})
public class LoadshoppingcartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ArrayList<String> added = (ArrayList<String>) session.getAttribute("added");
        PrintWriter out = response.getWriter();
        
        JsonArray ja = new JsonArray();
        for(int i = 0; i < added.size(); i++){
            JsonObject jo = new JsonObject();
            jo.addProperty("product", added.get(i));
            ja.add(jo);
        }
        out.write(ja.toString());
        out.close();
        response.setStatus(200);
    }
}
