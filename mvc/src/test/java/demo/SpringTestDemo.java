package demo;

import demo.config.MvcConfig;
import demo.config.SpringConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringTestDemo {

    @Test
    public void simpleSpringTest() {

        var ctx = new AnnotationConfigApplicationContext(SpringConfig.class);

        var dao = ctx.getBean(RealPostDao.class);

        System.out.println(dao.findAll());
    }

    @Test
    public void controllerTestAttempt() {
        var ctx = new AnnotationConfigApplicationContext(MvcConfig.class);

        var controller = ctx.getBean(PostController.class);

        System.out.println(controller.getPosts());
    }


}