-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 23-01-2017 a las 21:31:13
-- Versión del servidor: 10.1.19-MariaDB
-- Versión de PHP: 5.6.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `sexshop`
--
CREATE DATABASE IF NOT EXISTS `sexshop` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `sexshop`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `articulos`
--

CREATE TABLE `articulos` (
  `idArticulos` int(11) NOT NULL,
  `descripccion` varchar(100) NOT NULL,
  `costo` double(10,2) DEFAULT NULL,
  `precio` double(10,2) DEFAULT NULL,
  `fechaCompra` date DEFAULT NULL,
  `fk_idProveedores` int(11) NOT NULL,
  `fk_idCategorias` int(11) NOT NULL,
  `fk_idEstados` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='	';

--
-- Volcado de datos para la tabla `articulos`
--

INSERT INTO `articulos` (`idArticulos`, `descripccion`, `costo`, `precio`, `fechaCompra`, `fk_idProveedores`, `fk_idCategorias`, `fk_idEstados`) VALUES
(5, 'test pelicula', 1.00, 2.00, '2017-01-02', 1, 1, 1),
(6, 'test articulo', 1.00, 2.00, '2017-01-02', 1, 2, 1),
(7, 'test a eliminar', 3.00, 3.50, '2017-01-22', 1, 2, 3),
(8, 'skjdks', 2.00, 30.00, '2017-01-22', 1, 1, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `caja`
--

CREATE TABLE `caja` (
  `idCaja` int(11) NOT NULL,
  `fechaapertura` datetime DEFAULT NULL,
  `fechacierre` datetime DEFAULT NULL,
  `abierta` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `caja`
--

INSERT INTO `caja` (`idCaja`, `fechaapertura`, `fechacierre`, `abierta`) VALUES
(1, '2017-01-22 00:00:00', '2017-01-22 00:00:00', 0),
(2, '2017-01-22 00:00:00', '2017-01-22 00:00:00', 0),
(3, '2017-01-22 00:00:00', NULL, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categorias`
--

CREATE TABLE `categorias` (
  `idCategorias` int(11) NOT NULL,
  `descripcion` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `categorias`
--

INSERT INTO `categorias` (`idCategorias`, `descripcion`) VALUES
(1, 'Alquiler'),
(2, 'Venta');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `idcliente` int(11) NOT NULL,
  `fk_idDescuentoCli` int(11) NOT NULL,
  `notas` blob,
  `nombre` varchar(45) DEFAULT NULL,
  `apellido` varchar(45) DEFAULT NULL,
  `direccion` varchar(45) DEFAULT NULL,
  `mail` varchar(45) DEFAULT NULL,
  `telefono` varchar(45) DEFAULT NULL,
  `dni` mediumtext,
  `fechanac` date DEFAULT NULL,
  `codigoCliente` varchar(45) NOT NULL,
  `fk_idEstados` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`idcliente`, `fk_idDescuentoCli`, `notas`, `nombre`, `apellido`, `direccion`, `mail`, `telefono`, `dni`, `fechanac`, `codigoCliente`, `fk_idEstados`) VALUES
(1, 1, 0x74657374206e6f7461, 'Bruno', 'Bollati', '9 de julio', 'bruno@bollati.com', '155739305', '30122490', '1983-03-16', '00001', 2),
(2, 2, 0x74657374206e6f7461, 'Azul', 'Gonzalez', '9 de julio', 'pocha@pocha.com', '156077108', '28963674', '1981-05-13', '12a', 1),
(3, 1, 0x616263, 'abc', 'abc', 'abc', 'abc', '12265', '189218', '2016-11-20', 'abc', 1),
(4, 1, 0x617364, 'asd', 'asd', 'asd', 'asd', 'asd', '123', '2016-11-20', 'asd', 3),
(5, 1, 0x717765, 'qwe', 'qweq', 'qwe', 'qwe', 'qwe', '1234', '2016-11-20', 'qwe', 1),
(6, 1, 0x7a7863, 'zxc', 'zxc', 'zxc', 'zxc', 'zxc', '1234', '2016-11-20', 'zxcz', 1),
(7, 1, 0x6a6b6c, 'jkl', 'jkl', 'jkl', 'jkl', 'jkl', '123', '2016-11-20', 'jkl', 1),
(8, 1, 0x696f70, 'iop', 'iop', 'iop', 'iop', 'iop', '123', '2016-11-20', 'iop', 1),
(9, 1, 0x626e6d, 'bnm', 'bnm', 'bnm', 'bnm', 'bnm', '123', '2016-11-20', 'bnm', 1),
(10, 1, 0x637662, 'cvb', 'cvb', 'cvb', 'cvb', 'cvb', '456', '2016-11-20', 'cvb', 1),
(11, 1, 0x727479, 'rty', 'try', 'rty', 'rty', 'rty', '789', '2016-11-20', 'rty', 2),
(12, 1, 0x646667, 'dfg', 'dfg', 'dfg', 'dfg', 'dfg', '890', '2016-11-20', 'dfg', 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cuentacorriente`
--

CREATE TABLE `cuentacorriente` (
  `idCuentaCorriente` int(11) NOT NULL,
  `fk_idcliente` int(11) NOT NULL,
  `saldo` decimal(10,0) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `descuentocli`
--

CREATE TABLE `descuentocli` (
  `idDescuentoCli` int(11) NOT NULL,
  `descripcion` varchar(45) DEFAULT NULL,
  `porcentaje` int(11) DEFAULT NULL,
  `importe` double(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `descuentocli`
--

INSERT INTO `descuentocli` (`idDescuentoCli`, `descripcion`, `porcentaje`, `importe`) VALUES
(1, 'sin descuento', 0, 0.00),
(2, 'descuento 5%', 5, 0.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detallecaja`
--

CREATE TABLE `detallecaja` (
  `idDetalleCaja` int(11) NOT NULL,
  `fk_idCaja` int(11) NOT NULL,
  `concepto` varchar(45) DEFAULT NULL,
  `monto` decimal(10,0) DEFAULT NULL,
  `fk_idCabVenta` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detallemovemientostock`
--

CREATE TABLE `detallemovemientostock` (
  `idStock` int(11) NOT NULL,
  `fk_idArticulos` int(11) NOT NULL,
  `fk_idMovimientoStock` int(11) NOT NULL,
  `tipomovimiento` varchar(45) DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalleoperacion`
--

CREATE TABLE `detalleoperacion` (
  `idDetVenta` int(11) NOT NULL,
  `fk_idArticulos` int(11) NOT NULL,
  `precio` decimal(10,0) DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estados`
--

CREATE TABLE `estados` (
  `idEstados` int(11) NOT NULL,
  `Descripcion` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `estados`
--

INSERT INTO `estados` (`idEstados`, `Descripcion`) VALUES
(1, 'activo'),
(2, 'inactivo'),
(3, 'Eliminado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `movimientostock`
--

CREATE TABLE `movimientostock` (
  `idMovimientoStock` int(11) NOT NULL,
  `concepto` varchar(45) DEFAULT NULL,
  `fecha` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `operacion`
--

CREATE TABLE `operacion` (
  `idCabVenta` int(11) NOT NULL,
  `fk_idDetVenta` int(11) NOT NULL,
  `fk_idTipoperacion` int(11) NOT NULL,
  `fk_idcliente` int(11) NOT NULL,
  `fk_idUsuarioLogueado` int(11) NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `idUsuarioVendedor` int(11) DEFAULT NULL,
  `total` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedores`
--

CREATE TABLE `proveedores` (
  `idProveedores` int(11) NOT NULL,
  `razonsocial` varchar(45) DEFAULT NULL,
  `direccion` varchar(45) DEFAULT NULL,
  `telefono` varchar(45) DEFAULT NULL,
  `mail` varchar(45) DEFAULT NULL,
  `codigoProveedor` varchar(45) NOT NULL,
  `fk_idEstados` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `proveedores`
--

INSERT INTO `proveedores` (`idProveedores`, `razonsocial`, `direccion`, `telefono`, `mail`, `codigoProveedor`, `fk_idEstados`) VALUES
(1, 'prueba', 'dire test', 'tel test', 'mail test', '00001', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

CREATE TABLE `roles` (
  `idRoles` int(11) NOT NULL,
  `descripcion` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `roles`
--

INSERT INTO `roles` (`idRoles`, `descripcion`) VALUES
(1, 'Administrador'),
(2, 'Operador');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipoperacion`
--

CREATE TABLE `tipoperacion` (
  `idTipoperacion` int(11) NOT NULL,
  `concepto` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tipoperacion`
--

INSERT INTO `tipoperacion` (`idTipoperacion`, `concepto`) VALUES
(1, 'Alquiler'),
(2, 'Venta');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `idUsuario` int(11) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `fk_idRoles` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`idUsuario`, `nombre`, `password`, `fk_idRoles`) VALUES
(1, 'admin', '1234', 1),
(2, 'pruebaop', '1', 2),
(3, 'pruebams', '1', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `articulos`
--
ALTER TABLE `articulos`
  ADD PRIMARY KEY (`idArticulos`,`fk_idProveedores`,`fk_idCategorias`,`fk_idEstados`),
  ADD UNIQUE KEY `idArticulos_UNIQUE` (`idArticulos`),
  ADD KEY `fk_Articulos_Proveedores1_idx` (`fk_idProveedores`),
  ADD KEY `fk_Articulos_Generos1_idx` (`fk_idCategorias`),
  ADD KEY `fk_Articulos_Estados1_idx` (`fk_idEstados`);

--
-- Indices de la tabla `caja`
--
ALTER TABLE `caja`
  ADD PRIMARY KEY (`idCaja`);

--
-- Indices de la tabla `categorias`
--
ALTER TABLE `categorias`
  ADD PRIMARY KEY (`idCategorias`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`idcliente`,`fk_idDescuentoCli`,`fk_idEstados`),
  ADD UNIQUE KEY `codigoCliente_UNIQUE` (`codigoCliente`),
  ADD KEY `fk_cliente_DescuentoCli1_idx` (`fk_idDescuentoCli`),
  ADD KEY `fk_cliente_Estados1_idx` (`fk_idEstados`);

--
-- Indices de la tabla `cuentacorriente`
--
ALTER TABLE `cuentacorriente`
  ADD PRIMARY KEY (`idCuentaCorriente`,`fk_idcliente`),
  ADD KEY `fk_CuentaCorriente_cliente1_idx` (`fk_idcliente`);

--
-- Indices de la tabla `descuentocli`
--
ALTER TABLE `descuentocli`
  ADD PRIMARY KEY (`idDescuentoCli`);

--
-- Indices de la tabla `detallecaja`
--
ALTER TABLE `detallecaja`
  ADD PRIMARY KEY (`idDetalleCaja`,`fk_idCaja`),
  ADD KEY `fk_DetalleCaja_Caja1_idx` (`fk_idCaja`);

--
-- Indices de la tabla `detallemovemientostock`
--
ALTER TABLE `detallemovemientostock`
  ADD PRIMARY KEY (`idStock`,`fk_idArticulos`,`fk_idMovimientoStock`),
  ADD KEY `fk_Stock_Articulos1_idx` (`fk_idArticulos`),
  ADD KEY `fk_DetalleMovemientoStock_MovimientoStock1_idx` (`fk_idMovimientoStock`);

--
-- Indices de la tabla `detalleoperacion`
--
ALTER TABLE `detalleoperacion`
  ADD PRIMARY KEY (`idDetVenta`,`fk_idArticulos`),
  ADD KEY `fk_DetVenta_Articulos1_idx` (`fk_idArticulos`);

--
-- Indices de la tabla `estados`
--
ALTER TABLE `estados`
  ADD PRIMARY KEY (`idEstados`);

--
-- Indices de la tabla `movimientostock`
--
ALTER TABLE `movimientostock`
  ADD PRIMARY KEY (`idMovimientoStock`);

--
-- Indices de la tabla `operacion`
--
ALTER TABLE `operacion`
  ADD PRIMARY KEY (`idCabVenta`,`fk_idDetVenta`,`fk_idTipoperacion`,`fk_idcliente`,`fk_idUsuarioLogueado`),
  ADD KEY `fk_CabVenta_DetVenta1_idx` (`fk_idDetVenta`),
  ADD KEY `fk_CabVenta_Tipoperacion1_idx` (`fk_idTipoperacion`),
  ADD KEY `fk_Operacion_cliente1_idx` (`fk_idcliente`),
  ADD KEY `fk_Operacion_Usuario1_idx` (`fk_idUsuarioLogueado`);

--
-- Indices de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  ADD PRIMARY KEY (`idProveedores`,`fk_idEstados`),
  ADD UNIQUE KEY `codigoProveedor_UNIQUE` (`codigoProveedor`),
  ADD KEY `fk_Proveedores_Estados1_idx` (`fk_idEstados`);

--
-- Indices de la tabla `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`idRoles`);

--
-- Indices de la tabla `tipoperacion`
--
ALTER TABLE `tipoperacion`
  ADD PRIMARY KEY (`idTipoperacion`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`idUsuario`,`fk_idRoles`),
  ADD KEY `fk_Usuario_Roles1_idx` (`fk_idRoles`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `articulos`
--
ALTER TABLE `articulos`
  MODIFY `idArticulos` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT de la tabla `caja`
--
ALTER TABLE `caja`
  MODIFY `idCaja` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `categorias`
--
ALTER TABLE `categorias`
  MODIFY `idCategorias` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `idcliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT de la tabla `cuentacorriente`
--
ALTER TABLE `cuentacorriente`
  MODIFY `idCuentaCorriente` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `descuentocli`
--
ALTER TABLE `descuentocli`
  MODIFY `idDescuentoCli` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `detallecaja`
--
ALTER TABLE `detallecaja`
  MODIFY `idDetalleCaja` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `detallemovemientostock`
--
ALTER TABLE `detallemovemientostock`
  MODIFY `idStock` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `detalleoperacion`
--
ALTER TABLE `detalleoperacion`
  MODIFY `idDetVenta` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `estados`
--
ALTER TABLE `estados`
  MODIFY `idEstados` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `movimientostock`
--
ALTER TABLE `movimientostock`
  MODIFY `idMovimientoStock` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `operacion`
--
ALTER TABLE `operacion`
  MODIFY `idCabVenta` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  MODIFY `idProveedores` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `roles`
--
ALTER TABLE `roles`
  MODIFY `idRoles` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `tipoperacion`
--
ALTER TABLE `tipoperacion`
  MODIFY `idTipoperacion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `idUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `articulos`
--
ALTER TABLE `articulos`
  ADD CONSTRAINT `fk_Articulos_Estados1` FOREIGN KEY (`fk_idEstados`) REFERENCES `estados` (`idEstados`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Articulos_Generos1` FOREIGN KEY (`fk_idCategorias`) REFERENCES `categorias` (`idCategorias`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Articulos_Proveedores1` FOREIGN KEY (`fk_idProveedores`) REFERENCES `proveedores` (`idProveedores`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `fk_cliente_DescuentoCli1` FOREIGN KEY (`fk_idDescuentoCli`) REFERENCES `descuentocli` (`idDescuentoCli`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_cliente_Estados1` FOREIGN KEY (`fk_idEstados`) REFERENCES `estados` (`idEstados`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `cuentacorriente`
--
ALTER TABLE `cuentacorriente`
  ADD CONSTRAINT `fk_CuentaCorriente_cliente1` FOREIGN KEY (`fk_idcliente`) REFERENCES `cliente` (`idcliente`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `detallecaja`
--
ALTER TABLE `detallecaja`
  ADD CONSTRAINT `fk_DetalleCaja_Caja1` FOREIGN KEY (`fk_idCaja`) REFERENCES `caja` (`idCaja`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `detallemovemientostock`
--
ALTER TABLE `detallemovemientostock`
  ADD CONSTRAINT `fk_DetalleMovemientoStock_MovimientoStock1` FOREIGN KEY (`fk_idMovimientoStock`) REFERENCES `movimientostock` (`idMovimientoStock`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Stock_Articulos1` FOREIGN KEY (`fk_idArticulos`) REFERENCES `articulos` (`idArticulos`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `detalleoperacion`
--
ALTER TABLE `detalleoperacion`
  ADD CONSTRAINT `fk_DetVenta_Articulos1` FOREIGN KEY (`fk_idArticulos`) REFERENCES `articulos` (`idArticulos`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `operacion`
--
ALTER TABLE `operacion`
  ADD CONSTRAINT `fk_CabVenta_DetVenta1` FOREIGN KEY (`fk_idDetVenta`) REFERENCES `detalleoperacion` (`idDetVenta`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_CabVenta_Tipoperacion1` FOREIGN KEY (`fk_idTipoperacion`) REFERENCES `tipoperacion` (`idTipoperacion`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Operacion_Usuario1` FOREIGN KEY (`fk_idUsuarioLogueado`) REFERENCES `usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Operacion_cliente1` FOREIGN KEY (`fk_idcliente`) REFERENCES `cliente` (`idcliente`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `proveedores`
--
ALTER TABLE `proveedores`
  ADD CONSTRAINT `fk_Proveedores_Estados1` FOREIGN KEY (`fk_idEstados`) REFERENCES `estados` (`idEstados`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `fk_Usuario_Roles1` FOREIGN KEY (`fk_idRoles`) REFERENCES `roles` (`idRoles`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
