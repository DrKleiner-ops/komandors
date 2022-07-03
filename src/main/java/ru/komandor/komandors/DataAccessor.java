package ru.komandor.komandors;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DataAccessor {
	private static final DataAccessor da = new DataAccessor("jdbc:postgresql://localhost:5432/cashtest", "Cashtest", "123");
	private Connection connection;

	private DataAccessor(String dBUrl, String usr, String pass) {
		try {
			connection = DriverManager.getConnection(dBUrl, usr, pass);

		} catch (SQLException ignored) {
		}
	}

	public static DataAccessor getDataAccessor() {
		return da;
	}

	public void checks(int id, Float sum) {
		try {
			Statement stmnt;
			stmnt = connection.createStatement();
			String sql = "INSERT INTO TASKS (ID,TITLE,DESCRIPTION,DATE) "
					+ "VALUES " + "(" + id + ", "
					+ Date.valueOf(LocalDate.now()) + ", "
					+ Time.valueOf(LocalTime.now()) + ", "
					+ sum + ")";
			stmnt.executeUpdate(sql);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void checklines(int goodsId, int line, int count, float sum) {
		try {
			Statement stmnt;
			stmnt = connection.createStatement();
			String sql = "INSERT INTO TASKS (ID,TITLE,DESCRIPTION,DATE) "
					+ "VALUES " + "(" + goodsId + ", "
					+ line + ", "
					+ count + ", "
					+ sum + ")";
			stmnt.executeUpdate(sql);


		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public List<Goods> getGoods() throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("select * from cashtest.goods");

		List<Goods> goodsList = new ArrayList<>();

		while (resultSet.next()) {
			int goodsId = resultSet.getInt("id");
			String goodsName = resultSet.getString("name");
			float goodsCoast = resultSet.getFloat("coast");
			Goods goods = new Goods(goodsId, goodsName, goodsCoast);
			goodsList.add(goods);
		}
		return goodsList;
	}


}
