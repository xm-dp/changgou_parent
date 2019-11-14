package com.changgou.goods.dao;

import com.changgou.goods.pojo.Brand;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface BrandMapper extends Mapper<Brand> {

    //根据分类名称查询品牌列表
    @Select("select name,image from tb_brand where id in (select brand_id from tb_category_brand where category_id in (select id from tb_category where name = #{name}))")
    List<Brand> findBrandListByCategoryName(@Param("name") String name);

    //根据分类名称查询规格列表
    @Select("SELECT NAME,OPTIONS FROM tb_spec WHERE template_id IN (SELECT template_id FROM tb_category WHERE NAME = #{name})")
    List<Map> findSpecListByCategoryName(@Param("name") String name);
}
