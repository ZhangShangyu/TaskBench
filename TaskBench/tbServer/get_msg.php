<?php
require ('linkdb.php');

$userName = $_POST["userName"];

$select_msg_info=mysql_query("SELECT * from invite_message where user_name = '$userName' ");
$array = array();
$numRows = mysql_num_rows($select_msg_info);
if($numRows == 0)
{
    $array = array('code'=>2);
}
else 
{
    $msg_info = array();
    $i = 0;
    while ($rs=mysql_fetch_array($select_msg_info,MYSQL_ASSOC)) 
    {
        $msg_info[$i]=$rs;
        $i++;
    }
    $array = array('code'=>1,'infoArray'=>$msg_info);

}

echo json_encode($array);




?>