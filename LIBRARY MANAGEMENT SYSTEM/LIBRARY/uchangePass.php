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
                <h3 id="heading">Change Password</h3>
                <div id="center">

                <?php
                    if(isset($_POST["submit"]))
                    {
                        $sql="SELECT * FROM student WHERE pass='{$_POST["opass"]}' AND id='{$_SESSION["id"]}'";
                        $res=$conn->query($sql);
                        if($res->num_rows>0)
                        {
                            $s="UPDATE student SET pass='{$_POST["npass"]}' WHERE id=".$_SESSION["id"];
                            $res=$conn->query($s);
                            echo "<p class='success'>Password Changed Successfully</p>";
                        }
                        else
                        {
                            echo "<p class='error'>Invalid Password</p>";
                        }
                    }
                ?>
                    <form action="<?php echo $_SERVER["PHP_SELF"];?>" method="post">
                        <label>Old Password</label>
                        <input type="password" name="opass" required>
                        <label>New Password</label>
                        <input type="password" name="npass" required>
                        <button type="submit" name="submit">Update Password</button>
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