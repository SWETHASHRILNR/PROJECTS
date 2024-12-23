<?php
    session_start();
    include "database.php";
    if(!isset($_SESSION["aid"]))
    {
        header("location:alogin.php");
    }
?>
<!doctype html>
<html lang="en">

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
                <h3 id="heading">View Comments</h3>
                <?php
                    $sql="SELECT book.btitle, student.name, comment.comm, comment.logs FROM comment INNER JOIN book ON book.bid=comment.bid INNER JOIN student ON comment.sid=student.id;
";
                    $res=$conn->query($sql);
                    if($res->num_rows>0)
                    {
                        //print_r($res);
                        echo "
                        <table>
                            <tr>
                                <th>S.No</th>
                                <th>Book</th>
                                <th>Student Name</th>
                                <th>Comment</th>
                                <th>Logs</th>
                            <tr>";
                            $i=0;
                            while($row=$res->fetch_assoc())
                            {
                                $i++;
                                echo "<tr>";
                                echo "<td>{$i}</td>";
                                echo "<td>{$row["btitle"]}</td>";
                                echo "<td>{$row["name"]}</td>";
                                echo "<td>{$row["comm"]}</td>";
                                echo "<td>{$row["logs"]}</td>";
                                echo "</tr>";
                            }
                        echo "</table>";
                    }
                    else
                    {
                        echo "<p class='error'>No comments found</p>";
                    }
                ?>
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
