package bg.tuvarna.ticketoffice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableScheduling
public class TicketofficeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketofficeApplication.class, args);
    }

}
