<?PHP
include('Conexion.php');

$user = $_POST['user'];
$id_contrato = $_POST['id_contrato'];
$a_sereñar = $_POST['a_sereñar'];
$comentario_resenas = $_POST['comentario_resenas'];
$resenas = $_POST['resenas'];
$progreso = $_POST['progreso'];

$tabla = $a_sereñar . "_result";

$buscar = "SELECT * FROM $tabla WHERE user = '$user' AND id_contrato = $id_contrato;";
$busqueda = mysqli_query($conexion, $buscar);
$duplicados = mysqli_num_rows($busqueda);

if($duplicados)
{
    $actualiza = "UPDATE $tabla SET 
    comentario_resenas = '$comentario_resenas', resenas = $resenas, exito = $progreso WHERE user = '$user' AND id_contrato = $id_contrato;";
    
    $agrega = mysqli_query($conexion, $actualiza);
}
else
{
    $inserta = "INSERT INTO $tabla VALUES ('$user', $id_contrato, '$comentario_resenas', $resenas, null, $progreso);";

    $agrega = mysqli_query($conexion, $inserta);
}		


echo ("Reseña guardada");


mysqli_free_result($resultado);
mysqli_close($conexion);
	
?>