package org.picon.controller;

import org.picon.config.RestDocsConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


/**
 * @see <a href="https://github.com/ePages-de/restdocs-wiremock/blob/master/server/src/test/java/com/example/notes/ApiDocumentation.java">참고 링크</a>
 */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
public class DisplayControllerTest {

}