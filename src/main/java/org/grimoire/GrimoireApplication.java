package org.grimoire;

import org.grimoire.support.ContextReadyEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class GrimoireApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(GrimoireApplication.class, args);
        context.publishEvent(new ContextReadyEvent(context));
    }

}
