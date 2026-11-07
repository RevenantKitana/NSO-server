package com.nsoz2.fake;

import com.nsoz2.constants.SQLStatement;
import com.nsoz2.db.jdbc.DbManager;
import com.nsoz2.server.Config;
import com.nsoz2.server.NinjaSchool;
import com.nsoz2.server.Server;
import com.nsoz2.util.Log;
import com.nsoz2.util.NinjaUtils;
import com.nsoz2.util.StringUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class FakeData {
    public static void main(String[] args) {

        try {

                if (!DbManager.start()) {
                    return;
                }
                Connection connection = DbManager.getConnection();
                PreparedStatement stmt = connection.prepareStatement(SQLStatement.FAKE_USER);


                for (int i = 1; i <= 10; i++) { // chạy từ id 51 tới 100
                    String username = "" + i;  // user
                    String otp = generateRandomOTP();
                    String phoneNumber = generateRandomPhoneNumber();
                    stmt.setString(1, username);
                    stmt.setString(2, StringUtils.genPass(""));  // pass
                    stmt.setString(3, "");
//                    stmt.setString(4, phoneNumber);
                    stmt.setString(4, "");
                    stmt.setInt(5, 1); // kích hoạt
                    stmt.executeUpdate();

                }
                stmt.close();
                connection.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String generateRandomUsername() {
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder username = new StringBuilder();

        for (int i = 0; i < 7; i++) {
            int index = NinjaUtils.nextInt(characters.length());
            username.append(characters.charAt(index));
        }

        return username.toString();
    }

    private static String generateRandomOTP() {
        Random rand = new Random();
        int otp = 100000 + rand.nextInt(900000); // Random 6-digit OTP without leading zeros
        return String.valueOf(otp);
    }

    private static String generateRandomPhoneNumber() {
        int prefix = 300000000 + NinjaUtils.nextInt(700000000); // Random 10-digit phone number starting with 03
        return "0" + prefix;
    }
}
