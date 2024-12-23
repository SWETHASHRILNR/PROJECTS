<?php
    session_start();
    unset($_SESSION["aid"]);
    unset($_SESSION["id"]);
    session_destroy();
    header("location:index.php");
?>