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
   $array = array('code'=>1);   
}
else
{
    $array = array('code'=>2);    
}

echo json_encode($array);


?>
