package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao {
    public MySqlCategoryDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();

        String sql = "Select * from categories;";
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rows = statement.executeQuery(sql);
            while (rows.next()) {
                categories.add(mapRow(rows));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;
        // get all categories
    }

    @Override
    public Category getById(int categoryId) {
        String query = "SELECT * FROM Categories WHERE category_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, categoryId);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Category create(Category category) {
        // create a new category
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("Insert into Categories(null,'category_id','name','description')" +
                     " values(?,?,?);")) {
            statement.setInt(1, category.getCategoryId());
            statement.setString(2, category.getName());
            statement.setString(3, category.getDescription());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void update(int categoryId, Category category) {
        // update category
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("Update categories set name = '?', description = '?' where category_id = ?;")) {

            statement.setString(1, category.getName());
            statement.setString(2, category.getDescription());
            statement.setInt(3, categoryId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0){
                System.out.println("No category was updated");
            }else {
                System.out.println("Category was updated : rows affected =" + rowsAffected);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int categoryId) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("Delete from categories where 'category_id' = ?")) {
            statement.setInt(1, categoryId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No category found");
            } else System.out.println("Category deleted");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Category mapRow(ResultSet row) throws SQLException {
        int categoryId = row.getInt("category_id");
        String name = row.getString("name");
        String description = row.getString("description");

        Category category = new Category() {{
            setCategoryId(categoryId);
            setName(name);
            setDescription(description);
        }};

        return category;
    }

}
