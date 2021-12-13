package services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Seeder {

    public void isCreate() throws SQLException, IOException {
        Connection connection = ConnectionJbdc.getConnection();

        String query = "SELECT SCHEMA_NAME\n" +
                "FROM INFORMATION_SCHEMA.SCHEMATA\n" +
                "WHERE SCHEMA_NAME = 'litera'";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        boolean check = resultSet.next();

        if (!check) {
            createDb();
        }
    }

    public void createDb() throws SQLException, IOException {
        Connection connection = ConnectionJbdc.getConnection();

        connection.prepareStatement("CREATE SCHEMA litera").execute();
        connection.prepareStatement("USE litera").execute();

        connection.prepareStatement("""
                CREATE TABLE `city` (
                `id` int NOT NULL AUTO_INCREMENT,
                `name` varchar(35) DEFAULT NULL,
                PRIMARY KEY (`id`)
                );
                """).execute();

        connection.prepareStatement("""
                CREATE TABLE `interview` (
                `id` int NOT NULL AUTO_INCREMENT,
                `interview_date` datetime DEFAULT NULL,
                `interview_result` enum('REFUSED','PASSED','FAILED','') DEFAULT NULL,
                PRIMARY KEY (`id`)
                );
                """).execute();

        connection.prepareStatement("""
                CREATE TABLE `technology` (
                `id` int NOT NULL AUTO_INCREMENT,
                `name` varchar(20) DEFAULT NULL,
                PRIMARY KEY (`id`)
                );
                """).execute();

        connection.prepareStatement("""
                CREATE TABLE `candidate` (
                `id` int NOT NULL AUTO_INCREMENT,
                `name` varchar(50) NOT NULL,
                `surname` varchar(50) NOT NULL,
                `city_id` int DEFAULT NULL,
                `email` varchar(65) NOT NULL,
                `phone` varchar(50) NOT NULL,
                `source` enum('InMail','Facebook','Referral') NOT NULL,
                PRIMARY KEY (`id`),
                KEY `fk_city_candidates_idx` (`city_id`),
                CONSTRAINT `fk_city_candidates` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`)
                );
                """).execute();

        connection.prepareStatement("""
                CREATE TABLE `application` (
                `id` int NOT NULL AUTO_INCREMENT,
                `pre-selection_status` enum('LOW_SCORE','TO_BE_INVITED','OVERDUE','') DEFAULT NULL,
                `selection_result` enum('REFUSED','ACCEPTED','REJECTED','INVALID','') DEFAULT NULL,
                `candidate_id` int NOT NULL,
                `technology_id` int NOT NULL,
                `interview_id` int NOT NULL,
                PRIMARY KEY (`id`,`interview_id`),
                KEY `fk_candidates_application_idx` (`candidate_id`),
                KEY `fk_technologies_application_idx` (`technology_id`),
                KEY `fk_interview_application_idx` (`interview_id`),
                CONSTRAINT `fk_candidates_application` FOREIGN KEY (`candidate_id`) REFERENCES `candidate` (`id`),
                CONSTRAINT `fk_interview_application` FOREIGN KEY (`interview_id`) REFERENCES `interview` (`id`),
                CONSTRAINT `fk_technologies_application` FOREIGN KEY (`technology_id`) REFERENCES `technology` (`id`)
                );
                """).execute();
    }
}
