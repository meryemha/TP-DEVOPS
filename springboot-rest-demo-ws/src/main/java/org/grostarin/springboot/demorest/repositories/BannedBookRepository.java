package org.grostarin.springboot.demorest.repositories;

import org.grostarin.springboot.demorest.domain.BannedBook;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BannedBookRepository extends CrudRepository<BannedBook, Long> {
    List<BannedBook> findByTitle(String title);
}
