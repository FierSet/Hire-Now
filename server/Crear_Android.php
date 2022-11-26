<?PHP

include('Conexion.php');

$user=$_POST['user'];
$pwd=$_POST['pwd'];
		
$busca = "SELECT user FROM usuarios WHERE user = '$user';";
$busqueda = mysqli_query($conexion, $busca);

$duplicados = mysqli_num_rows($busqueda);

if(!$duplicados)
{
    $inserta = "INSERT INTO usuarios VALUES (null, '$user',     null,  null,  null,    null,     null,  '$pwd', 0, null);";  
    $agrega = mysqli_query($conexion, $inserta);
    
    $crea_tablad = "CREATE TABLE $user";
    $crea_tablad .= "_CVpres(
    CV varchar(3000),
    presupuestos varchar(3000)
    );";
    $crear = mysqli_query($conexion, $crea_tablad);
    
    $crea_rese = "CREATE TABLE $user";
    $crea_rese .= "_result (
    user varchar(50),
    id_contrato integer(100),
    comentario_resenas varchar(1000),
    resenas float(5),
    comentarios_exito varchar(1000),
    exito float(5)
    );";
    $crear_r = mysqli_query($conexion, $crea_rese);
    
    $crea_notificacion = "CREATE TABLE $user";
    $crea_notificacion .= "_notificacion (
	id_user_notificacion varchar(50),
    id_oferta varchar(50),
    imagen varchar(1000),
    titulo varchar(20),
    tipo integer(1)
    );";
    $crear_notif = mysqli_query($conexion, $crea_notificacion);
    
    

    echo json_encode("noduplicados");
    
}
else
{
    echo json_encode("duplicados");
}

//mysqli_free_result($busqueda);
//mysqli_close($conexion);
	
?>