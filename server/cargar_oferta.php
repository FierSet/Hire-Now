<?PHP

include('Conexion.php');
$datos = array();

$id_oferta = $_POST['id_oferta'];

$tabla = "Ofertas";

		
$consulta="SELECT * FROM $tabla WHERE ID = $id_oferta;";

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