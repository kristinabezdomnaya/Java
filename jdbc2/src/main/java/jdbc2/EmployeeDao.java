package jdbc2;

import javax.sql.DataSource;

public class EmployeeDao {

    private DataSource dataSource;

    public EmployeeDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
