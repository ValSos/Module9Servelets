import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;

@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest req,
                         HttpServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {
        resp.setContentType("text/html; charset=utf-8");
        String authHeaderValue = req.getHeader("timezone");
        String timezone = req.getParameter("timezone");
        if (timezone == null) {
            chain.doFilter(req, resp);
        } else if (isValidTimezone(timezone.replace(" ", "+"))) {
            chain.doFilter(req, resp);
        } else {
            resp.setContentType("text/html; charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid timezone");
            return;
        }

    }

    public static boolean isValidTimezone(String timezone) {
        try {
            ZoneId.of(timezone);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
