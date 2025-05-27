package org.example.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Repository
public class ServiceDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    // 查询所有快递公司
    public List<Map<String,Object>> queryAllExpressCompanies(){
        String sql = "SELECT * FROM tz_delivery";
        return jdbcTemplate.queryForList(sql);
    }

    // 根据ID查询快递公司
    public Map<String,Object> queryCompanyById(int id){
        String sql = "SELECT * FROM tz_delivery WHERE id=?";
        return jdbcTemplate.queryForMap(sql, id);
    }



    // 添加快递公司
    public int addCompany(String name, String website, String createTime, String updateTime, String queryUrl){
        String sql = "INSERT INTO tz_delivery (name, website, createTime, updateTime, queryurl) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, name, website, createTime, updateTime, queryUrl);
    }




    // 更新快递公司信息
    public int updateCompany(int id, String name, String website,
                             String updateTime, String queryUrl) {
        String sql = "UPDATE tz_delivery SET name=?, website=?, updateTime=?, queryUrl=? WHERE id=?";
        return jdbcTemplate.update(sql, name, website, updateTime, queryUrl, id);
    }



    // 删除快递公司
    public int deleteCompany(int id){
        String sql = "DELETE FROM tz_delivery WHERE id=?";
        return jdbcTemplate.update(sql, id);
    }
}
