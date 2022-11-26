<?PHP

include('Conexion.php');

$user_oferta = $_POST['id_user_oferta'];
$user = $_POST['user'];
$id_oferta = $_POST['id_oferta'];
$imagen = $_POST['imagen'];
$titulo = $_POST['titulo'];
$tipo = $_POST['tipo'];

//$tipo = intval($tipo);

$tabla = $user_oferta . "_notificacion";
		
$notificar = "INSERT INTO $tabla VALUES ( '$user', $id_oferta, '$imagen', '$titulo', $tipo );";

$test1 = "INSERT INTO miguel_notificacion VALUES (1, 3, 0);";

$crear_notif = mysqli_query($conexion, $notificar);


echo ("Notificacion enviada");



//mysqli_free_result($resultado);
//mysqli_close($conexion);
	
?>