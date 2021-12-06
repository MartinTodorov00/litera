package run;

import parse_data.ApplicationModel;
import parse_data.ParseCsv;
import store_data.StoreToMysql;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        ParseCsv parseCsv = new ParseCsv();
        ApplicationModel applications = parseCsv.parseCsv();

        StoreToMysql storeToMysql = new StoreToMysql();
        storeToMysql.storeAllDataToMysql(applications);
    }
}
