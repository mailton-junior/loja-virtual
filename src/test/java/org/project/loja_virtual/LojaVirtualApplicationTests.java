package org.project.loja_virtual;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.project.loja_virtual.controller.AccessController;
import org.project.loja_virtual.model.Access;
import org.project.loja_virtual.repository.AccessRepository;
import org.project.loja_virtual.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = LojaVirtualApplication.class)
public class LojaVirtualApplicationTests {

	@Autowired
	private AccessController accessController;

	@Autowired
	private AccessRepository accessRepository;

	@Test
	void saveAccess() {
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
