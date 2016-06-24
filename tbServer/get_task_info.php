<?php

require ('linkdb.php');

$isTeam = $_POST["isTeam"];

$task_info = array(); 
$array = array();

if($isTeam == "yes")
{
    (int)$teamId = $_POST["teamId"];
    $i = 0;
    $select_tasks =  mysql_query("SELECT * FROM task WHERE team_id=$teamId ");
    $numRows = mysql_num_rows($select_tasks);
    if($numRows == 0)
    {
        $array = array('code'=>2);
    }
    else 
    {
        while ($rs = mysql_fetch_array($select_tasks,MYSQL_ASSOC))
        {
            $task_info[$i]=$rs; 
            $i++;                    
        }  
        $array = array('code'=>1,'infoArray'=>$task_info);
    }
  
}
else if($isTeam == "no")
{
    (int)$userId = $_POST["userId"];
    $i = 0;
    $select_tasks =  mysql_query("SELECT * FROM task WHERE user_id=$userId ");
    $numRows = mysql_num_rows($select_tasks);
    if($numRows == 0)
    {
        $array = array('code'=>2);
    }
    else 
    {
        while ($rs = mysql_fetch_array($select_tasks,MYSQL_ASSOC))
        {
            $task_info[$i]=$rs; 
            $i++;                    
        }  
        $array = array('code'=>1,'infoArray'=>$task_info);
    }
   
}
else
{
     $array = array('code'=>3);
}

echo json_encode($array);


?>