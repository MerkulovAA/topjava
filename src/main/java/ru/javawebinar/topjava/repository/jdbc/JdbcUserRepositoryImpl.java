package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update(
                "UPDATE users SET name=:name, email=:email, password=:password, " +
                        "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
            return null;
        }
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", user.getId());
        insertBatch(user);
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        return setRolesInUser(DataAccessUtils.singleResult(users));
    }


    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        return setRolesInUser(DataAccessUtils.singleResult(users));
    }

    @Override
    public List<User> getAll() {
        List<Map<String, Object>> queryResult = jdbcTemplate.queryForList("SELECT * FROM user_roles");
        Map<Integer, Set<Role>> mapIdUserToRoles = new HashMap<>();
        queryResult.forEach(stringObjectMap -> mapIdUserToRoles.computeIfAbsent((Integer) stringObjectMap.get("user_id"),
                userId -> EnumSet.noneOf(Role.class))
                .add(Role.valueOf((String) stringObjectMap.get("role"))));
        List<User> allUsers = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
        allUsers.forEach(user -> user.setRoles(mapIdUserToRoles.get(user.getId())));
        return allUsers;
    }


    private User setRolesInUser(User user) {
        if (user != null) {
            List<Role> userRole = jdbcTemplate.query("SELECT role FROM user_roles WHERE user_id=?",
                    new SingleColumnRowMapper<>(Role.class), user.getId());
            user.setRoles(userRole);
        }
        return user;
    }

    private void insertBatch(User user) {
        if (user != null) {
            final List<Role> roles = new ArrayList<>(user.getRoles());

            String sql = "INSERT INTO user_roles " +
                    "(role, user_id) VALUES (?, ?)";

            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Role role = roles.get(i);
                    ps.setString(1, role.name());
                    ps.setInt(2, user.getId());
                }
                @Override
                public int getBatchSize() {
                    return roles.size();
                }
            });
        }
    }
}
