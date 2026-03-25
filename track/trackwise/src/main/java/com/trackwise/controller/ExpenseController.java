package com.trackwise.controller;

import com.trackwise.model.Expense;
import com.trackwise.service.ExpenseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;


    /* Dashboard Page */
    @GetMapping("/dashboard")
    public String dashboard(){
        return "dashboard";
    }


    /* API for dashboard charts */
   @GetMapping("/dashboard/summary")
@ResponseBody
public List<Expense> getSummary(){
    return expenseService.getAllExpenses();
}



    /* Add expense page */
    @GetMapping("/add-expense")
    public String addExpensePage(){
        return "add-expense";
    }

    @PostMapping("/expense/save")
    public String saveExpense(Expense expense){
    expenseService.saveExpense(expense);
        return "redirect:/dashboard";
}


}

