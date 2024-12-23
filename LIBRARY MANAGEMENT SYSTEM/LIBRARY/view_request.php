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
                <h3 id="heading">View Request Details</h3>
                <?php
                    $sql="SELECT student.name, request.mes, request.logs FROM student INNER JOIN request ON student.id=request.id;";
                    $res=$conn->query($sql);
                    if($res->num_rows>0)
                    {
                        //print_r($res);
                        echo "
                        <table>
                            <tr>
                                <th>S.No</th>
                                <th>Student Name</th>
                                <th>Message</th>
                                <th>Logs</th>
                            <tr>";
                            $i=0;
                            while($row=$res->fetch_assoc())
                            {
                                $i++;
                                echo "<tr>";
                                echo "<td>{$i}</td>";
                                echo "<td>{$row["name"]}</td>";
                                echo "<td>{$row["mes"]}</td>";
                                echo "<td>{$row["logs"]}</td>";
                                echo "</tr>";
                            }
                        echo "</table>";
                    }
                    else
                    {
                        echo "<p class='error'>No requests found</p>";
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
