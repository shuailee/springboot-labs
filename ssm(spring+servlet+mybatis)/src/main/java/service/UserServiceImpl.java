package service;

import dao.IUserDao;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService{
    //自动装配
    @Autowired
    private IUserDao userDao;
    /**
     * 分页
     */
    public List<User> queryUserPager(int pageNO, int size) {
        int skip=(pageNO-1)*size;
        return userDao.queryUserPager(skip, size);
    }
    /**
     * 查询用户总数
     */
    public int queryUserCount() {
        return userDao.queryUserCount();
    }

}
