<?php
    session_start();
    include "database.php";
    function countRecord($sql, $conn)
    {
        $res = $conn->query($sql);
        return $res->num_rows;
    }
    if(!isset($_SESSION["id"]))
    {
        header("location:ulogin.php");
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
                <h3 id="heading">Welcome <?php echo $_SESSION["name"];?></h3>

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
