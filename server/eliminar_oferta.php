<?php

    include('Conexion.php');

    $ID = $_POST['ID'];

    $tabla = "Ofertas";
		
    $Delete = "DELETE FROM $tabla WHERE ID = $ID;";

    $agrega = mysqli_query($conexion, $Delete);

    echo ("Oferta Borrada");

?>