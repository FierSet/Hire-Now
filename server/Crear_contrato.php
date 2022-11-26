<?PHP
include('Conexion.php');

$id_oferta = $_POST['id_oferta'];
$Contratante = $_POST['Contratante'];
$contratista = $_POST['contratista'];
$pago = $_POST['pago'];

$tabla = "contratos";
		
$inserta = "INSERT INTO $tabla VALUES (null, $id_oferta, '$Contratante', '$contratista',  $pago, 1, 0, 0);";
$agrega = mysqli_query($conexion, $inserta);

echo ("Solicitud Aceptada, contrato creado");

//mysqli_free_result($resultado);
//mysqli_close($conexion);
	
?>