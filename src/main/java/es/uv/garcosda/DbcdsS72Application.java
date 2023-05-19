package es.uv.garcosda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import es.uv.garcosda.services.ImportService;
import io.r2dbc.spi.ConnectionFactory;

@SpringBootApplication
public class DbcdsS72Application implements CommandLineRunner{

	@Autowired
    private ImportService is;

	public static void main(String[] args) {
		SpringApplication.run(DbcdsS72Application.class, args);
	}

	@Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));

        return initializer;
    }
	
	@Override
	public void run(String... args) throws Exception {
		Resource resource = new ClassPathResource("data.txt");
        is.doImport(resource);        
	}
}
