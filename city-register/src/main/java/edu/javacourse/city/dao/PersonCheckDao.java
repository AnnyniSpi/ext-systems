package edu.javacourse.city.dao;

import edu.javacourse.city.domain.PersonRequest;
import edu.javacourse.city.domain.PersonResponse;
import edu.javacourse.city.exception.PersonCheckException;

import java.sql.*;

public class PersonCheckDao {

    private static final String SQL_REQUEST = """
            SELECT temporal
            FROM cr_address_person ap
            JOIN cr_person p ON p.person_id = ap.person_id
            JOIN cr_address a ON a.address_id = ap.address_id
            WHERE CURRENT_DATE >= ap.start_date and (CURRENT_DATE <= ap.end_date  OR ap.end_date IS NULL) and
            p.sur_name ILIKE ? and p.given_name ILIKE ? and p.patronymic ILIKE ? and p.date_of_birth = ?
            and a.street_code = ? and a.building ILIKE ? 
            """;

    public PersonResponse checkPerson(PersonRequest request) throws PersonCheckException {
        PersonResponse response = new PersonResponse();

        String sql = SQL_REQUEST;
        if (request.getExtension() != null){
            sql += "and a.extension ILIKE ? ";
        } else {
            sql += "and a.extension is NULL ";
        }
        if (request.getApartment() != null){
            sql += "and a.apartment ILIKE ? ";
        } else {
            sql += "and a.apartment is null ";
        }

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            int count = 1;
            preparedStatement.setString(count++, request.getSurName());
            preparedStatement.setString(count++, request.getGivenName());
            preparedStatement.setString(count++, request.getPatronymic());
            preparedStatement.setDate(count++, java.sql.Date.valueOf(request.getDateOfBirth()));
            preparedStatement.setInt(count++, request.getStreetCode());
            preparedStatement.setString(count++, request.getBuilding());
            if (request.getExtension() != null){
                preparedStatement.setString(count++, request.getExtension());
            }
            if (request.getApartment() != null){
                preparedStatement.setString(count++, request.getApartment());
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                response.setRegistered(true);
                response.setTemporal(resultSet.getBoolean("temporal"));
            }
        }catch (SQLException e){
            throw new PersonCheckException(e);
        }

        return response;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/city_register",
                "postgres", "postgres");
    }
}
