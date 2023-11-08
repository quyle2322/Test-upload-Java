package com.edusys.dao;

import com.edusys.entity.HocVien;
import com.edusys.utils.XJdbc;
import com.edusys.entity.NguoiHoc;
import com.edusys.entity.NguoiHoc1;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NguoiHocDAO1 extends EduSysDAO<NguoiHoc1, String>{

    public void insert(NguoiHoc1 model){
        String sql="INSERT INTO NguoiHoc (MaNH, HoTen, NgaySinh, GioiTinh, DienThoai, Email, GhiChu, MaNV) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        System.out.println("Ngay: "+model.getNgaySinh());
        XJdbc.update(sql, 
                model.getMaNH(), 
                model.getHoTen(), 
                model.getNgaySinh(), 
                model.getGioiTinh(), 
                model.getDienThoai(),
                model.geteMail(),
                model.getGhiChu(),
                model.getMaNV());
                model.getNgayDK();
    }
    
    public void update(NguoiHoc1 model){
        String sql="UPDATE NguoiHoc SET HoTen=?, NgaySinh=?, GioiTinh=?, DienThoai=?, Email=?, GhiChu=?, MaNV=? WHERE MaNH=?";
        XJdbc.update(sql, 
                model.getHoTen(), 
                model.getNgaySinh(), 
                model.getGioiTinh(), 
                model.getDienThoai(),
                model.geteMail(),
                model.getGhiChu(),
                model.getMaNV(),
                model.getMaNH());
    }
    
    public void delete(String id){
        String sql="DELETE FROM NguoiHoc WHERE MaNH=?";
        XJdbc.update(sql, id);
    }
    
    public List<NguoiHoc1> selectAll(){
        String sql="SELECT * FROM NguoiHoc";
        return selectBySql(sql);
    }
    
    public NguoiHoc1 selectById(String manh){
        String sql="SELECT * FROM NguoiHoc WHERE MaNH=?";
        List<NguoiHoc1> list = selectBySql(sql, manh);
        return list.size() > 0 ? list.get(0) : null;
    }
    
    protected List<NguoiHoc1> selectBySql(String sql, Object...args){
        List<NguoiHoc1> list=new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = XJdbc.query(sql, args);
                while(rs.next()){
                    NguoiHoc1 entity=new NguoiHoc1();
                    entity.setMaNH(rs.getString("MaNH"));
                    entity.setHoTen(rs.getString("HoTen"));
                    entity.setNgaySinh(rs.getDate("NgaySinh"));
                    entity.setGioiTinh(rs.getInt("GioiTinh"));
                    entity.setDienThoai(rs.getString("DienThoai"));
                    entity.seteMail(rs.getString("Email"));
                    entity.setGhiChu(rs.getString("GhiChu"));
                    entity.setMaNV(rs.getString("MaNV"));
                    entity.setNgayDK(rs.getDate("NgayDK"));
                    list.add(entity);
                }
            } 
            finally{
                rs.getStatement().getConnection().close();
            }
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }
    
    public List<NguoiHoc1> selectByKeyword(String keyword){
        String sql="SELECT * FROM NguoiHoc WHERE HoTen LIKE ?";
        return this.selectBySql(sql, "%"+keyword+"%");
    }
    
    public List<NguoiHoc1> selectNotInCourse(int makh, String keyword) {
    String sql = "SELECT * FROM NguoiHoc "
            + " WHERE (HoTen LIKE ? OR Email LIKE ? OR dienthoai LIKE ?) AND " 
            + " MaNH NOT IN (SELECT MaNH FROM HocVien WHERE MaKH=?)";
    String keywordWithWildcard = "%" + keyword + "%";
    return this.selectBySql(sql, keywordWithWildcard, keywordWithWildcard, keywordWithWildcard, makh);
}
    
}
