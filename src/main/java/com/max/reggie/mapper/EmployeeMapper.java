package com.max.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.max.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper  extends BaseMapper<Employee> {
        //继承了BaseMapper之后，常见的CRUD方法就都被继承了
}
