<?php
require ('linkdb.php');

$leaderName =$_POST["leaderName"];
(int)$teamId = $_POST["teamId"];
$userName = $_POST["userName"];
$teamName = $_POST["teamName"];

$insert_invite_msg = mysql_query("INSERT INTO invite_message(leader_name,team_id,user_name,team_name)
    values('$leaderName',$teamId,'$userName','$teamName') ");

$array=array();
if($insert_invite_msg)
{
    $array = array('code' => 1 );
}
else 
{
    $array = array('code' => 2 );
}

echo json_encode($array);


?>