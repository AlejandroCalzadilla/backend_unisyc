package com.si2.parcial2.config;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import com.si2.parcial2.entities.*;
import com.si2.parcial2.repositories.*;

@Component
public class DataSeeder {

    @Autowired
    private FacultadRepository facultadRepository;

    @Autowired
    private CarreraRepository carreraRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private HorarioRepository horarioRepository;

    @Autowired
    private ModuloRepository moduloRepository;

    private boolean dataInitialized = false;

    @EventListener(ContextRefreshedEvent.class)
    public void seedData() {
        if (dataInitialized) {
            return;
        }

        try {
            System.out.println("⏳ Esperando a que Hibernate cree las tablas (3 segundos)...");
            Thread.sleep(3000);
            System.out.println("✅ Iniciando seeding de datos");

            seedFacultades();
            seedCarreras();
            seedMaterias();
            seedAulas();
            seedProfesores();
            seedGrupos();
            seedHorarios();
            seedModulos();

            dataInitialized = true;
        } catch (Exception e) {
            System.err.println("⚠️  Error al seedear datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void seedFacultades() {
        if (facultadRepository.count() == 0) {
            Facultad f1 = new Facultad();
            f1.setNombre("Facultad de Ingeniería");
            f1.setDescripcion("Área de tecnología e ingeniería");
            facultadRepository.save(f1);

            Facultad f2 = new Facultad();
            f2.setNombre("Facultad de Ciencias");
            f2.setDescripcion("Área de ciencias exactas");
            facultadRepository.save(f2);

            System.out.println("✅ Facultades creadas");
        }
    }

    private void seedCarreras() {
        if (carreraRepository.count() == 0) {
            Facultad ingenieria = facultadRepository.findAll().iterator().next();

            Carrera c1 = new Carrera();
            c1.setNombre("Ingeniería en Sistemas");
            c1.setFacultad(ingenieria);
            c1.setCodigo( 204);
           
            carreraRepository.save(c1);

            Carrera c2 = new Carrera();
            c2.setNombre("Ingeniería Civil");
            c2.setFacultad(ingenieria);
            c2.setCodigo(101);
            carreraRepository.save(c2);

            System.out.println("✅ Carreras creadas");
        }
    }

    private void seedMaterias() {
        if (materiaRepository.count() == 0) {
            Materia m1 = new Materia();
            m1.setNombre("Programación I");
            m1.setSigla("PRO1");
            m1.setSemestre(1);
            materiaRepository.save(m1);

            Materia m2 = new Materia();
            m2.setNombre("Bases de Datos");
            m2.setSigla("BD");
            m2.setSemestre(2);
            materiaRepository.save(m2);

            Materia m3 = new Materia();
            m3.setNombre("Estructura de Datos");
            m3.setSigla("ED");
            m3.setSemestre(2);
            materiaRepository.save(m3);

            System.out.println("✅ Materias creadas");
        }
    }

    private void seedAulas() {
        if (aulaRepository.count() == 0) {
            Aula a1 = new Aula();
            a1.setNumero(101);
            a1.setCapacidad(30);
            a1.setTipo("aula");
            aulaRepository.save(a1);

            Aula a2 = new Aula();
            a2.setNumero(102);
            a2.setCapacidad(40);
            a2.setTipo("aula");
            aulaRepository.save(a2);

            Aula a3 = new Aula();
            a3.setNumero(201);
            a3.setCapacidad(25);
            a3.setTipo("aula");
            aulaRepository.save(a3);

            System.out.println("✅ Aulas creadas");
        }
    }

    private void seedProfesores() {
        if (profesorRepository.count() == 0) {
            profesor p1 = new profesor();
            p1.setNombre("Dr. Juan Pérez");
            p1.setDireccion("calle falsa 123");
            p1.setTelefono(798456123);
         
            profesorRepository.save(p1);

            profesor p2 = new profesor();
            p2.setNombre("Dra. María García");
            p2.setDireccion("avenida siempre viva 456");
            p2.setTelefono(987654321);
            profesorRepository.save(p2);

            System.out.println("✅ Profesores creados");
        }
    }

    private void seedGrupos() {
        if (grupoRepository.count() == 0) {
            Carrera carrera = carreraRepository.findAll().iterator().next();
            Materia materia = materiaRepository.findAll().iterator().next();

            Grupo g1 = new Grupo();
            g1.setNombre("Grupo A");
            g1.setCarrera(carrera);
            g1.setMateria(materia);
            
            grupoRepository.save(g1);

            Grupo g2 = new Grupo();
            g2.setNombre("Grupo B");
            g2.setCarrera(carrera);
            g2.setMateria(materia);
            grupoRepository.save(g2);

            System.out.println("✅ Grupos creados");
        }
    }

    private void seedHorarios() {
        if (horarioRepository.count() == 0) {
            Grupo grupo = grupoRepository.findAll().iterator().next();
            Aula aula = aulaRepository.findAll().iterator().next();
            profesor profesor = profesorRepository.findAll().iterator().next();

            Horario h1 = new Horario();
            h1.setGrupo(grupo);
            h1.setAula(aula);
            
            h1.setDia("Lunes");
            h1.setHoraInicio(LocalTime.parse("08:00"));
            h1.setHoraFin(LocalTime.parse("10:00"));
            horarioRepository.save(h1);

            Horario h2 = new Horario();
            h2.setGrupo(grupo);
            h2.setAula(aula);
          
            h2.setDia("Miércoles");
            h2.setHoraInicio(LocalTime.parse("08:00"));
            h2.setHoraFin(LocalTime.parse("10:00"));
            horarioRepository.save(h2);

            System.out.println("✅ Horarios creados");
        }
    }

    private void seedModulos() {
        if (moduloRepository.count() == 0) {
            Materia materia = materiaRepository.findAll().iterator().next();

            Modulo m1 = new Modulo();
            m1.setNumero(236);
            m1.setLatitud(10.12345f);
            m1.setLongitud(-75.12345f);
            moduloRepository.save(m1);

            Modulo m2 = new Modulo();
            m2.setNumero(237);
            m2.setLatitud(10.54321f);
            m2.setLongitud(-75.54321f);
            moduloRepository.save(m2);

            System.out.println("✅ Módulos creados");
        }
    }
}
