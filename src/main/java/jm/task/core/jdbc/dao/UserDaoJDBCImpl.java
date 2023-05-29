package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//TODO соблюдай отступы везде!!!
public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {//TODO убрать , он есть по умолчанию

    }

    public void createUsersTable() {
        String create = "CREATE TABLE IF NOT EXISTS USERSTABLE" +
                "(id INTEGER NOT NULL AUTO_INCREMENT," +
                "name VARCHAR(100) not null, " +
                "lastname VARCHAR(100) not null, " +
                "age INTEGER (128) not null, " +
                "PRIMARY KEY(id))";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(create);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String drob = "DROP TABLE IF EXISTS USERSTABLE"; //TODO dpob?
        try (Statement statement = Util.getConnection().createStatement();) {
            statement.execute(drob);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        name = user.getName(); //TODO лишняя переменная
        lastName = user.getLastName();//TODO лишняя переменная
        age = user.getAge();//TODO лишняя переменная
        String save = "INSERT INTO USERSTABLE (name, lastname, age) VALUES(?,?,?)";
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(save)) {
            //TODO лишний отступ кажется
            preparedStatement.setString(1, name); //TODO можно тут вызывать геттер
            preparedStatement.setString(2, lastName);//TODO можно тут вызывать геттер
            preparedStatement.setByte(3, age);//TODO можно тут вызывать геттер

            preparedStatement.executeUpdate();
            //TODO лишний отступ кажется
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String remove = "DELETE FROM USERSTABLE WHERE id=?";
        User user = new User(id);
        id = user.getId();//TODO лишняя переменная

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(remove)) {
            preparedStatement.setLong(1, id);//TODO можно тут вызывать геттер
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String get = "SELECT id, name, lastname, age from USERSTABLE";//TODO таблица должна называеть users или user
        try (Statement statement = Util.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(get); //TODO переменная get должна называться логически правильно, например getAllQuery
            while(resultSet.next()){
                User user = new User(); //TODO зачем создавать тут юзеров, этот метод предназначен только для чтения таблицы?
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(userList); //TODO убрать
        return userList;
    }

    public void cleanUsersTable() {
        String clean = "TRUNCATE USERSTABLE"; //TODO cleanUsers
        try(PreparedStatement preparedStatement = Util.getConnection().prepareStatement(clean)){
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}