package org.example.app.repository;

import org.example.app.entity.book.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RateRepository extends JpaRepository<Rate, Integer> {

    List<Rate> findRatesByBooksSlug(String slug);

    Rate findRateByValue(Integer value);

}
