<?php
    session_start();
    include "database.php";
    if(!isset($_SESSION["aid"]))
    {
        header("location:alogin.php");
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
                <h3 id="heading">Upload New Books</h3>
                <div id="center">

                <?php
                    if(isset($_POST["submit"]))
                    {
                        $target_dir="upload/";
                        $target_file=$target_dir.basename($_FILES["efile"]["name"]);
                        if(move_uploaded_file($_FILES["efile"]["tmp_name"],$target_file))
                        {
                            //echo "good";
                            $s="INSERT INTO book (btitle, keywords, file) VALUES ('{$_POST["bname"]}', '{$_POST["keys"]}', '{$target_file}')";
                            $conn->query($s);
                            echo "<p class='success'>Book Uploaded Successfully</p>";
                        }
                        else
                        {
                            echo "<p class='error'>Error in Upload</p>";
                        }
                    }
                ?>
                    <form action="<?php echo $_SERVER["PHP_SELF"];?>" method="post" enctype="multipart/form-data">
                        <label>Book Title</label>
                        <input type="text" name="bname" required>
                        <label>Keywords</label>
                        <textarea name="keys" required></textarea>
                        <label>Upload File</label>
                        <input type="file" name="efile" required>
                        <button type="submit" name="submit">Upload Books</button>
                    </form>
                </div>
            </div>

            <div id="navi">
                <?php
                include("adminsidebar.php");
                ?>
            </div>

            <div id="footer">
                <p>Copyright &copy; 2024</p>
            </div>
        </div>
    </body>
</html>