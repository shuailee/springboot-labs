package com.shuailee.springboot.study.api.mapper;

import com.shuailee.springboot.study.api.model.Cat;
import com.shuailee.springboot.study.api.model.CatDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @program: springboot-study
 * @description: CatMapper
 * @author: shuai.li
 * @create: 2020-06-10 16:25
 **/
@Mapper
public interface CatMapper {
    CatMapper INSTANCE = Mappers.getMapper( CatMapper.class );

    CatDto carToCarDto(Cat cat);
}
