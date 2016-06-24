<?php
require ('linkdb.php');

(int)$taskId = $_POST["taskId"];
(int)$newSchedule = $_POST["newSchedule"];

$update_task = mysql_query("UPDATE task set schedule=$newSchedule WHERE task_id=$taskId");

$array = array();
if($update_task)
{
    $array = array('code'=>1);
}
else 
{
    $array = array('code'=>2);
}

echo json_encode($array);

?>