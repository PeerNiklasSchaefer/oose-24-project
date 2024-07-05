package com.sse.ooseproject.controllers;

import com.sse.ooseproject.models.Course;
import com.sse.ooseproject.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CourseController {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping("/courses")
    public String courses(Model model, @RequestParam(value = "sort_by", defaultValue = "name") String sortBy,
                           @RequestParam(value = "sort_asc", defaultValue = "true") boolean sortAsc) {

        Sort.Direction direction = sortAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);

        List<Course> courses = courseRepository.findAll(sort);
        model.addAttribute("courses", courses);

        // Returning the name of a view (found in resources/templates) as a string lets this endpoint return that view.
        return "courses";
    }
}
