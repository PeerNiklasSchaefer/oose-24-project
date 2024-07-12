package com.sse.ooseproject.controllers;

import com.sse.ooseproject.repositories.EmployeeRepository;
import com.sse.ooseproject.models.Employee;
import com.sse.ooseproject.validators.EmployeeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final EmployeeValidator employeeValidator;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository, EmployeeValidator employeeValidator) {
        this.employeeRepository = employeeRepository;
        this.employeeValidator = employeeValidator;
    }

    @GetMapping("/employees")
    public String employees(Model model, @RequestParam(value = "sort_by", defaultValue = "firstName") String sortBy,
                           @RequestParam(value = "sort_asc", defaultValue = "true") boolean sortAsc) {

        Sort.Direction direction = sortAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);

        List<Employee> employees = employeeRepository.findAll(sort);
        model.addAttribute("employees", employees);

        // Returning the name of a view (found in resources/templates) as a string lets this endpoint return that view.
        return "employees";
    }

    private void validateAndSaveEmployee(Employee employee, Model model) {
        try {
            employeeValidator.validateEmployee(employee);
            employeeRepository.save(employee);
            model.addAttribute("message", "Operation was successful");
            model.addAttribute("message_type", "success");
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            model.addAttribute("message_type", "error");
            model.addAttribute("employee", employee);
        }
    }

    @GetMapping("/employee/edit")
    public String showEditEmployeeForm(@RequestParam("id") long id, Model model) {
        model.addAttribute("employee", employeeRepository.findById(id));

        return "edit_employee";
    }

    @PostMapping("/employee/edit")
    public String editEmployee(@ModelAttribute("employee") Employee employee, Model model){
        validateAndSaveEmployee(employee, model);

        return "edit_employee";
    }

    @GetMapping ("/employee/delete")
    public String showDeleteEmployeeForm(@RequestParam("id") long id, Model model) {
        model.addAttribute("employee", employeeRepository.findById(id));

        return "delete_employee";
    }

    @PostMapping("/employee/delete")
    public String deleteEmployee(@ModelAttribute("employee") Employee employee, Model model){
        try {
            employeeValidator.validateEmployeeExists(employee);
            employeeRepository.deleteById(employee.getId());

            // Will not be displayed, because of redirect TODO: redirect from view after delay
            model.addAttribute("message", "Removing employee was successful");
            model.addAttribute("message_type", "success");
            return "redirect:/employees";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            model.addAttribute("message_type", "error");
            model.addAttribute("employee", employeeRepository.findById(employee.getId()));
            return "delete_employee";
        }
    }
}
