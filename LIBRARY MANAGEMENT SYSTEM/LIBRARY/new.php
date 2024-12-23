<?php
    include "database.php";
?>

<!DOCTYPE HTML>
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
                <h3 id="heading">New User Registration</h3>
                <div id="center">

                <?php
                    if(isset($_POST["submit"]))
                    {

                        $s="INSERT INTO student (name,pass,mail,dep) VALUES ('{$_POST["name"]}', '{$_POST["pass"]}', '{$_POST["mail"]}', '{$_POST["dep"]}')";
                        $conn->query($s);
                        echo "<p class='success'>Registered Successfully</p>";
   
                    }
                ?>
                    <form action="<?php echo $_SERVER["PHP_SELF"];?>" method="post">
                        <label>Name</label>
                        <input type="text" name="name" required>
                        <label>Password</label>
                        <input type="password" name="pass" required>
                        <label>Email</label>
                        <input type="email" name="mail" required>
                        <select name="dep" required>
                            <option value="">Select</option>
                            <option value="CSE">CSE</option>
                            <option value="ECE">ECE</option>
                            <option value="EEE">EEE</option>
                            <option value="ME">ME</option>
                            <option value="CE">CE</option>
                            <option value="I.T">I.T</option>
                            <option value="MCA">MCA</option>
                            <option value="MBA">MBA</option>
                        </select>
                        <button type="submit" name="submit">Register</button>
                    </form>
                </div>
            </div>

            <div id="navi">
                <?php
                include("sidebar.php");
                ?>
            </div>

            <div id="footer">
                <p>Copyright &copy; 2024</p>
            </div>
        </div>
    </body>
</html>