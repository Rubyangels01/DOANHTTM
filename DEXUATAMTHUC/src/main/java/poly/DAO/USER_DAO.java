package poly.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import poly.bean.USER;


public class USER_DAO {
	private Connection connection;

	public USER_DAO() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // Hoặc bạn có thể xử lý ngoại lệ theo cách khác tùy vào yêu cầu của bạn
            throw new SQLException("Không tìm thấy driver SQLServer");
        }

        // Khởi tạo kết nối đến database
        String url = "jdbc:sqlserver://ADMIN-PC:1435;databaseName=DEXUATAMTHUC;encrypt=false;trustServerCertificate=true;";
        String username = "sa";
        String password = "123";
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            // Hoặc bạn có thể xử lý ngoại lệ theo cách khác tùy vào yêu cầu của bạn
            throw new SQLException("Không thể kết nối đến cơ sở dữ liệu");
        }
    }


   

public ArrayList<USER> LISTUSER() throws SQLException {
   	ArrayList<USER> LIST_USERS = new ArrayList<>();
       // Thực hiện truy vấn để lấy tất cả các sản phẩm từ database
       String query = "SELECT * FROM USERS";
       PreparedStatement statement = connection.prepareStatement(query);
       ResultSet resultSet = statement.executeQuery();
       
       // Duyệt qua các dòng kết quả trả về và tạo đối tượng Product từ mỗi dòng
       while (resultSet.next()) 
       {
           int maUser = resultSet.getInt("ID_ND");
           String name_user = resultSet.getString("TENND");
           String email = resultSet.getString("EMAIL");                
           String pass = resultSet.getString("PASSWORD");
           
           USER user = new USER(maUser,name_user,email,pass);
          
           LIST_USERS.add(user);
       }
       return LIST_USERS;
   }
}
