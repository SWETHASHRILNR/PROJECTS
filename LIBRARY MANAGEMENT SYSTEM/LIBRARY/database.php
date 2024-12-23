<?php
    $db_server = "localhost";
    $user = "root";
    $db_pass = "Karthikeyan@7";
    $db_name = "book";
    $conn = "";

    $conn = mysqli_connect($db_server, $user, $db_pass, $db_name);

/*     if(!$conn)
    {
        echo "Error";
    }
    else
    {
        echo "Database Connected";
    } */
?>