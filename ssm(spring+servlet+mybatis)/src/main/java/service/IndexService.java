package service;

import model.User;

import java.net.UnknownHostException;
import java.util.List;

public interface IndexService {
    void  push(String id, User user);

    void bulkProcess(String index,List<User> users);
}
