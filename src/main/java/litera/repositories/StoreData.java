package repositories;

import services.ApplicationModel;

import java.io.IOException;
import java.sql.SQLException;

public interface StoreData {

    void storeAllDataToMysql() throws SQLException, IOException;

    void storeCity(ApplicationModel applications, int i) throws SQLException;

    void storeCandidate(ApplicationModel applications, int i) throws SQLException;

    void storeTechnology(ApplicationModel applications, int i) throws SQLException;

    void storeInterview(ApplicationModel applications, int i) throws SQLException;

    void storeApplication(ApplicationModel applications, int i) throws SQLException;
}
