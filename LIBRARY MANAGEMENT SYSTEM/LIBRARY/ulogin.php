<?php
	session_start();
	include("database.php");
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
                <h3 id="heading">UserLogin Here.</h3>
                <div id="center">
					<?php
						if(isset($_POST["submit"]))
						{
							$sql = "SELECT * FROM student WHERE name='{$_POST["name"]}' AND pass='{$_POST["pass"]}'";
							//echo "<p class='success'>Logged in</p>";
							$res = $conn->query($sql);
							//print_r($res);
							if($res->num_rows>0)
							{
								//echo "Good";
								$row=$res->fetch_assoc();
								$_SESSION["id"]=$row["id"];
								$_SESSION["name"]=$row["name"];
								header("location:uhome.php");
							}
							else
							{
								echo "<p class='error'>Invalid User</p>";
							}
						}
					?>
                    <form action="ulogin.php" method="post">
                        <label>Name</label>
                        <input type="text" name="name" required>
                        <label>Password</label>
                        <input type="password" name="pass" required>
                        <button type="submit" name="submit">Login Now</button>
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