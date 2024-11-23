// THIS IS THE SERVICE LAYER, IS USED BY THE CONTROLLER
// USES THE DATA ACCESS INTERFACE
package com.example.demo.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service // MEANS THAT THIS CLASS IS GOING TO BE A SERVICE CLASS, DEPENDENCY INJECTION
public class StudentService
{
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // BASIC CRUD API

    // FILTER ALL STUDENTS (GET)
    public List<Student> getStudents()
    {
        return studentRepository.findAll();
    }

    // INSERT STUDENT (POST)
    public void addNewStudent(Student student)
    {
        // VALIDATES IF THE EMAIL IS DIFFERENT FROM OTHERS
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());

        if (studentOptional.isPresent())
        {
            throw new IllegalStateException("email taken");
        }

        studentRepository.save(student);
    }

    // DELETE STUDENT (DELETE)
    public void deleteStudent(Long studentId)
    {
        boolean exists = studentRepository.existsById(studentId);

        if (!exists)
        {
            throw new IllegalStateException("student with ID: " + studentId + " does not exist");
        }

        studentRepository.deleteById(studentId);

    }

    // UPDATE THE NAME AND EMAIL OF A STUDENT (PUT)
    // THIS ANOTATION MAKES THE ENTITY GOES INTO A MANAGED STATE
    @Transactional
    public void updateStudent(Long studentId, String name, String email)
    {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException("student with ID: " + studentId + " does not exist"));

        if (name != null && name.length() > 0 && !Objects.equals(student.getName(), name))
        {
            student.setName(name);
        }

        if (email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email))
        {
            // VALIDATES IF THE EMAIL IS DIFFERENT FROM OTHERS
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);

            if (studentOptional.isPresent())
            {
                throw new IllegalStateException("email taken");
            }

            student.setEmail(email);
        }
    }
}
