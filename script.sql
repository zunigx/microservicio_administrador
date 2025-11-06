
-- ================== COORDINADORES ====================
INSERT INTO usuarios (nombre, email, tipo_usuario, activo, area_coordinacion, nivel_acceso) VALUES 
('Dra. María Elena González Pérez', 'maria.gonzalez@uteq.edu.mx', 'COORDINADOR', true, 'Ingeniería en Desarrollo de Software', 'ADMINISTRADOR'),
('Dr. Carlos Alberto Ramírez', 'carlos.ramirez@uteq.edu.mx', 'COORDINADOR', true, 'Ingeniería Industrial', 'COORDINADOR'),
('Mtra. Ana Patricia Martínez', 'ana.martinez@uteq.edu.mx', 'COORDINADOR', true, 'Tecnologías de la Información', 'COORDINADOR'),
('Dr. Jorge Luis Fernández', 'jorge.fernandez@uteq.edu.mx', 'COORDINADOR', true, 'Ingeniería en Sistemas', 'COORDINADOR');

-- ================== PROFESORES ====================
INSERT INTO usuarios (nombre, email, tipo_usuario, activo) VALUES 
('Ing. Juan Manuel Pérez López', 'juan.perez@uteq.edu.mx', 'PROFESOR', true),
('Mtra. Laura Sofía García Hernández', 'laura.garcia@uteq.edu.mx', 'PROFESOR', true),
('Dr. Roberto Carlos Sánchez', 'roberto.sanchez@uteq.edu.mx', 'PROFESOR', true),
('Ing. Martha Elena Rodríguez', 'martha.rodriguez@uteq.edu.mx', 'PROFESOR', false),
('Mtro. Francisco Javier Torres', 'francisco.torres@uteq.edu.mx', 'PROFESOR', true),
('Dra. Gabriela Ivonne Morales', 'gabriela.morales@uteq.edu.mx', 'PROFESOR', true);

-- ================== ALUMNOS ====================
INSERT INTO usuarios (nombre, email, tipo_usuario, activo, matricula, carrera, semestre) VALUES 
('Diego Alejandro Martínez Cruz', 'diego.martinez@uteq.edu.mx', 'ALUMNO', true, '2021110001', 'Ingeniería en Desarrollo de Software', 6),
('Andrea Carolina López Gómez', 'andrea.lopez@uteq.edu.mx', 'ALUMNO', true, '2021110002', 'Ingeniería en Desarrollo de Software', 6),
('Luis Fernando Hernández Ruiz', 'luis.hernandez@uteq.edu.mx', 'ALUMNO', true, '2022110003', 'Ingeniería Industrial', 4),
('María Fernanda Ramírez Silva', 'maria.ramirez@uteq.edu.mx', 'ALUMNO', true, '2022110004', 'Tecnologías de la Información', 4),
('Carlos Eduardo Flores Pérez', 'carlos.flores@uteq.edu.mx', 'ALUMNO', false, '2023110005', 'Ingeniería en Sistemas', 2),
('Ana Gabriela Mendoza Torres', 'ana.mendoza@uteq.edu.mx', 'ALUMNO', true, '2023110006', 'Ingeniería en Desarrollo de Software', 2),
('Jorge Alberto Castro Vargas', 'jorge.castro@uteq.edu.mx', 'ALUMNO', true, '2021110007', 'Ingeniería Industrial', 6),
('Daniela Patricia Moreno León', 'daniela.moreno@uteq.edu.mx', 'ALUMNO', true, '2022110008', 'Tecnologías de la Información', 4);

-- ================== DIVISIONES PARA PROFESORES ====================
-- Profesor Juan Manuel Pérez (ID: 5) - Divisiones 1 y 2
INSERT INTO profesores_divisiones (usuario_id, division_id, activo) VALUES 
(5, 1, true),
(5, 2, true);

-- Profesora Laura García (ID: 6) - Divisiones 3 y 4
INSERT INTO profesores_divisiones (usuario_id, division_id, activo) VALUES 
(6, 3, true),
(6, 4, true);

-- Profesor Roberto Sánchez (ID: 7) - División 5
INSERT INTO profesores_divisiones (usuario_id, division_id, activo) VALUES 
(7, 5, true);

-- Profesor Francisco Torres (ID: 9) - Divisiones 6 y 7
INSERT INTO profesores_divisiones (usuario_id, division_id, activo) VALUES 
(9, 6, true),
(9, 7, true);