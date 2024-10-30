package br.dev.leandro.spring.cloud;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

import java.util.Date;
import java.util.TimeZone;

@Log
@EnableConfigServer
@SpringBootApplication
public class SpringConfigServerApplication {

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC-3"));
    }

    public static void main(String[] args) {
        log.info(":: Iniciando Spring-Config-Server ::");
        long startTime = System.currentTimeMillis(); // Captura o tempo de início
        SpringApplication.run(SpringConfigServerApplication.class, args);

        long endTime = System.currentTimeMillis(); // Captura o tempo de fim
        long totalTime = endTime - startTime; // Calcula o tempo total em milissegundos
        log.info(":: Spring-Config-Server iniciado com sucesso :: - " + totalTime + " ms" );

    }

}
