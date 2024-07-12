package com.sse.ooseproject.validators;

import com.sse.ooseproject.models.Employee;
import com.sse.ooseproject.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EmployeeValidator {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeValidator(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void validateEmployee(Employee employee) throws EmployeeValidationException {
        //Check necessary fields filled
        if (employee.getFirstName() == null || employee.getFirstName().isEmpty()) {
            throw new EmployeeValidationException("First name is required.");
        }
        if (employee.getLastName() == null || employee.getLastName().isEmpty()) {
            throw new EmployeeValidationException("Last name is required.");
        }
        if (employee.getEmail() == null || employee.getEmail().isEmpty()) {
            throw new EmployeeValidationException("A email address is required.");
        }

        //Check Email Valid. Regex: [Valid Characters without trailing dot]@[Valid Characters that dont end on -].[Valid Characters]
        if (!Pattern.compile("[-A-Za-z0-9!#$%&'*+/=?^_`{|}~]+(?:\\.[-A-Za-z0-9!#$%&'*+/=?^_`{|}~]+)*@(?:[A-Za-z0-9](?:[-A-Za-z0-9]*[A-Za-z0-9])?\\.)+[A-Za-z0-9](?:[-A-Za-z0-9]*[A-Za-z0-9])?").matcher(employee.getEmail()).matches()){
            throw new EmployeeValidationException("The email address is not valid.");
        }

        //Check StaffNr existing
        Employee existingStudent = employeeRepository.findByStaffNr(employee.getStaffNr());
        if (existingStudent != null) {
            if(existingStudent.getId() != employee.getId()) throw new EmployeeValidationException("Matriculation number already exists.");
        }
    }

    public void validateEmployeeExists(Employee employee) throws EmployeeValidationException {
        Employee existingEmployee = employeeRepository.findById(employee.getId());
        if (existingEmployee == null) {
            throw new EmployeeValidationException("Employee does not exist.");
        }
    }
}
