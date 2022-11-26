<?php

include('Conexion.php');

$user = $_POST['user'];

$tabla = $user . "_notificacion";

$consulta = "SELECT * FROM $tabla;";

$lista = mysqli_query($conexion, $consulta);

$filas = mysqli_num_rows($lista);

while($row = mysqli_fetch_object($lista))
{
    $datos['datos'][] = $row;
}

if($filas)
	echo json_encode($datos);
    
?>