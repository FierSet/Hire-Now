<?PHP
include('Conexion.php');

$id_contrato = $_POST['id_contrato'];
$pago = $_POST['pago'];

$tabla = "contratos";
              
		
$update = "UPDATE $tabla SET pago = $pago WHERE id_contrato = $id_contrato;";

$agrega = mysqli_query($conexion, $update);

echo ("Contrato Actualizado");


//mysqli_free_result($resultado);
//mysqli_close($conexion);
	
?>