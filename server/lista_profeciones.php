<?PHP
include('Conexion.php');

$datos = array();
$tabla = "profesion";
		
$consulta = "SELECT profesion FROM $tabla;";

$resultado = mysqli_query($conexion, $consulta);

$filas = mysqli_num_rows($resultado);

while($row = mysqli_fetch_object($resultado))
{
    $datos[] = $row;
}

if($filas)
{
	echo json_encode($datos);
}

//mysqli_free_result($resultado);
//mysqli_close($conexion);
	
?>