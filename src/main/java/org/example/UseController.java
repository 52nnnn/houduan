package org.example;


import java.util.Map;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.service.UserService;
import org.example.util.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.PutMapping;
import org.example.User.userOper;
import org.springframework.web.bind.annotation.RequestParam;





@Controller
@Tag(name = "用户管理")
public class UseController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserService userService;




    //更新用户信息
    @PutMapping("/updateUser")
    @ResponseBody
    @Operation(summary = "更新用户信息（通过Query参数）")
    @Parameters( {
            @Parameter(name = "name", description = "用户名",in = ParameterIn.QUERY),
            @Parameter(name = "password", description = "密码",in = ParameterIn.QUERY),
            @Parameter(name = "birthday", description = "生日",in = ParameterIn.QUERY),
            @Parameter(name = "phone", description = "手机号",in = ParameterIn.QUERY)})
    public JsonResponse<Object>  updateUserByQuery(
            @RequestParam String name,
            @RequestParam String password,
            @RequestParam String birthday,
            @RequestParam String phone) {

        try {
            // 构造 userOper 对象
            userOper user = new userOper();
            user.setName(name);
            user.setPassword(password);
            user.setBirthday(birthday);
            user.setPhone(phone);

            boolean flag = userService.updateUser(user);

            JsonResponse<Object> jsonResponse = new JsonResponse<>();
            if (flag) {
                jsonResponse.setCode(0);
                jsonResponse.setMsg("更新成功");
            } else {
                jsonResponse.setCode(1);
                jsonResponse.setMsg("更新失败，用户不存在或数据异常");
            }
            return jsonResponse;
        } catch (Exception e) {
            e.printStackTrace();
            JsonResponse<Object> jsonResponse = new JsonResponse<>();
            jsonResponse.setCode(1);
            jsonResponse.setMsg("服务器异常：" + e.getMessage());
            return jsonResponse;
        }
    }







    @DeleteMapping("/delUser/{name}")
    @ResponseBody
    @Operation(summary = "删除用户")
    public JsonResponse<Object>  deleteUser(@PathVariable String name) {
        JsonResponse<Object> response = new JsonResponse<>();
        boolean flag = userService.deleteUser(name);
        if (flag) {
            response.setCode(0);
            response.setMsg("用户：" + name + "删除成功");
        } else {
            response.setCode(1);
            response.setMsg(name + "用户不存在");
        }
        return response;
    }


    // 用户登录
    @PostMapping("/user")
    @ResponseBody
    @Operation(summary = "用户登录")
    @Parameters( {
            @Parameter(name = "name", description = "用户名",in = ParameterIn.QUERY),
            @Parameter(name = "password", description = "密码",in = ParameterIn.QUERY)})
    public JsonResponse<Object>  user(String name, String password) {
        JsonResponse<Object>  jsonResponse = new JsonResponse<> ();

        // 检查用户名是否存在
        if (!userService.isUserExist(name)) {
            jsonResponse.setCode(1);
            jsonResponse.setMsg("用户名不存在");
            return jsonResponse;
        }

        // 检查密码是否正确
        if (!userService.isPasswordCorrect(name, password)) {
            jsonResponse.setCode(1);
            jsonResponse.setMsg("密码错误");
            return jsonResponse;
        }

        // 登录成功
        jsonResponse.setCode(0);
        jsonResponse.setMsg("登录成功");
        return jsonResponse;
    }

//获取所有用户信息
    @GetMapping("/userAll")
    @ResponseBody
    @Operation(summary = "获取所有用户信息")
    public List<Map<String, Object>> userAll() {
        return userService.queryAllUser();
    }


    @PostMapping("/user3")
    @ResponseBody
    @Operation(summary = "用户注册")
    @Parameters( {
            @Parameter(name = "name", description = "用户名",in = ParameterIn.QUERY),
            @Parameter(name = "password", description = "密码",in = ParameterIn.QUERY),
            @Parameter(name = "comfirmPassword", description = "确认密码",in = ParameterIn.QUERY),
            @Parameter(name = "birthday", description = "出生日期格式为yyyy-MM-dd",in = ParameterIn.QUERY),
            @Parameter(name = "phone", description = "手机号",in = ParameterIn.QUERY)
    })
    public JsonResponse<Object>  user3(String name,
                              String password,
                              String comfirmPassword,
                              String birthday,String phone) {
        // 检查空值
        JsonResponse<Object>  jsonResponse = new JsonResponse<> ();  // 修复此处类名大小写
        // boolean flag = true;//默认表示注册成功
        if (password == null || password.isEmpty()) {
            jsonResponse.setCode(1);
            jsonResponse.setMsg("密码不能为空");
            //  flag = false;
            return jsonResponse;
        }
        if (comfirmPassword == null || comfirmPassword.isEmpty()) {
            jsonResponse.setCode(1);
            jsonResponse.setMsg("确认密码不能为空");
            //  flag = false;
            return jsonResponse;
        }

        if (name == null || name.isEmpty()) {
            jsonResponse.setCode(1);
            jsonResponse.setMsg("用户名不能为空");
            //  flag = false;
            return jsonResponse;
        }

        if (phone.isEmpty()) {
            jsonResponse.setCode(1);
            jsonResponse.setMsg("手机号不能为空");
            // flag = false;
            return jsonResponse;
        }
        if (birthday == null || birthday.trim().isEmpty()) {
            jsonResponse.setCode(1);
            jsonResponse.setMsg("出生日期不能为空");
            // flag = false;
            return jsonResponse;
        }


        //把前端传入的数据成功的写入到数据库才代表注册成功
        //在此处我们先调用jdbcteampate中的update方法
        //String sql = "insert into appUser(name,password,birthday,phone) values(?,?,?,?)";
        //update方法两个参数，第一个参数传入要执行的sql语句(upddate方法可以执行insert,delete,update语句)
        //第二个参数是一个可变参数据
        try {
           userService.useres(name,password,birthday,phone);

            System.out.println("注册成功");
            jsonResponse.setCode(0);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.setCode(1);
            jsonResponse.setMsg("注册失败"+e.getMessage());

        }


            return jsonResponse;
        }

        //查询所有用户
        @GetMapping("/queryAllUser")
        @ResponseBody
        @Operation(summary = "查询所有用户")
        public JsonResponse<Object>  queryAllUser() {
        JsonResponse<Object>  jsonResponse = new JsonResponse<> ();
        try {
            jsonResponse.setData(userService.queryAllUser());

            jsonResponse.setCode(0);
            jsonResponse.setMsg("查询成功");
        }
        catch (Exception e) {
            e.printStackTrace();
            jsonResponse.setCode(1);
            jsonResponse.setMsg("查询失败"+e.getMessage());
        }
        return jsonResponse;
        }

}
