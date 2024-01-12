package org.grostarin.springboot.demorest.tests;

import org.grostarin.springboot.demorest.domain.BannedBook;
import org.grostarin.springboot.demorest.services.BannedBookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
public class BannedBookServiceTests {

    @Autowired
    private BannedBookService bannedBookService;

    @Test
    public void testCreationNoAttributes() {
        BannedBook toCreate = new BannedBook();
        assertThatExceptionOfType(DataIntegrityViolationException.class).isThrownBy(() -> bannedBookService.create(toCreate));
    }
}
