package spring.helloworld.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.SQLException;

@SpringBootApplication
@Slf4j
public class ProgrammaticTransactionDemoApplication implements CommandLineRunner {
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(ProgrammaticTransactionDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws SQLException{
        log.info("count before transaction: {}", getCount());
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                jdbcTemplate.execute("INSERT INTO FOO (ID, BAR) VALUES (3, 'aaa')");
                log.info("count in transaction: {}", getCount());
                transactionStatus.setRollbackOnly();
            }
        });
        log.info("count after transaction: {}", getCount());
    }

    private long getCount() {
        return (long) jdbcTemplate.queryForList("select count(*) as TOTAL from FOO")
                .get(0).get("TOTAL");
    }
}
