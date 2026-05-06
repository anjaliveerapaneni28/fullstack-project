package com.trackwise.repository;

import com.trackwise.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    java.util.List<Expense> findByUserId(Long userId);

}
