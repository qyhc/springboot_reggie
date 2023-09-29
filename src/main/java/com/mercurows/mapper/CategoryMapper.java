package com.mercurows.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mercurows.domain.Category;

/*
 * 该接口使用了@Mapper注解，这表明它是一个MyBatis的Mapper接口，用于数据库操作。
 * 继承了BaseMapper<Category>，这意味着该接口将会继承BaseMapper接口中对Category类的数据库操作方法。
 * 该接口主要用于对Category类在数据库中的操作，比如增删改查等。
 * 这里的操作更偏向于底层的数据持久化操作，主要关注数据库表和实体对象之间的映射。
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category>{

}
