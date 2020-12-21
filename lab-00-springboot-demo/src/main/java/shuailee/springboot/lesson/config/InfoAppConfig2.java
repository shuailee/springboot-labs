package shuailee.springboot.lesson.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
//自定義的配置文件和bean關聯，使用時只用注入該bean就可以了
@ConfigurationProperties(prefix = "info")
public class InfoAppConfig2 {
    private String address;
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