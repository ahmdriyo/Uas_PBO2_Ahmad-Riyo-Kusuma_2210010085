/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uas_kehutanan;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.io.File;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author ahmad
 */
public class configDB {
    private DefaultTableModel Modelnya;
    private TableColumn Kolomnya;
    String jdbcURL ="jdbc:mysql://localhost:3306/ahmadriyokusuma_2210010085_pbo2";
    String username ="root";
    String password ="";
    
    Connection koneksi;
    
    public configDB(){
        try (Connection Koneksi = DriverManager.getConnection(jdbcURL, username, password)){
            Driver mysqldriver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(mysqldriver);
            System.out.println("Berhasil");
        } catch (SQLException error) {
            System.err.println(error.toString());
        }
    }
    
        public configDB(String url, String user, String pass){
        
        try(Connection Koneksi = DriverManager.getConnection(url, user, pass)) {
            Driver mysqldriver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(mysqldriver);
            
            System.out.println("Berhasil");
        } catch (Exception error) {
            System.err.println(error.toString());
        }
        
    }
        
        public static Connection getKoneksi() {
        try {
            String url = "jdbc:mysql://localhost:3306/ahmadriyokusuma_2210010085_pbo2";  // Ganti dengan URL dan database Anda
            String username = "root"; 
            String password = ""; 
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Koneksi Gagal: " + e.getMessage());
            return null;
        }
    }

    
    public static boolean duplicateKey(String table, String PrimaryKey, String value){
        boolean hasil=false;
        
        try {
            Statement sts = getKoneksi().createStatement();
            ResultSet rs = sts.executeQuery("SELECT*FROM "+table+" WHERE "+PrimaryKey+" ='"+value+"'");
            hasil = rs.next();
            
            rs.close();
            sts.close();
            getKoneksi().close();
            
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        
        
        return hasil;
    }
     public String getFieldList(String[] Field) {
    String hasil = "";
    for (int i = 0; i < Field.length; i++) {
        if (i == Field.length - 1) {
            hasil += Field[i];
        } else {
            hasil += Field[i] + ", ";
        }
    }
    return hasil;
}
    
    public String getValueList(String[] value) {
    String hasil = "";
    for (int i = 0; i < value.length; i++) {
        if (i == value.length - 1) {
            hasil += "'" + value[i] + "'"; // No comma for the last value
        } else {
            hasil += "'" + value[i] + "', ";
        }
    }
    return hasil;
    
}
    
    public static String getFieldValueEdit(String[] Field, String[] value){
        String hasil = "";
        int deteksi = Field.length-1;
        try {
            for (int i = 0; i < Field.length; i++) {
                if (i==deteksi){
                    hasil = hasil +Field[i]+" ='"+value[i]+"'";
                }else{
                   hasil = hasil +Field[i]+" ='"+value[i]+"',";  
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return hasil;
    }
    
    public void TambahDinamis(String Table, String[] Field, String[] value) {
    try {
            String SQL = "INSERT INTO " + Table + " (" + getFieldList(Field) + 
                         ") VALUES (" + getValueList(value) + ")";
            System.out.println("SQL: " + SQL);
            Statement perintah = getKoneksi().createStatement();
            perintah.executeUpdate(SQL);
            perintah.close();
            getKoneksi().close();
            
            
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
}

   public String getFieldTabel(String[] FieldTabelnya){
        String hasilnya="";
        int deteksiIndexAkhir=FieldTabelnya.length-1;
        try {
            for (int i = 0; i < FieldTabelnya.length; i++){
                if(i==deteksiIndexAkhir){   
                   hasilnya=hasilnya+FieldTabelnya[i];
                }else{
                   hasilnya=hasilnya+FieldTabelnya[i]+",";
                }
            }
        } catch (Exception e) 
            {
             System.out.println(e.toString());
            }  
        
         return "("+hasilnya+")";
     }

    public String getTabelValue(String[] Value) {
        return "(" + String.join(", ", Value) + ")";
    }

  


    public static void UbahDinamis(String NamaTabel, String PrimaryKey, String IsiPrimary,String[] Field, String[] Value){
        
        try {
           String SQLUbah = "UPDATE "+NamaTabel+" SET "+getFieldValueEdit(Field, Value)+" WHERE "+PrimaryKey+"='"+IsiPrimary+"'";
           Statement perintah = getKoneksi().createStatement();
           perintah.executeUpdate(SQLUbah);
           perintah.close();
           getKoneksi().close();
           JOptionPane.showMessageDialog(null,"Data Berhasil Diubah");
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        
    }


     
    public void HapusDinamis(String NamaTabel, String PK, String isi) {
        try {
            String SQLCheck = "DELETE FROM " + NamaTabel + " WHERE " + PK + " = '" + isi + "'";
            Statement perintah = getKoneksi().createStatement();
            perintah.executeUpdate(SQLCheck);
            perintah.close(); 
            JOptionPane.showMessageDialog(null,"Data Berhasil Dihapus");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    public void settingJudulTabel(JTable Tabelnya, String[] JudulKolom){
        try {
            Modelnya = new DefaultTableModel();
            Tabelnya.setModel(Modelnya);
            for (int i = 0; i < JudulKolom.length; i++) {
                Modelnya.addColumn(JudulKolom[i]);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
      
        public void settingLebarKolom(JTable Tabelnya,int[] Kolom){
          try {
              Tabelnya.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
              for (int i = 0; i < Kolom.length; i++) {
              Kolomnya =Tabelnya.getColumnModel().getColumn(i);
              Kolomnya.setPreferredWidth(Kolom[i]);   
              }


          } catch (Exception e) {
              System.out.println(e.toString());
          }
      }
         public Object[][] isiTabel(String SQL, int jumlah){
          Object[][] data =null;
          try {
             Statement perintah = getKoneksi().createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE, 
                ResultSet.CONCUR_READ_ONLY
             );
             ResultSet dataset = perintah.executeQuery(SQL);
             dataset.last();
             int baris = dataset.getRow();
             dataset.beforeFirst();
             int j =0;

             data = new Object[baris][jumlah];

             while (dataset.next()){
                 for (int i = 0; i < jumlah; i++) {
                     data[j][i]=dataset.getString(i+1);
                 }
                 j++;
                }
         
            } catch (Exception e) {
            }

            return data;
        }
        public void tampilTabel(JTable Tabelnya, String SQL, String[] Judul){
          try {
            Tabelnya.setModel(new DefaultTableModel(isiTabel(SQL,Judul.length), Judul));
          } catch (Exception e) {
              System.out.println(e.toString());
          }
      }
        public void tampilLaporan(String laporanFile, String SQL) throws SQLException{
      try {
          File file = new File(laporanFile);
          JasperDesign jasDes = JRXmlLoader.load(file);

           JRDesignQuery sqlQuery = new JRDesignQuery();
           sqlQuery.setText(SQL);
           jasDes.setQuery(sqlQuery);

           JasperReport JR = JasperCompileManager.compileReport(jasDes);
           JasperPrint JP = JasperFillManager.fillReport(JR,null,getKoneksi()); 
           JasperViewer.viewReport(JP,false);
         } catch (JRException e) {
            JOptionPane.showMessageDialog(null,e.toString());       

         }
    }
    
}