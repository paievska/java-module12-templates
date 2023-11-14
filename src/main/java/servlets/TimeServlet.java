package servlets;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {
    private TemplateEngine engine;

    @Override
    public void init() throws ServletException {
        engine = new TemplateEngine();
        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix("C:\\Users\\Diana\\java-module11-tomcat\\templates\\");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(engine.getTemplateResolvers().size());
        resolver.setCacheable(false);
        engine.addTemplateResolver(resolver);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String timezone = req.getParameter("timezone");
        if (timezone == null || timezone.isEmpty()) {
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("lastTimezone".equals(cookie.getName())) {
                        timezone = cookie.getValue();
                        break;
                    }
                }
            }

            if (timezone == null || timezone.isEmpty()) {
                timezone = "UTC";
            }
        } else {
            Cookie cookie = new Cookie("lastTimezone", timezone);
            resp.addCookie(cookie);
        }
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(timezone));
        String formattedTime = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
        Context context = new Context();
        context.setVariable("formattedTime", formattedTime);

        engine.process("test", context, resp.getWriter());
        resp.getWriter().close();
    }
}
