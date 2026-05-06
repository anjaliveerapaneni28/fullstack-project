package com.trackwise.controller;

import com.trackwise.model.Expense;
import com.trackwise.model.User;
import com.trackwise.model.Category;
import com.trackwise.service.ExpenseService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;


    /* Dashboard Page */
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session){
        if(session.getAttribute("loggedInUser") == null) return "redirect:/login";
        return "dashboard";
    }


    /* API for dashboard charts */
   @GetMapping("/dashboard/summary")
@ResponseBody
public List<Expense> getSummary(HttpSession session){
    User user = (User) session.getAttribute("loggedInUser");
    if(user == null) return List.of();
    return expenseService.getExpensesByUserId(user.getId());
}



    /* Add expense page */
    @GetMapping("/add-expense")
    public String addExpensePage(HttpSession session){
        if(session.getAttribute("loggedInUser") == null) return "redirect:/login";
        return "add-expense";
    }

    @PostMapping("/expense/save")
    public String saveExpense(Expense expense, @RequestParam("categoryId") Long categoryId, HttpSession session){
        User user = (User) session.getAttribute("loggedInUser");
        if(user == null) return "redirect:/login";
        expense.setUser(user);
        Category category = new Category();
        category.setId(categoryId);
        expense.setCategory(category);
        expenseService.saveExpense(expense);
        return "redirect:/dashboard";
    }

    /* Edit expense page */
    @GetMapping("/expense/edit/{id}")
    public String editExpensePage(@PathVariable Long id, Model model, HttpSession session){
        User user = (User) session.getAttribute("loggedInUser");
        if(user == null) return "redirect:/login";
        Expense expense = expenseService.getExpenseById(id);
        if(expense == null || !expense.getUser().getId().equals(user.getId())) {
             return "redirect:/dashboard";
        }
        model.addAttribute("expense", expense);
        return "edit-expense";
    }

    /* Update expense */
    @PostMapping("/expense/update/{id}")
    public String updateExpense(@PathVariable Long id, @ModelAttribute Expense expenseDetails, @RequestParam("categoryId") Long categoryId, HttpSession session){
        User user = (User) session.getAttribute("loggedInUser");
        if(user == null) return "redirect:/login";
        Expense existingExpense = expenseService.getExpenseById(id);
        if(existingExpense != null && existingExpense.getUser().getId().equals(user.getId())) {
            existingExpense.setAmount(expenseDetails.getAmount());
            existingExpense.setDescription(expenseDetails.getDescription());
            existingExpense.setDate(expenseDetails.getDate());
            Category category = new Category();
            category.setId(categoryId);
            existingExpense.setCategory(category);
            expenseService.saveExpense(existingExpense);
        }
        return "redirect:/dashboard";
    }

    /* Delete expense */
    @PostMapping("/expense/delete/{id}")
    public String deleteExpense(@PathVariable Long id, HttpSession session){
        User user = (User) session.getAttribute("loggedInUser");
        if(user == null) return "redirect:/login";
        Expense expense = expenseService.getExpenseById(id);
        if(expense != null && expense.getUser().getId().equals(user.getId())) {
            expenseService.deleteExpense(id);
        }
        return "redirect:/dashboard";
    }

    /* Reports Page */
    @GetMapping("/reports")
    public String reportsPage(HttpSession session){
        if(session.getAttribute("loggedInUser") == null) return "redirect:/login";
        return "reports";
    }

}

