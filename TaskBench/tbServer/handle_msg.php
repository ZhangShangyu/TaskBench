<?php
require ('linkdb.php');

(int)$userId =$_POST["userId"];
(int)$msgId =$_POST["msgId"];
(int)$teamId =$_POST["teamId"];

$array = array();
$insert_user_team = mysql_query("INSERT into user_team(user_id,team_id)
                values($userId,$teamId)");   
if($insert_user_team)
{
    $select_member_count = mysql_query("SELECT member_count from team where team_id=$teamId ");
    $row  = mysql_fetch_array($select_member_count, MYSQL_NUM);
    $member_count = $row[0];
    $member_count++;
    $update_count = mysql_query("UPDATE team set member_count=$member_count
         where team_id = $teamId");
    
    if($update_count)
    {
        $delete_msg = mysql_query("DELETE from invite_message where invite_id =$msgId");
        if($delete_msg)
        {
            $array = array('code'=>1);
        }
        else 
        {
            $array = array('code'=>2,'erro'=>'no delete');
        }
    }
    else 
    {
        $array = array('code'=>2,'erro'=>'no update','count'=>$member_count);
    }
}
else 
{
    $array = array('code'=>2);
}

echo json_encode($array);


?>