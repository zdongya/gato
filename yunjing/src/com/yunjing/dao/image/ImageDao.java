package com.yunjing.dao.image;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.yunjing.entity.image.Image;

import sun.misc.BASE64Decoder;



/** 
 * @author dongya
 * @version 创建时间：2012-3-31 下午11:02:02 
 * 类说明 
 */
public class ImageDao {
	
	private DataSource dataSource;
	private Connection connection;
	
	public void save(Image image) throws Exception {
		byte[] photo = BASE64Decoder.class.newInstance().decodeBuffer(image.getBase64Image().replace(" ",""));
		connection = this.getConnection();
		this.setConnection(connection);
		image.setPhoto(photo);
		connection.setAutoCommit(false);
		PreparedStatement ps = null ;
		String sql = "insert into odb_Image values(?,?)";
		try {
			ps = this.getConnection().prepareStatement(sql);
			ps.setString(1, image.getCardNo());
			ps.setBytes(2, image.getPhoto());
			ps.execute();
			connection.commit();
		} catch (SQLException e) {
			
			e.printStackTrace();
			connection.rollback();
		}finally{
			if(null!=ps)
				ps.close();
			if(null!=connection)
				connection.close();
		}
		
	}
	
	public Image getImageByCardNo() throws SQLException{
		Image image = null;
		connection = this.getConnection();
		this.setConnection(connection);
		PreparedStatement ps = null ;
		ResultSet rs = null;
		String sql = "select * from odb_Image where cardNo=?";
		ps = connection.prepareStatement(sql);
		ps.setString(1, "412829198804126815");
		rs = ps.executeQuery();
		if(rs.next()){
			image = new Image();
			String cardNo = rs.getString("cardNo");
			byte[] photo = rs.getBytes("photo");
			image.setCardNo(cardNo);
			image.setPhoto(photo);
		}
		return image;
	}

	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		
	}

	public Connection getConnection() throws SQLException{
		return dataSource.getConnection();
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}



	
	
	

}
