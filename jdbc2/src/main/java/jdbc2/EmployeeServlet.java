package jdbc2;

import com.fasterxml.jackson.databind.ObjectMapper;
import util.FileUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/employees")
public class EmployeeServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");

        var sampleData = List.of(
                new Employee(1L, "Alice", "Smith", "London"),
                new Employee(2L, "Bob", "Smith", "London"));

        DataTableParams input = readParameters(request);

        System.out.println("Parameters from DataTables ajax query: "  + input);

        ResponseData responseData = new ResponseData(sampleData, input.getToken());

        new ObjectMapper().writeValue(response.getOutputStream(), responseData);
    }

    @Override
    public void doPost(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        System.out.println("got POST request with: " +
                FileUtil.readStream(request.getInputStream()));
    }

    @Override
    protected void doPut(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {

        System.out.println("got PUT request with: " +
                FileUtil.readStream(request.getInputStream()));

    }

    @Override
    public void doDelete(HttpServletRequest request,
                            HttpServletResponse response) throws IOException {

        System.out.println("got delete request for id " +
                request.getParameter("id"));
    }

    private DataTableParams readParameters(HttpServletRequest request) {
        DataTableParams params = new DataTableParams();
        params.setOrderColumnNr(parseInt(request.getParameter("order[0][column]"), 0));
        params.setToken(parseInt(request.getParameter("draw"), 0));
        params.setPageStart(parseInt(request.getParameter("start"), 0));
        params.setPageLength(parseInt(request.getParameter("length"), 0));
        params.setSearchString(request.getParameter("search[value]"));
        params.setOrderDirection(request.getParameter("order[0][dir]"));

        return params;
    }

    private Integer parseInt(String input, int defaultValue) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
