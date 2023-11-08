/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.entity.NhanVien;
import com.edusys.utils.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Truong
 */
public class NhanVienDAO extends EduSysDAO<NhanVien, String>{
    String insertsql= "insert into NhanVien(MaNV,MatKhau,HoTen,VaiTro) values(?,?,?,?)";
    String updatesql = "update NhanVien set MatKhau =?,HoTen=?,VaiTro=? where MaNV=?";
    String deletesql = "delete from NhanVien where manv=?";
    String selectallsql="select * from NhanVien";
    String selectbyidsql="select * from NhanVien where MaNV=?";


    @Override
    public void insert(NhanVien entity) {
        XJdbc.update(insertsql, entity.getMaNV(),entity.getMatKhau(),entity.getHoTen(),entity.getVaiTro());
    }

    @Override
    public void update(NhanVien entity) {
        XJdbc.update(updatesql,entity.getMatKhau(), entity.getHoTen(),entity.getVaiTro(),entity.getMaNV());
    }

    @Override
    public void delete(String key) {
        XJdbc.update(deletesql, key);
    }

    @Override
    public List<NhanVien> selectAll() {
        return this.selectBySql(selectallsql);
    }

    @Override
    public NhanVien selectById(String key) {
        List<NhanVien> list = this.selectBySql(selectbyidsql, key);
        if(list.isEmpty()) 
            return null;
        else 
            return list.get(0);

    }

    @Override
    protected List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list=new ArrayList<NhanVien>();
        try {
            ResultSet rs = XJdbc.query(sql, args);
                while(rs.next()){
                    NhanVien entity=new NhanVien();
                    entity.setMaNV(rs.getString("MaNV"));
                    entity.setMatKhau(rs.getString("MatKhau"));
                    entity.setHoTen(rs.getString("HoTen"));
                    entity.setVaiTro(rs.getBoolean("VaiTro"));
                    list.add(entity);
                }

                rs.getStatement().getConnection().close();
                return list;
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
      
    
    }

    
   
}
