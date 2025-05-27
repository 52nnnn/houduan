package org.example.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

//针对appUser表做SQL操作的类
@Repository   //此注解的作用：与控制器中的@Controller注解的功能相同，意为将该类纳入Spring容器管理
public class UserDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * 用于往appUser表中插入数据
     * @param name
     * @param password
     * @param birthday
     * @param phone
     * @return
     */
    public  int saveUser(String name,
                         String password,
                         String birthday,
                         String phone){
        String sql = "insert into appUser(name,password,birthday,phone) values(?,?,?,?)";
        return    jdbcTemplate.update(sql,name,password,birthday,phone);
    }


    /**
     * 根据name(主键) 删除表中对应的数据
     * @param name
     * @return
     */
    public  int deleteUser(String name){
        String sql  = "DELETE from appuser where name=?";
        return  jdbcTemplate.update(sql,name);
    }

    /**
     *
     */
    public List<Map<String, Object>> queryUserByName(String name){
        String sql = "select * from appuser where name =?";


        return  jdbcTemplate.queryForList(sql,name);
        // return  jdbcTemplate.queryForMap(sql,userName);//此方法将查询结果，形成一个Map 结构，Map的key就是查询结果的列表，map的alue就是对应的列值

    }

    public List<Map<String,Object>> queryUserByMobilePhone(String phone){
        String sql = "select * from appuser where phone =?";


        return  jdbcTemplate.queryForList(sql,phone);
    }

    public List<Map<String,Object>> queryAllUser(){
        String sql = "select * from appuser";

        //String sql = "select name,phone from appuser";
        return  jdbcTemplate.queryForList(sql);
    }


}


