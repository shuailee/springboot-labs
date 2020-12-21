package model;

import  org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;


public class User {

    //用户id
    private int user_id;
    //姓名
    @NotEmpty(message="{user_name.notEmpty}")
    private String user_name;
    //性别
    @NotEmpty(message="{user_sex.notEmpty}")
    private String user_sex;
    //出生日期
    @NotEmpty(message="{user_birthday.notEmpty}")
    private String user_birthday;
    //邮箱
    @NotEmpty(message="{user_email.notEmpty}")
    @Email(message="{user_email.wrong}")
    private String user_email;
    //学历
    @NotEmpty(message="{user_edu.notEmpty}")
    private String user_edu;
    //联系方式
    @NotEmpty(message="{user_telephone.notEmpty}")
    private String user_telephone;
    //住址
    private String user_address;

    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    public String getUser_sex() {
        return user_sex;
    }
    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }
    public String getUser_birthday() {
        return user_birthday;
    }
    public void setUser_birthday(String user_birthday) {
        this.user_birthday = user_birthday;
    }
    public String getUser_email() {
        return user_email;
    }
    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
    public String getUser_edu() {
        return user_edu;
    }
    public void setUser_edu(String user_edu) {
        this.user_edu = user_edu;
    }
    public String getUser_telephone() {
        return user_telephone;
    }
    public void setUser_telephone(String user_telephone) {
        this.user_telephone = user_telephone;
    }
    public String getUser_address() {
        return user_address;
    }
    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }


    /*
CREATE TABLE `tb_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `user_sex` varchar(2) DEFAULT NULL COMMENT '性别',
  `user_birthday` date DEFAULT NULL COMMENT '出生日期',
  `user_email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `user_edu` varchar(2) DEFAULT NULL COMMENT '学历',
  `user_telephone` varchar(30) DEFAULT NULL COMMENT '联系方式',
  `user_address` varchar(100) DEFAULT NULL COMMENT '住址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('1', '李磊', '1', '1985-01-12', 'lilei123@sina.com', '3', '13211335451', '北京市东城区XXXX', '2017-01-31 18:24:41');
INSERT INTO `tb_user` VALUES ('2', '张华', '2', '1988-11-15', 'zhanghuajig@163.com', '3', '13811362254', '北京市西城区XXXX', '2017-01-31 18:29:08');
INSERT INTO `tb_user` VALUES ('3', '王媛媛', '2', '1990-04-06', 'yuanyuan112@sina.com', '3', '13511784568', '北京市西城区XXXX', '2017-01-18 10:30:48');
INSERT INTO `tb_user` VALUES ('4', '陈迪', '1', '1990-06-16', 'chendi0616@sina.com', '3', '13511697892', '北京市东城区XXXX', '2017-01-10 09:20:50');
INSERT INTO `tb_user` VALUES ('5', '王海东', '1', '1989-05-23', 'wanghaidong@163.com', '4', '13811981290', '北京市石景山区苹果园XXXXX', '2017-01-12 18:33:31');
INSERT INTO `tb_user` VALUES ('6', '李雪梅', '2', '1985-05-12', 'lixuemei@163.com', '2', '13911378945', '北京市朝阳区XXX', '2017-01-27 18:34:42');
INSERT INTO `tb_user` VALUES ('7', '张扬', '1', '1988-04-12', 'zhangyang11@sina.com', '3', '13611651245', '北京市石景山区XXXX', '2017-01-24 18:35:46');
INSERT INTO `tb_user` VALUES ('8', '赵庆', '1', '1986-05-06', 'zhaoqing56@163.com', '2', '13599632147', '北京市朝阳区XXX', '2017-01-31 18:38:57');
    * */

}
