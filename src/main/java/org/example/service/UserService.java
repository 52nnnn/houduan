package org.example.service;

import org.example.User.userOper;
import org.example.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserDao userDao;//spring会自动将userDao注入到此类为(自动为该属性赋值)


    public boolean deleteUser(String name) {
        int rowsAffected = userDao.deleteUser(name);
        return rowsAffected > 0; // 如果影响行数大于0，说明删除成功
    }

    public List<Map<String,Object>> queryAllUser(){
        return  userDao.queryAllUser();
    }


    /**
     *
     * @param name 用户名
     * @return  返回true表示用户名密码正确，返回false表示用户名或密码错误
     */

    public boolean isUserExist(String name) {
        List<Map<String, Object>> result = userDao.queryUserByName(name);
        return !result.isEmpty();
    }

    /**
     * 检查密码是否正确
     */
    public boolean isPasswordCorrect(String name, String password) {
        List<Map<String, Object>> result = userDao.queryUserByName(name);
        if (!result.isEmpty()) {
            Map<String, Object> rowData = result.get(0);
            String storedPassword = (String) rowData.get("password");
            return password.equals(storedPassword);
        }
        return false;
    }

    public boolean updateUser(userOper UserOper) {
        String sql = "UPDATE appUser SET password = ?, birthday = ?, phone = ? WHERE name = ?";
        int rows = jdbcTemplate.update(sql,
                UserOper.getPassword(),
                UserOper.getBirthday(),
                UserOper.getPhone(),
                UserOper.getName()
        );
        return rows > 0;
    }


    /**
     * 用户注册的业务逻辑方法
     */
    public int useres(String name,
                              String password,
                              String birthday,
                              String phone){

        //此方法中，要判断前端传递过来的用户名，在appuser表中中否存在
        List<Map<String, Object>> res =  userDao.queryUserByName(name);//如果能够得到查询结果，代表用户名已存在
        if (!res.isEmpty()){
            throw  new RuntimeException("用户名已存在");
        }

        //  还要判断手机号是否已被占用
        List<Map<String, Object>> res2 =  userDao.queryUserByMobilePhone(phone);
        if (!res2.isEmpty()){
            throw new RuntimeException("手机已被占用");
        }



//以上两个条件都通过时，才允许做用户注册
        return userDao.saveUser(name,password,birthday,phone);

    }
}
