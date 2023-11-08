/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;
import com.edusys.entity.KhoaHoc;
import com.edusys.utils.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Truong
 */
public class KhoaHocDAO extends EduSysDAO<KhoaHoc, String>{
    String insertsql= "insert into KhoaHoc(MaCD,HocPhi,ThoiLuong,NgayKG,GhiChu,MaNV,NgayTao) values(?,?,?,?,?,?,?)";
    String updatesql = "update KhoaHoc set MaCD =?,HocPhi=?,ThoiLuong=?,NgayKG=?,GhiChu=?,MaNV=?,NgayTao=? where MaKH=?";
    String deletesql = "delete from KhoaHoc where MaKH=?";
    String selectallsql="select * from KhoaHoc";
    String selectbyidsql="select * from KhoaHoc where MaKH=?";


    @Override
    public void insert(KhoaHoc entity) {
        XJdbc.update(insertsql,entity.getMaCD(),entity.getHocPhi(),
                entity.getThoiLuong(),entity.getNgayKG(),
                entity.getGhiChu(),entity.getMaNV(),entity.getNgayTao());
    }

    @Override
    public void update(KhoaHoc entity) {
        XJdbc.update(updatesql, entity.getMaCD(),entity.getHocPhi(),
                entity.getThoiLuong(),entity.getNgayKG(),
                entity.getGhiChu(),entity.getMaNV(),entity.getNgayTao(),entity.getMaKH());
    
    }

    @Override
    public void delete(String key) {
        XJdbc.update(deletesql, key);
    }

    @Override
    public List<KhoaHoc> selectAll() {
        return this.selectBySql(selectallsql);
    }

    @Override
    public KhoaHoc selectById(String key) {
        List<KhoaHoc> list = this.selectBySql(selectbyidsql, key);
        if(list.isEmpty()) 
            return null;
        else 
            return list.get(0);

    }

    @Override
    protected List<KhoaHoc> selectBySql(String sql, Object... args) {
        List<KhoaHoc> list=new ArrayList<KhoaHoc>();
        try {
            ResultSet rs = XJdbc.query(sql, args);
                while(rs.next()){
                    KhoaHoc entity=new KhoaHoc();
                    entity.setMaKH(rs.getInt("MaKH"));
                    entity.setMaCD(rs.getString("MaCD"));
                    entity.setHocPhi(rs.getFloat("HocPhi"));
                    entity.setThoiLuong(rs.getInt("ThoiLuong"));
                    entity.setNgayKG(rs.getDate("NgayKG"));
                    entity.setGhiChu(rs.getString("GhiChu"));
                    entity.setMaNV(rs.getString("MaNV"));
                    entity.setNgayTao(rs.getDate("NgayTao"));
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
    
    public List<KhoaHoc> selectByChuyenDe(String macd){
        String sql ="select * from  KhoaHoc where MaCD= ?";
        return this.selectBySql(sql, macd);
    }

    public List<Integer> selectYear() throws SQLException{
        String sql="select distinct year(ngaykg) from khoahoc order by year(ngaykg) desc";
        List<Integer> list = new ArrayList<>();
        ResultSet rs= XJdbc.query(sql);
        while(rs.next()){
            list.add(rs.getInt(1));
            
        }
        rs.getStatement().getConnection().close();
        return list;
    }
   
}
