package top.gaogle;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan(value = "top.gaogle.dao.master", sqlSessionTemplateRef = "sqlSessionTemplate", sqlSessionFactoryRef = "sqlSessionFactory")
@MapperScan(value = "top.gaogle.dao.slave", sqlSessionTemplateRef = "slaveSqlSessionTemplate", sqlSessionFactoryRef = "slaveSqlSessionFactory")
@EnableTransactionManagement
public class RegisterApplication {
    public static void main(String[] args) {
        SpringApplication.run(RegisterApplication.class, args);
    }

}
