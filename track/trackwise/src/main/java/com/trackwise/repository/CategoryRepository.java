package com.trackwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.trackwise.model.Category;

public interface CategoryRepository extends JpaRepository<Category,Long>{
}
