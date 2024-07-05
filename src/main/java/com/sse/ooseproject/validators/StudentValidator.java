package com.sse.ooseproject.validators;

import com.sse.ooseproject.models.Student;
import com.sse.ooseproject.repositories.StudentRepository;
import com.sse.ooseproject.repositories.InstituteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.regex.Pattern;

@Component
public class StudentValidator {
    private final StudentRepository studentRepository;
    private final InstituteRepository instituteRepository;

    @Autowired
    public StudentValidator(StudentRepository studentRepository, InstituteRepository instituteRepository) {
        this.studentRepository = studentRepository;
        this.instituteRepository = instituteRepository;
    }

    public void validateStudent(Student student) throws StudentValidationException {
        //Check necessary fields filled
        if (student.getFirstName() == null || student.getFirstName().isEmpty()) {
            throw new StudentValidationException("First name is required.");
        }
        if (student.getLastName() == null || student.getLastName().isEmpty()) {
            throw new StudentValidationException("Last name is required.");
        }
        if (student.getEmail() == null || student.getEmail().isEmpty()) {
            throw new StudentValidationException("A email address is required.");
        }
        if (student.getStudySubject() == null || student.getStudySubject().isEmpty()){
            throw new StudentValidationException("Study subject is required.");
        }
        if (student.getMatNr() == 0) {
            throw new StudentValidationException("Matriculation number is required.");
        }
        //Check Email Valid
        if (!Pattern.compile("[-A-Za-z0-9!#$%&'*+/=?^_`{|}~]+(?:\\.[-A-Za-z0-9!#$%&'*+/=?^_`{|}~]+)*@(?:[A-Za-z0-9](?:[-A-Za-z0-9]*[A-Za-z0-9])?\\.)+[A-Za-z0-9](?:[-A-Za-z0-9]*[A-Za-z0-9])?").matcher(student.getEmail()).matches()){
            throw new StudentValidationException("The email address is not valid.");
        }
        //Check MatNr existing
        if (studentRepository.findByMatNr(student.getMatNr()) != null) {
            throw new StudentValidationException("Matriculation number already exists.");
        }
        //Check Subject existing
        if (instituteRepository.findByProvidesStudySubject(student.getStudySubject()) == null){
            throw new StudentValidationException("Study subject does not exist.");
        }
    }
}
