package com.librarymanagement.repository;

import com.librarymanagement.model.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Books, Long> {
    Books findByTitle(String title);
    @Query("SELECT b FROM Books b WHERE b.id NOT IN (SELECT br.book.id FROM Borrowed br)")
    List<Books> findAllAvailableBooks();
}
