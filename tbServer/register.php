<?php

require ('linkdb.php');

$userName = $_POST["userName"];
$password = $_POST["password"];

mysql_query("SET NAMES 'utf8'");

$check_name = mysql_query("SELECT user_id FROM user WHERE user_name='$userName' limit 1");
$numRows = mysql_num_rows($check_name);

$array = array();
if($numRows == 0)
{
     $ok_insert = "INSERT INTO user(user_name,password)
              values('$userName','$password')";
              
     $insert_result = mysql_query($ok_insert);
     if($insert_result)
     {
        $array = array('code'=>1);
     }
     else 
     {
         $array = array('code'=>3);
     }
}
else
{
    $array=array('code'=>2);    
}

echo json_encode($array);



?>
