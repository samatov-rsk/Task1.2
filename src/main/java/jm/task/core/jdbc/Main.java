package jm.task.core.jdbc;

//TODO убрать импорт
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userDao = new UserServiceImpl();
        userDao.createUsersTable();
        //TODO где логика??
    }
}
