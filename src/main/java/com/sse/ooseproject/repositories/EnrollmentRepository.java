package com.sse.ooseproject.repositories;

import com.sse.ooseproject.models.EnrollmentId;
import com.sse.ooseproject.models.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends  JpaRepository<Enrollment, EnrollmentId> {
    List<Enrollment> findByIdStudentIdAndSemester(long studentId, String semester);
}