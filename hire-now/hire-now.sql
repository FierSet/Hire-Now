-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 26-11-2022 a las 20:58:34
-- Versión del servidor: 10.5.16-MariaDB
-- Versión de PHP: 7.3.32

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `id18554423_hier_now`
--
CREATE DATABASE IF NOT EXISTS `id18554423_hier_now` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `id18554423_hier_now`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `acosta_CVpres`
--

CREATE TABLE `acosta_CVpres` (
  `CV` varchar(3000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `presupuestos` varchar(3000) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `acosta_notificacion`
--

CREATE TABLE `acosta_notificacion` (
  `id_user_notificacion` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_oferta` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `imagen` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `titulo` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tipo` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `acosta_result`
--

CREATE TABLE `acosta_result` (
  `user` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_contrato` int(100) DEFAULT NULL,
  `comentario_resenas` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `resenas` float DEFAULT NULL,
  `comentarios_exito` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `exito` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `acosta_result`
--

INSERT INTO `acosta_result` (`user`, `id_contrato`, `comentario_resenas`, `resenas`, `comentarios_exito`, `exito`) VALUES
('miguel', 6, '', 5, NULL, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contratos`
--

CREATE TABLE `contratos` (
  `id_contrato` int(100) NOT NULL,
  `id_oferta` int(100) DEFAULT NULL,
  `Contratante` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `contratista` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pago` float DEFAULT NULL,
  `estado` int(1) DEFAULT NULL,
  `permiso_contratante` tinyint(1) DEFAULT NULL,
  `permiso_contratista` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `contratos`
--

INSERT INTO `contratos` (`id_contrato`, `id_oferta`, `Contratante`, `contratista`, `pago`, `estado`, `permiso_contratante`, `permiso_contratista`) VALUES
(2, 5, 'miguel', 'juan', 100.9, 2, 1, 1),
(3, 5, 'miguel', 'juan', 100.8, 2, 0, 0),
(4, 7, 'miguel', 'juan', 1002, 2, 0, 0),
(5, 5, 'miguel', 'juan', 100.8, 1, 0, 0),
(6, 5, 'miguel', 'acosta', 100.8, 0, 0, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `david2_CVpres`
--

CREATE TABLE `david2_CVpres` (
  `CV` varchar(3000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `presupuestos` varchar(3000) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `david2_notificacion`
--

CREATE TABLE `david2_notificacion` (
  `id_user_notificacion` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_oferta` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `imagen` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `titulo` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tipo` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `david2_result`
--

CREATE TABLE `david2_result` (
  `user` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_contrato` int(100) DEFAULT NULL,
  `comentario_resenas` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `resenas` float DEFAULT NULL,
  `comentarios_exito` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `exito` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `David_CVpres`
--

CREATE TABLE `David_CVpres` (
  `CV` varchar(3000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `presupuestos` varchar(3000) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `David_notificacion`
--

CREATE TABLE `David_notificacion` (
  `id_user_notificacion` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_oferta` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `imagen` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `titulo` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tipo` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `David_result`
--

CREATE TABLE `David_result` (
  `user` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_contrato` int(100) DEFAULT NULL,
  `comentario_resenas` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `resenas` float DEFAULT NULL,
  `comentarios_exito` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `exito` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `gera_CVpres`
--

CREATE TABLE `gera_CVpres` (
  `CV` varchar(3000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `presupuestos` varchar(3000) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `gera_notificacion`
--

CREATE TABLE `gera_notificacion` (
  `id_user_notificacion` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_oferta` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `imagen` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `titulo` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tipo` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `gera_result`
--

CREATE TABLE `gera_result` (
  `user` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_contrato` int(100) DEFAULT NULL,
  `comentario_resenas` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `resenas` float DEFAULT NULL,
  `comentarios_exito` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `exito` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `gusman_CVpres`
--

CREATE TABLE `gusman_CVpres` (
  `CV` varchar(3000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `presupuestos` varchar(3000) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `gusman_notificacion`
--

CREATE TABLE `gusman_notificacion` (
  `id_user_notificacion` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_oferta` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `imagen` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `titulo` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tipo` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `gusman_result`
--

CREATE TABLE `gusman_result` (
  `user` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_contrato` int(100) DEFAULT NULL,
  `comentario_resenas` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `resenas` float DEFAULT NULL,
  `comentarios_exito` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `exito` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `juan_CVpres`
--

CREATE TABLE `juan_CVpres` (
  `CV` varchar(3000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `presupuestos` varchar(3000) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `juan_notificacion`
--

CREATE TABLE `juan_notificacion` (
  `id_user_notificacion` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_oferta` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `imagen` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `titulo` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tipo` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `juan_notificacion`
--

INSERT INTO `juan_notificacion` (`id_user_notificacion`, `id_oferta`, `imagen`, `titulo`, `tipo`) VALUES
('miguel', '11', '', 'solicitas plomero', 0),
('gera', '11', '', 'solicitas plomero', 0),
('miguel', '11', '', 'solicitas plomero', 0),
('miguel', '11', '', 'solicitas plomero', 0),
('miguel', '11', '', 'solicitas plomero', 0),
('miguel', '11', '', 'solicitas plomero', 0),
('miguel', '11', '', 'solicitas plomero', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `juan_result`
--

CREATE TABLE `juan_result` (
  `user` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_contrato` int(100) DEFAULT NULL,
  `comentario_resenas` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `resenas` float DEFAULT NULL,
  `comentarios_exito` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `exito` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `juan_result`
--

INSERT INTO `juan_result` (`user`, `id_contrato`, `comentario_resenas`, `resenas`, `comentarios_exito`, `exito`) VALUES
('miguel', 2, 'AAAAADDD', 3.45, NULL, 4.95),
('miguel', 3, '', 5, NULL, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lopez_CVpres`
--

CREATE TABLE `lopez_CVpres` (
  `CV` varchar(3000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `presupuestos` varchar(3000) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lopez_notificacion`
--

CREATE TABLE `lopez_notificacion` (
  `id_user_notificacion` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_oferta` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `imagen` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `titulo` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tipo` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lopez_result`
--

CREATE TABLE `lopez_result` (
  `user` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_contrato` int(100) DEFAULT NULL,
  `comentario_resenas` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `resenas` float DEFAULT NULL,
  `comentarios_exito` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `exito` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `miguel_CVpres`
--

CREATE TABLE `miguel_CVpres` (
  `CV` varchar(3000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `presupuestos` varchar(3000) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `miguel_notificacion`
--

CREATE TABLE `miguel_notificacion` (
  `id_user_notificacion` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_oferta` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `imagen` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `titulo` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tipo` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `miguel_notificacion`
--

INSERT INTO `miguel_notificacion` (`id_user_notificacion`, `id_oferta`, `imagen`, `titulo`, `tipo`) VALUES
('gera', '18', 'https://tecnovehio.files.wordpress.com/2013/01/engranaje-eje-paralelo-2.gif', 'Se requiere mecanico', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `miguel_result`
--

CREATE TABLE `miguel_result` (
  `user` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_contrato` int(100) DEFAULT NULL,
  `comentario_resenas` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `resenas` float DEFAULT NULL,
  `comentarios_exito` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `exito` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `miguel_result`
--

INSERT INTO `miguel_result` (`user`, `id_contrato`, `comentario_resenas`, `resenas`, `comentarios_exito`, `exito`) VALUES
('juan', 2, '', 5, NULL, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Ofertas`
--

CREATE TABLE `Ofertas` (
  `Imagen` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Titulo` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `descripcion` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_user` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `Informacion_general` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `profecion_requerida` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ID` int(100) NOT NULL,
  `pago` float DEFAULT NULL,
  `vacantes` int(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `Ofertas`
--

INSERT INTO `Ofertas` (`Imagen`, `Titulo`, `descripcion`, `id_user`, `Informacion_general`, `profecion_requerida`, `ID`, `pago`, `vacantes`) VALUES
('', 'solicitas plomero', 'destapar el caño', 'juan', 'y lacañeria', 'ingenieria en sistemas computacionales', 11, 120, 121),
('', 'trabajo de promeria', 'se requiere destapar caños', 'Yo', 'y coladeras', 'ingenieria en sistemas computacionales', 12, 3000, 1),
('https://i.pinimg.com/originals/09/d1/bd/09d1bd0b88cb437fbe61fed01a44c872.gif', 'Solicita electricista', 'Se requiere electricista para una red domestica de 130V.', 'miguel', 'El inmueble cuenta con 3 cuartos, 4 baños, una cocina y un patio.', 'ingenieria en sistemas computacionales', 17, 12500, 5),
('https://i.pinimg.com/originals/f0/89/a4/f089a4baaec0ceeefed2d6d5ee241744.gif', 'Se requiere mecanico', 'se requiere mecánicos para motores eolicos.', 'miguel', 'se requieren conocimientos en motores eolicos para el mantenimiento, reparación e instalación.', 'ingenieria en sistemas computacionales', 19, 12000, 5),
('https://media.tenor.com/bToML9V3LhoAAAAj/medicina-hmpb-medicina.gif', 'Se solicita medico.', 'Se requiere medico especialista.', 'miguel', 'Requerido medico con 1 año de experiencia.', 'licenciatura en medicina', 20, 20000, 3),
('https://media2.giphy.com/media/xT9IgqLDd5yOxGK35K/giphy.gif?cid=790b7611e07bff8a3e824f2e59e1a39bd6ca7f08f0b09e21&rid=giphy.gif&ct=g', 'Se requiere programador web.', 'Se requiere desarrollador web. con 6 mese de experiencia.', 'miguel', 'Requisitos:\nconocimiento en html5\nPHP\n', 'ingenieria en sistemas computacionales', 21, 12000, 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `profesion`
--

CREATE TABLE `profesion` (
  `profesion` varchar(50) DEFAULT NULL,
  `cantidad` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `profesion`
--

INSERT INTO `profesion` (`profesion`, `cantidad`) VALUES
('ingenieria en sistemas computacionales', 0),
('ingenieria en Electrica', 0),
('ingenieria en Electronica', 0),
('ingenieria Mecanica', 0),
('ingenieria en Gestion', 0),
('ingenieria Industrial', 0),
('ingenieria en Mecatronica', 0),
('licenciatura en derecho', 0),
('licenciatura en medicina', 0),
('ingenieria en Electrica', 0),
('ingenieria en Electronica', 0),
('ingenieria Mecanica', 0),
('ingenieria en Gestion', 0),
('ingenieria Industrial', 0),
('ingenieria en Mecatronica', 0),
('licenciatura en derecho', 0),
('licenciatura en medicina', 0),
('Ninguna', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(100) NOT NULL,
  `user` varchar(50) NOT NULL,
  `profesion` varchar(50) DEFAULT NULL,
  `RFC` varchar(20) DEFAULT NULL,
  `nombres` varchar(20) DEFAULT NULL,
  `apellidoP` varchar(15) DEFAULT NULL,
  `apellidoM` varchar(15) DEFAULT NULL,
  `password` varchar(50) NOT NULL,
  `comprobado` int(1) DEFAULT NULL,
  `imagen` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `user`, `profesion`, `RFC`, `nombres`, `apellidoP`, `apellidoM`, `password`, `comprobado`, `imagen`) VALUES
(1, 'miguel', 'ingenieria en sistemas computacionales', 'RANDOM001110', 'miguel Angel', 'ortega', 'zacarias', '1234', 1, 'https://www.dropbox.com/s/k9j2cl56p4ukf2w/instituto-tecnologico-de-queretaro-zorros-itq-american-football-logo-clip-art-others.png?dl=1'),
(3, 'juan', 'ingenieria en sistemas computacionales', 'juanRandom', 'juan', 'pablo', 'scoot', 'juan', 1, ''),
(4, 'David', NULL, NULL, NULL, NULL, NULL, '89914091', 0, NULL),
(5, 'Yo', 'ingenieria Mecanica', 'Verga', 'Yo Soy', 'La', 'Mera', '1234', 1, ''),
(6, 'david2', NULL, NULL, NULL, NULL, NULL, '123', 0, NULL),
(7, 'acosta', 'ingenieria en sistemas computacionales', 'OEMZ', 'Miguel Angel', 'ortega', 'zacarias', '1234', 1, ''),
(8, 'gusman', 'ingenieria en sistemas computacionales', 'OEMZ', 'Miguel', 'ortega', 'zacarias', '1234', 1, ''),
(9, 'gera', 'ingenieria Mecanica', 'RANDOM22331GE', 'gerardo', 'guzman', 'acosta', '1234', 1, 'https://www.dropbox.com/s/k9j2cl56p4ukf2w/instituto-tecnologico-de-queretaro-zorros-itq-american-football-logo-clip-art-others.png?dl=1\r\n'),
(10, 'lopez', NULL, NULL, NULL, NULL, NULL, '1234', 0, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Yo_CVpres`
--

CREATE TABLE `Yo_CVpres` (
  `CV` varchar(3000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `presupuestos` varchar(3000) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Yo_notificacion`
--

CREATE TABLE `Yo_notificacion` (
  `id_user_notificacion` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_oferta` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `imagen` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `titulo` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tipo` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `Yo_notificacion`
--

INSERT INTO `Yo_notificacion` (`id_user_notificacion`, `id_oferta`, `imagen`, `titulo`, `tipo`) VALUES
('gusman', '12', '', 'trabajo de promeria', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Yo_result`
--

CREATE TABLE `Yo_result` (
  `user` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_contrato` int(100) DEFAULT NULL,
  `comentario_resenas` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `resenas` float DEFAULT NULL,
  `comentarios_exito` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `exito` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `contratos`
--
ALTER TABLE `contratos`
  ADD PRIMARY KEY (`id_contrato`);

--
-- Indices de la tabla `Ofertas`
--
ALTER TABLE `Ofertas`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user` (`user`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `contratos`
--
ALTER TABLE `contratos`
  MODIFY `id_contrato` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `Ofertas`
--
ALTER TABLE `Ofertas`
  MODIFY `ID` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
