<?PHP

include('Conexion.php');
$datos = array();

$user = $_POST['Buscar'];

$tabla = "Ofertas";
		
//$conexion=mysqli_connect("localhost", "nroot", "1234", "proyecto");
if($user)
    $consulta="SELECT * FROM $tabla WHERE id_user ='$user'";
else
    $consulta="SELECT * FROM $tabla WHERE vacantes > 0;";

$resultado = mysqli_query($conexion, $consulta);

$filas = mysqli_num_rows($resultado);

while($row = mysqli_fetch_object($resultado))
{
    $datos['datos'][] = $row;
}

if($filas)
	echo json_encode($datos);

//mysqli_free_result($resultado);
//mysqli_close($conexion);
?>