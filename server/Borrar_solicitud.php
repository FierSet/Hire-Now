<?PHP
include('Conexion.php');

$user = $_POST['user'];
$id_user_notificacion = $_POST['id_user_notificacion'];
$id_oferta = $_POST['id_oferta'];

$tabla =  $user . "_notificacion";


$Delete = "DELETE FROM $tabla 
        WHERE id_user_notificacion = '$id_user_notificacion' AND id_oferta = $id_oferta;";

$agrega = mysqli_query($conexion, $Delete);

echo ("Solicitud Borrada");

//mysqli_free_result($resultado);
//mysqli_close($conexion);
	
?>