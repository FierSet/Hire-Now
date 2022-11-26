<?PHP
include('Conexion.php');

$id_contrato = $_POST['id_contrato'];

$estado = $_POST['estado'];

$tabla = "contratos";
		
$update = "UPDATE $tabla SET  estado = $estado where id_contrato = $id_contrato;";

$agrega = mysqli_query($conexion, $update);

echo ("Contrato Actualizado");


mysqli_free_result($resultado);
mysqli_close($conexion);
	
?>