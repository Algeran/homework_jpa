package otus.springfreamwork.jpa.domain.dao;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.shell.SpringShellAutoConfiguration;
import org.springframework.shell.jline.JLineShellAutoConfiguration;
import org.springframework.shell.standard.StandardAPIAutoConfiguration;
import org.springframework.shell.standard.commands.StandardCommandsAutoConfiguration;

@SpringBootApplication(
        exclude = {
                SpringShellAutoConfiguration.class
                , JLineShellAutoConfiguration.class
                , StandardAPIAutoConfiguration.class
                , StandardCommandsAutoConfiguration.class
        })
@ComponentScan(basePackages = {
        "otus.springfreamwork.jpa.domain",
        "otus.springfreamwork.jpa.com.repositories"
})
@EntityScan(basePackages = "otus.springfreamwork.jpa.domain.model")
public class TestSpringBootConfiguration {
}
