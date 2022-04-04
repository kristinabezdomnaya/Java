package jdbc2;

import util.ConfigUtil;
import util.DevDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Tester {

    public static void main(String[] args) {

        DataSource dataSource = new DevDataSource(ConfigUtil.readConnectionInfo());

        String sql = "select 42";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new IllegalStateException("should have one row");
            }

            System.out.println(rs.getString(1));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

}
