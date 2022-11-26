<?PHP
include('Conexion.php');
$datos = array();

$user = $_POST['user'];

$tabla = $user . "_result";

$consulta = "SELECT * FROM $tabla;";

$resultado = mysqli_query($conexion, $consulta);

$filas = mysqli_num_rows($resultado);

while($row = mysqli_fetch_object($resultado))
{
    $datos['datos'][] = $row;
}

//echo ($consulta );
echo json_encode($datos);


//mysqli_free_result($resultado);
//mysqli_close($conexion);
	
?>