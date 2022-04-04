package main;

import config.DbConfig;
import config.HsqlDataSource;
import dao.PersonDao;
import model.Person;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Tester {

    public static void main(String[] args) {

        ConfigurableApplicationContext ctx =
              new AnnotationConfigApplicationContext(
                      DbConfig.class, HsqlDataSource.class);

        try (ctx) {

            PersonDao dao = ctx.getBean(PersonDao.class);

            Person person = new Person("Alice");

           // dao.save(person);

            var readEntity = dao.findPersonByName("Bob");

            readEntity.setName("Carol");

            dao.save(readEntity);

        }

    }
}