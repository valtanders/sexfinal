CREATE DATABASE  IF NOT EXISTS `sexshop` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `sexshop`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: sexshop
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.16-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `articulos`
--

DROP TABLE IF EXISTS `articulos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `articulos` (
  `idArticulos` int(11) NOT NULL AUTO_INCREMENT,
  `descripccion` varchar(100) NOT NULL,
  `costo` double(10,2) DEFAULT NULL,
  `precio` double(10,2) DEFAULT NULL,
  `fechaCompra` date DEFAULT NULL,
  `fk_idProveedores` int(11) NOT NULL,
  `fk_idCategorias` int(11) NOT NULL,
  `fk_idEstados` int(11) NOT NULL,
  PRIMARY KEY (`idArticulos`,`fk_idProveedores`,`fk_idCategorias`,`fk_idEstados`),
  UNIQUE KEY `idArticulos_UNIQUE` (`idArticulos`),
  KEY `fk_Articulos_Proveedores1_idx` (`fk_idProveedores`),
  KEY `fk_Articulos_Generos1_idx` (`fk_idCategorias`),
  KEY `fk_Articulos_Estados1_idx` (`fk_idEstados`),
  CONSTRAINT `fk_Articulos_Estados1` FOREIGN KEY (`fk_idEstados`) REFERENCES `estados` (`idEstados`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Articulos_Generos1` FOREIGN KEY (`fk_idCategorias`) REFERENCES `categorias` (`idCategorias`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Articulos_Proveedores1` FOREIGN KEY (`fk_idProveedores`) REFERENCES `proveedores` (`idProveedores`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='	';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articulos`
--

LOCK TABLES `articulos` WRITE;
/*!40000 ALTER TABLE `articulos` DISABLE KEYS */;
INSERT INTO `articulos` VALUES (5,'test pelicula',1.00,2.00,'2017-01-02',1,1,1),(6,'test articulo',1.00,2.00,'2017-01-02',1,2,1),(7,'test a eliminar',3.00,3.50,'2017-01-22',1,2,3),(8,'skjdks',2.00,30.00,'2017-01-22',1,1,3);
/*!40000 ALTER TABLE `articulos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `caja`
--

DROP TABLE IF EXISTS `caja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `caja` (
  `idCaja` int(11) NOT NULL AUTO_INCREMENT,
  `fechaapertura` datetime DEFAULT NULL,
  `fechacierre` datetime DEFAULT NULL,
  `abierta` tinyint(1) NOT NULL,
  PRIMARY KEY (`idCaja`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `caja`
--

LOCK TABLES `caja` WRITE;
/*!40000 ALTER TABLE `caja` DISABLE KEYS */;
INSERT INTO `caja` VALUES (1,'2017-01-22 00:00:00','2017-01-22 00:00:00',0),(2,'2017-01-22 00:00:00','2017-01-22 00:00:00',0);
/*!40000 ALTER TABLE `caja` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categorias`
--

DROP TABLE IF EXISTS `categorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categorias` (
  `idCategorias` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idCategorias`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias`
--

LOCK TABLES `categorias` WRITE;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` VALUES (1,'Alquiler'),(2,'Venta');
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente` (
  `idcliente` int(11) NOT NULL AUTO_INCREMENT,
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
  `fk_idEstados` int(11) NOT NULL,
  PRIMARY KEY (`idcliente`,`fk_idDescuentoCli`,`fk_idEstados`),
  UNIQUE KEY `codigoCliente_UNIQUE` (`codigoCliente`),
  KEY `fk_cliente_DescuentoCli1_idx` (`fk_idDescuentoCli`),
  KEY `fk_cliente_Estados1_idx` (`fk_idEstados`),
  CONSTRAINT `fk_cliente_DescuentoCli1` FOREIGN KEY (`fk_idDescuentoCli`) REFERENCES `descuentocli` (`idDescuentoCli`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cliente_Estados1` FOREIGN KEY (`fk_idEstados`) REFERENCES `estados` (`idEstados`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,1,'test nota','Bruno','Bollati','9 de julio','bruno@bollati.com','155739305','30122490','1983-03-16','00001',2),(2,2,'test nota','Azul','Gonzalez','9 de julio','pocha@pocha.com','156077108','28963674','1981-05-13','12a',1),(3,1,'abc','abc','abc','abc','abc','12265','189218','2016-11-20','abc',1),(4,1,'asd','asd','asd','asd','asd','asd','123','2016-11-20','asd',3),(5,1,'qwe','qwe','qweq','qwe','qwe','qwe','1234','2016-11-20','qwe',1),(6,1,'zxc','zxc','zxc','zxc','zxc','zxc','1234','2016-11-20','zxcz',1),(7,1,'jkl','jkl','jkl','jkl','jkl','jkl','123','2016-11-20','jkl',1),(8,1,'iop','iop','iop','iop','iop','iop','123','2016-11-20','iop',1),(9,1,'bnm','bnm','bnm','bnm','bnm','bnm','123','2016-11-20','bnm',1),(10,1,'cvb','cvb','cvb','cvb','cvb','cvb','456','2016-11-20','cvb',1),(11,1,'rty','rty','try','rty','rty','rty','789','2016-11-20','rty',2),(12,1,'dfg','dfg','dfg','dfg','dfg','dfg','890','2016-11-20','dfg',3);
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuentacorriente`
--

DROP TABLE IF EXISTS `cuentacorriente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cuentacorriente` (
  `idCuentaCorriente` int(11) NOT NULL AUTO_INCREMENT,
  `fk_idcliente` int(11) NOT NULL,
  `saldo` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`idCuentaCorriente`,`fk_idcliente`),
  KEY `fk_CuentaCorriente_cliente1_idx` (`fk_idcliente`),
  CONSTRAINT `fk_CuentaCorriente_cliente1` FOREIGN KEY (`fk_idcliente`) REFERENCES `cliente` (`idcliente`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuentacorriente`
--

LOCK TABLES `cuentacorriente` WRITE;
/*!40000 ALTER TABLE `cuentacorriente` DISABLE KEYS */;
/*!40000 ALTER TABLE `cuentacorriente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `descuentocli`
--

DROP TABLE IF EXISTS `descuentocli`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `descuentocli` (
  `idDescuentoCli` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(45) DEFAULT NULL,
  `porcentaje` int(11) DEFAULT NULL,
  `importe` double(10,2) DEFAULT NULL,
  PRIMARY KEY (`idDescuentoCli`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `descuentocli`
--

LOCK TABLES `descuentocli` WRITE;
/*!40000 ALTER TABLE `descuentocli` DISABLE KEYS */;
INSERT INTO `descuentocli` VALUES (1,'sin descuento',0,0.00),(2,'descuento 5%',5,0.00);
/*!40000 ALTER TABLE `descuentocli` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detallecaja`
--

DROP TABLE IF EXISTS `detallecaja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detallecaja` (
  `idDetalleCaja` int(11) NOT NULL AUTO_INCREMENT,
  `fk_idCaja` int(11) NOT NULL,
  `concepto` varchar(45) DEFAULT NULL,
  `monto` decimal(10,0) DEFAULT NULL,
  `fk_idCabVenta` int(11) NOT NULL,
  PRIMARY KEY (`idDetalleCaja`,`fk_idCaja`,`fk_idCabVenta`),
  KEY `fk_DetalleCaja_Caja1_idx` (`fk_idCaja`),
  KEY `fk_DetalleCaja_Operacion1_idx` (`fk_idCabVenta`),
  CONSTRAINT `fk_DetalleCaja_Caja1` FOREIGN KEY (`fk_idCaja`) REFERENCES `caja` (`idCaja`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_DetalleCaja_Operacion1` FOREIGN KEY (`fk_idCabVenta`) REFERENCES `operacion` (`idCabVenta`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detallecaja`
--

LOCK TABLES `detallecaja` WRITE;
/*!40000 ALTER TABLE `detallecaja` DISABLE KEYS */;
/*!40000 ALTER TABLE `detallecaja` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detallemovemientostock`
--

DROP TABLE IF EXISTS `detallemovemientostock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detallemovemientostock` (
  `idStock` int(11) NOT NULL AUTO_INCREMENT,
  `fk_idArticulos` int(11) NOT NULL,
  `fk_idMovimientoStock` int(11) NOT NULL,
  `tipomovimiento` varchar(45) DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL,
  PRIMARY KEY (`idStock`,`fk_idArticulos`,`fk_idMovimientoStock`),
  KEY `fk_Stock_Articulos1_idx` (`fk_idArticulos`),
  KEY `fk_DetalleMovemientoStock_MovimientoStock1_idx` (`fk_idMovimientoStock`),
  CONSTRAINT `fk_DetalleMovemientoStock_MovimientoStock1` FOREIGN KEY (`fk_idMovimientoStock`) REFERENCES `movimientostock` (`idMovimientoStock`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Stock_Articulos1` FOREIGN KEY (`fk_idArticulos`) REFERENCES `articulos` (`idArticulos`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detallemovemientostock`
--

LOCK TABLES `detallemovemientostock` WRITE;
/*!40000 ALTER TABLE `detallemovemientostock` DISABLE KEYS */;
/*!40000 ALTER TABLE `detallemovemientostock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalleoperacion`
--

DROP TABLE IF EXISTS `detalleoperacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detalleoperacion` (
  `idDetVenta` int(11) NOT NULL AUTO_INCREMENT,
  `fk_idArticulos` int(11) NOT NULL,
  `precio` decimal(10,0) DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL,
  PRIMARY KEY (`idDetVenta`,`fk_idArticulos`),
  KEY `fk_DetVenta_Articulos1_idx` (`fk_idArticulos`),
  CONSTRAINT `fk_DetVenta_Articulos1` FOREIGN KEY (`fk_idArticulos`) REFERENCES `articulos` (`idArticulos`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalleoperacion`
--

LOCK TABLES `detalleoperacion` WRITE;
/*!40000 ALTER TABLE `detalleoperacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `detalleoperacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estados`
--

DROP TABLE IF EXISTS `estados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estados` (
  `idEstados` int(11) NOT NULL AUTO_INCREMENT,
  `Descripcion` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idEstados`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estados`
--

LOCK TABLES `estados` WRITE;
/*!40000 ALTER TABLE `estados` DISABLE KEYS */;
INSERT INTO `estados` VALUES (1,'activo'),(2,'inactivo'),(3,'Eliminado');
/*!40000 ALTER TABLE `estados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimientostock`
--

DROP TABLE IF EXISTS `movimientostock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movimientostock` (
  `idMovimientoStock` int(11) NOT NULL AUTO_INCREMENT,
  `concepto` varchar(45) DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  PRIMARY KEY (`idMovimientoStock`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimientostock`
--

LOCK TABLES `movimientostock` WRITE;
/*!40000 ALTER TABLE `movimientostock` DISABLE KEYS */;
/*!40000 ALTER TABLE `movimientostock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operacion`
--

DROP TABLE IF EXISTS `operacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `operacion` (
  `idCabVenta` int(11) NOT NULL AUTO_INCREMENT,
  `fk_idDetVenta` int(11) NOT NULL,
  `fk_idTipoperacion` int(11) NOT NULL,
  `fk_idcliente` int(11) NOT NULL,
  `fk_idUsuarioLogueado` int(11) NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `idUsuarioVendedor` int(11) DEFAULT NULL,
  `total` decimal(10,0) NOT NULL,
  PRIMARY KEY (`idCabVenta`,`fk_idDetVenta`,`fk_idTipoperacion`,`fk_idcliente`,`fk_idUsuarioLogueado`),
  KEY `fk_CabVenta_DetVenta1_idx` (`fk_idDetVenta`),
  KEY `fk_CabVenta_Tipoperacion1_idx` (`fk_idTipoperacion`),
  KEY `fk_Operacion_cliente1_idx` (`fk_idcliente`),
  KEY `fk_Operacion_Usuario1_idx` (`fk_idUsuarioLogueado`),
  CONSTRAINT `fk_CabVenta_DetVenta1` FOREIGN KEY (`fk_idDetVenta`) REFERENCES `detalleoperacion` (`idDetVenta`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_CabVenta_Tipoperacion1` FOREIGN KEY (`fk_idTipoperacion`) REFERENCES `tipoperacion` (`idTipoperacion`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Operacion_Usuario1` FOREIGN KEY (`fk_idUsuarioLogueado`) REFERENCES `usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Operacion_cliente1` FOREIGN KEY (`fk_idcliente`) REFERENCES `cliente` (`idcliente`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operacion`
--

LOCK TABLES `operacion` WRITE;
/*!40000 ALTER TABLE `operacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `operacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proveedores`
--

DROP TABLE IF EXISTS `proveedores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proveedores` (
  `idProveedores` int(11) NOT NULL AUTO_INCREMENT,
  `razonsocial` varchar(45) DEFAULT NULL,
  `direccion` varchar(45) DEFAULT NULL,
  `telefono` varchar(45) DEFAULT NULL,
  `mail` varchar(45) DEFAULT NULL,
  `codigoProveedor` varchar(45) NOT NULL,
  `fk_idEstados` int(11) NOT NULL,
  PRIMARY KEY (`idProveedores`,`fk_idEstados`),
  UNIQUE KEY `codigoProveedor_UNIQUE` (`codigoProveedor`),
  KEY `fk_Proveedores_Estados1_idx` (`fk_idEstados`),
  CONSTRAINT `fk_Proveedores_Estados1` FOREIGN KEY (`fk_idEstados`) REFERENCES `estados` (`idEstados`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proveedores`
--

LOCK TABLES `proveedores` WRITE;
/*!40000 ALTER TABLE `proveedores` DISABLE KEYS */;
INSERT INTO `proveedores` VALUES (1,'prueba','dire test','tel test','mail test','00001',1);
/*!40000 ALTER TABLE `proveedores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `idRoles` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idRoles`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'Administrador'),(2,'Operador');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipoperacion`
--

DROP TABLE IF EXISTS `tipoperacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipoperacion` (
  `idTipoperacion` int(11) NOT NULL AUTO_INCREMENT,
  `concepto` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idTipoperacion`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipoperacion`
--

LOCK TABLES `tipoperacion` WRITE;
/*!40000 ALTER TABLE `tipoperacion` DISABLE KEYS */;
INSERT INTO `tipoperacion` VALUES (1,'Alquiler'),(2,'Venta');
/*!40000 ALTER TABLE `tipoperacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `idUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `fk_idRoles` int(11) NOT NULL,
  PRIMARY KEY (`idUsuario`,`fk_idRoles`),
  KEY `fk_Usuario_Roles1_idx` (`fk_idRoles`),
  CONSTRAINT `fk_Usuario_Roles1` FOREIGN KEY (`fk_idRoles`) REFERENCES `roles` (`idRoles`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'admin','1234',1),(2,'pruebaop','1',2),(3,'pruebams','1',1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-01-22  2:46:40