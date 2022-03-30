import java.sql.*;

public class Hello {
    public static void main(String[] args) throws SQLException {
        String connectionUrl = "jdbc:mysql://localhost:3306/new_schema";
        String username = "root";
        String password = "root";
        Connection connection = DriverManager.getConnection(connectionUrl, username, password);
        Statement ps = connection.createStatement();

        //  PERFORM QUERY TO READ DATA
        ResultSet rs = ps.executeQuery("SELECT * FROM customers");
        while (rs.next()) {
            System.out.println(rs.getString("first_name"));
        }

        System.out.println("----------------------------------------------------");

        // PERFORM QUERY TO WRITE DATA
        //PreparedStatement psw = connection.prepareStatement("INSERT INTO `project_v2` (`description`, `end_date`) VALUES (?, ?);");
        //psw.setString(1, "alabala");
       // psw.setInt(2, 123);
        //psw.execute();


        //  getById
        PreparedStatement gbi = connection.prepareStatement("SELECT * FROM `customers` WHERE `id` = 2");
        ResultSet rs1 = gbi.executeQuery();
        while (rs1.next()) {
            System.out.println(rs1.getString("username"));
        }

        //  getAll
        PreparedStatement gai = connection.prepareStatement("SELECT * FROM `customers`");
        ResultSet rs2 = gai.executeQuery();
        while (rs2.next()) {
            System.out.println(rs2.getString("username"));
        }

        //  update
        PreparedStatement up = connection.prepareStatement("UPDATE `customers` SET `username` = 'Hello Kitty' WHERE `id` = 2");
        up.execute();

        //  insert
        insert(new Customer(3, "Ana", "Blandiana", "Ana", "07431312", "Str Eroilor",
                "Ploiesti", "325400", "Romania"));

        //  delete
        PreparedStatement del = connection.prepareStatement("DELETE FROM `customers` WHERE `id` = 3");
        del.execute();


        //  add orders
        PreparedStatement addOrders = connection.prepareStatement("INSERT INTO `orders` (`id`, `order_date`, `shipped_date`," +
                " `status`, `comments`, `customer_id` ) VALUES (?, ?, ?, ?, ?, ?);");
        addOrders.setInt(1, 1);
        addOrders.setDate(2, Date.valueOf("2019-03-01"));
        addOrders.setDate(3, Date.valueOf("2019-03-02"));
        addOrders.setString(4, "shipped");
        addOrders.setString(5, "You ordered a lot of stuff");
        addOrders.setInt(6, 1);
        addOrders.execute();


        //  add a new order for an existing customer
        PreparedStatement addOrders2 = connection.prepareStatement("INSERT INTO `orders` (`id`, `order_date`, `shipped_date`," +
                " `status`, `comments`, `customer_id` ) VALUES (?, ?, ?, ?, ?, ?);");
        addOrders2.setInt(1, 2);
        addOrders2.setDate(2, Date.valueOf("2010-05-01"));
        addOrders2.setDate(3, Date.valueOf("2010-05-02"));
        addOrders2.setString(4, "shipped");
        addOrders2.setString(5, "You ordered a new product");
        addOrders2.setInt(6, 2);
        addOrders2.execute();

        //  view all orders for an existing customer
        PreparedStatement viewOrders = connection.prepareStatement("SELECT * FROM `orders` WHERE `customer_id` = 1");
        ResultSet rs3 = viewOrders.executeQuery();
        while (rs3.next()) {
            System.out.println(rs3.getString("comments"));
        }

        //  update the status of an existing order
        PreparedStatement updateOrder = connection.prepareStatement("UPDATE `orders` SET `status` = 'shipped' WHERE `id` = 1");
        updateOrder.execute();

        //  add comments to an existing order
        PreparedStatement addComments = connection.prepareStatement("UPDATE `orders` SET `comments` = 'succesfull' WHERE `id` = 2");
        addComments.execute();

        //  update the stock for the products
        PreparedStatement updateStock = connection.prepareStatement("UPDATE `products` SET `stock` = `stock` - 1 WHERE `code` = 1");
        updateStock.execute();

    }

    public static void insert(Customer customer) throws SQLException {
        String connectionUrl = "jdbc:mysql://localhost:3306/new_schema";

        String username = "root";
        String password = "root";
        Connection connection = DriverManager.getConnection(connectionUrl, username, password);

        PreparedStatement psw = connection.prepareStatement("INSERT INTO `customers` (`id`, `username`, `last_name`," +
                " `first_name`, `phone`, `address`, `city`, `postalCode`, `country`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
        psw.setInt(1, customer.getID());
        psw.setString(2, customer.getUsername());
        psw.setString(3, customer.getLast_name());
        psw.setString(4, customer.getFirst_name());
        psw.setString(5, customer.getPhone());
        psw.setString(6, customer.getAddress());
        psw.setString(7, customer.getCity());
        psw.setString(8, customer.getPostalCode());
        psw.setString(9, customer.getCountry());
        psw.execute();
    }
}
