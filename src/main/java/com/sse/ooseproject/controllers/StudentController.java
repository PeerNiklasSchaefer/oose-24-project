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
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class StudentController {
    private final StudentRepository studentRepository;
    private final InstituteRepository instituteRepository;
    private final StudentValidator studentValidator;

    @Autowired
    public StudentController(StudentRepository studentRepository, InstituteRepository instituteRepository, StudentValidator studentValidator) {
        this.studentRepository = studentRepository;
        this.instituteRepository = instituteRepository;
        this.studentValidator = studentValidator;
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
        model.addAttribute("student", new Student());
        model.addAttribute("page_type", "new");
        model.addAttribute("study_subjects", getStudySubjects());

        // Returning the name of a view (found in resources/templates) as a string lets this endpoint return that view.
        return "edit_student";
    }

    @PostMapping("/student/new")
    public String createStudent(@ModelAttribute("student") Student student, Model model) {
        validateAndSaveStudent(student, model, "add");

        // Returning the name of a view (found in resources/templates) as a string lets this endpoint return that view.
        return "edit_student";
    }

    @GetMapping("/student/edit")
    public String showEditForm(@RequestParam("id") long id, Model model) {
        Optional<Student> student = studentRepository.findById(id);
        model.addAttribute("student", student);
        model.addAttribute("page_type", "edit");
        model.addAttribute("study_subjects", getStudySubjects());

        // Returning the name of a view (found in resources/templates) as a string lets this endpoint return that view.
        return "edit_student";
    }

    @PostMapping("/student/edit")
    public String editStudent(@ModelAttribute("student") Student student, Model model){
        validateAndSaveStudent(student, model, "edit");

        // Returning the name of a view (found in resources/templates) as a string lets this endpoint return that view.
        return "edit_student";
    }

    private List<String> getStudySubjects(){
        List<Institute> institutes = instituteRepository.findAll();
        List<String> studySubjects = institutes.stream().map(Institute::getProvidesStudySubject).collect(Collectors.toList());
        return studySubjects.stream().distinct().collect(Collectors.toList());
    }

    private void validateAndSaveStudent(Student student, Model model, String pageType) {
        try {
            studentValidator.validateStudent(student);
            studentRepository.save(student);
            model.addAttribute("message", "Operation was successful");
            model.addAttribute("message_type", "success");
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            model.addAttribute("message_type", "error");
            model.addAttribute("student", student);
        }
        model.addAttribute("study_subjects", getStudySubjects());
        model.addAttribute("page_type", pageType);
    }
}
