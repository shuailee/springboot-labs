package dao;

import model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface IUserDao {
    /**
     * 查询用户信息并分页
     * @param skip
     * @param size
     * @return
     */
    @Select("   select t.user_id,t.user_name,t.user_sex,date_format(t.user_birthday,'%Y-%m-%d')user_birthday,\n" +
            "                t.user_email,t.user_edu,t.user_telephone,t.user_address\n" +
            "                from tb_user t\n" +
            "                order by t.create_time desc\n" +
            "                limit #{skip},#{size}")
    public List<User> queryUserPager(@Param("skip") int skip, @Param("size") int size);

    /**
     * 查询用户总数
     * @return
     */
    @Select("select count(*) from tb_user")
    public int queryUserCount();

}
