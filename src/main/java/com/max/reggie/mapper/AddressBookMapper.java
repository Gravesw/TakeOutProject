package com.max.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.max.reggie.entity.AddressBook;
import com.max.reggie.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
