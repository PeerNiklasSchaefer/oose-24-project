package com.sse.ooseproject;

import com.sse.ooseproject.controllers.CourseController;
import com.sse.ooseproject.models.Chair;
import com.sse.ooseproject.models.Course;
import com.sse.ooseproject.models.Room;
import com.sse.ooseproject.repositories.CourseRepository;
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
class CourseTests {
	@InjectMocks
	CourseController courseController;

	@Mock
	CourseRepository courseRepository;

	@Mock
	Model model;

	Course validCourse;
	Course validCourse2;

	@BeforeEach
	public void init() {
		courseRepository = Mockito.mock(CourseRepository.class);

		courseController = new CourseController(courseRepository);

		validCourse = new Course("course1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new Chair());
		validCourse2 = new Course("course2", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new Chair());
	}

	@Test
	public void GetCoursesNameAscTest() {
		Sort sort = Sort.by(Sort.Direction.ASC, "name");
		Mockito.when(courseRepository.findAll(sort))
				.thenReturn(List.of(validCourse, validCourse2));

		var viewName  = courseController.courses(model, "name", true);

		assertEquals("courses", viewName);

		ArgumentCaptor<List<Room>> captor = ArgumentCaptor.forClass(List.class);

		verify(model).addAttribute(eq("courses"), captor.capture());

		// Check that the captured list has at least one entry
		List<Room> capturedParameter = captor.getValue();
		assertThatCollection(capturedParameter).isNotEmpty();
		assertThatCollection(capturedParameter).first().isEqualTo(validCourse);
	}

	@Test
	public void GetCoursesNameDescTest() {
		Sort sort = Sort.by(Sort.Direction.DESC, "name");
		Mockito.when(courseRepository.findAll(sort))
				.thenReturn(List.of(validCourse2, validCourse));

		var viewName  = courseController.courses(model, "name", false);

		assertEquals("courses", viewName);

		ArgumentCaptor<List<Room>> captor = ArgumentCaptor.forClass(List.class);

		verify(model).addAttribute(eq("courses"), captor.capture());

		// Check that the captured list has at least one entry
		List<Room> capturedParameter = captor.getValue();
		assertThatCollection(capturedParameter).isNotEmpty();
		assertThatCollection(capturedParameter).first().isEqualTo(validCourse2);
	}
}
