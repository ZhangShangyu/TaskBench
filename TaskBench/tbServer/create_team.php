<?php
require ('linkdb.php');

$leaderName = $_POST["leaderName"];
$teamName = $_POST["teamName"];
$description = $_POST["teamDescription"];
(int)$userId =$_POST["userId"];

$create_team = mysql_query("INSERT INTO team(team_name,leader_name,
    description,member_count) values('$teamName','$leaderName','$description',1)");

$array = array();
if($create_team)
{
    $select_team_id = mysql_query("SELECT team_id from team where team_name='$teamName' ");
    $row  = mysql_fetch_array($select_team_id, MYSQL_NUM);
    $teamId = $row[0];
    $insert_user_team = mysql_query("INSERT into user_team(user_id,team_id)
                values($userId,$teamId)");   
    if($insert_user_team)
    {
        $array = array('code'=>1);
    }
    else 
    {
         $array = array('code'=>2,'id'=>$teamId); 
    }
}
else 
{
    $array = array('code'=>2); 
}

echo json_encode($array);

?>