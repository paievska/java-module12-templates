package servlets;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.TimeZone;

@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        String timezoneParam = req.getParameter("timezone");

        HttpServletResponse httpResponse = (HttpServletResponse) res;

        if (TimeZone.getTimeZone(timezoneParam).getID().equals("UTC")) {
            httpResponse.setContentType("text/html");
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = res.getWriter();
            out.println("<html>");
            out.println("<body>");
            out.println("<h1>ERROR 400: Invalid timezone</h1>");
            out.println("</body></html>");
        } else {
            chain.doFilter(req, res);
        }
    }
}
