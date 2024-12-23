<?php
    session_start();
    include "database.php";
    function countRecord($sql, $conn)
    {
        $res = $conn->query($sql);
        return $res->num_rows;
    }
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
                <h3 id="heading">Welcome Admin Home</h3>
                <div id="center">
                    <ul class="record">
                        <li>Total Students : <?php echo countRecord("SELECT * FROM student", $conn);?></li>
                        <li>Total Books    : <?php echo countRecord("SELECT * FROM book", $conn);?></li>
                        <li>Total Requests : <?php echo countRecord("SELECT * FROM request", $conn);?></li>
                        <li>Total Comments : <?php echo countRecord("SELECT * FROM comment", $conn);?></li>
                    </ul>
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
