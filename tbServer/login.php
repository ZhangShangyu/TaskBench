<?php

require ('linkdb.php');

$userName = $_POST["userName"];
$password = $_POST["password"];

mysql_query("SET NAMES 'utf8'");

$check_account = mysql_query("SELECT user_id FROM user WHERE user_name='$userName' 
            and password='$password' ");
            
$numRows = mysql_num_rows($check_account);

$array = array();

if($numRows == 1)
{  
  $row  = mysql_fetch_array($check_account, MYSQL_NUM);
  $userId = $row[0];

   $array = array('code'=>1,'userId'=>$userId);
}
else
{
    $array = array('code'=>2);    
}

echo json_encode($array);


?>
