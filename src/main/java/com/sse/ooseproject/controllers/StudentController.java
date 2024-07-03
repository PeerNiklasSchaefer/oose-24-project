package com.sse.ooseproject.controllers;

import com.sse.ooseproject.models.Institute;
import com.sse.ooseproject.repositories.InstituteRepository;
import com.sse.ooseproject.repositories.StudentRepository;
import com.sse.ooseproject.models.Student;
import com.sse.ooseproject.validators.StudentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class StudentController {
    private final StudentRepository studentRepository;
    private final InstituteRepository instituteRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository, InstituteRepository instituteRepository) {
        this.studentRepository = studentRepository;
        this.instituteRepository = instituteRepository;
    }

    @GetMapping("/students")
    public String students(Model model, @RequestParam(value = "sort_by", defaultValue = "firstName") String sortBy,
                           @RequestParam(value = "sort_asc", defaultValue = "true") boolean sortAsc) {

        Sort.Direction direction = sortAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);

        List<Student> students = studentRepository.findAll(sort);
        model.addAttribute("students", students);

        // Returning the name of a view (found in resources/templates) as a string lets this endpoint return that view.
        return "students";
    }

    @GetMapping ("/student/new")
    public String newStudent(Model model) {
        //Attributes
        Student student = new Student();
        List<Institute> institutes = instituteRepository.findAll();
        List<String> studySubjects = institutes.stream().map(Institute::getProvidesStudySubject).collect(Collectors.toList());
        studySubjects = studySubjects.stream().distinct().collect(Collectors.toList());

        model.addAttribute("student", student);
        model.addAttribute("page_type", "new");
        model.addAttribute("study_subjects", studySubjects);

        // Returning the name of a view (found in resources/templates) as a string lets this endpoint return that view.
        return "edit_student";
    }

    @PostMapping("/student/new")
    public String createStudent(@ModelAttribute("student") Student student, Model model) {
        StudentValidator studentValidator = new StudentValidator(studentRepository, instituteRepository);
        List<Institute> institutes = instituteRepository.findAll();
        List<String> studySubjects = institutes.stream().map(Institute::getProvidesStudySubject).collect(Collectors.toList());
        studySubjects = studySubjects.stream().distinct().collect(Collectors.toList());
        try {
            studentValidator.validateStudent(student);
            studentRepository.save(student);
            model.addAttribute("message", "Operation was successful");
            model.addAttribute("message_type", "success");
            model.addAttribute("student", new Student()); // Reset the form
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            model.addAttribute("message_type", "error");
            model.addAttribute("student", student);
        }

        model.addAttribute("page_type", "new");
        model.addAttribute("study_subjects", studySubjects);

        // Returning the name of a view (found in resources/templates) as a string lets this endpoint return that view.
        return "edit_student";
    }
}
