package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by uzzz on 28.03.14.
 */

public class Executor {
    public static <T> T executeQuery(Connection connection, ExecHandler<T> handler, String query, Object... vars) throws SQLException {
        PreparedStatement stm = connection.prepareStatement(query);
        for (int i = 0; i < vars.length; i++) {
            stm.setObject(i + 1, vars[i]);
        }
        ResultSet resultSet = stm.executeQuery();
        T value = handler.handle(resultSet);
        resultSet.close();
        stm.close();
        return value;
    }

    public static int executeUpdate(Connection connection, String query, Object... vars) throws SQLException {
        PreparedStatement stm = connection.prepareStatement(query);

        for (int i = 0; i < vars.length; i++) {
            stm.setObject(i + 1, vars[i]);
        }
        int affected = stm.executeUpdate();
        stm.close();
        return affected;
    }
}
