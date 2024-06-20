package com.randered.mfa.base;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@ActiveProfiles("test")
public abstract class BaseTest {
    private static final DockerImageName imageName = DockerImageName.parse("mysql:8.0");
    private static final MySQLContainer container;

    static {
        container = (MySQLContainer) new MySQLContainer(imageName)
                .withReuse(true);
        container.start();
    }
}
