package com.company.unauthorizedDeliveries.Repo;

import com.company.unauthorizedDeliveries.domain.Postings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PostingsRepo extends JpaRepository<Postings,Long> {
    List<Postings> findAllByPstngDateAfter(LocalDate date);
    List<Postings> findAllByPstngDateAfterAndAuthorizedDelivery(LocalDate date, boolean b);

    List<Postings> findAllByAuthorizedDelivery(boolean b);

    List<Postings> findAllByPstngDateBefore(LocalDate date);
    List<Postings> findAllByPstngDateBeforeAndAuthorizedDelivery(LocalDate date,boolean b);

    List<Postings> findAllByPstngDateBetween(LocalDate dateS,LocalDate dateE);
    List<Postings> findAllByPstngDateBetweenAndAuthorizedDelivery(LocalDate dateS,LocalDate dateE,boolean b);
}
