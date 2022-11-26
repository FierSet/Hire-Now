<?PHP

include('Conexion.php');

$user=$_POST['user'];
$pwd=$_POST['pwd'];

$tabla = "usuarios";
		
//$conexion=mysqli_connect("localhost", "nroot", "1234", "proyecto");
		
$consulta="SELECT user FROM $tabla WHERE user = '$user' AND password = '$pwd';";

$resultado = mysqli_query($conexion, $consulta);

$filas = mysqli_num_rows($resultado);

if($filas)
	echo json_encode($filas);

//mysqli_free_result($resultado);
//mysqli_close($conexion);
	
?>