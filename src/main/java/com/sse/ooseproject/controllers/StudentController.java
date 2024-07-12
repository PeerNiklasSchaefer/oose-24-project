package com.sse.ooseproject.controllers;

import com.sse.ooseproject.models.*;
import com.sse.ooseproject.repositories.CourseRepository;
import com.sse.ooseproject.repositories.EnrollmentRepository;
import com.sse.ooseproject.repositories.InstituteRepository;
import com.sse.ooseproject.repositories.StudentRepository;
import com.sse.ooseproject.validators.StudentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class StudentController {
    private final StudentRepository studentRepository;
    private final InstituteRepository instituteRepository;
    private final StudentValidator studentValidator;
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository, InstituteRepository instituteRepository, StudentValidator studentValidator, EnrollmentRepository enrollmentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.instituteRepository = instituteRepository;
        this.studentValidator = studentValidator;
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
    }

    private List<String> getStudySubjects(){
        List<Institute> institutes = instituteRepository.findAll();
        List<String> studySubjects = institutes.stream().map(Institute::getProvidesStudySubject).collect(Collectors.toList());
        return studySubjects.stream().distinct().collect(Collectors.toList());
    }

    private List<Course> getCourses(String studySubject){
        Institute institute = instituteRepository.findByProvidesStudySubject(studySubject);
        if(institute == null) return Collections.emptyList();

        List<Chair> chairs = institute.getChairs();

        Set<Course> courses = new HashSet<>();
        for(Chair chair : chairs){
            if(chair.getCourses() != null) courses.addAll(chair.getCourses());
        }
        ArrayList<Course> finalCourses = new ArrayList<>(courses);
        Collections.sort(finalCourses, (a,b) -> a.getName().compareTo(b.getName()));
        return finalCourses;
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

    @GetMapping("/students")
    public String students(Model model, @RequestParam(value = "sort_by", defaultValue = "firstName") String sortBy,
                           @RequestParam(value = "sort_asc", defaultValue = "true") boolean sortAsc) {

        Sort.Direction direction = sortAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);

        List<Student> students = studentRepository.findAll(sort);
        model.addAttribute("students", students);

        return "students";
    }

    @GetMapping ("/student/new")
    public String showStudentForm(Model model) {
        //Attributes
        model.addAttribute("student", new Student());
        model.addAttribute("page_type", "new");
        model.addAttribute("study_subjects", getStudySubjects());

        // Returning the name of a view
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
        model.addAttribute("student", studentRepository.findById(id));
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

    @GetMapping ("/student/delete")
    public String showDeleteStudentForm(@RequestParam("id") long id, Model model) {
        model.addAttribute("student", studentRepository.findById(id));

        return "delete_student";
    }

    @PostMapping("/student/delete")
    public String deleteStudent(@ModelAttribute("student") Student student, Model model){
        try {
            studentValidator.validateStudentExists(student);
            studentRepository.deleteById(student.getId());

            // Will not be displayed, because of redirect TODO: redirect from view after delay
            model.addAttribute("message", "Removing student was successful");
            model.addAttribute("message_type", "success");
            return "redirect:/students";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            model.addAttribute("message_type", "error");
            model.addAttribute("student", studentRepository.findById(student.getId()));
            return "delete_student";
        }
    }

    @GetMapping("/student/enroll")
    public String showEnrollmentForm(@RequestParam("id") long id, @RequestParam(name = "semester", defaultValue = "2024 Spring") String semester, Model model){
        Student student = studentRepository.findById(id);

        model.addAttribute("student", student);
        model.addAttribute("enrollments", enrollmentRepository.findByIdStudentIdAndSemester(id, semester));
        model.addAttribute("semester", semester);
        model.addAttribute("courses", getCourses(student.getStudySubject()));

        return "enrollment";
    }

    @GetMapping("/enrollment/enroll")
    public String createStudentEnrollment(@RequestParam("student_id") long student_id, @RequestParam("course_id") long course_id, @RequestParam("semester") String semester, Model model){
        Student student = studentRepository.findById(student_id);
        Course course = courseRepository.findById(course_id);
        enrollmentRepository.save(new Enrollment(semester, student, course));

        showEnrollmentForm(student_id, semester, model);
        return "enrollment";
    }

    @GetMapping("/enrollment/delete")
    public String deleteStudentEnrollment(@RequestParam("student_id") long student_id, @RequestParam("course_id") long course_id, @RequestParam("semester") String semester, Model model){
        enrollmentRepository.deleteById_StudentIdAndId_CourseId(student_id, course_id);

        showEnrollmentForm(student_id, semester, model);
        return "enrollment";
    }
}
