package com.mdt168.mysticEnchantments.custom;

import com.mdt168.mysticEnchantments.MysticEnchantments;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

import java.io.File;
import java.sql.*;

public class DB {
    private static final String URL = "jdbc:sqlite:plugins/MysticEnchantments/placed_blocks.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void createTable() throws SQLException {
        try {
            File file = new File(MysticEnchantments.getDataFol(), "placed_blocks.db");
            File folder = file.getParentFile();
            if (!folder.exists()) {
                folder.mkdirs();
            }
            if (file.createNewFile() || file.exists()) {
                try (Connection conn = getConnection();
                     Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS player_placed_blocks (
                        world TEXT NOT NULL,
                        x INTEGER NOT NULL,
                        y INTEGER NOT NULL,
                        z INTEGER NOT NULL,
                        PRIMARY KEY (world, x, y, z)
                    );
                """);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void addBlock(Block block) {
        try (Connection conn = DB.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                INSERT OR IGNORE INTO player_placed_blocks (world, x, y, z) VALUES (?, ?, ?, ?)
            """);
            ps.setString(1, block.getWorld().getName());
            ps.setInt(2, block.getX());
            ps.setInt(3, block.getY());
            ps.setInt(4, block.getZ());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeBlock(Block block) {
        try (Connection conn = DB.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                DELETE FROM player_placed_blocks WHERE world = ? AND x = ? AND y = ? AND z = ?
            """);
            ps.setString(1, block.getWorld().getName());
            ps.setInt(2, block.getX());
            ps.setInt(3, block.getY());
            ps.setInt(4, block.getZ());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isPlayerPlaced(Block block) {
        try (Connection conn = DB.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                SELECT 1 FROM player_placed_blocks WHERE world = ? AND x = ? AND y = ? AND z = ?
            """);
            ps.setString(1, block.getWorld().getName());
            ps.setInt(2, block.getX());
            ps.setInt(3, block.getY());
            ps.setInt(4, block.getZ());

            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean isPlayerPlaced(BlockState block) {
        try (Connection conn = DB.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                SELECT 1 FROM player_placed_blocks WHERE world = ? AND x = ? AND y = ? AND z = ?
            """);
            ps.setString(1, block.getWorld().getName());
            ps.setInt(2, block.getX());
            ps.setInt(3, block.getY());
            ps.setInt(4, block.getZ());

            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean isPlayerPlaced(Location location) {
        try (Connection conn = DB.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                SELECT 1 FROM player_placed_blocks WHERE world = ? AND x = ? AND y = ? AND z = ?
            """);
            ps.setString(1, location.getWorld().getName());
            ps.setInt(2, (int) location.getX());
            ps.setInt(3, (int) location.getY());
            ps.setInt(4, (int) location.getZ());

            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
