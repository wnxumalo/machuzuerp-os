<!DOCTYPE html>
<html>
    <?php
header("Content-Type:application/json");

	$uuid = $_GET['uuid'];
	$organisation = $_GET['organisation'];
	$telephone = $_GET['telephone'];
	$cellphone = $_GET['cellphone'];
	$emailaddress = $_GET['emailaddress'];
	$coordinates = $_GET['coordinates'];	
	$syncdate=  date("Y/m/d"); 	
  ?>
  <head>
  <meta http-equiv=refresh content=7; url=<?php echo "<a href=https://www.machuzu.com/rest/ecobuzz/accountapi.php?uuid='$uuid'&organisation='$organisation'&telephone='$telephone'&cellphone='$cellphone'&emailaddress='$emailaddress'&coordinates='$coordinates'"?> />
	
  </head>
  <body>
    <p>Please follow <?php echo "<a href=https://www.machuzu.com/rest/ecobuzz/accountapi.php?uuid='$uuid'&organisation='$organisation'&telephone='$telephone'&cellphone='$cellphone'&emailaddress='$emailaddress'&coordinates='$coordinates'"?>>this link</a>.</p>
  </body>
</html>