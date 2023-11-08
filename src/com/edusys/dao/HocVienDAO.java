/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.entity.HocVien;
import com.edusys.utils.XJdbc;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Truong
 */
public class HocVienDAO extends EduSysDAO<HocVien, String> {
    String insertsql= "insert into HocVien(MaKH,MaNH,Diem) values(?,?,?)";
    String updatesql = "update HocVien set Diem=? where MaHV=?";
    String deletesql = "delete from HocVien where MaHV=?";
    String selectallsql="select * from HocVien";
    String selectbyidsql="select * from HocVien where MaHV=?";
    @Override
    public void insert(HocVien entity) {
        XJdbc.update(insertsql,entity.getMaKH(),entity.getMaNH(),entity.getDiem());
    }

    @Override
    public void update(HocVien entity) {
        XJdbc.update(updatesql,entity.getDiem(),entity.getMaHV());

    }

    @Override
    public void delete(String key) {
        XJdbc.update(deletesql, key);

    }

    @Override
    public List<HocVien> selectAll() {
        return this.selectBySql(selectallsql);

    }

    @Override
    public HocVien selectById(String key) {
        List<HocVien> list = this.selectBySql(selectbyidsql, key);
        if(list.isEmpty()) 
            return null;
        else 
            return list.get(0);
    }

    @Override
    protected List<HocVien> selectBySql(String sql, Object... args) {
         List<HocVien> list=new ArrayList<>();
        try {
            ResultSet rs = XJdbc.query(sql, args);
                while(rs.next()){
                    HocVien entity=new HocVien();
                    entity.setMaHV(rs.getInt("MaHV"));
                    entity.setMaKH(rs.getInt("MaKH"));
                    entity.setMaNH(rs.getString("MaNH"));
                    entity.setDiem(rs.getFloat("Diem"));
                    
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
    public List<HocVien> selectByKhoaHoc(int maKH){
        String sql="SELECT * FROM HocVien WHERE MaKH = ?";
        return this.selectBySql(sql, maKH);
    }
  
}
