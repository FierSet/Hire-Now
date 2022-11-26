<?php

include('Conexion.php');

$user = $_POST['user'];

$tabla = "contratos";

$consulta = "SELECT * FROM $tabla WHERE Contratante = '$user' OR contratista = '$user';";

$lista = mysqli_query($conexion, $consulta);

$filas = mysqli_num_rows($lista);

while($row = mysqli_fetch_object($lista))
{
    $datos['CONTRATOS'][] = $row;
}

if($filas)
	echo json_encode($datos);
    
?>