package db;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by uzzz on 28.03.14.
 */

public interface ExecHandler<T> {
    public T handle(ResultSet res) throws SQLException;
}