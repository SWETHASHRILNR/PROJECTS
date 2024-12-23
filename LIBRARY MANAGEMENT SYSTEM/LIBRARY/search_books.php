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
                <h3 id="heading">Search Books</h3>
                <div id="center">
                    <form action="<?php echo $_SERVER["PHP_SELF"];?>" method="post">
                        <label>Enter Book Name or Keywords</label>
                        <input type="text" required name="name">
                        <button type="submit" name="submit">Search Now</button>
                    </form>
                </div>
                <?php
                    if(isset($_POST["submit"]))
                    {         
                    $sql="SELECT * FROM book WHERE btitle LIKE '%{$_POST["name"]}%' OR keywords LIKE '%{$_POST["name"]}%';";
                    $res=$conn->query($sql);
                    if($res->num_rows>0)
                    {
                        //print_r($res);
                        echo "
                        <table>
                            <tr>
                                <th>S.No</th>
                                <th>Name</th>
                                <th>Keywords</th>
                                <th>View Book</th>
                                <th>Comment</th>
                            <tr>";
                            $i=0;
                            while($row=$res->fetch_assoc())
                            {
                                $i++;
                                echo "<tr>";
                                echo "<td>{$i}</td>";
                                echo "<td>{$row["btitle"]}</td>";
                                echo "<td>{$row["keywords"]}</td>";
                                echo "<td><a href='{$row["file"]}' target='_blank'>View</a></td>";
                                echo "<td><a href='comment.php?id={$row["bid"]}'>Go</a></td>";
                                echo "</tr>";
                            }
                        echo "</table>";
                    }
                    else
                    {
                        echo "<p class='error'>No books found</p>";
                    }
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