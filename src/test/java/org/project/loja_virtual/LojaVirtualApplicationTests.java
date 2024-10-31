package org.project.loja_virtual;

import org.junit.jupiter.api.Test;
import org.project.loja_virtual.controller.AccessController;
import org.project.loja_virtual.model.Access;
import org.project.loja_virtual.repository.AccessRepository;
import org.project.loja_virtual.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = LojaVirtualApplication.class)
class LojaVirtualApplicationTests {

	@Autowired
	private AccessController accessController;

	@Test
	void saveAccess() {
		Access access = new Access();
		access.setDescription("ROLE_ADMIN");
		accessController.save(access);
	}



}
