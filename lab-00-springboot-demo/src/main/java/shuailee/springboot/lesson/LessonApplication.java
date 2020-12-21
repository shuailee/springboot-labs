package shuailee.springboot.lesson;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
//import shuailee.springboot.lesson.listener.SpringCustomApplicationListenerStarted;


@SpringBootApplication
public class LessonApplication  {

	public static ApplicationContext applicationContext;

	public static void main(String[] args) {

		/*if(applicationContext == null){
			System.out.println(" >>> Springboot Application 开始启动...");
			SpringApplicationBuilder builder = new SpringApplicationBuilder(LessonApplication.class);
			SpringApplication lessonApplication = builder.application();
			//这里注册spring框架事件监听器
			lessonApplication.addListeners(new SpringCustomApplicationListenerStarted());
			applicationContext = lessonApplication.run(args);
			System.out.println(" >>> Springboot Application 启动完成!");
		}*/


		SpringApplication.run(LessonApplication.class, args);
	}


	/**
	 * 同上
	 * */
	public static ApplicationContext getApplicationContext() {
		if (applicationContext == null) {
			System.out.println(" >>> Error：Springboot Application ApplicationContext is Null.");
		}
		return applicationContext;
	}
}
