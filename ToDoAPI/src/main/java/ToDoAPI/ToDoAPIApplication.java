package ToDoAPI;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "To-Do API",
				version = "1.0.0",
				description = "",

				contact = @Contact(
						name = "Kauan Gabriel França Rodrigues",
						email = "Kauanfranca16@gmail.com"
				),

				license = @License(
						name = "license",
						url = ""
				)
		)
)
@SpringBootApplication
public class ToDoAPIApplication {
	public static void main(String[] args) {
		SpringApplication.run(ToDoAPIApplication.class, args);
	}
}
