package controller;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import service.IUserService;
import service.IndexService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {


    @Autowired
    private IUserService userService;
    @Autowired
    private IndexService indexService;
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }


    @RequestMapping(value = "user/list", method = RequestMethod.GET)
    public String list(ModelMap modelMap, @RequestParam(required=false,defaultValue="1") int pageNO)
    {
        int size=5;
        modelMap.addAttribute("size",size);
        modelMap.addAttribute("pageNO",pageNO);
        modelMap.addAttribute("count",userService.queryUserCount());
        modelMap.addAttribute("userList", userService.queryUserPager(pageNO, size));
        return "user/list";

    }
    @RequestMapping(value="user/push",method=RequestMethod.POST)
    @ResponseBody
    public  Map<String, Object>  pushUserInfo(@RequestBody User user){
        List<User> users=new ArrayList<User>();
        users.add(user);
        users.add(user);
        indexService.bulkProcess("test",users);
        Map<String,Object> mapModel=new HashMap<String, Object>(3);
        mapModel.put("code","sucess");
        mapModel.put("tip",user);
        return mapModel;
    }
}
