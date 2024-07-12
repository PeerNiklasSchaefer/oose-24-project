package com.sse.ooseproject.repositories;

import com.sse.ooseproject.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByStaffNr(int staffNr);
    Employee findById(long id);
}
