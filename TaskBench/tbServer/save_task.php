<?php
require ('linkdb.php');

(int)$teamId = $_POST["teamId"];
$userName = $_POST["userName"];
$taskName = $_POST["taskName"];
$description = $_POST["description"];
$startDate = $_POST["startDate"];
$deadline = $_POST["deadline"];

$select_user_id = mysql_query("SELECT user_id FROM user WHERE user_name='$userName' ");
$row  = mysql_fetch_array($select_user_id, MYSQL_NUM);
$userId = $row[0];

$select_team_name = mysql_query("SELECT team_name from team where team_id = $teamId");
$row  = mysql_fetch_array($select_team_name, MYSQL_NUM);
$teamName = $row[0];

$insert_task = mysql_query("INSERT INTO task(task_name,user_id,team_id,start_date,deadline,description,
     user_name,team_name)values('$taskName',$userId,$teamId,'$startDate','$deadline','$description',
     '$userName','$teamName') ");
$array = array();
if($insert_task)
{
    $array = array('code'=>1);
}
else 
{
    $array = array('code'=>2);
}

echo json_encode($array);

?>