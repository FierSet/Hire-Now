<?PHP
include('Conexion.php');

$ID = $_POST['ID'];
$user = $_POST['user'];
$vacantes = $_POST['vacantes'];

$tabla = "Ofertas";
		
$update = "UPDATE $tabla SET 
            vacantes = '$vacantes'
            where ID = '$ID' AND id_user = '$user';";

$agrega = mysqli_query($conexion, $update);

echo ("Oferta Actualizada");


mysqli_free_result($resultado);
mysqli_close($conexion);
	
?>