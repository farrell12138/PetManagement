package com.example.petmanagement;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class UserDao {
    private static final String TAG = "mysql-party-UserDao";

    public User login(String userAccount, String userPassword) {
        User user = null;
        HashMap<String, Object> map = new HashMap<>();
        // 根据数据库名称，建立连接
        Connection connection = JDBCUtils.getConn();
        try {
            // mysql简单的查询语句。这里是根据user表的userAccount字段来查询某条记录
            String sql = "select * from user where name = ?";
            if (connection != null) {// connection不为null表示与数据库建立了连接
                PreparedStatement ps = connection.prepareStatement(sql);
                if (ps != null) {
                    Log.e(TAG, "账号：" + userAccount);
                    //根据账号进行查询
                    ps.setString(1, userAccount);
                    // 执行sql查询语句并返回结果集
                    ResultSet rs = ps.executeQuery();
                    int count = rs.getMetaData().getColumnCount();
                    //将查到的内容储存在map里
                    while (rs.next()) {
                        // 注意：下标是从1开始的
                        for (int i = 1; i <= count; i++) {
                            String field = rs.getMetaData().getColumnName(i);
                            map.put(field, rs.getString(field));
                        }
                    }

                    connection.close();
                    ps.close();

                    if (map.size() != 0) {
                        StringBuilder s = new StringBuilder();
                        //寻找密码是否匹配
                        for (String key : map.keySet()) {
                            if (key.equals("password")) {
                                if (userPassword.equals(map.get(key))) {
                                    //注意：下标是从1开始
                                    int id = Integer.parseInt((String) map.get("id"));
                                    String userName = (String) map.get("name");
                                    String password = (String) map.get("password");
                                    String phone = (String) map.get("phone");
                                    String address = (String) map.get("address");

                                    user = new User(id, userName, password, phone, address);
                                }
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "异常login：" + e.getMessage());
        }
        return user;
    }

}
