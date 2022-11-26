<?PHP
include('Conexion.php');

$ID = $_POST['ID'];
$user = $_POST['user'];
$imagen = $_POST['imagen'];
$titulo = $_POST['titulo'];
$descripcion = $_POST['descripcion'];
$informacion_g = $_POST['info_general'];
$profecion_req = $_POST['profecion_general'];
$costo = $_POST['costo'];
$vacante = $_POST['vacantes'];

$tabla = "Ofertas";
		
$update = "UPDATE $tabla SET 
            Imagen = '$imagen', Titulo = '$titulo', descripcion = '$descripcion', 
            Informacion_general = '$informacion_g', profecion_requerida = '$profecion_req', 
            pago = $costo, vacantes = $vacante
            where ID = '$ID' AND id_user = '$user';";

$agrega = mysqli_query($conexion, $update);

echo ("Oferta Actualizada");


mysqli_free_result($resultado);
mysqli_close($conexion);
	
?>