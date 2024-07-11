package com.sse.ooseproject;

import com.sse.ooseproject.controllers.StudentController;
import com.sse.ooseproject.models.*;
import com.sse.ooseproject.repositories.CourseRepository;
import com.sse.ooseproject.repositories.EnrollmentRepository;
import com.sse.ooseproject.repositories.InstituteRepository;
import com.sse.ooseproject.repositories.StudentRepository;
import com.sse.ooseproject.validators.StudentValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCollection;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class StudentTests {

    @InjectMocks
    StudentController studentController;

    @Mock
    StudentRepository studentRepo;

    @Mock
    InstituteRepository instituteRepo;

    @Mock
    EnrollmentRepository enrollmentRepo;

    @Mock
    CourseRepository courseRepo;

    @InjectMocks
    StudentValidator studentValidator;

    @Mock
    Model model;

    Student validStudent;
    University validUniversity;
    Institute validInstitute;
    Course validCourse;
    Chair validChair;

    @BeforeEach
    public void init() {
        studentRepo = Mockito.mock(StudentRepository.class);
        instituteRepo = Mockito.mock(InstituteRepository.class);
        enrollmentRepo = Mockito.mock(EnrollmentRepository.class);
        courseRepo = Mockito.mock(CourseRepository.class);
        model = Mockito.mock(Model.class);
        studentValidator = new StudentValidator(studentRepo, instituteRepo);

        studentController = new StudentController(studentRepo, instituteRepo, studentValidator, enrollmentRepo, courseRepo);

        validUniversity = new University("university1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        validStudent = new Student(
                "firstName1", "lastName1", "email@email.de",
                12345, "studySubject", validUniversity);
        validStudent.setId(1);
        validCourse = new Course("course1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), null);
        validChair = new Chair("chair1", null, null, null, List.of(validCourse));
        validInstitute = new Institute("institute1", List.of(validChair), "studySubject");

        validCourse.setChair(validChair);
        validChair.setInstitute(validInstitute);
        validUniversity.setStudents(List.of(validStudent));
    }

    @Test
    public void showEditFormTest() {
        List<Institute> institutes = List.of(validInstitute);

        Mockito.when(studentRepo.findById(1L))
                .thenReturn(validStudent);
        Mockito.when(instituteRepo.findAll())
                .thenReturn(institutes);

        var viewName  = studentController.showEditForm(1, model);

        assertEquals("edit_student", viewName);
        verify(model).addAttribute("student", validStudent);
        verify(model).addAttribute("page_type", "edit");

        ArgumentCaptor<List<String>> captor = ArgumentCaptor.forClass(List.class);

        verify(model).addAttribute(eq("study_subjects"), captor.capture());

        // Check that the captured list has at least one entry
        List<String> capturedList = captor.getValue();
        assertThatCollection(capturedList).isNotEmpty();
        assertThatCollection(capturedList).contains("studySubject");
    }

    @Test
    public void editStudentTest() {
        Mockito.when(studentRepo.findByMatNr(12345))
                .thenReturn(validStudent);
        Mockito.when(instituteRepo.findByProvidesStudySubject("studySubject"))
                .thenReturn(validInstitute);

        var viewName  = studentController.editStudent(validStudent, model);

        assertEquals("edit_student", viewName);

        ArgumentCaptor<Student> captor = ArgumentCaptor.forClass(Student.class);

        verify(studentRepo).save(captor.capture());

        Student capturedStudent = captor.getValue();

        assertThat(capturedStudent.getId()).isEqualTo(validStudent.getId());
    }

    @Test
    public void editStudentInValidFirstNameTest2() {
        validStudent.setFirstName("");

        var viewName  = studentController.editStudent(validStudent, model);

        assertEquals("edit_student", viewName);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        verify(model).addAttribute(eq("message_type"), captor.capture());

        String capturedMessageType = captor.getValue();

        assertThat(capturedMessageType).isEqualTo("error");
    }

    @Test
    public void editStudentInValidStudySubjectTest2() {
        validStudent.setStudySubject("NotExistingStudySubject");
        Mockito.when(studentRepo.findByMatNr(12345))
                .thenReturn(validStudent);
        Mockito.when(instituteRepo.findByProvidesStudySubject("studySubject"))
                .thenReturn(validInstitute);

        var viewName  = studentController.editStudent(validStudent, model);

        assertEquals("edit_student", viewName);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        verify(model).addAttribute(eq("message_type"), captor.capture());

        String capturedMessageType = captor.getValue();

        assertThat(capturedMessageType).isEqualTo("error");
    }

    @Test
    public void showEnrollmentFormTest() {
        List<Enrollment> enrollments = List.of(new Enrollment());
        String semester = "2024 Spring";

        Mockito.when(studentRepo.findById(1L))
                .thenReturn(validStudent);
        Mockito.when(enrollmentRepo.findByIdStudentIdAndSemester(1L, semester))
                .thenReturn(enrollments);
        Mockito.when(instituteRepo.findByProvidesStudySubject("studySubject"))
                .thenReturn(validInstitute);

        var viewName  = studentController.showEnrollmentForm(1, semester, model);

        assertEquals("enrollment", viewName);
        verify(model).addAttribute("student", validStudent);
        verify(model).addAttribute("enrollments", enrollments);
        verify(model).addAttribute("semester", semester);

        ArgumentCaptor<List<Course>> captor = ArgumentCaptor.forClass(List.class);

        verify(model).addAttribute(eq("courses"), captor.capture());

        // Check that the captured list has at least one entry
        List<Course> capturedList = captor.getValue();
        assertThatCollection(capturedList).isNotEmpty();
        assertThatCollection(capturedList).contains(validCourse);
    }

    @Test
    public void createStudentEnrollmentTest() {
        String semester = "2024 Spring";

        Mockito.when(studentRepo.findById(1L))
                .thenReturn(validStudent);
        Mockito.when(courseRepo.findById(1L))
                .thenReturn(validCourse);

        var viewName  = studentController.createStudentEnrollment(1, 1, semester, model);

        assertEquals("enrollment", viewName);

        ArgumentCaptor<Enrollment> captor = ArgumentCaptor.forClass(Enrollment.class);

        verify(enrollmentRepo).save(captor.capture());

        // Check that the captured list has at least one entry
        Enrollment capturedList = captor.getValue();
        assertThat(capturedList.getStudent().getId()).isEqualTo(validStudent.getId());
        assertThat(capturedList.getSemester()).isEqualTo(semester);
        assertThat(capturedList.getCourse().getName()).isEqualTo(validCourse.getName());
    }

    @Test
    public void deleteStudentEnrollment() {
        String semester = "2024 Spring";

        Mockito.when(studentRepo.findById(1L))
                .thenReturn(validStudent);

        var viewName  = studentController.deleteStudentEnrollment(1, 1, semester, model);

        assertEquals("enrollment", viewName);
        verify(enrollmentRepo).deleteById_StudentIdAndId_CourseId(1, 1);
    }
}
