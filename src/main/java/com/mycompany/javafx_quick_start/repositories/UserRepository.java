package com.mycompany.javafx_quick_start.repositories;

import com.mycompany.javafx_quick_start.models.UserModel;
import com.mycompany.javafx_quick_start.utils.db.DbSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements CrudRepository<UserModel, Long> {
    private final DbSource db = DbSource.getInstance();

    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    @Override
    public int create(UserModel user) {
        String sql = "INSERT INTO users (username, password, access_level) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getAccessLevel());

            logger.info("Creating new user...");
            return stmt.executeUpdate();
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                logger.warn("Error Unique constraint when creating data on users table occurred.");
                return -1;
            }
            logger.error("Error when creating user, message: " + e.getMessage());
            for (StackTraceElement el : e.getStackTrace()) {
                logger.error(el.toString());
            }
            return 0;
        }
    }

    @Override
    public List<UserModel> findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        ResultSet resultSet;
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            List<UserModel> userList = new ArrayList<>();

            stmt.setLong(1, id);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                UserModel user = new UserModel();
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setAccessLevel(resultSet.getString("access_level"));
                userList.add(user);
            }

            logger.info("Getting users data...");
            resultSet.close();
            return userList;

        } catch (SQLException e) {
            logger.error("Error when getting users data, message: " + e.getMessage());
            for (StackTraceElement el : e.getStackTrace()) {
                logger.error(el.toString());
            }
            return null;
        }
    }

    public UserModel findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        ResultSet resultSet;
        UserModel user = new UserModel();
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setString(1, username);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setAccessLevel(resultSet.getString("access_level"));
            }

            logger.info("Getting users data...");
            resultSet.close();
            return user;

        } catch (SQLException e) {
            logger.error("Error when getting users data, message: " + e.getMessage());
            for (StackTraceElement el : e.getStackTrace()) {
                logger.error(el.toString());
            }
            return null;
        }
    }

    @Override
    public List<UserModel> findAll() {
        String sql = "SELECT * FROM users";
        try (ResultSet resultSet = db.executeQuery(sql)) {
            List<UserModel> userList = new ArrayList<>();

            while (resultSet.next()) {
                UserModel user = new UserModel();
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setAccessLevel(resultSet.getString("access_level"));
                userList.add(user);

            }

            logger.info("Getting users data...");
            return userList;

        } catch (SQLException e) {
            logger.error("Error when getting users data, message: " + e.getMessage());
            for (StackTraceElement el : e.getStackTrace()) {
                logger.error(el.toString());
            }
            return null;
        }
    }

    @Override
    public int update(UserModel user) {
        String sql = "UPDATE users SET username = ?, password = ?, access_level = ? WHERE id = ?";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getAccessLevel());
            stmt.setLong(4, user.getId());

            logger.info("Updating users data...");
            return stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error when updating users with id: " + user.getId() + ", Message: " + e.getMessage());
            for (StackTraceElement el : e.getStackTrace()) {
                logger.error(el.toString());
            }
            return 0;
        }
    }

    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setLong(1, id);

            logger.info("Deleting users data...");
            return stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error when deleting users, id: " + id + ", Message: " + e.getMessage());
            for (StackTraceElement el : e.getStackTrace()) {
                logger.error(el.toString());
            }
            return 0;
        }
    }
}
