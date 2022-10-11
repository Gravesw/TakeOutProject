package com.max.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.max.reggie.common.R;
import com.max.reggie.entity.Employee;
import com.max.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")//请求路径
public class EmpolyeeController {
        @Autowired

        private EmployeeService employeeService;
        /*
           员工登陆功能
         * @param request
         * @param employee
         * @return
         * @author: Max
         * @data:   2022/9/23
         */
        @PostMapping("/login")
        //参数中的HttpServletRequest 是为了用户登陆成功后在session中存入用户的ID以判断是否已经登陆
        //@RequestBody主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的)
        public R<Employee> login(HttpServletRequest request , @RequestBody Employee employee){
                //1.将页面提交的密码进行md5加密处理
                String password = employee.getPassword();
                password = DigestUtils.md5DigestAsHex(password.getBytes());
                //2.根据页面输入的username查询数据库
                LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(Employee::getUsername,employee.getUsername());
                //数据库中username是主键
                Employee emp = employeeService.getOne(queryWrapper);
                //3.如果没有查询到则返回登陆失败的结果
                if(emp == null){
                        return R.error("用户不存在");
                }
                //4.密码比对，不一样则返回：账号或密码错误
                if(!emp.getPassword().equals(password))
                        return R.error("账号或密码错误");
                //5.查询员工状态，如已禁用则反馈禁用结果
                if(emp.getStatus() == 0)
                        return R.error("账号已禁用");
                //6.登录成功，将员工ID存入session并反馈成功结果
                request.getSession().setAttribute("employee",emp.getId());
                return R.success(emp);
        }

        /*
           员工退出功能
         * @param request
         * @return  R
         * @author: Max
         * @data:   2022/9/23
         */
        @PostMapping("/logout")
        public R<String> logOut(HttpServletRequest request){
                //1.清理session中保存的当前员工的id
                request.getSession().removeAttribute("employee");
                return R.success("退出成功");
        }

        /*
           新增员工功能
         * @param Employee
         * @return  R<String>
         * @author: Max
         * @data:   2022/9/25
         */
        @PostMapping
        public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
                log.info("新增员工，员工信息：{}",employee.toString());
                //统一给新增员工初始密码
                //加密
                employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//                employee.setCreateTime(LocalDateTime.now());
//                employee.setUpdateTime(LocalDateTime.now());
                //获得当前登录用户的ID
                Long userID = (Long) request.getSession().getAttribute("employee");
//                employee.setCreateUser(userID);
//                employee.setUpdateUser(userID);
                //mp的save方法
                employeeService.save(employee);
                return R.success("新增员工成功");
        }

        /*
         员工信息分页查询
         * @param page pageSize name
         * @return  R<Page>
         * @author: Max
         * @data:   2022/9/26
         */
        @GetMapping("/page")
        public R<Page> page(int page , int pageSize , String name){

                log.info("page={},pageSize={},name={}",page,pageSize,name);
                //分页构造器
                Page pageInfo = new Page(page,pageSize);
                //条件查询构造器
                LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
                if(name!=null){
                        queryWrapper.like(Employee::getName,name);
                }
                //添加排序条件
                queryWrapper.orderByDesc(Employee::getUpdateTime);
                //执行查询
                employeeService.page(pageInfo,queryWrapper);
                return R.success(pageInfo);
        }

        /*
         修改员工信息&账号状态
         * @param null
         * @return R<String>
         * @author: Max
         * @data:   2022/9/27
         */
        @PutMapping
        public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
                log.info(employee.toString());
//                Long empId = (Long)request.getSession().getAttribute("employee");
//                employee.setUpdateUser(empId);
//                employee.setUpdateTime(LocalDateTime.now());
                Long id = Thread.currentThread().getId();
                log.info("线程id为：{}",id);
                employeeService.updateById(employee);
                return R.success("员工信息修改成功");
        }


        /*
          根据id查询员工信息
          * @param null
          * @return  R<Employee>
          * @author: Max
          * @data:   2022/9/27
             */
        @GetMapping("/{id}")
        public R<Employee> getById(@PathVariable Long id){
                log.info("根据id查询员工信息...");
                Employee employee = employeeService.getById(id);
                if(employee != null)
                        return R.success(employee);
                return R.error("没有查询到相关员工的信息...");
        }





}
