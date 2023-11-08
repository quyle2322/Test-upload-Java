/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.entity.NhanVien1;
import com.edusys.entity.NhanVien1;
import com.edusys.utils.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Truong
 */
public class NhanVienDAO1 extends EduSysDAO<NhanVien1, String>{
    String insertsql= "insert into NhanVien(MaNV,MatKhau,HoTen,VaiTro,sdt,hinh) values(?,?,?,?,?,?)";
    String updatesql = "update NhanVien set MatKhau =?,HoTen=?,VaiTro=?,sdt=?,hinh=? where MaNV=?";
    String deletesql = "delete from NhanVien where manv=?";
    String selectallsql="select * from NhanVien";
    String selectbyidsql="select * from NhanVien where MaNV=?";


    @Override
    public void insert(NhanVien1 entity) {
        XJdbc.update(insertsql, entity.getMaNV(),entity.getMatKhau(),entity.getHoTen(),entity.getVaiTro(),entity.getSdt(),entity.getHinh());
    }

    @Override
    public void update(NhanVien1 entity) {
        XJdbc.update(updatesql,entity.getMatKhau(), entity.getHoTen(),entity.getVaiTro(),entity.getSdt(),entity.getHinh(),entity.getMaNV());
    }

    @Override
    public void delete(String key) {
        XJdbc.update(deletesql, key);
    }

    @Override
    public List<NhanVien1> selectAll() {
        return this.selectBySql(selectallsql);
    }

    @Override
    public NhanVien1 selectById(String key) {
        List<NhanVien1> list = this.selectBySql(selectbyidsql, key);
        if(list.isEmpty()) 
            return null;
        else 
            return list.get(0);

    }

    @Override
    protected List<NhanVien1> selectBySql(String sql, Object... args) {
        List<NhanVien1> list=new ArrayList<NhanVien1>();
        try {
            ResultSet rs = XJdbc.query(sql, args);
                while(rs.next()){
                    NhanVien1 entity=new NhanVien1();
                    entity.setMaNV(rs.getString("MaNV"));
                    entity.setMatKhau(rs.getString("MatKhau"));
                    entity.setHoTen(rs.getString("HoTen"));
                    entity.setVaiTro(rs.getBoolean("VaiTro"));
                    entity.setSdt(rs.getString("sdt"));
                    entity.setHinh(rs.getString("hinh"));
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
