package com.trackwise.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    private String description;

    private LocalDate date;

    @ManyToOne
    private Category category;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Expense(){}

    public Long getId(){ return id; }
    public void setId(Long id){ this.id=id; }

    public double getAmount(){ return amount; }
    public void setAmount(double amount){ this.amount=amount; }

    public String getDescription(){ return description; }
    public void setDescription(String description){ this.description=description; }

    public LocalDate getDate(){ return date; }
    public void setDate(LocalDate date){ this.date=date; }

    public Category getCategory(){ return category; }
    public void setCategory(Category category){ this.category=category; }

    public User getUser(){ return user; }
    public void setUser(User user){ this.user=user; }
}
