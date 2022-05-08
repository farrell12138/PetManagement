package com.example.petmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class FosterInfoDao {
    public ArrayList<FosterInfo> getAllFosterInfo(String user_id) {
        ArrayList<FosterInfo> fosterList = new ArrayList<>();
        ResultSet res = null;
        // 根据数据库名称，建立连接
        Connection connection = JDBCUtils.getConn();
        try {
            // mysql简单的查询语句。这里是根据user表的userAccount字段来查询某条记录
            String sql = "SELECT `user`.id user_id, pet.`name` pet_name,foster.id foster_id, " +
                    "foster.pet_id pet_id, foster.duration, foster.bathing_time, foster.total_cost, " +
                    "foster.health, foster.activity from `foster` INNER JOIN " +
                    "(`user` LEFT JOIN pet ON `pet`.user_id = `user`.id) on foster.pet_id = pet.id WHERE user.id = ?;";
            if (connection != null) {// connection不为null表示与数据库建立了连接
                PreparedStatement ps = connection.prepareStatement(sql);
                if (ps != null) {
                    ps.setString(1, user_id);
                    // 执行sql查询语句并返回结果集
                    res = ps.executeQuery();
                    int count = res.getMetaData().getColumnCount();
                    while (res.next()) {
                        int fosterId = res.getInt("foster_id");
                        int petId = res.getInt("pet_id");
                        String petName = res.getString("pet_name");
                        int duration = res.getInt("duration");
                        int bathingTime = res.getInt("bathing_time");
                        int totalCost = res.getInt("total_cost");
                        String health = res.getString("health");
                        String activity = res.getString("activity");

                        FosterInfo info = new FosterInfo(fosterId, petId, petName, duration, bathingTime, totalCost, health, activity);
                        fosterList.add(info);
                    }

                    connection.close();
                    ps.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fosterList;
    }


    public ArrayList<Pet> getAllPetInfo(String userId) {
        ArrayList<Pet> petList = new ArrayList<>();
        ResultSet res = null;
        // 根据数据库名称，建立连接
        Connection connection = JDBCUtils.getConn();
        try {
            // mysql简单的查询语句。这里是根据user表的userAccount字段来查询某条记录
            String sql = "SELECT foster.id foster_id, user.id user_id, pet.id pet_id, pet.name pet_name FROM " +
                    "foster RIGHT JOIN (user LEFT JOIN pet ON user.id = pet.user_id) on " +
                    "foster.pet_id = pet.id WHERE user.id = ?;";
            if (connection != null) {// connection不为null表示与数据库建立了连接
                PreparedStatement ps = connection.prepareStatement(sql);
                if (ps != null) {
                    ps.setString(1, userId);
                    // 执行sql查询语句并返回结果集
                    res = ps.executeQuery();
                    while (res.next()) {
                        int fosterId = res.getInt("foster_id");
                        int petId = res.getInt("pet_id");
                        if (petId == 0) {
                            break;
                        }
                        String petName = res.getString("pet_name");

                        Pet info = new Pet(petId, petName, fosterId != 0);
                        petList.add(info);
                    }

                    connection.close();
                    ps.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return petList;
    }

    public int addPetInfo(String userId, String petName, String petSex, String petAge, String petBreed) {
        int msg = 0;
        // 根据数据库名称，建立连接
        Connection connection = JDBCUtils.getConn();
        try {
            // mysql简单的查询语句。这里是根据user表的userAccount字段来查询某条记录
            String sql = "INSERT INTO pet (user_id, name, sex, age, breed) VALUES (?, ?, ?, ?, ?);";
            if (connection != null) {// connection不为null表示与数据库建立了连接
                PreparedStatement ps = connection.prepareStatement(sql);
                if (ps != null) {
                    ps.setString(1, userId);
                    ps.setString(2, petName);
                    ps.setString(3, petSex);
                    ps.setString(4, petAge);
                    ps.setString(5, petBreed);
                    ps.executeUpdate();

                    connection.close();
                    ps.close();
                    msg = 2;
                }
            }
        } catch (Exception e) {
            msg = 1;
            e.printStackTrace();
        }
        return msg;
    }
}
