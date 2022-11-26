<?php
    include('Conexion.php');

    $busca = $_POST['dato'];

    $datos = array();

    $busqueda_usuarios_tabla = "SELECT * FROM usuarios WHERE user LIKE '%$busca%' OR nombres LIKE '%$busca%' OR apellidoP LIKE '%$busca%' OR apellidoM LIKE '%$busca%' OR profesion LIKE '%$busca%';";
    $busqueda_usuarios = mysqli_query($conexion, $busqueda_usuarios_tabla);
    $econtro_usuarios = mysqli_num_rows($busqueda_usuarios);

    $busqueda_ofertas_tabla = "SELECT * FROM Ofertas WHERE Titulo LIKE '%$busca%' OR profecion_requerida LIKE '%$busca%';";
    $busqueda_ofertas = mysqli_query($conexion, $busqueda_ofertas_tabla);
    $econtro_ofertas = mysqli_num_rows($busqueda_ofertas);

    while($row = mysqli_fetch_object($busqueda_usuarios))
    {
        $datos['usuarios'][] = $row;
    }

    while($row2 = mysqli_fetch_object($busqueda_ofertas))
    {
        $datos['ofertas'][] = $row2;
    }

    if($econtro_usuarios or $econtro_ofertas)
	    echo json_encode($datos);

?>