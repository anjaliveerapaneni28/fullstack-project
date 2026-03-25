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


    /* Save expense */
    public void saveExpense(Expense expense){
        expenseRepository.save(expense);
    }

}

