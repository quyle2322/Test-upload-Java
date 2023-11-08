/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.entity.ChuyenDe;
import com.edusys.utils.XJdbc;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Truong
 */
public class ChuyenDeDAO extends EduSysDAO<ChuyenDe, String> {
    String insertsql= "insert into ChuyenDe(MaCD,TenCD,HocPhi,ThoiLuong,Hinh,MoTa) values(?,?,?,?,?,?)";
    String updatesql = "update ChuyenDe set TenCD =?,HocPhi=?,ThoiLuong=?,Hinh=?,MoTa =? where MaCD=?";
    String deletesql = "delete from ChuyenDe where MaCD=?";
    String selectallsql="select * from ChuyenDe";
    String selectbyidsql="select * from ChuyenDe where MaCD=?";
    @Override
    public void insert(ChuyenDe entity) {
        XJdbc.update(insertsql, entity.getMaCD(),entity.getTenCD(),entity.getHocPhi(),entity.getThoiLuong(),entity.getHinh(),entity.getMoTa());
    }

    @Override
    public void update(ChuyenDe entity) {
        XJdbc.update(updatesql, entity.getTenCD(),entity.getHocPhi(),entity.getThoiLuong(),entity.getHinh(),entity.getMoTa(),entity.getMaCD());

    }

    @Override
    public void delete(String key) {
        XJdbc.update(deletesql, key);

    }

    @Override
    public List<ChuyenDe> selectAll() {
        return this.selectBySql(selectallsql);

    }

    @Override
    public ChuyenDe selectById(String key) {
        List<ChuyenDe> list = this.selectBySql(selectbyidsql, key);
        if(list.isEmpty()) 
            return null;
        else 
            return list.get(0);
    }

    @Override
    protected List<ChuyenDe> selectBySql(String sql, Object... args) {
         List<ChuyenDe> list=new ArrayList<>();
        try {
            ResultSet rs = XJdbc.query(sql, args);
                while(rs.next()){
                    ChuyenDe entity=new ChuyenDe();
                    entity.setMaCD(rs.getString("MaCD"));
                    entity.setTenCD(rs.getString("TenCD"));
                    entity.setHocPhi(rs.getFloat("HocPhi"));
                    entity.setThoiLuong(rs.getInt("ThoiLuong"));
                    entity.setHinh(rs.getString("Hinh"));
                    entity.setMoTa(rs.getString("MoTa"));
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
