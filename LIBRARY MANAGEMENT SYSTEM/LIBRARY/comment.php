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
                <h3 id="heading">Comment</h3>
                <?php
                    if(isset($_POST["submit"]))
                    {

                        $s="INSERT INTO comment (bid,sid,comm,logs) VALUES ({$_GET["id"]},{$_SESSION["id"]},'{$_POST["mes"]}',now())";
                        $conn->query($s);
    
                    }
                    $sql="SELECT * FROM book WHERE bid=".$_GET["id"];
                    $res=$conn->query($sql);
                    if($res->num_rows>0)
                    {
                        echo "<table>";
                        $row=$res->fetch_assoc();
                        echo "
                            <tr>
                                <th>Book Name</th>
                                <td>{$row["btitle"]}</td>
                            </tr>
                            <tr>
                                <th>Keywords</th>
                                <td>{$row["keywords"]}</td>
                            </tr>
                            ";
                        echo "</table>";
                    }
                    else
                    {
                        echo "<p class='error'>No books found</p>";
                    }
                ?>
                <div id="center">
                    <form action="<?php $_SERVER["REQUEST_URI"];?>" method="post">
                        <label>Your comments</lable>
                        <textarea name="mes" required></textarea>
                        <button type="submit" name="submit">Post now</button>
                    </form>
                </div>

                <?php
                    $sql="SELECT student.name, comment.comm, comment.logs 
                    FROM comment INNER JOIN student ON comment.sid=student.id 
                    WHERE comment.bid={$_GET["id"]} 
                    ORDER BY comment.cid DESC";
                    $res=$conn->query($sql);
                    if($res->num_rows>0)
                    {
                        while($row=$res->fetch_assoc())
                        {
                            echo "<p>
                            <strong>{$row["name"]} : </strong>
                            {$row["comm"]}
                            <i>{$row["logs"]}</i>
                            </p>";
                        }
                    }
                    else
                    {
                        echo "<p class='error'>No comments yet...</p>";
                    }
                ?>
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