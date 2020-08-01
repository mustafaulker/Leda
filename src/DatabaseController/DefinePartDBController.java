package DatabaseController;

import Model.DefinePartModel;
import Util.DatabaseConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DefinePartDBController {

    // Once name ve Count insertle, bunların id'sini al, feature'ları insertle
    //  Insert Item
    public static void insertNameCount(String name, Integer count) throws SQLException, ClassNotFoundException {
        String updateStmt = "INSERT INTO tbl_part (name, count) VALUES ('" + name + "','" + count + "');";
        try {
            DatabaseConnector.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while INSERT Operation: " + e);
            throw e;
        }
    }
    public static Integer getPartID(String name) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT part_id FROM tbl_part WHERE name ='" + name + "'";
        int idx = 0;
        try {
            ResultSet rsItem = DatabaseConnector.dbExecuteQuery(selectStmt);
            while (rsItem.next()) {
                idx = rsItem.getInt("part_id");
            }
            return idx;
        } catch (SQLException e) {
            System.out.println("While searching an Part with " + name + " name, an error occurred: " + e);
            throw e;
        }
    }

    public static void insertFeatures(Integer part_id, Integer visibility, String spec, String value) throws SQLException, ClassNotFoundException {
        String updateStmt = "INSERT INTO tbl_relation (part_id, visibility, spec, value) VALUES" +
                " ('" + part_id + "','" + visibility + "','" + spec + "','" + value + "');";
        try {
            DatabaseConnector.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while INSERT Operation: " + e);
            throw e;
        }
    }


    // Search for specific Part's Feature
    public static ObservableList<DefinePartModel> searchFeature(int id) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM tbl_relation WHERE part_id='" + id + "'";
        try {
            ResultSet rsFeature = DatabaseConnector.dbExecuteQuery(selectStmt);
            return getFeatureList(rsFeature);
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }

    public static ObservableList<DefinePartModel> getFeatureList(ResultSet rs) throws SQLException {
        ObservableList<DefinePartModel> featureList = FXCollections.observableArrayList();
        while (rs.next()) {
            DefinePartModel feature = new DefinePartModel();
//            feature.setVisibility(rs.getInt("visibility"));
            feature.setVisibility(rs.getInt("visibility") == 1);
            feature.setSpec(rs.getString("spec"));
            feature.setValue(rs.getString("value"));
            featureList.add(feature);
        }
        return featureList;
    }

    //  Delete Feature with it's spec
    public static void deleteFeaturewithSpec(String spec) throws SQLException, ClassNotFoundException {
        String updateStmt = "DELETE FROM tbl_relation WHERE spec='" + spec + "'";
        try {
            DatabaseConnector.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            throw e;
        }
    }
}