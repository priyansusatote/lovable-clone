package com.priyansu.project.lovable_clone.repository;

import com.priyansu.project.lovable_clone.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface PlanRepository extends JpaRepository<Plan, Long> {
}
