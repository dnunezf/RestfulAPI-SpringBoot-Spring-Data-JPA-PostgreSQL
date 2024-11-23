// THIS IS THE JPA REPOSITORY
// DATA ACCESS LAYER, IT IS AN INTERFACE

package com.example.demo.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// SPECIFY THE OBJECT AND THE PRIMARY KEY PARAMETER TYPE
@Repository // Responsible for data access
public interface StudentRepository extends JpaRepository<Student,Long>
{
    // LOGIC TO FIND STUDENTS BY EMAIL
    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);
}
