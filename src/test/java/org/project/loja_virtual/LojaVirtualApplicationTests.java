package org.project.loja_virtual;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.project.loja_virtual.controller.AccessController;
import org.project.loja_virtual.model.Access;
import org.project.loja_virtual.repository.AccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = LojaVirtualApplication.class)
public class LojaVirtualApplicationTests {

	@Autowired
	private AccessController accessController;

	@Autowired
	private AccessRepository accessRepository;

	@Autowired
	private WebApplicationContext wac;

	/*Teste do EndPoint para Salvar*/
	@Test
	public void testRestApiCadasterAccess() throws Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		Access access = new Access();

		access.setDescription("ROLE_COMPRADOR");

		ObjectMapper mapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform
				(MockMvcRequestBuilders.post("/saveAccess")
				.content(mapper.writeValueAsString(access))
				.accept("application/json")
				.contentType("application/json"));

		/*Converter Retorno da Api para Objeto de Acesso*/

		Access objectReturn = mapper.readValue(returnApi.andReturn().getResponse().getContentAsString(), Access.class);

		assertEquals(access.getDescription(), objectReturn.getDescription());
	}

	@Test
	public void testRestApiDeleteAccess() throws Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		Access access = new Access();

		access.setDescription("ROLE_TEST_DELETE");

		access = accessController.saveAccess(access).getBody();

		ObjectMapper mapper = new ObjectMapper();

		ResultActions returnApi = mockMvc.perform
				(MockMvcRequestBuilders.post("/deleteAccess")
						.content(mapper.writeValueAsString(access))
						.accept("application/json")
						.contentType("application/json"));

		assertEquals("Acesso Deletado", returnApi.andReturn().getResponse().getContentAsString());
	}

	@Test
	void testCadaster() {
		Access access = new Access();
		access.setDescription("ROLE_ADMIN");

		access = accessController.saveAccess(access).getBody();

		assertNotNull(access, "Access should not be null");
		assertEquals(true, access.getId() > 0);
		assertEquals("ROLE_ADMIN", access.getDescription());

		/*Teste de Carregamento*/
		Access access2 = accessRepository.findById(access.getId()).get();
		assertEquals(access, access2);

		/*Teste de Exclusão*/
		accessController.deleteAccess(access);
		accessRepository.flush();
		assertFalse(accessRepository.existsById(access.getId()));

		/*Teste de Query*/
		Access access3 = new Access();
		access3.setDescription("ROLE_ALUNO");

		access3 = accessController.saveAccess(access3).getBody();

		List<Access> accessList = accessRepository.findByDescription("ALUNO".trim().toUpperCase());

		assertEquals(1, accessList.size());
		accessRepository.delete(access3);

	}



}
