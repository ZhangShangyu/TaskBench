<?php
require ('linkdb.php');

(int)$userId = $_POST["userId"];

$array = array();

$select_teams =  mysql_query("SELECT team_id FROM user_team WHERE user_id=$userId ");
$i = 0;
$team_ids = array(); 
while ($rs = mysql_fetch_array($select_teams,MYSQL_ASSOC))
{
   $team_ids[$i]=$rs['team_id']; 
   $i++;                    
}  
if(count($team_ids) == 0)
{
    $array = array('code'=>2);
}
else 
{
    $team_info = array();
    $i = 0;
    foreach ($team_ids as $team_id) 
    {
        $select_team_info = mysql_query("SELECT * FROM team WHERE team_id=$team_id ");
        while ($rs = mysql_fetch_array($select_team_info,MYSQL_ASSOC))
        {
            $team_info[$i]=$rs; 
            $i++;                    
        }  
   // $select_count = mysql_query("SELECT count(*) FROM user_team WHERE team_id= $team_id");
   // $row = mysql_fetch_array($select_count, MYSQL_NUM);
    //$memberCount = $row[0];
    }

    $array = array('code'=>1,'infoArray'=>$team_info);
}


echo json_encode($array);


?>