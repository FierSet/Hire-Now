<?PHP
include('Conexion.php');

$user = $_POST['user'];
$imagen = $_POST['imagen'];
$titulo = $_POST['titulo'];
$descripcion = $_POST['descripcion'];
$informacion_g = $_POST['info_general'];
$profecion_req = $_POST['profecion_general'];
$costo = $_POST['costo'];
$vacante = $_POST['vacantes'];

$tabla = "Ofertas";
		
//$conexion=mysqli_connect("localhost", "nroot", "1234", "proyecto");
		
$inserta = "INSERT INTO $tabla VALUES ('$imagen', '$titulo',    '$descripcion',  '$user',  '$informacion_g',   '$profecion_req', NULL, $costo, $vacante);";
$agrega = mysqli_query($conexion, $inserta);

echo ("Oferta Creada");


//mysqli_free_result($resultado);
//mysqli_close($conexion);
	
?>