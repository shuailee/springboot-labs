package shuailee.springboot.lesson.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InfoAppConfig {
    //在变量中注入配置文件中的配置
    @Value("${info.address}")
    private String address;
    @Value("${info.company}")
    private String Company;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }
}


