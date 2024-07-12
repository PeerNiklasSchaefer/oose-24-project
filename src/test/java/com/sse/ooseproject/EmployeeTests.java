package com.sse.ooseproject;

import com.sse.ooseproject.controllers.EmployeeController;
import com.sse.ooseproject.models.*;
import com.sse.ooseproject.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCollection;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest
class EmployeeTests {
	@InjectMocks
	EmployeeController employeeController;

	@Mock
	EmployeeRepository employeeRepository;

	@Mock
	Model model;

	Employee validEmployee;
	Employee validEmployee2;

	@BeforeEach
	public void init() {
		employeeRepository = Mockito.mock(EmployeeRepository.class);

		employeeController = new EmployeeController(employeeRepository);

		validEmployee = new Employee(
				"firstName1", "lastName1", "email@email.de",
				12345, true, new University(), new ArrayList<>());
		validEmployee.setId(1);
		validEmployee2 = new Employee(
				"firstName2", "lastName2", "email2@email.de",
				123456,true, new University(), new ArrayList<>());
		validEmployee2.setId(2);
	}

	@Test
	public void GetCoursesFistNameAscTest() {
		Sort sort = Sort.by(Sort.Direction.ASC, "firstName");
		Mockito.when(employeeRepository.findAll(sort))
				.thenReturn(List.of(validEmployee, validEmployee2));

		var viewName  = employeeController.employees(model, "firstName", true);

		assertEquals("employees", viewName);

		ArgumentCaptor<List<Room>> captor = ArgumentCaptor.forClass(List.class);

		verify(model).addAttribute(eq("employees"), captor.capture());

		// Check that the captured list has at least one entry
		List<Room> capturedParameter = captor.getValue();
		assertThatCollection(capturedParameter).isNotEmpty();
		assertThatCollection(capturedParameter).first().isEqualTo(validEmployee);
	}

	@Test
	public void GetCoursesLastNameDescTest() {
		Sort sort = Sort.by(Sort.Direction.DESC, "lastName");
		Mockito.when(employeeRepository.findAll(sort))
				.thenReturn(List.of(validEmployee2, validEmployee));

		var viewName  = employeeController.employees(model, "lastName", false);

		assertEquals("employees", viewName);

		ArgumentCaptor<List<Room>> captor = ArgumentCaptor.forClass(List.class);

		verify(model).addAttribute(eq("employees"), captor.capture());

		// Check that the captured list has at least one entry
		List<Room> capturedParameter = captor.getValue();
		assertThatCollection(capturedParameter).isNotEmpty();
		assertThatCollection(capturedParameter).first().isEqualTo(validEmployee2);
	}
}
