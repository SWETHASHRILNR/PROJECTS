<?php
    session_start();
    include "database.php";
    if(!isset($_SESSION["id"]))
    {
        header("location:ulogin.php");
    }
?>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Library Management System</title>
        <link rel="stylesheet" type="text/css" href="css/style.css">
    </head>

    <body>
        <div id="container">
            <div id="header">
                <h1>E-Library Management System</h1>
            </div>

            <div id="wrapper">
                <h3 id="heading">New Book Request</h3>
                <div id="center">

                <?php
                    if(isset($_POST["submit"]))
                    {
                        $sql="INSERT INTO request (id,mes,logs) VALUES ('{$_SESSION["id"]}','{$_POST["mes"]}',now())";
                        $conn->query($sql);
                        echo "<p class='success'>Request Sent to Admin</p>";
                    }
                ?>
                    <form action="<?php echo $_SERVER["PHP_SELF"];?>" method="post">
                        <label>Message</label>
                        <textarea required name="mes"></textarea>
                        <button type="submit" name="submit">Send message</button>
                    </form>
                </div>
            </div>

            <div id="navi">
                <?php
                include("usersidebar.php");
                ?>
            </div>

            <div id="footer">
                <p>Copyright &copy; 2024</p>
            </div>
        </div>
    </body>
</html>