package com.trackwise.service;

import com.trackwise.model.Expense;
import com.trackwise.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;


    /* Get all expenses (for dashboard charts) */
    public List<Expense> getAllExpenses(){
        return expenseRepository.findAll();
    }

    public List<Expense> getExpensesByUserId(Long userId){
        return expenseRepository.findByUserId(userId);
    }


    /* Save expense */
    public void saveExpense(Expense expense){
        expenseRepository.save(expense);
    }

    /* Get expense by ID */
    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id).orElse(null);
    }

    /* Delete expense */
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

}

