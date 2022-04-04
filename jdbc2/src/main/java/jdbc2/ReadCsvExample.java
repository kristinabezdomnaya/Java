package jdbc2;

import com.opencsv.bean.CsvToBeanBuilder;
import util.FileUtil;

import java.io.StringReader;
import java.util.List;

public class ReadCsvExample {

    public static void main(String[] args) {
        String contents = FileUtil.readFileFromClasspath("us-sample-data.csv");

        List<Employee> employeeList = new CsvToBeanBuilder<Employee>(
                new StringReader(contents)).withType(Employee.class)
                .build().parse();

        System.out.println(employeeList);

    }


}
