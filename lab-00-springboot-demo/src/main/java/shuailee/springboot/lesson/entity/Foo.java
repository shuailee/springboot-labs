package shuailee.springboot.lesson.entity;



/**
 * @program: lesson
 * @description:
 * @create: 2019-10-09 11:13
 **/
public class Foo {

     //@JsonProperty(value = "ID")
     public Integer ID;
     //@JsonProperty(value = "BAR")
     public String BAR;

     public Integer getID() {
          return ID;
     }

     public void setID(Integer ID) {
          this.ID = ID;
     }

     public String getBAR() {
          return BAR;
     }

     public void setBAR(String BAR) {
          this.BAR = BAR;
     }
}
