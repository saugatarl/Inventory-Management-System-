package com.ims.dao;

import com.ims.model.RawMaterial;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RawMaterialDAO {

    private Connection conn = DBConnection.getInstance().getConnection();

    public List<RawMaterial> getAllRawMaterials() {
        List<RawMaterial> list = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE type = 'RAW'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new RawMaterial(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("quantity"),
                    rs.getInt("min_stock_level"),
                    rs.getString("supplier_name"),
                    rs.getString("unit")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addRawMaterial(RawMaterial rm) {
        String sql = "INSERT INTO items (name, quantity, min_stock_level, " +
                     "supplier_name, unit, type) VALUES (?, ?, ?, ?, ?, 'RAW')";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, rm.getName());
            ps.setInt(2, rm.getQuantity());
            ps.setInt(3, rm.getMinStockLevel());
            ps.setString(4, rm.getSupplierName());
            ps.setString(5, rm.getUnit());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateRawMaterial(RawMaterial rm) {
        String sql = "UPDATE items SET name = ?, quantity = ?, min_stock_level = ?, " +
                     "supplier_name = ?, unit = ? WHERE id = ? AND type = 'RAW'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, rm.getName());
            ps.setInt(2, rm.getQuantity());
            ps.setInt(3, rm.getMinStockLevel());
            ps.setString(4, rm.getSupplierName());
            ps.setString(5, rm.getUnit());
            ps.setInt(6, rm.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteRawMaterial(int id) {
        String sql = "DELETE FROM items WHERE id = ? AND type = 'RAW'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<RawMaterial> searchRawMaterials(String keyword) {
        List<RawMaterial> list = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE type = 'RAW' AND name LIKE ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new RawMaterial(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("quantity"),
                    rs.getInt("min_stock_level"),
                    rs.getString("supplier_name"),
                    rs.getString("unit")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}