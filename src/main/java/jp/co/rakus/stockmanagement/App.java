package jp.co.rakus.stockmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan
public class App {

    /**
     * アプリケーションスタート.
     */
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
    
}
