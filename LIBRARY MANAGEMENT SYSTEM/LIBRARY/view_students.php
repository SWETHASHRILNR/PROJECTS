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
                <h3 id="heading">View Student Details</h3>
                <?php
                    $sql="SELECT * FROM student";
                    $res=$conn->query($sql);
                    if($res->num_rows>0)
                    {
                        //print_r($res);
                        echo "
                        <table>
                            <tr>
                                <th>S.No</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Department</th>
                            <tr>";
                            $i=0;
                            while($row=$res->fetch_assoc())
                            {
                                $i++;
                                echo "<tr>";
                                echo "<td>{$i}</td>";
                                echo "<td>{$row["name"]}</td>";
                                echo "<td>{$row["mail"]}</td>";
                                echo "<td>{$row["dep"]}</td>";
                                echo "</tr>";
                            }
                        echo "</table>";
                    }
                    else
                    {
                        echo "<p class='error'>No Student records found</p>";
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
