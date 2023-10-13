package poly.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import poly.bean.MEAL;
public class MEAL_DAO {

	  private Connection connection;

	     public MEAL_DAO() 
			// TODO Auto-generated constructor stub
		 throws SQLException {
	    	 try {
	    	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    	    } catch (ClassNotFoundException e) {
	    	        e.printStackTrace();
	    	    }
	        // Khởi tạo kết nối đến database
	        String url = "jdbc:sqlserver://ADMIN-PC:1435;databaseName=DEXUATAMTHUC;encrypt=false;trustServerCertificate=true;";
	        String username = "sa";
	        String password = "123";
	        connection = DriverManager.getConnection(url, username, password);
	    }

	    public ArrayList<MEAL> getAllMeal() throws SQLException {
	    	ArrayList<MEAL> Meal_List = new ArrayList<>();
	        // Thực hiện truy vấn để lấy tất cả các sản phẩm từ database
	        String query = "SELECT * FROM MONAN";
	        PreparedStatement statement = connection.prepareStatement(query);
	        ResultSet resultSet = statement.executeQuery();
	       
	        // Duyệt qua các dòng kết quả trả về và tạo đối tượng Product từ mỗi dòng
	        while (resultSet.next()) 
	        {
	            int maMon = resultSet.getInt("IDMONAN");
	            String tenMon = resultSet.getString("TENMON");
	            String hinhAnh = resultSet.getString("HINHANH");
	            String moTa = resultSet.getString("MOTA");	           	            	            	          
	            MEAL meal = new MEAL(maMon,tenMon,hinhAnh,moTa);
	            Meal_List.add(meal);
	        }
	        return Meal_List;
	    }
	    
	    // hàm thêm một khách hàng vào cơ sở dữ liệu
//	    public void addCustomer(KhachHang khachHang) throws SQLException {
//	        String query = "INSERT INTO KhachHang (fullName, email, phone,accountName,pass) VALUES (?, ?, ?,?,?)";
//	        try (PreparedStatement statement = connection.prepareStatement(query)) {
//	            statement.setString(1, khachHang.getFullName());
//	            statement.setString(2, khachHang.getEmail());
//	            statement.setString(3, khachHang.getPhone());
//	            statement.setString(4, khachHang.getAccountName());
//	            statement.setString(5, khachHang.getPassword());
//	            statement.executeUpdate();
//	        }
//	    }
//	    
//	    public void updateKH(KhachHang kh) throws SQLException {
//	        String query = "UPDATE KhachHang SET DiachiNH = ? WHERE makh=?"; 
//	        try (PreparedStatement statement = connection.prepareStatement(query)) {
//	            statement.setString(1, kh.getDiachiNH());
//	            statement.setInt(2, kh.getMaKH());
//	     
//	            statement.executeUpdate();
//	        }
//	    }
//
//	    public void close() throws SQLException {
//	        // Đóng kết nối đến database
//	        if (connection != null) {
//	            connection.close();
//	        }
//	    }


}
