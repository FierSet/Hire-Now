<?PHP
include('Conexion.php');

$user=$_POST['user'];

$tabla = "usuarios";

$datos = array();
		
//$conexion=mysqli_connect("localhost", "nroot", "1234", "proyecto");
		
$consulta = "SELECT comprobado FROM $tabla WHERE user = '$user';";

$resultado = mysqli_query($conexion, $consulta);

$row = mysqli_fetch_object($resultado);

$datos['dato'][] = $row;

echo json_encode($datos);


mysqli_free_result($resultado);
mysqli_close($conexion);
	
?>