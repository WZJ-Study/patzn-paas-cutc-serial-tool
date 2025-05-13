package com.patzn.paas.cutc;

import com.patzn.paas.cutc.utils.spring.SpringHelper;
import com.patzn.paas.cutc.ui.JavaFxApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class CutcSerialToolApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(CutcSerialToolApplication.class);
        builder.headless(false);
        SpringHelper.setApplicationContext(builder.run(args));
        JavaFxApplication.main(args);
    }

}
