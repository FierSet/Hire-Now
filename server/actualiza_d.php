<?PHP

include('Conexion.php');

$user=$_POST['user'];
$pwd=$_POST['pwd'];

$nombres = $_POST['nombres'];
$apellido_p = $_POST['apellido_p'];
$apellido_m = $_POST['apellido_m'];
$rfc = $_POST['RFC'];
$imagen = $_POST['IMAGEN'];

$profecion = $_POST['profecion'];
$nuevo_pass = $_POST['nuevo_pass'];

$tabla = "usuarios";


$consulta="SELECT user FROM $tabla WHERE user = '$user' AND password = '$pwd';";
$resultado = mysqli_query($conexion, $consulta);

$filas = mysqli_num_rows($resultado);

if($filas)
{
    $actualiza = "UPDATE usuarios SET 
        imagen = '$imagen', nombres = '$nombres', apellidoP = '$apellido_p', apellidoM = '$apellido_m', RFC = '$rfc'";
    
    if($nuevo_pass)
        $actualiza .= ", password = '$nuevo_pass'";

    if($profecion)
        $actualiza .= ", profesion = '$profecion'"; 
    
    $actualiza .= " WHERE user = '$user';";

    $resultado = mysqli_query($conexion, $actualiza);
    
    
    //___________________________________
    
    $VERIFICAR = "SELECT * FROM usuarios WHERE user = '$user' AND (nombres IS NULL OR apellidoP IS NULL OR apellidoM IS NULL or RFC IS NULL or profesion IS NULL or nombres = '' OR apellidoP = '' OR apellidoM = '' or RFC = '' or profesion = '');";
    $resultado_VERIFICACION = mysqli_query($conexion, $VERIFICAR);
    $filas = mysqli_num_rows($resultado_VERIFICACION);
        
    if(!$filas)
        $verificacion = "UPDATE usuarios SET comprobado = 1 WHERE user = '$user';";
    else
        $verificacion = "UPDATE usuarios SET comprobado = 0 WHERE user = '$user';";
        
    $verifica = mysqli_query($conexion, $verificacion);    

    
    //____________________________________
    
    
    echo('actualizado');
}
else
    echo ('contraseña incorrecta');




/* */
//mysqli_free_result($resultado);
//mysqli_close($conexion);
?>