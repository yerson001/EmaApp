<?php
$conexion = mysqli_connect('209.45.83.59','sistemas','qbJITBTz29o8Svf','demo');

if(!$conexion){
    echo "error en conexion";
}

$nombre = $_POST['nombre'];
$edad = $_POST['edad'];

$query = "INSERT INTO android(nombre,edad) VALUES('$nombre','$edad')";

if($resultado){
    echo "datos insertados";
}else{
    echo "error en la insercion";
}
mysqli_close($conexion);
?>