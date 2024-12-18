# Capstone 3 E-Commerce-Shopping
____________________________
This is the 3rd and final capstone in my series of which I demonstrate how I've 
learned to implement Java and this time JDBC SQL and other methods to make a project.
You will need Java 17 and Spring boot to run the backend for this program. I used a local
host for the front end of this program via VScode.
---
Favorite Code Block
```
public List<Product> search(Integer categoryId, BigDecimal minPrice, BigDecimal maxPrice, String color)
    {
        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM products " +
                "WHERE (category_id = ? OR ? = -1) " +
                "   AND (price >= ? OR ? = -1) " +
                "   AND (price <= ? OR ? = -1) " +
                "   AND (color = ? OR ? = '') ";

        categoryId = categoryId == null ? -1 : categoryId;
        minPrice = minPrice == null ? new BigDecimal("-1") : minPrice;
        maxPrice = maxPrice == null ? new BigDecimal("-1") : maxPrice;
        color = color == null ? "" : color;

        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, categoryId);
            statement.setInt(2, categoryId);
            statement.setBigDecimal(3, minPrice);
            statement.setBigDecimal(4, minPrice);
            statement.setBigDecimal(5, maxPrice);
            statement.setBigDecimal(6, maxPrice);
            statement.setString(7, color);
            statement.setString(8, color);

            ResultSet row = statement.executeQuery();

            while (row.next())
            {
                Product product = mapRow(row);
                products.add(product);
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return products;
    }
```
This piece was my favorite because I spent only a few seconds before noticing the bug
.When tested in postman I knew it was that piece because I kept getting above and below 
what was expected. Line 6 originally didn't have a line 7 and was just `price <= ?` so it 
was searching the wrong parameters not to mention line 12 repeated min price which I also
fixed.
---



