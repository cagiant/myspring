//package spring.helloworld.demo;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.SQLException;
//
//@SpringBootApplication
//@Slf4j
//public class DemoApplication implements CommandLineRunner {
//	@Autowired
//	public DataSource dataSource;
//
//	@Autowired
//	public JdbcTemplate jdbcTemplate;
//
//	public static void main(String[] args) {
//		SpringApplication.run(DemoApplication.class, args);
//	}
//
//	@Override
//	public void run(String... args) throws Exception {
//		showConnection();
//		showData();
//	}
//
//	private void showConnection() throws SQLException {
//		log.info(dataSource.toString());
//		Connection connection = dataSource.getConnection();
//		log.info(connection.toString());
//		connection.close();
//	}
//
//	private void showData() {
//		jdbcTemplate.queryForList("select * from FOO")
//				.forEach(row -> log.info(row.toString()));
//	}
//}
