package jdbc2;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
public class ResponseData {

    private Integer draw;
    private Integer recordsFiltered;
    private Integer recordsTotal;
    private List<List<String>> data = new ArrayList<>();

    public ResponseData(List<Employee> employees, Integer token) {
        this.draw = token;
        data.addAll(employees.stream()
                .map(this::asStringList)
                .collect(Collectors.toList()));

        recordsFiltered = employees.size();
        recordsTotal = employees.size();
    }

    private List<String> asStringList(Employee employee) {
        return List.of(
                employee.getId().toString(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getCity());
    }
}
