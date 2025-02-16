/*
 * Copyright 2024 Roland Gisler, HSLU Informatik, Switzerland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.hslu.swda.micro;

import java.util.List;

import jakarta.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.hslu.swda.business.Students;
import ch.hslu.swda.model.Student;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.QueryValue;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller für Customers.
 */
@Controller("/api/v1/students")
public class StudentController {

    private static final Logger LOG = LoggerFactory.getLogger(StudentController.class);

    @Inject
    private Students students;

    /**
     * Studenten suchen.
     *
     * @param lastName der gesuchten Studenten.
     * @return Liste aller Studierenden.
     */
    @Tag(name = "Student")
    @Get("/{?lastname}")
    public List<Student> search(@QueryValue("lastname") @Nullable final String lastName) {
        final List<Student> result;
        if (lastName != null) {
            result = students.findByLastName(lastName);
            LOG.info("REST: Liste aller Studenten ({}) mit Name '{}' liefern.", result.size(), lastName);
        } else {
            result = students.getAll();
            LOG.info("REST: Liste aller Studenten ({}).", result.size());
        }
        return result;
    }

    /**
     * Student mit Id lesen.
     *
     * @param id Id des Studenten.
     * @return Student.
     */
    @Tag(name = "Student")
    @Get("/{id}")
    public Student get(final long id) {
        final Student student = students.getById(id);
        LOG.info("REST: Student {} {}.", id, student != null ? "geliefert" : "nicht gefunden");
        return student;
    }

    /**
     * Erzeugt einen neuen Studenten.
     *
     * @param student Student.
     * @return Student der gespeichert wurde.
     */
    @Tag(name = "Student")
    @Post("/")
    public Student create(@Body final Student student) {
        final Student created = students.create(student);
        LOG.info("REST: Student {} gespeichert.", created);
        return student;
    }

    /**
     * Erzeugt einen neuen Studenten.
     *
     * @param id id des Studenten.
     * @param student Student.
     * @return Student der gespeichert wurde.
     */
    @Tag(name = "Student")
    @Put("/{id}")
    public Student update(final long id, @Body final Student student) {
        Student updated = students.update(id, student);
        LOG.info("REST: Student {} aktualisiert.", updated);
        return updated;
    }

    /**
     * Löscht Student.
     *
     * @param id Id des Studenten.
     */
    @Tag(name = "Student")
    @Delete("/{id}")
    public void delete(final int id) {
        final boolean deleted = students.delete(id);
        LOG.info("REST: Student mit id={} {}geloescht.", id, deleted ? "" : "nicht ");
    }
}
