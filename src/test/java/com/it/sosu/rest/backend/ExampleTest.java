/**
 * @author : Oguz Kahraman
 * @since : 6.12.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.it.sosu.rest.backend;

import com.sosu.rest.backend.SoSuBackend;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SoSuBackend.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles(resolver = SoSuActiveProfilesResolver.class)
public class ExampleTest {
}
