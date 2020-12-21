package service;

import model.User;
import java.util.List;
public interface IUserService {
    /**
     * 分页
     * @param pageNO
     * @param size
     * @return
     */
    public List<User> queryUserPager(int pageNO, int size);
    /**
     * 查询用户总数
     * @return
     */
    public int queryUserCount();

}
