package com.sse.ooseproject.repositories;

import com.sse.ooseproject.models.Enrollment;
import com.sse.ooseproject.models.EnrollmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EnrollmentRepository extends  JpaRepository<Enrollment, EnrollmentId> {
    List<Enrollment> findByIdStudentIdAndSemester(long studentId, String semester);

    @Transactional
    void deleteById_StudentIdAndId_CourseId(long student_id, long course_id);
}