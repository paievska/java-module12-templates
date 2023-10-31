package servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        String timezone = req.getParameter("timezone");

        if (timezone == null) {
            timezone = "UTC";
        }
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(timezone));
        String formattedTime = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
        out.println("<html>");
        out.println("<body>");
        out.println("<h1>" + formattedTime + "</h1>");
        out.println("</body></html>");
    }
}
