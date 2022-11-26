<?PHP
include('Conexion.php');

$user=$_POST['user'];
$datos = array();
$tabla = "usuarios";
		
//$conexion=mysqli_connect("localhost", "nroot", "1234", "proyecto");
		
$consulta = "SELECT * FROM $tabla WHERE user = '$user';";

$resultado = mysqli_query($conexion, $consulta);

$filas = mysqli_num_rows($resultado);

while($row = mysqli_fetch_object($resultado))
{
    $datos['datos'][] = $row;
}

if($filas)
{
	echo json_encode($datos);
}

//mysqli_free_result($resultado);
//mysqli_close($conexion);
	
?>
	